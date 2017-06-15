package thaisamut.nbs.css.document.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.exception.CssNotifyLimitException;
import thaisamut.css.model.CssUser;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.osgi.targetbundles.dmsws.search.v1.DmsDocument;
import thaisamut.util.CssConvertUtils;

public class DocumentAction extends CssJsonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7844467332247938541L;

	private static final Logger LOG = LoggerFactory.getLogger(DocumentAction.class);

	@Autowired
	DataWarehouseService dataWarehouseService;
	@Autowired
	CssMemberService cssMemberService;
	@Autowired
	CssMasterDataService cssMasterDataService;

	private InputStream fileInputStream;

	private String fileName;
	private long fileLength;

	private String policyNo;
	private String taxYear;
	private String errorReason;
	private String email;
	private Boolean update;
	private String docType;

	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public String certifyDownload() throws Exception {

		Map<String, PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
		errorReason = StringUtils.EMPTY;
		PolicyBean policy = policies.get(policyNo);
		if (policy != null&&StringUtils.isNotBlank(taxYear)) {		
			int txYear = Integer.parseInt(taxYear);
			if (txYear<2559||!isInAllowYear(taxYear)) {
				errorReason = String.format("ไม่มีข้อมูลหนังสือรับรองการชำระเบี้ยปี %s", taxYear);
				return "fail";
			}
		
			
			DmsDocument document = dataWarehouseService.findCertDocument(policyNo, taxYear);
			if (document != null) {
				fileInputStream = new ByteArrayInputStream(document.getContent());
				fileName = String.format("%s", URLEncoder.encode(document.getFileName(), "UTF-8")).toUpperCase();
				fileLength = document.getContent().length;
			} else {
				errorReason = "ไม่มีข้อมูลเอกสารที่ต้องการดาวน์โหลด";
				return "fail";
			}
		} else {
			errorReason = "ไม่มีข้อมูลเอกสารที่ต้องการดาวน์โหลด";
			return "fail";
		}

		return SUCCESS;
	}

	public String certifyEmail() throws Exception {

		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				CssUser user = getSessionUser();
				CssMemberEntity member = cssMemberService.findMemberByUserName(getLoginName());
				Map<String, PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
				int txYear = Integer.parseInt(taxYear);
				if (StringUtils.isBlank(member.getEmail()) && cssMasterDataService.isExistEmail(email, user.getUsername())) {
					data.put("SUCCESS", false);
					data.put("error", String.format("ไม่สามารถส่งอีเมลได้เนื่องจากอีเมล %s ถูกใช้งานไปแล้วโดยบุคคลอื่น", email));
				} else if (txYear<2559||!isInAllowYear(taxYear)) {
					data.put("SUCCESS", false);
					data.put("error", String.format("ไม่มีข้อมูลหนังสือรับรองการชำระเบี้ยปี %s", taxYear));
				} else {

					PolicyBean policy = policies.get(policyNo);
					if (policy != null) {
						DmsDocument document = dataWarehouseService.findCertDocument(policyNo, taxYear);
						if(StringUtils.isNotEmpty(member.getEmail())){
							email = member.getEmail();
						}
						if (document != null && document.getContent() != null) {
							String subject = String.format("หนังสือรับรองการชำระเบี้ยประกันชีวิต กรมธรรม์ประกันภัยเลขที่ %s", policyNo);
							try {
								sendLimitEmailWithAttachFile(getSessionUser(),DEFAULT_EMAIL_SENDER_NAME, email, subject, createCertEmailBody(String.format("%s %s", policy.getFirstName(), policy.getLastName()), policyNo, taxYear), document);
								updateEmail(user, email);
								data.put("SUCCESS", true);
								data.put("message", String.format("ส่งหนังสือรับรองการชำระเบี้ยประกันชีวิต กรมธรรม์เลขที่ %s ไปยังอีเมล %s", policyNo, email));
							} catch(CssNotifyLimitException ex){
								LOG.error(ex.getMessage(), ex);
								data.put("SUCCESS", false);
								data.put("error", String.format("ไม่สามารถส่งอีเมลไปยัง %s ได้ เนื่องจากเกินจำนวนครั้งที่บริษัทกำหนด ", email));
							} catch (Exception ex) {
								LOG.error(ex.getMessage(), ex);
								data.put("SUCCESS", false);
								data.put("error", String.format("ไม่สามารถส่งอีเมลไปยัง %s", email));

							}
						} else {
							data.put("SUCCESS", false);
							data.put("error", "ไม่มีข้อมูลกรมธรรม์ที่ต้องการดาวน์โหลด");
						}
					} else {
						data.put("SUCCESS", false);
						data.put("error", "ไม่มีข้อมูลกรมธรรม์ที่ต้องการดาวน์โหลด");
					}
					 }
					result.setData(data);
				}
		}).run();
	}

	public String notifyDownload() throws Exception {

		Map<String, PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);

		PolicyBean policy = policies.get(policyNo);
		errorReason = StringUtils.EMPTY;
		if (policy != null) {
			String dueDateStr = null;
			if ("P".equals(policy.getPolicyType())) {
				dueDateStr = policy.getLapseDate();
			} else {
				dueDateStr = policy.getNextPaidDate();
			}
			if (StringUtils.isNotBlank(dueDateStr)) {
				Date dueDate = CssConvertUtils.toDateFromStringDateThai(dueDateStr);
				DmsDocument document = dataWarehouseService.findNoticeDocument(policyNo, dueDate);
				if (document != null) {
					fileInputStream = new ByteArrayInputStream(document.getContent());
					fileName = String.format("%s", URLEncoder.encode(document.getFileName(), "UTF-8")).toUpperCase();
					fileLength = document.getContent().length;
				} else {
					errorReason = "ไม่มีข้อมูลเอกสารที่ต้องการดาวน์โหลด";
					return "fail";
				}
			} else {
				errorReason = "ไม่มีข้อมูลเอกสารที่ต้องการดาวน์โหลด";
				return "fail";
			}
		} else {
			errorReason = "ไม่มีข้อมูลเอกสารที่ต้องการดาวน์โหลด";
			return "fail";
		}

		return SUCCESS;
	}

	public String notifyEmail() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				CssUser user = getSessionUser();
				CssMemberEntity member = cssMemberService.findMemberByUserName(getLoginName());
				Map<String, PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
				// if(StringUtils.isBlank(member.getEmail())&&cssMasterDataService.isExistEmail(email,user.getUsername())){
				// data.put("SUCCESS", false);
				// data.put("error",String.format("ไม่สามารถส่งอีเมลได้เนื่องจากอีเมล
				// %s ถูกใช้งานไปแล้วโดยบุคคลอื่น",email));
				// }else{
				PolicyBean policy = policies.get(policyNo);
				if (policy != null) {
					String dueDateStr = null;
					if ("P".equals(policy.getPolicyType())) {
						dueDateStr = policy.getLapseDate();
					} else {
						dueDateStr = policy.getNextPaidDate();
					}
					if (StringUtils.isNotBlank(dueDateStr)) {
						Date dueDate = CssConvertUtils.toDateFromStringDateThai(dueDateStr);
						if (dueDate != null) {
							DmsDocument document = dataWarehouseService.findNoticeDocument(policyNo, dueDate);
							if(StringUtils.isNotEmpty(member.getEmail())){
								email = member.getEmail();
							}
							if (document != null && document.getContent() != null) {
								try {
									sendLimitEmailWithAttachFile(getSessionUser(),DEFAULT_EMAIL_SENDER_NAME, email, String.format("ใบแจ้งเตือนการชำระเบี้ยประกันชีวิต กรมธรรม์ประกันภัยเลขที่ %s", policyNo),
											createNoticEmailBody(String.format("%s %s", user.getFirstName(), user.getLastName()), policyNo, dueDateStr), document);

									updateEmail(user, email);
									data.put("SUCCESS", true);
									data.put("message", String.format("ใบแจ้งเตือนการชำระเบี้ยประกันชีวิต กรมธรรม์ประกันภัยเลขที่ %s ไปยังอีเมล %s เรียบร้อย", policyNo, email));
								}catch(CssNotifyLimitException ex){
									LOG.error(ex.getMessage(), ex);
									data.put("SUCCESS", false);
									data.put("error", String.format("ไม่สามารถส่งอีเมลไปยัง %s ได้ เนื่องจากเกินจำนวนครั้งที่บริษัทกำหนด ", email));
								} catch (Exception ex) {
									LOG.error(ex.getMessage(), ex);
									data.put("SUCCESS", false);
									data.put("error", String.format("ไม่สามารถส่งอีเมลไปยัง %s", email));

								}
							} else {
								data.put("SUCCESS", false);
								data.put("error", "ไม่มีข้อมูลกรมธรรม์ที่ต้องการดาวน์โหลด");
							}
						} else {
							data.put("SUCCESS", false);
							data.put("error", "ไม่มีข้อมูลกรมธรรม์ที่ต้องการดาวน์โหลด");
						}
					} else {
						data.put("SUCCESS", false);
						data.put("error", "ไม่มีข้อมูลกรมธรรม์ที่ต้องการดาวน์โหลด");
					}
				} else {
					data.put("SUCCESS", false);
					data.put("error", "ไม่มีข้อมูลกรมธรรม์ที่ต้องการดาวน์โหลด");
				}
				// }
				result.setData(data);
			}
		}).run();
	}
	public String checkAvailable() throws Exception {
		return (new JsonAction.Processor() {

			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				CssUser user = getSessionUser();
				CssMemberEntity member = cssMemberService.findMemberByUserName(getLoginName());
				Map<String, PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);

				PolicyBean policy = policies.get(policyNo);
				data.put("SUCCESS", false);
				if (policy != null) {
					DmsDocument document = null;
					if("notify".equals(docType)){
						String dueDateStr = null;
						if ("P".equals(policy.getPolicyType())) {
							dueDateStr = policy.getLapseDate();
						} else {
							dueDateStr = policy.getNextPaidDate();
						}
						if (StringUtils.isNotBlank(dueDateStr)) {
							Date dueDate = CssConvertUtils.toDateFromStringDateThai(dueDateStr);
							if (dueDate != null) {
								document = dataWarehouseService.findNoticeDocument(policyNo, dueDate);
							}
						}
					}else{

						document = dataWarehouseService.findCertDocument(policyNo, taxYear);
					}
					if (document != null && document.getContent() != null) {
						data.put("SUCCESS", true);
					}
					
				}
				result.setData(data);
			}
		}).run();
	}

	private boolean isInAllowYear(String taxYear) {
		Calendar cal = Calendar.getInstance(LOCAL_TH),
				check = Calendar.getInstance(LOCAL_TH);
		check.set(Calendar.DAY_OF_MONTH, 15);
		check.set(Calendar.MONTH,Calendar.JANUARY);
		check.set(Calendar.HOUR_OF_DAY,0);
		check.set(Calendar.MINUTE,0);
		check.set(Calendar.SECOND,0);
		int year = cal.get(Calendar.YEAR);
		
		if(check.before(cal)){
			year--;
		}
		
		return String.format("%d,%d",year--,year).indexOf(taxYear)!=-1;
	}
	private void updateEmail(CssUser user, String email) {
		if (user != null && StringUtils.isNotBlank(email)) {
			CssMemberEntity member = cssMemberService.findMemberByUserName(getLoginName());
			if (member != null && !email.equals(member.getEmail())) {
				member.setEmail(email);
				member.setUpdateBy(getLoginName());
				member.setUpdateDate(new Date());
				cssMemberService.updateMember(member);

				CssUser cssUser = (CssUser) request.getSession().getAttribute(CssUser.SESSION_CSS_USER);
				if (cssUser != null) {
					cssUser.setEmail(email);
				}
			}

		}
	}
	private String createNoticEmailBody(String fullname, String policyNo, String nextPaid) {
		return String.format("<p>เรียน คุณ%1$s</p>" + "<br/>"
				+ "<p style=\"text-indent:20px;\"> บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอจัดส่งใบแจ้งเตือนชำระเบี้ยประกันชีวิต ของกรมธรรม์ประกันภัยเลขที่ %2$s งวดการชำระที่ %3$s  ในรูปแบบ PDF โดยท่านสามารถตรวจสอบรายละเอียดได้ โดยดาวน์โหลดเอกสารตามไฟล์แนบ</p>"
				+ "<p style=\"text-indent:20px;\">ทั้งนี้ ท่านสามารถพิมพ์ใบแจ้งเตือนการชำระเบี้ยประกันชีวิต ตามไฟล์แนบที่มาพร้อมกับอีเมลฉบับนี้  นำไปชำระเงินตามช่องทางต่างๆ ได้ตามปกติ</p>" + "<br/>"
				+ "<p style=\"text-indent:20px;\">สำหรับเอกสารในรูปแบบ PDF ในการใช้งานเครื่องของท่านต้องมีโปรแกรม Adobe Reader เวอร์ชั่น 7.0 ขึ้นไป โดยท่านสามารถดาวน์โหลดโปรแกรมนี้ได้ โดยไม่เสียค่าใช้จ่ายที่ <a href=\"http://www.adobe.com/products/reader\">http://www.adobe.com/products/reader</a></p>"
				+ "<br/>" + "<p>หากท่านต้องการสอบถามข้อมูลเพิ่มเติม หรือมีข้อสงสัยใดๆ กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์  โทร: 0-2207-8888 ทุกวันจันทร์ - วันศุกร์ เวลา 8.00 น. - 18.00 น. และวันเสาร์ เวลา 8.00 – 17.00 น. อีเมล: info@ocean.co.th</p>" + "<br/>"
				+ "<p>ขอแสดงความนับถือ</p>" + "<p>บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน)</p>" + "<p>เว็บไซต์  www.ocean.co.th </p>" + "<p>เฟสบุ๊ค   www.facebook.com/oceanlifepage</p>" + "<p>หมายเหตุ</p>"
				+ "<p>- อีเมลฉบับนี้เป็นการแจ้งการจัดส่งใบแจ้งเตือนการชำระเบี้ยประกันชีวิต กรุณาอย่าตอบกลับอีเมลฉบับนี้</p>" + "<p>- หากท่านไม่ได้ทำรายการตามรายละเอียดข้างต้น หรือหากพบว่ามีข้อมูลหรือสิ่งผิดปกติใดๆที่ไม่ถูกต้อง โปรดติดต่อบริษัทฯ</p>"
				+ "<p>- บริษัทฯ ไม่มีนโยบายในการติดต่อ ท่านไม่ว่าผ่านช่องทางใดๆ เพื่อสอบถามหรือขอข้อมูลเฉพาะ เช่น บัญชีผู้ใช้งาน(User ID) และ/หรือ รหัสผ่าน(Password) สำหรับการใช้บริการ OceanLife iService หากพบบุคคลใดกระทำการดังกล่าว  โปรดแจ้งกับทางบริษัทฯ และให้ข้อมูลเพื่อให้เจ้าหน้าที่ของทางบริษัทฯ ทำการตรวจสอบ เพื่อความปลอดภัยของท่าน</p>",
				fullname, policyNo, CssConvertUtils.toStringMMYY(CssConvertUtils.toDateFromStringDateThai(nextPaid)));

	}

	private String createCertEmailBody(String fullname, String policyNo, String year) {
		return String.format("<p>เรียน คุณ%1$s</p>" + "<br/>"
				+ "<p style=\"text-indent:20px;\">บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอจัดส่งหนังสือรับรองการชำระเบี้ยประกันชีวิต ของกรมธรรม์ประกันภัยเลขที่ %2$s  ระหว่างปี %3$s  ในรูปแบบ PDF โดยท่านสามารถตรวจสอบรายละเอียดได้ โดยดาวน์โหลดเอกสารตามไฟล์แนบ</p>"
				+ "<p style=\"text-indent:20px;\">ทั้งนี้ ท่านสามารถพิมพ์หนังสือรับรองการชำระเบี้ยประกันชีวิต   ตามไฟล์แนบที่มาพร้อมกับอีเมลฉบับนี้ นำไปเป็นหลักฐานในการยื่นลดหย่อนภาษีได้</p>" + "<br/>"
				+ "<p style=\"text-indent:20px;\">สำหรับเอกสารในรูปแบบ PDF ในการใช้งานเครื่องของท่านต้องมีโปรแกรม Adobe Reader เวอร์ชั่น 7.0 ขึ้นไป โดยท่านสามารถดาวน์โหลดโปรแกรมนี้ได้ โดยไม่เสียค่าใช้จ่ายที่ <a href=\"http://www.adobe.com/products/reader\">http://www.adobe.com/products/reader</a></p>"
				+ "<br/>" + "<p>หากท่านต้องการสอบถามข้อมูลเพิ่มเติม หรือมีข้อสงสัยใดๆ กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์  โทร: 0-2207-8888 ทุกวันจันทร์ - วันศุกร์ เวลา 8.00 น. - 18.00 น. และวันเสาร์ เวลา 8.00 – 17.00 น. อีเมล: info@ocean.co.th</p>" + "<br/>"
				+ "<p>ขอแสดงความนับถือ</p>" + "<p>บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน)</p>" + "<p>เว็บไซต์  www.ocean.co.th </p>" + "<p>เฟสบุ๊ค   www.facebook.com/oceanlifepage</p>" + "<p>หมายเหตุ</p>"
				+ "<p>- อีเมลฉบับนี้เป็นการแจ้งการจัดส่งหนังสือรับรองการชำระเบี้ยประกันชีวิต กรุณาอย่าตอบกลับอีเมลฉบับนี้</p>" + "<p>- หากท่านไม่ได้ทำรายการตามรายละเอียดข้างต้น หรือหากพบว่ามีข้อมูลหรือสิ่งผิดปกติใดๆที่ไม่ถูกต้อง โปรดติดต่อบริษัทฯ</p>"
				+ "<p>- บริษัทฯ ไม่มีนโยบายในการติดต่อ ท่านไม่ว่าผ่านช่องทางใดๆ เพื่อสอบถามหรือขอข้อมูลเฉพาะ เช่น บัญชีผู้ใช้งาน(User ID) และ/หรือ รหัสผ่าน(Password) สำหรับการใช้บริการ OceanLife iService หากพบบุคคลใดกระทำการดังกล่าว  โปรดแจ้งกับทางบริษัทฯ และให้ข้อมูลเพื่อให้เจ้าหน้าที่ของทางบริษัทฯ ทำการตรวจสอบ เพื่อความปลอดภัยของท่าน</p>",
				fullname, policyNo, year);
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String index() throws Exception {
		return SUCCESS;
	}
	public String eform() throws Exception {
		return SUCCESS;
	}
	public String bookCertificate() throws Exception {
		return SUCCESS;
	}
	public String bookCertificateSuccess() throws Exception {
		return SUCCESS;
	}
	public String bookCertificateReceive() throws Exception {
		return SUCCESS;
	}
	public String getTaxYear() {
		return taxYear;
	}
	public void setTaxYear(String taxYear) {
		this.taxYear = taxYear;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getUpdate() {
		return update;
	}
	public void setUpdate(Boolean update) {
		this.update = update;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileLength() {
		return fileLength;
	}
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}

}
