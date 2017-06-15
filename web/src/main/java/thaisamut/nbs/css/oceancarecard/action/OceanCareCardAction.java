package thaisamut.nbs.css.oceancarecard.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.model.CssUser;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.remote.CssMasterService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.osgi.targetbundles.dmsws.search.v1.DmsDocument;
import thaisamut.pantheon.jasper.JasperRender;
import thaisamut.pantheon.jasper.ParamsEnum.StreamType;

public class OceanCareCardAction extends CssJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7160043186916899561L;
	private static final Logger LOG = LoggerFactory.getLogger(OceanCareCardAction.class);
	
	@Autowired private CssMasterService cssMasterService;

	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public Parameter parameter = new Parameter();
	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	@Autowired private DataWarehouseService dwhService;
	
	public String index() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanCareCardAction : index");
		}
		if(sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null){
			HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
			
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th","TH"));
			
			CssUser user = getSessionUser();
			sessionAttributes.put("occFullname", user.getFirstName()+" "+user.getLastName());
			sessionAttributes.put("occDate", sf.format(new Date()));
		}
		return super.index();
	}
	
	public String checkOceanCareCard() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (LOG.isDebugEnabled()) {
					LOG.debug("~On OceanCareCardAction : checkOceanCareCard");
				}
				Map<String, Object> data = new HashMap<String, Object>();
				
				if(sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null){
					HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
					
					boolean status = authenOCC(policies);
					data.put("status", status);
				}
				result.setData(data);
			}
		}).run();
	}
	
	public boolean authenOCC(Map<String, PolicyBean> policies){
		boolean result = false;
		//1.ตรวจสอบกรมธรรม์แต่ละกรมธรรม์
		for (Entry<String, PolicyBean> entry : policies.entrySet()) {
			PolicyBean policyBean = entry.getValue();
			if(StringUtils.isNotBlank(policyBean.getPolicyType()) && policyBean.getPolicyType().equals("O")){
				//ถ้า policy_type='O' และ สถานะกรมธรรม์ (policy_status) คือ I,E,R,W,F
				List<String> statusList = Arrays.asList("I", "E", "R", "W", "F");
				boolean isContain = statusList.contains(policyBean.getPolicyStatus());
				if(isContain){
					//TODO 2.ตรวจสอบสัญญาเพิ่มเติมของแต่ละกรมธรรม์
					result = true;
					break;
				}
			}else if(StringUtils.isNotBlank(policyBean.getPolicyType()) && (policyBean.getPolicyType().equals("I")||policyBean.getPolicyType().equals("G"))){
				//ถ้า policy_type= 'I' หรือ 'G' และ สถานะกรมธรรม์ (policy_status) คือ 0,1,2,3,4,5,6
				List<String> statusList = Arrays.asList("0", "1", "2", "3", "4", "5", "6");
				boolean isContain = statusList.contains(policyBean.getPolicyStatus());
				if(isContain){
					result = true;
					break;
				}
			}else if(StringUtils.isNotBlank(policyBean.getPolicyType()) && policyBean.getPolicyType().equals("P")){
				//ถ้า policy_type= 'P' และ สถานะกรมธรรม์ (policy_status) คือ I
				List<String> statusList = Arrays.asList("I");
				boolean isContain = statusList.contains(policyBean.getPolicyStatus());
				if(isContain){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	public String report() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On OceanCareCardAction : report");
		}
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th","TH"));
        HttpServletResponse response = ServletActionContext.getResponse();
		JasperRender jasperRender = new JasperRender();
		Map<String, Object> rParams = new HashMap<String, Object>();
		CssUser user = getSessionUser();
		rParams.put("cardName", user.getFirstName()+" "+user.getLastName());
		rParams.put("cardDate", "วันที่ออกบัตร " + sf.format(new Date()));
		jasperRender.setParams(rParams);
		InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/ocean-care-card.jrxml");
		JasperReport jpReport = JasperCompileManager.compileReport(inp);
		JasperPrint jpPrint = JasperFillManager.fillReport(jpReport, rParams);

		int pageIndex = 0;
        BufferedImage pageImage = new BufferedImage(jpPrint.getPageWidth() + 1, jpPrint.getPageHeight() + 1, BufferedImage.TYPE_INT_RGB);
        JRGraphics2DExporter exporter = new JRGraphics2DExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jpPrint);
        exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, pageImage.getGraphics());
        exporter.setParameter(JRExporterParameter.PAGE_INDEX, new Integer(pageIndex));
        exporter.exportReport();
        
		response.setContentType("image/jpeg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(pageImage, "jpeg", baos);
        
        inputStream = new ByteArrayInputStream(baos.toByteArray());
        
        return "jpeg";
	}
	
	public String sendEmail() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (LOG.isDebugEnabled()) {
					LOG.debug("~On OceanCareCardAction : sendEmail");
				}
				
				
				Map<String, Object> data = new HashMap<String, Object>();
				try{
					SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th","TH"));
			        HttpServletResponse response = ServletActionContext.getResponse();
					JasperRender jasperRender = new JasperRender();
					Map<String, Object> rParams = new HashMap<String, Object>();
					CssUser user = getSessionUser();
					rParams.put("cardName", user.getFirstName()+" "+user.getLastName());
					rParams.put("cardDate", "วันที่ออกบัตร " + sf.format(new Date()));
					jasperRender.setParams(rParams);
					InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/ocean-care-card.jrxml");
					JasperReport jpReport = JasperCompileManager.compileReport(inp);
					JasperPrint jpPrint = JasperFillManager.fillReport(jpReport, rParams);
	
					int pageIndex = 0;
			        BufferedImage pageImage = new BufferedImage(jpPrint.getPageWidth() + 1, jpPrint.getPageHeight() + 1, BufferedImage.TYPE_INT_RGB);
			        JRGraphics2DExporter exporter = new JRGraphics2DExporter();
			        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jpPrint);
			        exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, pageImage.getGraphics());
			        exporter.setParameter(JRExporterParameter.PAGE_INDEX, new Integer(pageIndex));
			        exporter.exportReport();
			        
					response.setContentType("image/jpeg");
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        ImageIO.write(pageImage, "jpeg", baos);
			        
			        inputStream = new ByteArrayInputStream(baos.toByteArray());
			        
			        String subject = createEmailSubject();
			        String body = createEmailBody("คุณ"+user.getFirstName()+" "+user.getLastName());
			        
//			        String email = user.getEmail();
			        String email = parameter.email;
			        
					DmsDocument reportDmsDocument = new DmsDocument();
					reportDmsDocument.setFileName("ocean-care-card.jpeg");
					reportDmsDocument.setMimeType("image/jpeg");
					reportDmsDocument.setContent(IOUtils.toByteArray(inputStream));
			        sendEmailWithAttachFile(DEFAULT_EMAIL_SENDER_NAME, email, subject, body, reportDmsDocument);
			        data.put("result", true);
				}catch(Exception e){
					data.put("result", false);
					e.printStackTrace();
				}
				result.setData(data);
			}
		}).run();
	}
	
	public String createEmailSubject(){
		String subject = "[CSS]: นำส่งบัตรประกันสุขภาพ Ocean Care Card ของบริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน)";
		return subject;
	}
	
	public String createEmailBody(String customerName){
		StringBuilder sb = new StringBuilder(1000);
		sb.append("<b>เรียน ").append(customerName).append("</b>");
		sb.append("<br>");
		sb.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอนำส่งบัตรประกันสุขภาพ Ocean Care Card ของท่านตามไฟล์แนบทั้งนี้ได้นำส่งรายละเอียดเกี่ยวกับบัตรประกันสุขภาพ OCEAN CARE CARD ดังนี้ </p>");
		sb.append("<p><b>ขั้นตอนในการเข้ารับบริการในสถานพยาบาลที่เข้าร่วมโครงการ</b></p>");
		sb.append("<p>· แสดงบัตรประกันสุขภาพ (OCEAN CARE CARD) พร้อมบัตรประจำตัวประชาชนหรือบัตรอื่นที่ทางราชการออกให้ (กรณีเป็นเด็กต้องแสดงบัตรประจำตัวนักเรียนที่มีรูปถ่ายรวบคู่กับบัตรประจำตัวประชาชนของบิดาหรือมารดา) ถ้ามีการเปลี่ยนแปลงชื่อ-สกุล กรุณาแสดงหลักฐานการเปลี่ยนแปลงชื่อ-สกุลให้สถานพยาบาลที่เข้าร่วมโครงการทราบด้วย พร้อมรับรองสำเนาถูกต้อง</p>");
		sb.append("<p>· ต้องแจ้งให้สถานพยาบาลทราบทันทีที่เข้ารับการรักษา เพื่อที่สถานพยาบาลจะได้ประสานงานกับบริษัทฯ ในการตรวจสอบรายละเอียดถึงสิทธิประโยชน์ของท่าน</p>");
		sb.append("<p>· กรอกแบบแจ้งเรียกร้องค่ารักษาพยาบาล โดยรับได้ที่สถานพยาบาลที่เข้ารับการรักษา</p>");
		sb.append("<p>· ลงลายมือชื่อของท่านในใบสรุปค่าใช้จ่ายการรักษาพยาบาล (ตามลายมือชื่อในใบคำขอเอาประกันภัย)</p>");
		sb.append("<p><b>เงื่อนไขการใช้บัตรประกันสุขภาพ OCEAN CARE CARD</b></p>");
		sb.append("<p>· การใช้บัตรจะต้องอยู่ภายใต้ข้อกำหนดของบริษัทฯ เท่านั้น</p>");
		sb.append("<p>· กรณีเปลี่ยนสถานพยาบาลที่เข้ารักษา ต้องแจ้งให้บริษัทฯ ทราบทุกครั้ง</p>");
		sb.append("<p>· บัตรประกันสุขภาพ (OCEAN CARE CARD) เป็นของบริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ซึ่งบริษัทฯ ขอสงวนสิทธิ์ในการยกเลิกหรือเรียกคืนการใช้บัตรได้ทุกเวลา</p>");
		sb.append("<p>· บัตรนี้เป็นสิทธิพิเศษโดยเฉพาะของผู้เอาประกันภัยของบริษัทฯ จะโอนให้ผู้อื่นใช้ไม่ได้</p>");
		sb.append("<p>· กรณีที่มีการละเมิดข้อกำหนดการใช้บัตรประกันสุขภาพ (OCEAN CARE CARD) หรือเงื่อนไขอื่นใด ผู้เอาประกันภัยซึ่งเป็นเจ้าของบัตรนี้จะต้องรับผิดชอบต่อผลทั้งปวงที่ผู้อื่นได้ก่อขึ้นจากการใช้บัตรนี้</p>");
		sb.append("<p>· กรณีที่บริษัทฯ ได้ชำระค่ารักษาพยาบาลให้กับสถานพยาบาลไปแล้วหากปรากฏภายหลังว่า การรักษาพยาบาลดังกล่าวอยู่ในข้อยกเว้นหรือไม่อยู่ในเงื่อนไขกรมธรรม์หรือกรณีที่ผู้เอาประกันภัยไม่มีสิทธิ์ได้รับค่าทดแทนค่ารักษาพยาบาลดังกล่าวบริษัทฯ ขอสงวนสิทธิ์ที่จะเรียกคืนที่ชำระไปแล้วจากผู้เอาประกันภัย</p>");
		sb.append("<p>· บัตรประกันสุขภาพ (OCEAN CARE CARD) สามารถใช้สิทธิ์ได้เฉพาะ สถานพยาบาลที่เข้าร่วมโครงการและรับการรักษาเป็นสถานพยาบาลแห่งแรกกรณีที่รักษาในสถานพยาบาลที่ไม่ได้เข้าร่วมโครงการ หรือรับการรักษาจากสถานพยาบาลอื่นมาก่อนแล้ว ท่านสามารถส่งเรื่องเรียกร้องสินไหมทดแทนค่ารักษาพยาบาลได้ โดยส่งเอกสารประกอบการเรียกร้องมายังบริษัทฯ โดยตรง</p>");
		sb.append("<p>· บัตรประกันสุขภาพ (OCEAN CARE CARD) สามารถใช้ได้กับสัญญาประกันภัย และ/หรือสัญญาเพิ่มเติมที่คุ้มครองค่ารักษาพยาบาลที่มีผลบังคับ</p>");
		sb.append("<p>· บัตรนี้สามารถใช้แทนบัตรประกันสุขภาพเดิมที่ท่านถืออยู่แล้วได้ทันที</p>");
		sb.append("<p>· หมายเหตุ บัตรนี้สามารถใช้ได้จนกว่าความคุ้มครองตามสัญญาประกันภัย และ/หรือสัญญาเพิ่มเติมที่ท่านซื้อจะสิ้นสุดลง</p>");
		sb.append("<p><b>กรณีที่ไม่ได้เข้ารับบริการในสถานพยาบาลที่เข้าร่วมโครงการ</b></p>");
		sb.append("<p>ผู้เอาประกันกันภัยจะต้องสำรองจ่ายค่ารักษาพยาบาลกับสถานพยาบาลไปก่อน จากนั้นให้ยื่นเรื่องเรียกร้องค่ารักษาพยาบาลต่อบริษัทฯ โดยตรงพร้อมต้นฉบับเอกสารหลักฐาน ดังต่อไปนี้</p>");
		sb.append("<p>1. ใบเสร็จรับเงิน</p>");
		sb.append("<p>- กรณีที่ท่านต้องการเบิกจากสวัสดิการอื่นร่วมด้วย ให้แจ้งบริษัทฯ ทราบล่วงหน้า</p>");
		sb.append("<p>- กรณีเบิกส่วนเกินจากสวสัดิการอื่น ให้สถาบันที่ให้ความคุ้มครองรับรองการจ่ายสวัสดิการที่ได้รับแล้วบนใบเสร็จรับเงิน</p>");
		sb.append("<p>2. ใบรับรองแพทย์ของบริษัทฯ ตามแบบฟอร์มที่บริษัทฯ กำหนด และต้องระบุสาเหตุการเกิดอุบัติเหตุ และการรักษาพยาบาลอย่างชัดเจน</p>");
		sb.append("<p>3. แบบเรียกร้องสินไหมค่าทดแทนที่แนบมาพร้อมกรมธรรม์กรอกโดยผู้เอาประกันภัย</p>");
		sb.append("<p>ขอแสดงความนับถือ</p>");
		sb.append("<p>บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน)</p>");
		sb.append("<p>เว็บไซต์ www.ocean.co.th</p>");
		sb.append("<p>เฟสบุ๊ค www.facebook.com/oceanlifepage</p>");
		sb.append("<p>---------------------------------------------------------------------------------------------------------------------------------------------------</p>");
		sb.append("<p><b>หมายเหตุ</b></p>");
		sb.append("<p>- อีเมลฉบับนี้เป็นการแจ้งข้อมูลโดยอัตโนมัติ เพื่อยืนยันการแก้ไขข้อมูลการติดต่อในกรมธรรม์ของท่าน ในบริการ OceanLife iService ที่ได้ดำเนินการเรียบร้อยแล้ว กรุณาอย่าตอบกลับอีเมลฉบับนี้</p>");
		sb.append("<p>- หากท่านไม่ได้ทำรายการตามรายละเอียดข้างต้น หรือหากพบว่ามีข้อมูลหรือสิ่งผิดปกติใดๆที่ไม่ถูกต้อง โปรดติดต่อบริษัทฯ</p>");
		sb.append("<p>- บริษัทฯ ไม่มีนโยบายในการติดต่อ ท่านไม่ว่าผ่านช่องทางใดๆ เพื่อสอบถามหรือขอข้อมูลเฉพาะ เช่น บัญชีผู้ใช้งาน (User ID) และ/หรือ รหัสผ่าน (Password) สำหรับการใช้บริการ OceanLife iService หากพบบุคคลใดกระทำการดังกล่าว โปรดแจ้งกับทางบริษัทฯ และให้ข้อมูลเพื่อให้เจ้าหน้าที่ของทางบริษัทฯ ทำการตรวจสอบ เพื่อความปลอดภัยของท่าน</p>");
		return sb.toString();
	}
	
	public String checkEmail(){
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (LOG.isDebugEnabled()) {
					LOG.debug("~On OceanCareCardAction : checkEmail");
				}
				Map<String, Object> data = new HashMap<String, Object>();
				CssUser user = getSessionUser();
				PolicyBean lastestPolicy = null;
				
				if(sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null){
					HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
					List<PolicyBean> policyList = new ArrayList<PolicyBean>(policies.values());
					int f = 0;
					String policy = null;
					for (PolicyBean policyBean : policyList) {
						if(f == 0){
							lastestPolicy = policyBean;
							policy = lastestPolicy.getPolicyNo();
							break;
						}
						f++;
					}
					
					boolean emailStatus = false;
					String email = null;
					if(StringUtils.isNotBlank(user.getEmail())){
						emailStatus = true;
						email = user.getEmail();
					}else{
						//ตรวจสอบ Email ล่าสุดที่มีการแก้ไขจาก CSS_ADDRESS_TEMP หากพบรายการให้เลือก Email กรมธรรม์มีวันที่ทำรายการล่าสุด (CSS_ADDRESS_TEMP.update_date)
						//ไปเปรียบเทียบกับการทำข้อมูล Email จาก Data Warehouse DB หากกรมธรรม์ใดล่าสุดให้ link URL ไปเปิดที่กรมธรรม์นั้น
						CssAddressTempEntity cssAddressTemp = cssMasterService.queryCssAddressTempByIdNo(user.getCardNo());
						if(cssAddressTemp != null){
							Calendar c = Calendar.getInstance();
							c.setTime(new Date());
							c.add(Calendar.DATE, -1);
							Date dwh = c.getTime();
							Date cssTemp = cssAddressTemp.getUpdateDate();
							if(cssTemp.compareTo(dwh) >= 0){
								policy = cssAddressTemp.getPolicyNo();
								if(StringUtils.isNotBlank(cssAddressTemp.getEmail())){
									emailStatus = true;
									email = cssAddressTemp.getEmail();
								}
							}
						}
					}

					data.put("email", email);
					data.put("emailStatus", emailStatus);
					data.put("policyNo", policy);
				}
				
				result.setData(data);
			}
		}).run();
	}
	
	public class Parameter {
		private String email;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}
}
