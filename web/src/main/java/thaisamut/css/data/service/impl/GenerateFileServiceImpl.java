package thaisamut.css.data.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import thaisamut.css.data.service.GenerateFileService;
import thaisamut.css.data.service.impl.GenerateFileServiceImpl.CSVHeaderMapping.CSVMap;
import thaisamut.css.otp.OTPHandler;
import thaisamut.csvwriter.util.CSVHeader;
import thaisamut.csvwriter.util.CSVRow;
import thaisamut.csvwriter.util.CSVValue;
import thaisamut.csvwriter.util.CSVWriterStream;
import thaisamut.cybertron.ejbweb.model.CssAddressSendEntity;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.omailwsclient.component.OMailWsClientFactory;
import thaisamut.osgi.targetbundles.omail.v1.Email;

public class GenerateFileServiceImpl implements GenerateFileService {
	private static final Logger LOG = LoggerFactory.getLogger(GenerateFileServiceImpl.class);

	@Autowired
	@Qualifier("localAddressFilePath")
	private String FILE_PATH;
	@Autowired
	@Qualifier("monitorPath")
	private String MONITOR_PATH;
	@Autowired
	private CssMemberService cssMemberService;
	@Autowired
	private OMailWsClientFactory oMailWsClientFactory;
	

	@Autowired
	private OTPHandler optHandler;

	private final String SEPERATOR = ",";
	private final String ENCODING = "UTF-8";
	private final Character DELIMITER = '|';
	private final String RECONCILE_EMAIL_SENDER = "cssproject@ocean.co.th";
	private final List<String> RECONCILE_EMAIL_RECIPIENTS = Arrays.asList(new String[]{
							"itsupport@ocean.co.th" 
//							,"shode666@gmail.com" 
//							,"sarintip.kh@ocean.co.th"
							});
	private final String RECONCILE_EMAIL__SUBJECT_PREFIX = "แจ้งผลการ update ข้อมูลที่อยู่ที่ติดต่อจากระบบ CSS ไปยังระบบ DC";

	private final CSVHeaderMapping config = new CSVHeaderMapping();

	{
		config.add("pol_no", "policyNo", String.class);
		config.add("pol_type", "polType", String.class);
		config.add("addr", "addr", String.class);
		config.add("sdt_desc", "sdtDesc", String.class);
		config.add("dt_desc", "dtDesc", String.class);
		config.add("dt_code", "dtCode", String.class);
		config.add("pv_desc", "pvDesc", String.class);
		config.add("pv_code", "pvCode", String.class);
		config.add("zipcode", "zipCode", String.class);
		config.add("mobile1", "mobile1", String.class);
		config.add("mobile2", "mobile2", String.class);
		config.add("phone1", "phone1", String.class);
		config.add("ext1", "ext1", String.class);
		config.add("phone2", "phone2", String.class);
		config.add("ext2", "ext2", String.class);
		config.add("email", "email", String.class);
		config.add("ref_id", "id", BigDecimal.class);
		config.add("status", "N", String.class);
		config.add("updated_date", "updateDate", Date.class);
		config.add("updated_by", "updateBy", String.class);
	}

	public boolean generateAddressTempFileOldArchive() {
		LOG.info("START [GenerateFileServiceImpl.generateAddressTempFile] Generate CSS_ADDRESS_TEMP Files");
		boolean result = true;
		try {
			List<CssAddressTempEntity> list = cssMemberService.queryCssAddressTempEntityNotSendStatus();
			if (list != null && !list.isEmpty()) {
				String generateDate = new DateTime().toString("yyyyMMdd", Locale.US);
				File directory = new File(FILE_PATH, generateDate);
				// create directory if it's not found.
				if (!directory.exists()) {
					directory.mkdirs();
				}
				if (LOG.isDebugEnabled()) {
					LOG.debug("CssAddressTemp with File path {}", directory.getAbsolutePath());
				}
				List<String> fileBody;
				for (CssAddressTempEntity bean : list) {
					try {
						writeFile(fileTemplate(bean), new File(directory, bean.getPolicyNo()));

						// update status Pending
						bean.setSendStatus("P");
						bean.setUpdateBy("E-Service");
						bean.setUpdateDate(new Date());
						cssMemberService.updateCssAddressTempEntity(bean);
					} catch (Exception ex) {
						LOG.error("ERROR [GenerateFileServiceImpl.generateAddressTempFile] Generate CSS_ADDRESS_TEMP Files", ex);
					}
				}

			} else {
				LOG.info("CssAddressTemp with SEND_STATUS = 'N' not found.. (no new recoreds found)");
			}
		} catch (Exception ex) {
			LOG.error("ERROR [GenerateFileServiceImpl.generateAddressTempFile] Generate CSS_ADDRESS_TEMP Files", ex);
			result = false;
		}
		LOG.info("FINISH [GenerateFileServiceImpl.generateAddressTempFile] Generate CSS_ADDRESS_TEMP Files");
		return result;
	}
	@Override
	public boolean generateAddressTempFile() {
		LOG.info("START [GenerateFileServiceImpl.generateAddressTempFile] Generate CSS_ADDRESS_TEMP Files");
		boolean result = true;
		try {
			List<CssAddressTempEntity> list = cssMemberService.queryCssAddressTempEntityNotSendStatus();
			if (list != null && !list.isEmpty()) {
				String generateDate = new DateTime().toString("yyyyMMdd", Locale.US);
				File directory = new File(FILE_PATH);
				// create directory if it's not found.
				if (!directory.exists()) {
					directory.mkdirs();
				}
				List<CSVHeader> headers = createHeader();
				for (CssAddressTempEntity bean : list) {
					try {
						writeFile(headers, bean, directory, generateDate);
						bean.setSendStatus("P");
						bean.setUpdateBy("E-Service");
						bean.setUpdateDate(new Date());
						cssMemberService.updateCssAddressTempEntity(bean);
					} catch (Exception ex) {
						LOG.error("ERROR [GenerateFileServiceImpl.generateAddressTempFile] Generate CSS_ADDRESS_TEMP Files", ex);
					}
				}

			} else {
				LOG.info("CssAddressTemp with SEND_STATUS = 'N' not found.. (no new recoreds found)");
			}
		} catch (Exception ex) {
			LOG.error("ERROR [GenerateFileServiceImpl.generateAddressTempFile] Generate CSS_ADDRESS_TEMP Files", ex);
			result = false;
		}
		LOG.info("FINISH [GenerateFileServiceImpl.generateAddressTempFile] Generate CSS_ADDRESS_TEMP Files");
		return result;
	}
	@Override
	public boolean readAddressSend() {
		LOG.info("START [GenerateFileServiceImpl.readAddressSend] Read CSS_ADDRESS_SEND Files");
		try {
			List<CssAddressSendEntity> cssAddressSendList = cssMemberService.queryCssAddressSendEntityNotSendStatus();
			List<CssAddressSendEntity> failList = new ArrayList<CssAddressSendEntity>();
			int successCount = 0, failCount = 0;
			if (cssAddressSendList != null && !cssAddressSendList.isEmpty()) {
				String reconcileStatus;
				CssAddressTempEntity cssAddressTemp;
				for (CssAddressSendEntity cssAddressSend : cssAddressSendList) {
					try {
						cssAddressTemp = null;
						reconcileStatus = cssAddressSend.getSendStatus() == null || "F".equals(cssAddressSend.getSendStatus()) ? "F" : "S";
						if (cssAddressSend.getRefId() != null) {
							cssAddressTemp = cssMemberService.queryCssAddressTempEntityByKey(cssAddressSend.getRefId());
						} else {
							throw new Exception(String.format("CSS_ADDRESS_SEND ไม่มีข้อมูล ref_id (%d)",cssAddressSend.getId()));
						}
						if (cssAddressTemp != null) {
							// css_address_temp ต้องไม่เป็น F หรอ S 
							if(!("F".equals(cssAddressTemp.getSendStatus())||"S".equals(cssAddressTemp.getSendStatus()))){
								cssAddressTemp.setSendStatus(reconcileStatus);
								cssAddressTemp.setUpdateBy("E-Service");
								cssAddressTemp.setUpdateDate(new Date());
								cssMemberService.updateCssAddressTempEntity(cssAddressTemp);
								if (cssAddressTemp.getEmail() != null)
								cssAddressSend.setEmail(cssAddressTemp.getEmail());
								if ("F".equals(reconcileStatus)) {
									throw new Exception("สถานะการแก้ไข(CSS_ADDRESS_SEND.STATUS)เป็น F(fail) หรือไม่มีข้อมูล (NULL)");
								}	
								successCount++;
							}
						} else {
							throw new Exception(String.format("ไม่พบข้อมูล CSS_ADDRESS_TEMP.ID = (%d)", cssAddressSend.getRefId()));
						}
					} catch (Exception ex) {
						cssAddressSend.setToken(ex.getMessage());
						failList.add(cssAddressSend);
						failCount++;
					}
				}
			}
			try {
				createEmailBody(successCount, failCount, failList);
			} catch (Exception ex) {
				LOG.error("Send email reconcile error ", ex);
			}

		} catch (Exception ex) {
			LOG.error("ERROR [GenerateFileServiceImpl.readAddressSend] Read CSS_ADDRESS_SEND", ex);
			return false;
		}finally{
			//ฝาก process เคลีย otp หมดอายุ
			optHandler.clearExprired();
		}
		
		return true;
	}
	private void createEmailBody(int successCount, int failCount, List<CssAddressSendEntity> failList) throws Exception {
		Email m = new Email();
		String subject = String.format("%s ประจำวันที่ %s", RECONCILE_EMAIL__SUBJECT_PREFIX, dateToStringTH(new Date()));
		StringBuilder body = new StringBuilder();
		body.append("<p>เรียนทุกท่าน</p><p>").append(subject).append(" ดังนี้<p>");
		body.append("<p>จำนวนไฟล์ทั้งหมด : ").append(successCount + failCount).append("</p>");
		body.append("<p>จำนวนไฟล์ที่ดำเนินการสำเร็จ : ").append(successCount).append("</p>");
		body.append("<p>จำนวนไฟล์ที่ดำเนินการไม่สำเร็จ : ").append(failCount).append("</p>");
		if (failCount > 0 && failList != null && !failList.isEmpty()) {
			body.append("<p>ทั้งนี้ ผลการดำเนินการไม่สำเร็จ สามารถดูรายละเอียดเพิ่มเติม ได้ตามข้อมูลด้านล่าง</p>"
					+ "<table border=\"1\" width=\"100%\" style=\"font-size:1em;\" cellspacing=\"0\" cellpadding=\"0\"><tr style=\"background-color:#CCC;white-space:nowrap\"><th style=\"padding: 1px 5px;\">เลขที่กรมธรรม์</th><th style=\"padding: 1px 5px;\">ที่อยู่</th><th  style=\"padding: 1px 5px;\">จังหวัด</th><th style=\"padding: 1px 5px;\">อำเภอ/เขต</th>"
					+ "<th style=\"padding: 1px 5px;\">ตำบล/แขวง</th><th style=\"padding: 1px 5px;\">รหัสไปรษณีย์</th><th  style=\"padding: 1px 5px;\">โทรศัพท์มือถือ1</th><th  style=\"padding: 1px 5px;\">โทรศัพท์มือถือ2</th><th  style=\"padding: 1px 5px;\">อีเมล</th><th  style=\"padding: 1px 5px;\">หมายเหตุ</th></tr>");
			for (CssAddressSendEntity bean : failList) {
				body.append("<tr><td  style=\"padding: 1px 5px;\">").append(bean.getPolicyNo()).append("</td><td  style=\"padding: 1px 5px;\">").append(bean.getAddr()).append("</td><td  style=\"padding: 1px 5px;\">(").append(bean.getPvCode()).append(") ").append(bean.getPvDesc()).append("</td><td  style=\"padding: 1px 5px;\">(").append(bean.getDtCode()).append(") ")
						.append(bean.getDtDesc()).append("</td><td  style=\"padding: 1px 5px;\">").append(bean.getSdtDesc()).append("</td><td  style=\"padding: 1px 5px;\">").append(bean.getZipCode()).append("</td><td  style=\"padding: 1px 5px;\">").append(bean.getMobile1()).append("</td><td  style=\"padding: 1px 5px;\">").append(bean.getMobile2()).append("</td><td  style=\"padding: 1px 5px;\">")
						.append(StringUtils.defaultString(bean.getEmail())).append("</td><td  style=\"padding: 1px 5px;\">").append(bean.getToken()).append("</td></tr>");

			}
			body.append("</table>");
		}

		m.setFrom(RECONCILE_EMAIL_SENDER);
		m.getTo().addAll(RECONCILE_EMAIL_RECIPIENTS);
		m.setIsHtmlMessage(true);
		m.setSubject(String.format("[CSS] : %s %s", failCount > 0 ? "[Fail]" : "[Success]", subject));
		m.setBody(body.toString());
		oMailWsClientFactory.getoMailWsService().sendEmail(m);
	}
	private List<CSVHeader> createHeader() {
		List<CSVHeader> heads = new ArrayList<CSVHeader>();

		if (config != null && !config.isEmpty()) {
			for (CSVMap m : config.getCollection()) {
				heads.add(new CSVHeader(m.colName, m.type));
			}
		}
		return heads;
	}
	private void writeFile(List<CSVHeader> headers, CssAddressTempEntity bean, File directory, String sfDate) throws Exception {
		File file = new File(directory, String.format("cybertron_%s_%s_%s.csv", bean.getPolicyNo(), bean.getPolType(), sfDate));
		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			try (
			// autoClose block
			BufferedWriter w = new BufferedWriter(fw);
					CSVWriterStream writer = new CSVWriterStream(w, headers);

			) {
				CSVRow row = new CSVRow();
				for (CSVHeader h : headers) {
					CSVMap maping = config.find(h.getColumnName());
					if (maping.type == BigDecimal.class) {
						row.add(h.getColumnName(), new CSVValue(BigDecimal.valueOf((long) maping.invoke(bean)), maping.type));
					} else {
						row.add(h.getColumnName(), new CSVValue(maping.invoke(bean), maping.type));
					}
				}
				writer.writeRow(row);
			}
		} catch (IOException ex) {

		} finally {
			if (fw != null)
				fw.close();
		}
	}

	private List<String> fileTemplate(CssAddressTempEntity bean) {
		List<String> list = new ArrayList<String>();
		list.add(StringUtils.defaultString(bean.getPolicyNo()));
		list.add(StringUtils.defaultString(bean.getPolType()));
		list.add(StringUtils.defaultString(bean.getAddr()));
		list.add(StringUtils.defaultString(bean.getSdtDesc()));
		list.add(StringUtils.defaultString(bean.getDtDesc()));
		list.add(StringUtils.defaultString(bean.getDtCode()));
		list.add(StringUtils.defaultString(bean.getPvDesc()));
		list.add(StringUtils.defaultString(bean.getPvCode()));
		list.add(StringUtils.defaultString(bean.getZipCode()));
		list.add(StringUtils.defaultString(bean.getMobile1()));
		list.add(StringUtils.defaultString(bean.getMobile2()));
		list.add(StringUtils.defaultString(bean.getPhone1()));
		list.add(StringUtils.defaultString(bean.getExt1()));
		list.add(StringUtils.defaultString(bean.getPhone2()));
		list.add(StringUtils.defaultString(bean.getExt2()));
		list.add(StringUtils.defaultString(bean.getEmail()));
		list.add(Long.toString(bean.getId()));
		list.add("N");
		list.add(dateToString(bean.getUpdateDate()));
		list.add(StringUtils.defaultString(bean.getUpdateBy()));
		return list;
	}
	private void writeFile(List<String> list, File file) throws Exception {
		FileWriterWithEncoding writer = new FileWriterWithEncoding(file, Charset.forName(ENCODING).newEncoder());
		CSVFormat format = CSVFormat.RFC4180.withDelimiter(DELIMITER);

		try (CSVPrinter printer = new CSVPrinter(writer, format)) {
			printer.printRecord(list);
		} catch (Exception e) {
			throw e;
		}
	}
	private String dateToString(Date date) {
		try {
			if (date != null){
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				return sd.format(date);
			}
		} catch (Exception ex) {
			// ignored
		}
		return "";
	}

	class CSVHeaderMapping {
		Map<String, CSVMap> map = new HashMap<>();
		void add(String colName, String beanField, Class type) {
			map.put(colName, new CSVMap(colName, beanField, type));
		}
		public Collection<CSVHeaderMapping.CSVMap> getCollection() {

			return map.values();
		}
		CSVMap find(String colName) {
			return map.get(colName);
		}
		public boolean isEmpty() {
			return map == null || map.isEmpty();
		}
		class CSVMap {

			private String colName;
			private Object beanField;
			private Class type;

			CSVMap(String colName, Object beanField, Class type) {
				this.colName = colName;
				this.beanField = beanField;
				this.type = type;
			}

			public Object invoke(Object bean) {
				try {
					if (beanField instanceof String) {
						return removeNewline(PropertyUtils.getProperty(bean, (String) this.beanField));
					} else {
						return this.beanField;
					}
				} catch (Exception ignored) {
					return beanField;
				}
			}
		}
	}
	private static final String aSpace = "";
	private static final String newLine = "\n";
	private Object removeNewline(Object property) {
		try{
			if(property instanceof String){
				return ((String)property).replace(newLine,aSpace);
			}
		}catch(Exception ex){
		}
		return property;
	}
	private String dateToStringTH(Date date) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
			return sf.format(date);
		} catch (Exception ex) {
		}
		return StringUtils.EMPTY;
	}
}
