package thaisamut.nbs.css.easyloan.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;

import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.PersonBean;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.easyloan.service.EasyLoanInfoService;
import thaisamut.css.model.CssUser;
import thaisamut.css.otp.OTPHandler;
import thaisamut.css.otp.OTPHandler.OTP;
import thaisamut.css.sms.CssSMSConnectionService;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.eformwsclient.component.EformWsClientFactory;
import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;
import thaisamut.cybertron.ejbweb.remote.CssMasterService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ThailandBank;

public class EasyLoanInfoAction extends CssJsonAction {
	private static final long serialVersionUID = 2239017910918508179L;
	private static final Logger LOG = LoggerFactory.getLogger(EasyLoanInfoAction.class);
	private Map<String,String> params = new HashMap<String,String>();
	private File file1;
	private File file2;
	private File file3;
	private File file4;
	
	@Autowired
	private DataWarehouseService dwhService;
	
	@Autowired
	private EasyLoanInfoService easyLoanInfoService;
	
	@Autowired
	private OTPHandler otpHandler;
	
	@Autowired 
	private CssMasterService cssMasterService;
	
	private final String OTP_TTL = "15";
	private final String USER_STORE = "userStore";
	private final String POLICY_STORE = "policyStore";
	private final String POLICYNO_STORE = "policyNoStore";
	private final String TOKEN_STORE = "tokenStore";
	private final String TELNO_STORE = "telNoStore";
	private final String REFNO_STORE = "refNoStore";
	
	private String backPage;
	
	Gson g = new Gson();
	private String currentDate;
	
	@SuppressWarnings("unchecked")
	public String index() throws Exception {
		LOG.info("~ On EasyLoanInfoAction.index ~");
		String policyNo = params.get("policyNo");
		String policyType = params.get("policyType");
		String refPage = params.get("refPage");
		LOG.info("~ params = policyNo : {}, policyType : {} ~",policyNo,policyType);
		setSessionDataRequired();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy",new Locale("th","TH"));
		setCurrentDate(sf.format(new Date()));
		List<ThailandBank> bankList = dwhService.getActiveBank();
		requestAttributes.put("bankList", g.toJson(bankList));
		requestAttributes.put("claimChannel", g.toJson(easyLoanInfoService.getClaimChannelByPolicyNoAndPolicyType(policyNo, policyType)));
		if(StringUtils.isNotBlank(refPage)){
			if(StringUtils.equals("Info", refPage)){
				setBackPage("secure/member/policy/policyinfo.html");
			}else{
				setBackPage("secure/member/policy/policydetail.html?policyNo="+policyNo+"&ref=2");
			}
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String validateOtpStatusN() throws Exception {
		LOG.info("~ On EasyLoanInfoAction.validateOtpStatusN ~");
		String policyNo = params.get("policyNo");
		setSessionDataRequired();
		CssUser user = getSessionUser();
		String userId = String.format("%1$s|%2$s", user.getUsername(),policyNo);
		OTP otp = otpHandler.validateOtpEasyLoan(userId);
		Map<String,PolicyBean> policies = (Map<String,PolicyBean>)sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
		PolicyBean policy = policies.get(policyNo);
		if(null == otp){
			otp = generateOTP(user.getUsername(),policyNo);
			boolean processSend = sendSmsOtp(user, otp);
			if(processSend){
				setSessionData(TOKEN_STORE,otp.getToken());
				setSessionData(REFNO_STORE,otp.getRefNo());
			}
		}else{
			setSessionData(TOKEN_STORE,otp.getToken());
			setSessionData(REFNO_STORE,otp.getRefNo());
		}
		setSessionData(TELNO_STORE,user.getTelNo());
		setSessionData(USER_STORE,g.toJson(user));
		setSessionData(POLICY_STORE,g.toJson(policy));
		setSessionData(POLICYNO_STORE,policyNo);
		return SUCCESS;
	}

	private void setSessionDataRequired() throws Exception {
		CssUser user = getSessionUser();
		if (sessionAttributes.get(SESSION_ATTRIBUTE_USER) == null) {
			PersonBean person = dwhService.getPersonData(user.getCardNo());
			setSessionData(SESSION_ATTRIBUTE_USER,person);
		}
			Map<String,PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(),user.getEmail());
			setSessionData(SESSION_ATTRIBUTE_POLICY_LIST,policies);
	}
	
	@SuppressWarnings("unchecked")
	private void setSessionData(String key,Object val){
		sessionAttributes.put(key, val);
	}
	
	public String save() throws Exception {
		LOG.info("~On EasyLoanInfoAction.save ~");
		return (new JsonAction.Processor() {
			@SuppressWarnings("unchecked")
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				List<Map<String,Object>> files = new ArrayList<Map<String,Object>>();
				files.add(setFileAttach(file1,"file1"));
				files.add(setFileAttach(file2,"file2"));
				files.add(setFileAttach(file3,"file3"));
				files.add(setFileAttach(file4,"file4"));
				if(!checkFiles(files)){
					data.put("PROCESS", false);
					data.put("ERROR_MESSAGE", "กรุณาระบุไพล์ที่มีนามสกุล jpeg,jpg,png,gif,pdf เท่านั้น");
				}else{
					setSessionDataRequired();
					CssUser user = getSessionUser();
					PersonBean person = (PersonBean)sessionAttributes.get(SESSION_ATTRIBUTE_USER);
					Map<String,PolicyBean> policies = (Map<String,PolicyBean>)sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
					String policyNo  = params.get("policyNo");
					PolicyBean policy = policies.get(policyNo);
					boolean processSave = easyLoanInfoService.save(files, params, policy, person, user);
					data.put("PROCESS", false);
					if(processSave){
						data.put("PROCESS", true);
					}
				}
				result.setData(data);
			}
		}).run();
	}
	
	public String genOtp() throws Exception {
		LOG.info("~On EasyLoanInfoAction.genOtp ~");
		return (new JsonAction.Processor() {
			@SuppressWarnings("unchecked")
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = getOtpEasyLoan();
				result.setData(data);
			}
		}).run();
	}
	
	public String confOtp() throws Exception {
		LOG.info("~On EasyLoanInfoAction.confOtp ~");
		return SUCCESS;
	}
	
	public String validateOtp() throws Exception {
		LOG.info("~On EasyLoanInfoAction.validateOtp ~");
		return (new JsonAction.Processor() {
			@SuppressWarnings("unchecked")
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("PROCESS", false);
				CssUser user = getSessionUser();
				String policyNo  = params.get("policyNo");
				String idNo  = user.getCardNo();
				String refNo  = params.get("refNo");
				String otp  = params.get("otp");
//				OTP o = otpHandler.validOTP(refNo, otp);
				OTP o = otpHandler.validateOtpEasyLoan(refNo, otp);
				String ownerId = String.format("%1$s|%2$s", user.getUsername(),policyNo);
				if(null != o && o.isOwner(ownerId)){
					setSessionDataRequired();
					PersonBean person = (PersonBean)sessionAttributes.get(SESSION_ATTRIBUTE_USER);
					Map<String,PolicyBean> policies = (Map<String,PolicyBean>)sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
					PolicyBean policy = policies.get(policyNo);
					String REQ_NO = easyLoanInfoService.sendDataToEform(policyNo, idNo, policy, person, user);
					easyLoanInfoService.updateCssLoanStatus(idNo, policyNo,REQ_NO);//update css_loan
					data.put("PROCESS", true);
					setSessionData("REQ_NO",REQ_NO);
					setSessionData(USER_STORE,null);
					setSessionData(POLICY_STORE,null);
					setSessionData(POLICYNO_STORE,null);
					setSessionData(TOKEN_STORE,null);
					setSessionData(TELNO_STORE,null);
					setSessionData(REFNO_STORE,null);
				}
				result.setData(data);
			}
		}).run();
	}
	
	public String resetOtp() throws Exception {
		LOG.info("~On EasyLoanInfoAction.resetOtp ~");
		return (new JsonAction.Processor() {
			@SuppressWarnings("unchecked")
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				CssUser user = getSessionUser();
				String policyNo  = params.get("policyNo");
				
				String userId = String.format("%1$s|%2$s", user.getUsername(),policyNo);
				boolean flag = otpHandler.resetOtpEasyLoan(userId);
				data.put("PROCESS", flag);
				if(flag){
					data = getOtpEasyLoan();
				}
				result.setData(data);
			}
		}).run();
	}
	
	public String successOtp() throws Exception {
		LOG.info("~On EasyLoanInfoAction.successOtp ~");
		return SUCCESS;
	}

	private Boolean sendSmsOtp(CssUser user, OTP otp) {
		return sendSmsOtpSuccess(user,otp.getRefNo(), otp.getOtp(), user.getTelNo(), CssSMSConnectionService.MAP_SMS_METHOD_EASY_LOAN, otp.getExpiredDate(),true);
	}
	
	private OTP generateOTP(String userName,String policyNo) throws Exception {
		String userId = String.format("%1$s|%2$s", userName,policyNo);
		OTP otp = otpHandler.generate(String.format("%sm", OTP_TTL), userId);
		return otp;
	}
	
	private Map<String,Object> setFileAttach(File file,String filename) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String name = String.format("%1$s%2$s", filename,"FileName");//file1FileName
		String type = String.format("%1$s%2$s", filename,"ContentType");//file1ContentType
		map.put("file", file);
		map.put("filename", file == null?StringUtils.EMPTY:((String[])requestParameters.get(name))[0]);
		map.put("filetype", file == null?StringUtils.EMPTY:((String[])requestParameters.get(type))[0]);
		return map;
	}
	
	private boolean checkFiles(List<Map<String,Object>> files) throws Exception {
		boolean result = true;
		if(!CollectionUtils.isEmpty(files)){
			String[] extention = {"jpeg","jpg","png","gif","pdf"};
			for(Map<String,Object> file : files){
				String fileType = file.get("filetype") == null?StringUtils.EMPTY:file.get("filetype").toString();
				if(StringUtils.isNotBlank(fileType)){
					String last = fileType.substring(fileType.lastIndexOf("/") + 1, fileType.length());
					if(!Arrays.asList(extention).contains(last.toLowerCase())){
						result = false;
						break;
					}
				}
			}
		}
		return result;
	}
	
	private boolean checkFileExtension(String[] feList){
		boolean result = true;
		String[] extention = {"jpeg","jpg","png","gif","pdf"};
		for (String ex : feList) {
			String last = ex.substring(ex.lastIndexOf("/") + 1, ex.length());
			if(!Arrays.asList(extention).contains(last.toLowerCase())){
				result = false;
				break;
			}
		}
		return result;
	}
	
	private boolean checkFileName(String[] fnList){
		boolean result = true;
		String[] extention = {"jpeg","jpg","png","gif","pdf"};
		for (String ex : fnList) {
			String last = ex.substring(ex.lastIndexOf(".") + 1, ex.length());
			
			if(!Arrays.asList(extention).contains(last.toLowerCase())){
				result = false;
				break;
			}
		}
		return result;
	}
	
	private Map<String, Object> getOtpEasyLoan() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		setSessionDataRequired();
		CssUser user = getSessionUser();
		Map<String,PolicyBean> policies = (Map<String,PolicyBean>)sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
		String policyNo  = params.get("policyNo");
		PolicyBean policy = policies.get(policyNo);
		data.put("PROCESS", false);
		OTP otp = generateOTP(user.getUsername(),policyNo);
		boolean processSend = sendSmsOtp(user, otp);
		if(processSend){
			setSessionData(USER_STORE,g.toJson(user));
			setSessionData(POLICY_STORE,g.toJson(policy));
			setSessionData(POLICYNO_STORE,policyNo);
			setSessionData(TOKEN_STORE,otp.getToken());
			setSessionData(TELNO_STORE,user.getTelNo());
			setSessionData(REFNO_STORE,otp.getRefNo());
			data.put("PROCESS", true);
		}
		return data;
	}
	
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public File getFile1() {
		return file1;
	}

	public void setFile1(File file1) {
		this.file1 = file1;
	}

	public File getFile2() {
		return file2;
	}

	public void setFile2(File file2) {
		this.file2 = file2;
	}

	public File getFile3() {
		return file3;
	}

	public void setFile3(File file3) {
		this.file3 = file3;
	}

	public File getFile4() {
		return file4;
	}

	public void setFile4(File file4) {
		this.file4 = file4;
	}

	public String getBackPage() {
		return backPage;
	}

	public void setBackPage(String backPage) {
		this.backPage = backPage;
	}
	
}
