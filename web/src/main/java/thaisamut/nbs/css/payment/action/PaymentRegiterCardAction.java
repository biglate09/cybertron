package thaisamut.nbs.css.payment.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.dwh.service.pojo.DistrictBean;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.dwh.service.pojo.ProvinceBean;
import thaisamut.css.dwh.service.pojo.SubDistrictBean;
import thaisamut.css.eform.service.EformService;
import thaisamut.css.model.CssUser;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.eform.ws.PolicyType;
import thaisamut.eform.ws.RegisterPaymentCardBean;
import thaisamut.eform.ws.SystemAuditLog;
import thaisamut.cybertron.ejbweb.model.BranchesEntity;
import thaisamut.cybertron.ejbweb.model.CssPaymentCardTempEntity;
import thaisamut.cybertron.ejbweb.remote.BranchesService;
import thaisamut.cybertron.ejbweb.remote.CssPaymentCardTempService;
import thaisamut.util.CssConvertUtils;

public class PaymentRegiterCardAction  extends CssJsonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7495630730664278837L;
	private static final Logger LOG = LoggerFactory.getLogger(PaymentRegiterCardAction.class);
	@Autowired private CssMasterDataService cssMasterDataService;
	@Autowired private BranchesService branchesService;
	@Autowired private CssPaymentCardTempService cssPaymentCardTempService;
	@Autowired private EformService eformService;
	//email config
	private static final String[] RECIPIENTS = new String[]{ "Premium_card@ocean.co.th"};
	private static final String[] CC = new String[]{"wisan.ya@ocean.co.th"};
	private static final String[] BCC = new String[]{};
	
	private Parameter param = new Parameter();
	private String policyNo;
	
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String paymentpaymentcard() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On PaymentRegiterCardAction : paymentpaymentcard");
		}
		Map<String,PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
		
		
			LOG.debug("policyNo====>>><<<========="+policyNo);

			PolicyBean policy = policies.get(policyNo);
			
			Gson g = new Gson();
			List<ProvinceBean> provinceBeanList = cssMasterDataService.getActiveProvince();
			requestAttributes.put("province", g.toJson(provinceBeanList));
			
			Map<String, List<DistrictBean>> districtBeanList = cssMasterDataService.getActiveDistrict();
			requestAttributes.put("district", g.toJson(districtBeanList));
			
			Map<String, List<SubDistrictBean>> subdistrictBeanList = cssMasterDataService.getActiveSubDistrict();
			LOG.debug("~>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+branchesService.findbranches());
			List<BranchesEntity> rs =branchesService.findbranches();	
			
			requestAttributes.put("subdistrict", g.toJson(subdistrictBeanList));
			requestAttributes.put("branches", g.toJson(rs));
			requestAttributes.put("parampolicyNo", policyNo);
			
			
			
			requestAttributes.put("policy", policy);
			
		return super.index();
	}
	
	public String registerpaymentcardsuccess() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On PaymentRegiterCardAction : registerpaymentcardsuccess");
		}
		LOG.debug("policyNo====>>>"+param.policyNo);
			Map<String,PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
			PolicyBean policy = policies.get(param.policyNo);
			CssUser user = getSessionUser();
			String errorMessage = authorizePaymentCard(policy.getPolicyNo());
			if(StringUtils.isBlank(errorMessage)){
				String requestNo = eformService.insertEformRequestPaymentCard(createRequestPaymentCardBean(policy,param));
				
				CssPaymentCardTempEntity ct = createBeanFromParameter(policy,param);
				
				ct.setRequestNo(requestNo);
				cssPaymentCardTempService.insertCssPaymentCardTemp(ct);
				
				sendEmail(user,ct);
			}else {
				setErrorMessage(errorMessage);
				requestAttributes.put("policy", policy);
				return "cancel";
			}
		return super.index();
	
	}

	private RegisterPaymentCardBean createRequestPaymentCardBean(PolicyBean policy, Parameter param) {
		RegisterPaymentCardBean bean = new RegisterPaymentCardBean();
		String loginName = getLoginName();
		PolicyType policyType = null;
		switch(policy.getPolicyType()){
			case "I" : policyType = PolicyType.I;break;
			case "G" : policyType = PolicyType.G;break;
			case "O" : policyType = PolicyType.O;break;
			case "P" : policyType = PolicyType.P;break;
		}
		String branchName = null;
		if(StringUtils.isNotBlank(policy.getBranchNo())){
			try{
				BranchesEntity branch = branchesService.queryBranchNameByBranchCode(policy.getBranchNo());
				if(branch!=null){
					branchName = branch.getBrnName();
				}
			} catch (Exception e) {
				LOG.warn(e.getMessage(),e);
			}
		}
		
		bean.setCreateBy(loginName);
		bean.setCreateDate(CssConvertUtils.getXMLGregorianCalendarfromDate(new Date()));
		bean.setCreateSystem("CSS");
		bean.setPolicyNo(policy.getPolicyNo());
		bean.setPolicyType(policy.getPolicyType());
		bean.setBranchNo(policy.getBranchNo());
		bean.setBranchName(branchName);
		bean.setAgentCode7(policy.getAgentCode());
		bean.setPlanCode(policy.getPrdCode());
		bean.setPlanName(policy.getPrdName());
		bean.setTitle(policy.getTitleDesc());
		bean.setFirstName(policy.getFirstName());
		bean.setLastName(policy.getLastName());
		
		if("O".equals(param.getAddressType())){
			bean.setSendAddressNo(param.getAddressLine1());
			bean.setSendSubDistrict(param.getSubDistDesc());
			bean.setSendDistrict(param.getDisDesc());
			bean.setSendProvince(param.getPovDesc());
			bean.setSendZipCode(param.getPostcode());
		}else{
			bean.setSendAddressNo(policy.getAddressLine1());
			bean.setSendSubDistrict(policy.getSubdistrictName());
			bean.setSendDistrict(policy.getDistrictName());
			bean.setSendProvince(policy.getProvinceName());
			bean.setSendZipCode(policy.getPostcode());
		}
		bean.setSendBranchName(param.getBranch());	
		bean.setTelNo(param.getTelno());
		bean.setMobile(param.getMobile());
		bean.setPaymentChannel(param.getHowto());
		bean.setSendType(param.getAddressType());
		bean.setSendStatus("N");
		bean.setPaymentMode(policy.getPaymentMode()!=null?policy.getPaymentMode().longValue():null);
		bean.setRequestBy(String.format("%s%s %s", StringUtils.defaultString(policy.getTitleDesc()) , StringUtils.defaultString(policy.getFirstName()),StringUtils.defaultString(policy.getLastName())));
		bean.setCommencementDate(CssConvertUtils.getXMLGregorianCalendarfromDate(CssConvertUtils.toDateFromStringDateThai(policy.getCommencementDate())));
		bean.setMaturityDate(CssConvertUtils.getXMLGregorianCalendarfromDate(CssConvertUtils.toDateFromStringDateThai(policy.getMaturityDate())));
		bean.setSumAssured(policy.getSumAssured()!=null ?policy.getSumAssured().longValue():null);
		bean.setPremiumAmount(policy.getPremiumAmount()!=null?policy.getPremiumAmount().longValue():null); 
		return bean;
	}

	private CssPaymentCardTempEntity createBeanFromParameter(PolicyBean policy, Parameter param) {
		CssPaymentCardTempEntity ct = new CssPaymentCardTempEntity();
		CssUser user = getSessionUser();
		ct.setPolicyNo(policy.getPolicyNo());
		ct.setPolicyType(policy.getPolicyType());
		ct.setPlanName(policy.getPrdName());
		ct.setTitle(policy.getTitleDesc());
		ct.setFirstName(policy.getFirstName());
		ct.setLastName(policy.getLastName());
		if(StringUtils.isNotEmpty(param.telno)){
			ct.setTelNo(param.telno);
		}
		if(StringUtils.isNotEmpty(param.mobile)){
			ct.setMobile(param.mobile);
		}
		ct.setPaymentChannel(param.howto);
		
		if(param.addressType.equals("C"))//	C: Current ปัจจุบัน 
		{
			LOG.debug("C: Current ปัจจุบัน====>>>");
			ct.setSendAddressNo(policy.getAddressLine1());
			ct.setSendSubDistrict(policy.getSubdistrictName());
			ct.setSendDistrict(policy.getDistrictName());
			ct.setSendProvince(policy.getProvinceName());
			ct.setSendZipCode(policy.getPostcode());
			ct.setSendType(param.addressType);
		}
		if(param.addressType.equals("O"))//	O: Other อื่นๆ
		{
			ct.setSendAddressNo(param.addressLine1);
			ct.setSendSubDistrict(param.subDistDesc);
			ct.setSendDistrict(param.disDesc);
			ct.setSendProvince(param.povDesc);
			ct.setSendZipCode(param.postcode);
			ct.setSendType(param.addressType);
		}
		if(param.addressType.equals("B"))//B: Branch สาขา
		{
			ct.setSendBranchName(param.branch);
			ct.setSendType(param.addressType);
		}

		ct.setSendStatus("N");
		ct.setCreateDate(new Date());
		ct.setCreateBy(user.getUsername());
		ct.setUpdateDate(new Date());
		ct.setUpdateBy(user.getUsername());
		
		return ct;
	}
	
	private void sendEmail(CssUser user, CssPaymentCardTempEntity paymentCard) throws Exception {
		String subject = String.format("แจ้งการขอสมัครบัตรชำระเบี้ยประกันภัย ผ่านช่องทาง CSS web คำร้องเลขที่ %s วันที่ %s", paymentCard.getRequestNo(),CssConvertUtils.toDateThaiString(paymentCard.getCreateDate()));

		StringBuilder sb = new StringBuilder(10000);
		sb.append("เรียน ").append("เจ้าหน้าที่ที่เกี่ยวข้อง");
		sb.append("<p>เนื่องจากผู้เอาประกันได้ขอดำเนินการขอสมัครบัตรชำระเบี้ยประกันภัย ผ่าน Customer web portal </p>"
				+ "<p>ขอให้ท่านโปรดดำเนินการตามรายละเอียดที่แนบมา ดังนี้</p>");
		sb.append("<TABLE cellspacing=\"0\" cellpadding=\"0\"><TR style=\"background:#9ACCFF;padding:8px;\">"
				+ "<TH style=\"text-align:center;border-bottom: 1px solid black;padding: 8px 16px;\">เลขที่ใบคำขอ</TH>"
				+ "<TH style=\"text-align:center;border-bottom: 1px solid black;padding: 8px 16px;\">เลขที่กรมธรรม์</TH>"
				+ "<TH style=\"text-align:center;border-bottom: 1px solid black;padding: 8px 16px;\">ผู้ยื่นคำร้อง</TH>"
				+ "<TH style=\"text-align:center;border-bottom: 1px solid black;padding: 8px 16px;\">แบบคำร้อง</TH>"
				+ "<TH style=\"text-align:center;border-bottom: 1px solid black;padding: 8px 16px;\">วันที่ส่งคำร้อง</TH>"
				+ "</TR>");
		sb.append("<TR style=\"border-bottom:1px solid black;\">"
				+ "<TD style=\"text-align:center;padding: 8px 16px;\">")
				.append(paymentCard.getRequestNo())
				.append("</TD><TD style=\"text-align:center;padding: 8px 16px;\">")
				.append(paymentCard.getPolicyNo())
				.append("</TD><TD style=\"text-align:center;padding: 8px 16px;\">")
				.append(paymentCard.getTitle())
				.append(paymentCard.getFirstName())
				.append(" ")
				.append(paymentCard.getLastName())
				.append("</TD><TD style=\"padding: 8px 16px;\">ขอสมัครบัตรชำระเบี้ยประกัน</TD><TD style\"text-align:center;padding: 8px 16px;\">")
				.append(CssConvertUtils.toDateWithTimeThaiString(paymentCard.getCreateDate()))
				.append("</TD></TR></TABLE>");
		sb.append("<BR/>");
		sb.append("<p>ขอแสดงความนับถือ</p>");
		sb.append("<p>Customer web portal</p>");
		

		sendEmail(DEFAULT_EMAIL_SENDER_NAME,
					RECIPIENTS,
					CC,//cc
					BCC,//bcc
					subject,sb.toString());
		
	}
	private Object removeTitle(CssUser user) {
		
		try{
			if(StringUtils.isBlank(user.getFirstName())||StringUtils.isBlank(user.getLastName())){
				return user.getFullname().replaceFirst("^(คุณ|นาย|นาง|น\\.ส\\.|นางสาว|ด\\.ช\\.|ด\\.ญ\\.|ดร\\.|นพ\\.)", "");
			}else{
				return String.format("%s %s", user.getFirstName(),user.getLastName());
			}
			
		}catch(Exception ex){}
		return user;
	}
	
	public Parameter getParam() {
		return param;
	}

	public void setParam(Parameter param) {
		this.param = param;
	}



	public class Parameter {
		private String policyNo;
		private String addressType;
		private String addressLine1;
		private String province;
		private String povDesc;
		private String district;
		private String disDesc;
		private String subDistrict;
		private String subDistDesc;
		private String postcode;
		private String branch;
		private String telno;
		private String mobile;
		private String howto;
		public String getAddressType() {
			return addressType;
		}
		public void setAddressType(String addressType) {
			this.addressType = addressType;
		}
		public String getAddressLine1() {
			return addressLine1;
		}
		public String getPovDesc() {
			return povDesc;
		}
		public void setPovDesc(String povDesc) {
			this.povDesc = povDesc;
		}
		public String getDisDesc() {
			return disDesc;
		}
		public void setDisDesc(String disDesc) {
			this.disDesc = disDesc;
		}
		public String getSubDistDesc() {
			return subDistDesc;
		}
		public void setSubDistDesc(String subDistDesc) {
			this.subDistDesc = subDistDesc;
		}
		public void setAddressLine1(String addressLine1) {
			this.addressLine1 = addressLine1;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getDistrict() {
			return district;
		}
		public void setDistrict(String district) {
			this.district = district;
		}
		public String getSubDistrict() {
			return subDistrict;
		}
		public void setSubDistrict(String subDistrict) {
			this.subDistrict = subDistrict;
		}
		public String getPostcode() {
			return postcode;
		}
		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}
		public String getBranch() {
			return branch;
		}
		public void setBranch(String branch) {
			this.branch = branch;
		}
		public String getTelno() {
			return telno;
		}
		public void setTelno(String telno) {
			this.telno = telno;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getHowto() {
			return howto;
		}
		public void setHowto(String howto) {
			this.howto = howto;
		}
		public String getPolicyNo() {
			return policyNo;
		}
		public void setPolicyNo(String policyNo) {
			this.policyNo = policyNo;
		}
		
		
	}

}
