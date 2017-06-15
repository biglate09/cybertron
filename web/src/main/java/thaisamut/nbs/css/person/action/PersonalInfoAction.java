package thaisamut.nbs.css.person.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.data.service.CssPolicyDataService;
import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.DistrictBean;
import thaisamut.css.dwh.service.pojo.GraphRiderBean;
import thaisamut.css.dwh.service.pojo.PersonBean;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.dwh.service.pojo.ProvinceBean;
import thaisamut.css.dwh.service.pojo.SubDistrictBean;
import thaisamut.css.model.CssUser;
import thaisamut.css.otp.OTPHandler;
import thaisamut.css.otp.OTPHandler.OTP;
import thaisamut.css.sms.CssSMSConnectionService;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.util.CssConvertUtils;

import com.google.gson.Gson;

public class PersonalInfoAction extends CssJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4243040121519560323L;
	private static final Logger LOG = LoggerFactory.getLogger(PersonalInfoAction.class);

	@Autowired
	DataWarehouseService dwhService;
	@Autowired
	CssMasterDataService cssMasterDataService;
	@Autowired
	CssMemberService cssMemberService;
	@Autowired
	CssPolicyDataService cssPolicyDataService;
	@Autowired
	OTPHandler otpHandler;

	private Parameter param = new Parameter();
	private OTPParameter otp = new OTPParameter();

	private final String OTP_CONFIRM_TELNO = "Personal_Info_Action_Tel_No";
	private final String OTP_TTL = "15";

	private String policyNo;
	
	public String index() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On PersonalInfoAction : index");
		}

		CssUser user = getSessionUser();
		if (user != null) {
			if (sessionAttributes.get(SESSION_ATTRIBUTE_USER) == null) {
				PersonBean person = dwhService.getPersonData(user.getCardNo());
				sessionAttributes.put(SESSION_ATTRIBUTE_USER, person);
			}

			if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) == null) {
				Map<String, PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(), user.getEmail());
				Gson g = new Gson();

				sessionAttributes.put(SESSION_ATTRIBUTE_POLICY_LIST, policies);
				sessionAttributes.put(SESSION_ATTRIBUTE_GRAPH_JSON, g.toJson(policies));
			}
			Map<String, PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);

			if (policies != null) {
				PolicyBean policy;
				GraphRiderBean graph = new GraphRiderBean();
				for (Entry<String, PolicyBean> entry : policies.entrySet()) {
					CssAddressTempEntity tmp = cssPolicyDataService.queryCssAddressTempEntityWaitingStatus(entry.getKey(), user.getUsername());
					if (tmp != null) {
						setTempAddres(entry.getValue(), tmp);
					}
				}

				sessionAttributes.put(SESSION_ATTRIBUTE_GRAPH, graph);
			}
		}

		return super.index();
	}
	private void setTempAddres(PolicyBean value, CssAddressTempEntity tmp) {
		if (value != null && tmp != null) {
			value.setAddressLine1(tmp.getAddr());
			value.setSubdistrictName(tmp.getSdtDesc());
			value.setDistrictName(tmp.getDtDesc());
			value.setDtCode(tmp.getDtCode());
			value.setProvinceName(tmp.getPvDesc());
			value.setPvCode(tmp.getPvCode());
			value.setPostcode(tmp.getZipCode());
			value.setPhone1(tmp.getPhone1());
			value.setExt1(tmp.getExt1());
			value.setPhone2(tmp.getPhone2());
			value.setExt2(tmp.getExt2());
			value.setMobile1(tmp.getMobile1());
			value.setMobile2(tmp.getMobile2());
			value.setEmail(tmp.getEmail());
			value.setTemp(true);
		}
	}

	private void initiateEdit(String polNo) throws Exception {
		CssUser user = getSessionUser();
		if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null) {
			HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
			PolicyBean policyMain = policies.get(polNo);
			if (policyMain != null) {
				Map<String, PolicyBean> subPolicies = (Map<String, PolicyBean>) policies.clone();
				subPolicies.remove(polNo);

				requestAttributes.put("main", policyMain);
				if (subPolicies != null && !subPolicies.isEmpty()) {
					requestAttributes.put("sub", subPolicies);
				}

				String telNo = user.getTelNo();
				requestAttributes.put("displayT",telNo.replaceAll("^(\\d{3})\\d{3}(\\d{4})$","$1-XXX-$2"));
				//
				Gson g = new Gson();

				List<ProvinceBean> provinceBeanList = cssMasterDataService.getActiveProvince();

				requestAttributes.put("province", g.toJson(provinceBeanList));

				Map<String, List<DistrictBean>> districtBeanList = cssMasterDataService.getActiveDistrict();

				requestAttributes.put("district", g.toJson(districtBeanList));

				Map<String, List<SubDistrictBean>> subdistrictBeanList = cssMasterDataService.getActiveSubDistrict();

				requestAttributes.put("subdistrict", g.toJson(subdistrictBeanList));
			}

		}
	}
	public String edit() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On PersonalInfoAction : edit");
		}
		initiateEdit(policyNo);
		sessionAttributes.put(PROCESS_KEY_ADDRESS, PROCESS_INIT);
		return SUCCESS;
	}
	public String save() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On PersonalInfoAction : save");
		}
		try {
			CssUser user = getSessionUser();
			final String telNo = user.getTelNo();
			OTP o = otpHandler.generate(String.format("%sm", OTP_TTL), user.getUsername());

			String message = checkIntrigity(param);
			
			if (StringUtils.isNotBlank(message)) {
				requestAttributes.put("INTRIGITY_ERROR", message);
				initiateEdit(param.policyNo);
				return "fail";
			}else if(sessionAttributes.get(PROCESS_KEY_ADDRESS)==null){
				return ADDRESS_ZERO;
			} else {
				List<CssAddressTempEntity> prepareupdate = cssPolicyDataService.prepareAddress(param, user, o.getToken());
				sessionAttributes.put(o.getToken(), prepareupdate);
				//telNo = StringUtils.defaultString(param != null ? param.getMobile1() : null, telNo);
				requestAttributes.put("policyNo", param.getPolicyNo());
				requestAttributes.put("otp_tel", telNo);
				requestAttributes.put("otp_ref", o.getRefNo());
				requestAttributes.put("otp_token", o.getToken());
//				sessionAttributes.put(OTP_CONFIRM_TELNO, telNo);
//				if (!LOG.isDebugEnabled()) {
				boolean success =	sendSmsOtpSuccess(getSessionUser(),o.getRefNo(), o.getOtp(), telNo, CssSMSConnectionService.MAP_SMS_METHOD_CHANGE_ADDRESS, o.getExpiredDate(),true);
//				}
				if (LOG.isDebugEnabled()) {
					LOG.debug(String.format("confirm OTP : %s = %s", o.getRefNo(), o.getOtp()));
				}
				if(!success) {
					requestAttributes.put("otp_tel", null);
					requestAttributes.put("otp_ref", null);
					requestAttributes.put("otp_token", null);
					setErrorMessage("ไม่สามารถขอรหัส OTP ได้ เนื่องจากเกินจำนวนครั้งที่บริษัทกำหนด");
					return ERROR;
				}
				requestAttributes.put(PROCESS_KEY_ADDRESS,PROCESS_CONFIRM);
				return SUCCESS;
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return ERROR;
		}
	}
	private String checkIntrigity(Parameter param) {
		StringBuilder sb = new StringBuilder(1000);
		CssUser user = getSessionUser();
		try{
			if(StringUtils.isBlank(param.addressLine1)){
				sb.append("กรอกที่อยู่ <br/>");
			}
			if(StringUtils.isBlank(param.province)){
				sb.append("กรอกจังหวัด <br/>");
			}
			if(StringUtils.isBlank(param.district)){
				sb.append("กรอกอำเภอ <br/>");
			}
			if(StringUtils.isBlank(param.subdistrictName)){
				sb.append("กรอกตำบล <br/>");
			}
			if(StringUtils.isBlank(param.postcode)){
				sb.append("กรอกรหัสไปรษณี <br/>");
			}
			if(StringUtils.isBlank(param.mobile1)){
				sb.append("กรอกโทรศัพท์มือถือ <br/>");
			}else if(!CssConvertUtils.isValideMobileNumber(param.mobile1)){
				sb.append("รูปแบบเบอร์โทรศัพท์มือถือ1ไม่ถูกต้อง <br/>");
			}
			if(StringUtils.isNotBlank(param.mobile2)&&!CssConvertUtils.isValideMobileNumber(param.mobile2)){
				sb.append("รูปแบบเบอร์โทรศัพท์มือถือ2ไม่ถูกต้อง <br/>");
			}
			if(StringUtils.isNotBlank(param.phone1)&&!CssConvertUtils.isValidePhoneNumber(param.phone1)){
				sb.append("รูปแบบเบอร์โทรศัพท์บ้านไม่ถูกต้อง <br/>");
			}
			if(StringUtils.isNotBlank(param.phone2)&&!CssConvertUtils.isValidePhoneNumber(param.phone2)){
				sb.append("รูปแบบเบอร์โทรศัพท์ที่ทำงานไม่ถูกต้อง <br/>");
			}
			//duplicate email
			if(StringUtils.isNotEmpty(param.email) && cssMasterDataService.isExistEmail(param.email,user.getUsername())){
				sb.append("อีเมล ").append(param.email).append(" ถูกใช้งานไปแล้วโดยบุคคลอื่น <br/>");
			}
			if(cssMasterDataService.isExistMobile1(param.mobile1,user.getUsername())){
				sb.append("โทรศัพท์มือถือ1 ").append(param.mobile1).append(" ถูกใช้งานไปแล้วโดยบุคคลอื่น <br/>");
			}
			if(param.mobile1!=null&&param.mobile1.equals(param.mobile2)){
				sb.append("โทรศัพท์มือถือ1 และ โทรศัพท์มือถือ2 ซ้ำกัน");
			}
		
		
		}catch(Exception ex){
			sb.append(ex.getMessage());
		}
		return sb.toString();
	}
	public String confirmotp() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On PersonalInfoAction : confirmotp");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				CssUser user = getSessionUser();
//				String telNo = (String) sessionAttributes.get(OTP_CONFIRM_TELNO);
//				telNo = telNo == null ? user.getTelNo() : telNo;
				String telNo = user.getTelNo();
				String email = user.getEmail();
				OTP o = otpHandler.validOTP(otp.ref, otp.otp);
				if (o != null && o.isOwner(user.getUsername())) {
					if (o.isNotExpired()) {
						data.put("SUCCESS", true);
						List<CssAddressTempEntity> addressUpdated = (List<CssAddressTempEntity>) sessionAttributes.get(o.getToken());
						if (addressUpdated != null && !addressUpdated.isEmpty()) {
							addressUpdated = cssPolicyDataService.saveAddress(addressUpdated, user);
							Map<String, PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);

							if (addressUpdated != null && !addressUpdated.isEmpty() && policies != null && !policies.isEmpty()) {
								PolicyBean bean;
								// StringBuilder smsPolicy = new
								// StringBuilder(500);
								// smsPolicy.append("แก้ไขข้อมูลที่อยู่ที่ติดต่อของกรมธรรม์:");
								String sep = "";
								CssAddressTempEntity updateMember=addressUpdated.get(0);
								for (CssAddressTempEntity tmpUpdate : addressUpdated) {
									bean = policies.get(tmpUpdate.getPolicyNo());
									if (bean != null && tmpUpdate != null) {
										// smsPolicy.append(sep).append(tmpUpdate.getPolicyNo());
										setTempAddres(bean, tmpUpdate);
										sep = ", ";
										email = StringUtils.defaultString(tmpUpdate.getEmail(), email);
										telNo = StringUtils.defaultString(tmpUpdate.getMobile1(), telNo);
									}
									// smsPolicy.append(" เรียบร้อยแล้ว");
								}

								
								updateMember(user, updateMember);
								
								if (addressUpdated != null && StringUtils.isNotBlank(email)) {
									try {
										sendEmail(DEFAULT_EMAIL_SENDER_NAME, email, "แจ้งการแก้ไขข้อมูลการติดต่อของกรมธรรม์ในบริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต", createEmailBody(user, addressUpdated));
									} catch (Exception ex) {
										LOG.error(String.format("ไม่สามารถส่งมีเมลยืนยันไปที่ %s", email), ex);
									}
								}
//								if (!LOG.isDebugEnabled()) {
									String SUCCESS_MESSAGE = "OceanLife iService ได้รับคำร้องการแก้ไขข้อมูลของท่านแล้วตามที่แสดงในหน้า Website โดยข้อมูลใหม่จะมีผลในอีก 3 วันทำการ";
									sendSmsSuccess(telNo, SUCCESS_MESSAGE);
									data.put("MESSAGE",SUCCESS_MESSAGE);
//								}
							}
						} else {
							data.put("WARNING", "ไม่พบข้อมูลที่ต้องการอัพเดท");
						}

					} else {
						data.put("WARNING", "รหัส OTP ของท่านหมดอายุ กรุณาทำรายการใหม่");
					}
				} else {
					data.put("WARNING", "รหัส OTP ไม่ถูกต้อง กรุณาตรวจสอบใหม่");
				}
				result.setData(data);
			}
		}).run();
	}
	public String resetotp() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On PersonalInfoAction : confirmotp");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				CssUser user = getSessionUser();
//				String telNo = (String) sessionAttributes.get(OTP_CONFIRM_TELNO);
//				telNo = telNo == null ? user.getTelNo() : telNo;
				String telNo = user.getTelNo();
				// OTP o = otpHandler.validToken(otp.token);
				// if(o!=null){
				OTP newOpt = otpHandler.generate(String.format("%sm", OTP_TTL), user.getUsername());
				// cssPolicyDataService.resetAddressTempToken(otp.token,newOpt.getToken());
				List<CssAddressTempEntity> prepareupdate = (List<CssAddressTempEntity>) sessionAttributes.get(otp.getToken());
				sessionAttributes.put(newOpt.getToken(), prepareupdate);
				
				boolean sms = sendSmsOtpSuccess(getSessionUser(),newOpt.getRefNo(), newOpt.getOtp(), telNo, CssSMSConnectionService.MAP_SMS_METHOD_CHANGE_ADDRESS, newOpt.getExpiredDate(),false);
				
				data.put("SUCCESS", sms);
				if(sms){
					data.put("token", newOpt.getToken());
					data.put("ref", newOpt.getRefNo());
				}else{
					data.put("WARNING","ไม่สามารถขอรหัส OTP ได้ เนื่องจากเกินจำนวนครั้งที่บริษัทกำหนด");
				}
				result.setData(data);
			}
		}).run();
	}

	private void updateMember(CssUser user, CssAddressTempEntity updateMember) throws Exception {
		//REGION update mobile1 and email for cssMember 
		CssMemberEntity member = cssMemberService.findMemberByUserName(user.getUsername());
		boolean needUpdate = false;
		if(member!=null&& updateMember!=null){
			if(StringUtils.isNotBlank(updateMember.getMobile1())&&!updateMember.getMobile1().equals(member.getTelNo())){
				member.setTelNo(updateMember.getMobile1());
				user.setTelNo(updateMember.getMobile1());
				needUpdate = true;
			}
			String updateEmail = StringUtils.defaultString(updateMember.getEmail());
			String memberEmail = StringUtils.defaultString(member.getEmail());
			if(!updateEmail.equals(memberEmail)){
				member.setEmail(updateMember.getEmail());
				user.setEmail(updateMember.getEmail());
				needUpdate = true;
			}
			if(needUpdate){
				cssMemberService.updateCssMember(member,user.getUsername());
			}
		}
		//REGION end;
	}

	private String createEmailBody(CssUser user, List<CssAddressTempEntity> addressUpdated) throws Exception {

		Map<String, PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(), user.getEmail());
		return createEmailBody(user, addressUpdated, policies);
	}

	public static synchronized String createEmailBody(CssUser user, List<CssAddressTempEntity> addressUpdated, Map<String, PolicyBean> policies) throws Exception {

		PolicyBean policy;
		StringBuilder sb = new StringBuilder(1000);
		if (addressUpdated != null && !addressUpdated.isEmpty()) {
			sb.append("เรียน คุณ").append(removeTitle(user));
			sb.append("<p style=\"text-indent: 40px;\">บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอแจ้งให้ท่านทราบว่า ท่านได้ดำเนินการแก้ไขข้อมูลการติดต่อของบริการ OceanLife iService เรียบร้อยแล้ว ตามข้อมูลที่แสดงดังนี้</p>");
			for (CssAddressTempEntity address : addressUpdated) {
				policy = policies.get(address.getPolicyNo());
				sb.append("<p>เลขที่กรมธรรม์ ").append(address.getPolicyNo()).append(" แบบประกัน ").append(policy.getPrdName()).append("</p>");
				sb.append("<p>ที่อยู่ ").append(StringUtils.defaultIfBlank(address.getAddr(), "-")).append("</p>");
				sb.append("<p>");
				if ("1".equals(policy.getPvCode())) {
					sb.append("แขวง ");
				} else {
					sb.append("ตำบล ");
				}
				sb.append(StringUtils.defaultIfBlank(address.getSdtDesc(), "-")).append("</p>");
				sb.append("<p>");
				if ("1".equals(policy.getPvCode())) {
					sb.append("เขต ");
				} else {
					sb.append("อำเภอ ");
				}
				sb.append(StringUtils.defaultIfBlank(address.getDtDesc(), "-")).append("</p>");
				sb.append("<p>จังหวัด ").append(StringUtils.defaultIfBlank(address.getPvDesc(), "-")).append("</p>");
				sb.append("<p>รหัสไปรษณีย์ ").append(StringUtils.defaultIfBlank(address.getZipCode(), "-")).append("</p>");
				sb.append("<p>หมายเลขโทรศัพท์มือถือ 1 ").append(StringUtils.defaultIfBlank(address.getMobile1(), "-")).append("</p>");
				sb.append("<p>หมายเลขโทรศัพท์มือถือ 2 ").append(StringUtils.defaultIfBlank(address.getMobile2(), "-")).append("</p>");
				sb.append("<p><div style=\"display:inline;display:inline-block;width:260px\">หมายเลขโทรศัพท์บ้าน ").append(StringUtils.defaultIfBlank(address.getPhone1(), "-")).append("</div> ต่อ&nbsp;&nbsp;&nbsp;")
						.append(StringUtils.defaultIfBlank(address.getExt1(), "-")).append("</p>");
				sb.append("<p><div style=\"display:inline;display:inline-block;width:260px\">หมายเลขโทรศัพท์ที่ทำงาน ").append(StringUtils.defaultIfBlank(address.getPhone2(), "-")).append("</div> ต่อ&nbsp;&nbsp;&nbsp;")
						.append(StringUtils.defaultIfBlank(address.getExt2(), "-")).append("</p>");
				sb.append("<p>อีเมล ").append(StringUtils.defaultString(address.getEmail(), "-")).append("</p>");
				sb.append("<p>----------------------------------------------------------------------------------------------------</p>");

			}
			sb.append("<p>OceanLife iService ได้รับคำร้องการแก้ไขข้อมูลของท่านแล้วตามที่แสดงในหน้า Website โดยบริษัทจะสามารถติดต่อท่านผ่านที่อยู่ใหม่ได้ในอีก 2 วันทำการ</p>");
			sb.append("<p>หากท่านต้องการสอบถามข้อมูลเพิ่มเติม หรือมีข้อสงสัยใดๆ กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์  โทร: 0-2207-8888 ทุกวันจันทร์ - วันศุกร์ เวลา 8.00 น. - 18.00 น. และวันเสาร์ เวลา 8.00 – 17.00 น. อีเมล: info@ocean.co.th</p>");
			sb.append("<p>&nbsp;</p>");
			sb.append("<p>ขอแสดงความนับถือ");
			sb.append("<p><div>บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน)</div>");
			sb.append("<div>เว็บไซต์ www.ocean.co.th</div>");
			sb.append("<div>เฟสบุ๊ค www.facebook.com/oceanlifepage</div></p>");
			sb.append("<p><h4>หมายเหตุ</h4></p>");
			sb.append("<p><div>- อีเมลฉบับนี้เป็นการแจ้งข้อมูลโดยอัตโนมัติ เพื่อยืนยันการแก้ไขข้อมูลการติดต่อในกรมธรรม์ของท่าน ในบริการ OceanLife iService ที่ได้ดำเนินการเรียบร้อยแล้ว กรุณาอย่าตอบกลับอีเมลฉบับนี้</div>");
			sb.append("<div>- หากท่านไม่ได้ทำรายการตามรายละเอียดข้างต้น หรือหากพบว่ามีข้อมูลหรือสิ่งผิดปกติใดๆที่ไม่ถูกต้อง โปรดติดต่อบริษัทฯ</div>");
			sb.append("<div>- บริษัทฯ ไม่มีนโยบายในการติดต่อ ท่านไม่ว่าผ่านช่องทางใดๆ เพื่อสอบถามหรือขอข้อมูลเฉพาะ เช่น บัญชีผู้ใช้งาน (User ID) และ/หรือ รหัสผ่าน (Password) สำหรับการใช้บริการ OceanLife iService หากพบบุคคลใดกระทำการดังกล่าว  โปรดแจ้งกับทางบริษัทฯ และให้ข้อมูลเพื่อให้เจ้าหน้าที่ของทางบริษัทฯ ทำการตรวจสอบ เพื่อความปลอดภัยของท่าน</div>");
			sb.append("</p>");

		} else {
			throw new Exception("not found changed address");
		}
		return sb.toString();
	}


	private static Object removeTitle(CssUser user) {

		try {
			if (StringUtils.isBlank(user.getFirstName()) || StringUtils.isBlank(user.getLastName())) {
				return user.getFullname().replaceFirst("^(คุณ|นาย|นาง|น\\.ส\\.|นางสาว|ด\\.ช\\.|ด\\.ญ\\.|ดร\\.|นพ\\.)", "");
			} else {
				return String.format("%s %s", user.getFirstName(), user.getLastName());
			}

		} catch (Exception ex) {
		}
		return user;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Parameter getParam() {
		return param;
	}
	public void setParam(Parameter param) {
		this.param = param;
	}

	public OTPParameter getOtp() {
		return otp;
	}
	public void setOtp(OTPParameter otp) {
		this.otp = otp;
	}

	public class Parameter {
		private String policyNo;
		private String addressLine1;
		private String province;
		private String povDesc;
		private String disDesc;
		private String policyType;
		private String district;
		private String postcode;
		private String subdistrictName;
		private String mobile1;
		private String mobile2;
		private String phone1;
		private String ext1;
		private String phone2;
		private String ext2;
		private String email;
		private String[] followPolicy;
		public String getPolicyNo() {
			return policyNo;
		}
		public void setPolicyNo(String policyNo) {
			this.policyNo = policyNo;
		}
		public String getAddressLine1() {
			return addressLine1;
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

		public String getPostcode() {
			return postcode;
		}
		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}
		public String getMobile1() {
			return mobile1;
		}
		public void setMobile1(String mobile1) {
			this.mobile1 = mobile1;
		}
		public String getMobile2() {
			return mobile2;
		}
		public void setMobile2(String mobile2) {
			this.mobile2 = mobile2;
		}
		public String getPhone1() {
			return phone1;
		}
		public void setPhone1(String phone1) {
			this.phone1 = phone1;
		}
		public String getExt1() {
			return ext1;
		}
		public void setExt1(String ext1) {
			this.ext1 = ext1;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String[] getFollowPolicy() {
			return followPolicy;
		}
		public void setFollowPolicy(String[] followPolicy) {
			this.followPolicy = followPolicy;
		}
		public String getSubdistrictName() {
			return subdistrictName;
		}
		public void setSubdistrictName(String subdistrictName) {
			this.subdistrictName = subdistrictName;
		}
		public String getPolicyType() {
			return policyType;
		}
		public void setPolicyType(String policyType) {
			this.policyType = policyType;
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
		public String getPhone2() {
			return phone2;
		}
		public void setPhone2(String phone2) {
			this.phone2 = phone2;
		}
		public String getExt2() {
			return ext2;
		}
		public void setExt2(String ext2) {
			this.ext2 = ext2;
		}
	}
	public class OTPParameter {
		private String token;
		private String tel;
		private String ref;
		private String otp;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getRef() {
			return ref;
		}
		public void setRef(String ref) {
			this.ref = ref;
		}
		public String getOtp() {
			return otp;
		}
		public void setOtp(String otp) {
			this.otp = otp;
		}
	}
}