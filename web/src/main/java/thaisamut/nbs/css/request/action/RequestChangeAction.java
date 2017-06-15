package thaisamut.nbs.css.request.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.DistrictBean;
import thaisamut.css.dwh.service.pojo.PersonBean;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.dwh.service.pojo.ProvinceBean;
import thaisamut.css.dwh.service.pojo.SubDistrictBean;
import thaisamut.css.eform.service.EformService;
import thaisamut.css.model.CssUser;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.eform.ws.PetitionAttachmentBookBankBean;
import thaisamut.eform.ws.PetitionAttachmentConsensusBean;
import thaisamut.eform.ws.PetitionAttachmentIdCardBean;
import thaisamut.eform.ws.PetitionAttachmentRenameBean;
import thaisamut.eform.ws.PetitionChangeAddressBean;
import thaisamut.eform.ws.PetitionChangeBenefitPaymentBean;
import thaisamut.eform.ws.PetitionChangeCustomerNameBean;
import thaisamut.eform.ws.PetitionChangePaymentPeriodBean;
import thaisamut.eform.ws.PetitionPolicyBean;
import thaisamut.eform.ws.PolicyType;
import thaisamut.eform.ws.SystemAuditLog;
import thaisamut.cybertron.ejbweb.model.BranchesEntity;
import thaisamut.cybertron.ejbweb.model.CssCommonDataEntity;
import thaisamut.cybertron.ejbweb.model.CssPetitionPolicyEntity;
import thaisamut.cybertron.ejbweb.model.CssPetitionRequestEntity;
import thaisamut.cybertron.ejbweb.remote.BranchesService;
import thaisamut.cybertron.ejbweb.remote.CssEFormService;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ClaimChannel;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Petition;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ThailandBank;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ThailandZipcode;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Title;
import thaisamut.osgi.targetbundles.dmsws.search.v1.DmsDocument;
import thaisamut.pantheon.jasper.JasperJrxmlComponent;
import thaisamut.pantheon.jasper.JasperRender;
import thaisamut.pantheon.jasper.ParamsEnum.StreamType;
import thaisamut.pantheon.servlet.remoteaction.FileUploadFetchComponent;
import thaisamut.util.PdfMergeUtils;

import com.google.gson.Gson;

public class RequestChangeAction extends CssJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2488064113269000934L;
	private static final Logger LOG = LoggerFactory.getLogger(RequestChangeAction.class);
	
	@Autowired private JasperJrxmlComponent jasperJrxmlComponent;
	@Autowired private EformService eformService;
	@Autowired private DataWarehouseService dwhService;
	@Autowired private CssMasterDataService cssMasterDataService;
	@Autowired private CssMemberService cssMemberService;
	@Autowired private CssEFormService cssEFormService;
	@Autowired private BranchesService branchesService;
	
	private static final String KEY_GROUP = "EFORM_MAIL";
	private static final String KEY_CODE_1 = "EFORM_MAIL_GROUP01";
	private static final String KEY_CODE_CC_1 = "EFORM_MAIL_GROUP01_CC";
	private static final String KEY_CODE_2 = "EFORM_MAIL_GROUP02";
	private static final String KEY_CODE_CC_2 = "EFORM_MAIL_GROUP02_CC";
	private static final String KEY_CODE_3 = "EFORM_MAIL_GROUP03";
	private static final String KEY_CODE_CC_3 = "EFORM_MAIL_GROUP03_CC";
	private static final String KEY_CODE_4 = "EFORM_MAIL_GROUP04";
	private static final String KEY_CODE_CC_4 = "EFORM_MAIL_GROUP04_CC";
	
	private List<PolicyParameter> policyList = new ArrayList<PolicyParameter>();
	private Parameter param = new Parameter();
	
	private File file1[];
	private String file1ContentType;
	private String file1FileName;
	
	private File file2;
	private File file3;
	
	private InputStream inputStream;

	public File[] getFile1() {
		return file1;
	}

	public void setFile1(File[] file1) {
		this.file1 = file1;
	}

	public String getFile1ContentType() {
		return file1ContentType;
	}

	public void setFile1ContentType(String file1ContentType) {
		this.file1ContentType = file1ContentType;
	}

	public String getFile1FileName() {
		return file1FileName;
	}

	public void setFile1FileName(String file1FileName) {
		this.file1FileName = file1FileName;
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

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List<PolicyParameter> getPolicyList() {
		return policyList;
	}

	public void setPolicyList(List<PolicyParameter> policyList) {
		this.policyList = policyList;
	}

	public Parameter getParam() {
		return param;
	}

	public void setParam(Parameter param) {
		this.param = param;
	}

	public String index() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On RequestChangeAction : index");
		}
		return super.index();
	}
	
	public String checkPolicyTypeOrd() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (LOG.isDebugEnabled()) {
					LOG.debug("~On RequestChangeAction : checkPolicyTypeOrd");
				}
				Map<String, Object> data = new HashMap<String, Object>();
				
				CssUser user = getSessionUser();
				Petition p = dwhService.countPolicyTypeORDByIdNo(user.getCardNo());
				
				data.put("status", p.isStatus());
				data.put("message", p.getMessage());
				result.setData(data);
			}
		}).run();
	}
	
	public String changePaymentModeChecking() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (LOG.isDebugEnabled()) {
					LOG.debug("~On RequestChangeAction : changePaymentModeChecking");
				}
				Map<String, Object> data = new HashMap<String, Object>();
				CssUser user = getSessionUser();
				Petition p = null;
				
				if(sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null){
					HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
					PolicyBean policy = policies.get(param.selectedPolicy);
					if(policy!=null){
						if(policy.getPolicyType().equals("O")){
							p = dwhService.changePaymentModeChecking(policy.getPrdCode(),param.selectedPaymentMode, policy.getPolicyYear());
							data.put("status", p.isStatus());
							data.put("message", p.getMessage());
							
							SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
							boolean b = checkRequestedEformType1And4(user.getCardNo(), policy.getPolicyNo(), "PTN01", sf.parse(sf.format(new Date())));
							data.put("requested", b);
						}else{
							data.put("status", false);
							data.put("message", "ไม่สามารถแก้ไขงวดการชำระเป็นราย "+param.selectedPaymentMode+" เดือน สำหรับกรมธรรม์นี้");
						}
					}
				}
				result.setData(data);
			}
		}).run();
	}
	
	public String checkPolicyTypeAll() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (LOG.isDebugEnabled()) {
					LOG.debug("~On RequestChangeAction : checkPolicyTypeAll");
				}
				Map<String, Object> data = new HashMap<String, Object>();
				
				CssUser user = getSessionUser();
				Petition p = dwhService.countPolicyTypeAllByIdNo(user.getCardNo());
				String requestType = param.selectedRequestType;
				
				data.put("status", p.isStatus());
				data.put("message", p.getMessage());
				
				SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
				boolean b = checkRequestedEformType2And3(user.getCardNo(), requestType, sf.parse(sf.format(new Date())));
				data.put("requested", b);
				
				result.setData(data);
			}
		}).run();
	}
	
	public String changeProvince() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (LOG.isDebugEnabled()) {
					LOG.debug("~On RequestChangeAction : changeProvince");
				}
				Map<String, Object> data = new HashMap<String, Object>();
				Gson g = new Gson();
				List<ThailandZipcode> districtList = dwhService.getActiveDistrictByProvinceCode(Integer.parseInt(param.selectedProvince));
				data.put("districtList", g.toJson(districtList));
				result.setData(data);
			}
		}).run();
	}
	
	public String changeDistrict() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (LOG.isDebugEnabled()) {
					LOG.debug("~On RequestChangeAction : changeDistrict");
				}
				Map<String, Object> data = new HashMap<String, Object>();
				Gson g = new Gson();
				List<ThailandZipcode> subdistrictList = dwhService.getActiveSubdistrictByProvinceCodeAndDistrictCode(Integer.parseInt(param.selectedProvince),Integer.parseInt(param.selectedDistrict));
				data.put("subdistrictList", g.toJson(subdistrictList));
				result.setData(data);
			}
		}).run();
	}
	
	public String changeReceiveChecking() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (LOG.isDebugEnabled()) {
					LOG.debug("~On RequestChangeAction : changeReceiveChecking");
				}
				Map<String, Object> data = new HashMap<String, Object>();
				CssUser user = getSessionUser();
				
				if(sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null){
					HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
					PolicyBean policy = policies.get(param.selectedPolicy);
					if(policy!=null){
						SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
						boolean b = checkRequestedEformType1And4(user.getCardNo(), policy.getPolicyNo(), "PTN04", sf.parse(sf.format(new Date())));
						data.put("requested", b);
					}
				}
				result.setData(data);
			}
		}).run();
	}
	
	public String installment() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On RequestChangeAction : installment");
		}
		
		CssUser user = getSessionUser();
		if (sessionAttributes.get(SESSION_ATTRIBUTE_USER) == null) {
			PersonBean person = dwhService.getPersonData(user.getCardNo());
			sessionAttributes.put(SESSION_ATTRIBUTE_USER, person);
		}
		
		if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) == null) {
			Map<String,PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(),user.getEmail());
			sessionAttributes.put(SESSION_ATTRIBUTE_POLICY_LIST, policies);
		}
		
		if(sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null){
			HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
			List<PolicyBean> policyList = new ArrayList<PolicyBean>(policies.values());
			if(policyList != null){
				Collections.sort(policyList);
			}
			
			Map<String,PolicyBean> rqcpList = new HashMap<String,PolicyBean>();
			int f = 0;
			for (PolicyBean policyBean : policyList) {
				if(checkRecordActiveForPetitionChange(policyBean)){
					rqcpList.put(policyBean.getPolicyNo(), policyBean);
					if(f == 0){
						PolicyBean lastestPolicy = policyBean;
						requestAttributes.put("lastestPolicy", lastestPolicy);
					}
					f++;
				}
			}
			requestAttributes.put("petitionPolicy", rqcpList);
			sessionAttributes.put("petitionPolicy", rqcpList);
		}

		return super.index();
	}
	
	
	
	@Autowired
    private FileUploadFetchComponent fileUploadFetchComponent;
	protected Map<String,Object> httpRequestParameters;
	public Map<String, Object> getHttpRequestParameters() {
        return httpRequestParameters;
    }

    public void setHttpRequestParameters(Map<String, Object> httpRequestParameters) {
        this.httpRequestParameters = httpRequestParameters;
    }
	public String saveInstallment() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On RequestChangeAction : saveInstallment");
		}
		
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				final PetitionChangePaymentPeriodBean bean = new PetitionChangePaymentPeriodBean();
				
				HashMap<String, PolicyBean> policies = null;
				if(sessionAttributes.get("petitionPolicy") != null){
					policies = (HashMap<String, PolicyBean>) sessionAttributes.get("petitionPolicy");
				}
				
				CssUser user = getSessionUser();
				
				SystemAuditLog  systemAuditLog = new SystemAuditLog();
				systemAuditLog.setCreateBy(user.getFullname());
				
				GregorianCalendar gcal = new GregorianCalendar();
			    XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			    systemAuditLog.setCreateDate(xgcal);
				
				systemAuditLog.setCreateSystem("CSS Web");
				
//				PetitionRequestorBean requestorBean = new PetitionRequestorBean();
//				String requestBy = (StringUtils.isNotBlank(user.getFullname()) ? user.getFullname() : "CSS Web");
//				requestorBean.setRequestBy(requestBy);
//				requestorBean.setRequestorEmail(user.getEmail());
//				requestorBean.setRequestorIdNo(user.getCardNo());
//				requestorBean.setRequestorTel(user.getTelNo());
				
				File[] fileArr = (File[]) requestParameters.get("file");
				String[] nameArr = (String[])requestParameters.get("fileFileName");
				String[] typeArr =  (String[])requestParameters.get("fileContentType");
				
				String policy[] = request.getParameterValues("policy");
				String installmentType = request.getParameter("installmentType");
				
				boolean b = false;
				for (String p : policy) {
					SimpleDateFormat chk = new SimpleDateFormat("dd/MM/yyyy");
					b = checkRequestedEformType1And4(user.getCardNo(), p, "PTN01", chk.parse(chk.format(new Date())));
				}
				
				//edit 28/12/59 (pentest)
				if(checkFileName(nameArr) && checkFileExtension(typeArr) && !b){
				
					PetitionAttachmentIdCardBean petitionAttachmentBean = null;
					try(InputStream is = new FileInputStream(fileArr[0]);){
						byte[] file = IOUtils.toByteArray(is);
						petitionAttachmentBean = new PetitionAttachmentIdCardBean();
						petitionAttachmentBean.setFileNameIdCard(nameArr[0]);
						petitionAttachmentBean.setFileObjectIdCard(file);
						petitionAttachmentBean.setFileTypeIdCard(typeArr[0]);
					}
					
					String emailPolicyNo = "";
					if(policy != null){
						if(policy.length == 1){
							emailPolicyNo = policy[0];
						}else if(policy.length > 1){
							emailPolicyNo = policy[0];
							for (int i = 1; i < policy.length; i++) {
								emailPolicyNo = emailPolicyNo + ", "+policy[i];
							}
						}
					}
					List<PetitionPolicyBean> ppbList = new ArrayList<PetitionPolicyBean>();
					for (String p : policy) {
						PolicyBean policyBean = policies.get(p);
						if(policyBean!=null){
							PetitionPolicyBean ppb = new PetitionPolicyBean();
							ppb.setPlanCode(policyBean.getPrdCode());
							ppb.setPlanDescription(policyBean.getPrdName());
							ppb.setPlanName(policyBean.getPrdName());
							
							
							if(policyBean.getBranchNo() == null){
								if(String.valueOf(policyBean.getPolicyOrg()).contains("0001")){
									ppb.setPolicyBranchCode("0001");
								}
							}else{
								ppb.setPolicyBranchCode(policyBean.getBranchNo());
							}
							
							BranchesEntity branch = branchesService.queryBranchNameByBranchCode(ppb.getPolicyBranchCode());
							ppb.setPolicyBranchName(branch == null ? null : branch.getBrnName());
							
							ppb.setPolicyNo(policyBean.getPolicyNo());
							ppb.setPolicyType(PolicyType.fromValue(policyBean.getPolicyType()));
							ppbList.add(ppb);
							
							//bean.setCurrentPeriod(policyBean.getPaymentMode());
							
						}
					}
					if(policy != null && policy.length > 0){
						bean.setCurrentPeriod(policies.get(policy[0]).getPaymentMode());
					}
					bean.setNewPeriod(convertInstallmentType(installmentType));
					bean.setSystemAuditLog(systemAuditLog);
					String requestBy = (StringUtils.isNotBlank(user.getFullname()) ? user.getFullname() : "CSS Web");
					bean.setRequestBy(requestBy);
					bean.setRequestorEmail(user.getEmail());
					bean.setRequestorIdNo(user.getCardNo());
					bean.setRequestorTel(user.getTelNo());
					bean.setIdCardAttachment(petitionAttachmentBean);
					bean.getPetitionPolicyList().addAll(ppbList);
					
					DmsDocument idCardDmsDocument = new DmsDocument();
					idCardDmsDocument.setFileName(petitionAttachmentBean.getFileNameIdCard());
					idCardDmsDocument.setMimeType(petitionAttachmentBean.getFileTypeIdCard());
					idCardDmsDocument.setContent(petitionAttachmentBean.getFileObjectIdCard());
					
					String requestNo = eformService.insertEformPetitionChangePaymentPeriod(bean);
	//				EformPetitionWSService eform = getService(EformPetitionWSService.class, eformws);
	//				String requestNo = eform.postEformPetitionChangePaymentPeriod(bean);
	//				String requestNo = "TEST00001";
					requestAttributes.put("reqNo", requestNo);
					/////////////////////////////////////////////////////////////////////////////////////  Send Email -> Save CssPetitionRequest
					if(requestNo != null){
						CssPetitionRequestEntity entity = new CssPetitionRequestEntity();
						entity.setCustomerCardNo(bean.getRequestorIdNo());
						entity.setCustomerName(user.getFirstName());
						entity.setCustomerSName(user.getLastName());
						entity.setCustomerTitle(user.getTitleDesc());
						entity.setCustomerId(user.getCustCode());
						entity.setRequestNo(requestNo);
						entity.setRequestType("PTN01");
						entity.setRequestBy(requestBy);
						entity.setCreateDate(new Date());
						
						List<CssPetitionPolicyEntity> cssPetitionPolicyList = new ArrayList<CssPetitionPolicyEntity>();
						for (PetitionPolicyBean petitionPolicyBean : ppbList) {
							CssPetitionPolicyEntity pEntity = new CssPetitionPolicyEntity();
							pEntity.setPlanCode(petitionPolicyBean.getPlanCode());
							pEntity.setPlanName(petitionPolicyBean.getPlanName());
							pEntity.setPolicyNo(petitionPolicyBean.getPolicyNo());
							pEntity.setPolicyType(petitionPolicyBean.getPolicyType().name());
							pEntity.setCreateDate(new Date());
							cssPetitionPolicyList.add(pEntity);
						}
						entity.setPetitionPolicyList(cssPetitionPolicyList);
						cssEFormService.saveCssPetitionRequest(entity);
					}
					/////////////////////////////////////////////////////////////////////////////////////  Call Web Service EForm -> Create Report
					InputStream inputStream = exportReport(bean, ppbList);
					DmsDocument reportDmsDocument = new DmsDocument();
					reportDmsDocument.setFileName("report-petition-modify.pdf");
					reportDmsDocument.setMimeType("text/plain");
					reportDmsDocument.setContent(IOUtils.toByteArray(inputStream));
					
					/////////////////////////////////////////////////////////////////////////////////////  Create Report -> Send Email
					SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
					List<String> ccList = new ArrayList<String>();
					String email = StringUtils.EMPTY;
					CssCommonDataEntity commonDataEmail = cssMasterDataService.getCommonDataEmail(KEY_GROUP, KEY_CODE_1);
					if(commonDataEmail != null && commonDataEmail.getKeyValueText() != null){
						email = commonDataEmail.getKeyValueText().trim();
					}
					List<CssCommonDataEntity> commonDataCCEmail = cssMasterDataService.getCommonDataCCEmail(KEY_GROUP, KEY_CODE_CC_1);
					for (CssCommonDataEntity cssCommonDataEntity : commonDataCCEmail) {
						if(cssCommonDataEntity.getKeyValueText() != null){
							ccList.add(cssCommonDataEntity.getKeyValueText().trim());
						}
					}
					if(email.equals(StringUtils.EMPTY) && ccList.size() > 0){
						email = ccList.get(0);
						ccList.remove(0);
					}
					if(!email.equals(StringUtils.EMPTY)){
						String emailSubject = createEmailSubject(requestNo, "1");
						String emailBody = createEmailBody(requestNo, emailPolicyNo, requestBy, "ขอเปลี่ยนงวดการชำระเบี้ยประกันภัย",sf.format(gcal.getTime()));
						sendEmailWithAttachFile(DEFAULT_EMAIL_SENDER_NAME, email, emailSubject, emailBody, ccList, reportDmsDocument,idCardDmsDocument);
					}
				}
			}
		}).run();
	}
	
	public String assured() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On RequestChangeAction : assured");
		}
		
		CssUser user = getSessionUser();
		if (sessionAttributes.get(SESSION_ATTRIBUTE_USER) == null) {
			PersonBean person = dwhService.getPersonData(user.getCardNo());
			sessionAttributes.put(SESSION_ATTRIBUTE_USER, person);
		}
		if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) == null) {
			Map<String,PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(),user.getEmail());
			sessionAttributes.put(SESSION_ATTRIBUTE_POLICY_LIST, policies);
		}
		
		Gson g = new Gson();
		List<Title> titleList = dwhService.getActiveTitle();
		requestAttributes.put("titleList", g.toJson(titleList));
		
		if(sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null){
			HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
			List<PolicyBean> policyList = new ArrayList<PolicyBean>(policies.values());
			if(policyList != null){
				Collections.sort(policyList);
			}
			
			Map<String,PolicyBean> rqcpList = new HashMap<String,PolicyBean>();
			int f = 0;
			for (PolicyBean policyBean : policyList) {
				if(checkRecordActiveForPetitionChange(policyBean)){
					rqcpList.put(policyBean.getPolicyNo(), policyBean);
					if(f == 0){
						PolicyBean lastestPolicy = policyBean;
						requestAttributes.put("lastestPolicy", lastestPolicy);
					}
					f++;
				}
			}
			requestAttributes.put("petitionPolicy", rqcpList);
			sessionAttributes.put("petitionPolicy", rqcpList);
		}
		
		return super.index();
	}
	
	public String saveAssured() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On RequestChangeAction : saveAssured");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				PetitionChangeCustomerNameBean bean = new PetitionChangeCustomerNameBean();
				
				HashMap<String, PolicyBean> policies = null;
				if(sessionAttributes.get("petitionPolicy") != null){
					policies = (HashMap<String, PolicyBean>) sessionAttributes.get("petitionPolicy");
				}
				
				CssUser user = getSessionUser();
				
				SystemAuditLog  systemAuditLog = new SystemAuditLog();
				systemAuditLog.setCreateBy(user.getFullname());
				
				GregorianCalendar gcal = new GregorianCalendar();
			    XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			    systemAuditLog.setCreateDate(xgcal);
				
				systemAuditLog.setCreateSystem("CSS Web");
				
				File[] fileArr = (File[]) requestParameters.get("file");
				String[] nameArr = (String[])requestParameters.get("fileFileName");
				String[] typeArr =  (String[])requestParameters.get("fileContentType");
				
				SimpleDateFormat chk = new SimpleDateFormat("dd/MM/yyyy");
				boolean b = checkRequestedEformType2And3(user.getCardNo(), "PTN02", chk.parse(chk.format(new Date())));
				
				//edit 28/12/59 (pentest)
				if(checkFileName(nameArr) && checkFileExtension(typeArr) && !b){
				
					PetitionAttachmentIdCardBean idCardAttachment = null;
					try(InputStream is = new FileInputStream(fileArr[0]);){
						byte[] fileIdNo = IOUtils.toByteArray(is);
						idCardAttachment = new PetitionAttachmentIdCardBean();
						idCardAttachment.setFileNameIdCard(nameArr[0].trim());
						idCardAttachment.setFileObjectIdCard(fileIdNo);
						idCardAttachment.setFileTypeIdCard(typeArr[0].trim());
					}
					
					PetitionAttachmentRenameBean renameAttachment = null;
					DmsDocument renameDmsDocument = null;
					if(fileArr.length > 1){
						try(InputStream is = new FileInputStream(fileArr[1]);){
							byte[] fileRename = IOUtils.toByteArray(is);
							renameAttachment = new PetitionAttachmentRenameBean();
							renameAttachment.setFileNameRename(nameArr[1].trim());
							renameAttachment.setFileObjectRename(fileRename);
							renameAttachment.setFileTypeRename(typeArr[1].trim());
						}
						
						renameDmsDocument = new DmsDocument();
						renameDmsDocument.setFileName(renameAttachment.getFileNameRename());
						renameDmsDocument.setMimeType(renameAttachment.getFileTypeRename());
						renameDmsDocument.setContent(renameAttachment.getFileObjectRename());
						bean.setRenameAttachment(renameAttachment);
					}
					
					PetitionAttachmentConsensusBean consensusAttachment = null;
					if(fileArr.length > 2){
						try(InputStream is = new FileInputStream(fileArr[2]);){
							byte[] fileConsensus = IOUtils.toByteArray(is);
							consensusAttachment = new PetitionAttachmentConsensusBean();
							consensusAttachment.setFileNameConsensus(nameArr[2].trim());
							consensusAttachment.setFileObjectConsensus(fileConsensus);
							consensusAttachment.setFileTypeConsensus(typeArr[2].trim());
						}
						bean.setConsensusAttachment(consensusAttachment);
						
					}
					
					List<PetitionPolicyBean> ppbList = new ArrayList<PetitionPolicyBean>();
					List<PolicyBean> policyList = new ArrayList<PolicyBean>(policies.values());
					String emailPolicyNo = "";
					if(policyList.size() == 1){
						emailPolicyNo = policyList.get(0).getPolicyNo();
					}else if(policyList.size() > 1){
						emailPolicyNo = policyList.get(0).getPolicyNo();
						for (int i = 1; i < policyList.size(); i++) {
							emailPolicyNo = emailPolicyNo + ", "+policyList.get(i).getPolicyNo();
						}
					}
					for (PolicyBean p : policyList) {
						PetitionPolicyBean ppb = new PetitionPolicyBean();
						ppb.setPlanCode(p.getPrdCode());
						ppb.setPlanDescription(p.getPrdName());
						ppb.setPlanName(p.getPrdName());
						ppb.setPolicyBranchCode(p.getBranchNo());
						
						if(p.getBranchNo() == null){
							if(String.valueOf(p.getPolicyOrg()).contains("0001")){
								ppb.setPolicyBranchCode("0001");
							}
						}else{
							ppb.setPolicyBranchCode(p.getBranchNo());
						}
						
						BranchesEntity branch = branchesService.queryBranchNameByBranchCode(ppb.getPolicyBranchCode());
						ppb.setPolicyBranchName(branch == null ? null : branch.getBrnName());
						
						ppb.setPolicyNo(p.getPolicyNo());
						ppb.setPolicyType(PolicyType.fromValue(p.getPolicyType()));
						ppbList.add(ppb);					
					}
					if(param.titleName.equals("อื่นๆ")){
						bean.setTitle(param.titleNameOther);
					}else{
						bean.setTitle(param.titleName);
					}
					
					bean.setName(param.firstname);
					bean.setSname(param.lastname);
					bean.setSystemAuditLog(systemAuditLog);
					String requestBy = (StringUtils.isNotBlank(user.getFullname()) ? user.getFullname() : "CSS Web");
					bean.setRequestBy(requestBy);
					bean.setRequestorEmail(user.getEmail());
					bean.setRequestorIdNo(user.getCardNo());
					bean.setRequestorTel(user.getTelNo());
					bean.setIdCardAttachment(idCardAttachment);
					bean.getPetitionPolicyList().addAll(ppbList);
					
					DmsDocument idCardDmsDocument = new DmsDocument();
					idCardDmsDocument.setFileName(idCardAttachment.getFileNameIdCard());
					idCardDmsDocument.setMimeType(idCardAttachment.getFileTypeIdCard());
					idCardDmsDocument.setContent(idCardAttachment.getFileObjectIdCard());
					
					String requestNo = eformService.insertEformPetitionChangeCustomerName(bean);
	//				String requestNo = "REQ201610000002";
					requestAttributes.put("reqNo", requestNo);
					/////////////////////////////////////////////////////////////////////////////////////  Send Email -> Save CssPetitionRequest
					if(requestNo != null){
						CssPetitionRequestEntity entity = new CssPetitionRequestEntity();
						entity.setCustomerCardNo(bean.getRequestorIdNo());
						entity.setCustomerName(user.getFirstName());
						entity.setCustomerSName(user.getLastName());
						entity.setCustomerTitle(user.getTitleDesc());
						entity.setCustomerId(user.getCustCode());
						entity.setRequestNo(requestNo);
						entity.setRequestType("PTN02");
						entity.setRequestBy(requestBy);
						entity.setCreateDate(new Date());
						
						List<CssPetitionPolicyEntity> cssPetitionPolicyList = new ArrayList<CssPetitionPolicyEntity>();
						for (PetitionPolicyBean petitionPolicyBean : ppbList) {
							CssPetitionPolicyEntity pEntity = new CssPetitionPolicyEntity();
							pEntity.setPlanCode(petitionPolicyBean.getPlanCode());
							pEntity.setPlanName(petitionPolicyBean.getPlanName());
							pEntity.setPolicyNo(petitionPolicyBean.getPolicyNo());
							pEntity.setPolicyType(petitionPolicyBean.getPolicyType().name());
							pEntity.setCreateDate(new Date());
							cssPetitionPolicyList.add(pEntity);
						}
						entity.setPetitionPolicyList(cssPetitionPolicyList);
						cssEFormService.saveCssPetitionRequest(entity);
					}
					/////////////////////////////////////////////////////////////////////////////////////  Call Web Service EForm -> Create Report
					InputStream inputStream = exportReport(bean, ppbList);
					DmsDocument reportDmsDocument = new DmsDocument();
					reportDmsDocument.setFileName("report-petition-modify.pdf");
					reportDmsDocument.setMimeType("text/plain");
					reportDmsDocument.setContent(IOUtils.toByteArray(inputStream));
					
					/////////////////////////////////////////////////////////////////////////////////////  Create Report -> Send Email
					SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
					List<String> ccList = new ArrayList<String>();
					String email = StringUtils.EMPTY;
					CssCommonDataEntity commonDataEmail = cssMasterDataService.getCommonDataEmail(KEY_GROUP, KEY_CODE_2);
					if(commonDataEmail != null && commonDataEmail.getKeyValueText() != null){
						email = commonDataEmail.getKeyValueText().trim();
					}
					List<CssCommonDataEntity> commonDataCCEmail = cssMasterDataService.getCommonDataCCEmail(KEY_GROUP, KEY_CODE_CC_2);
					for (CssCommonDataEntity cssCommonDataEntity : commonDataCCEmail) {
						if(cssCommonDataEntity.getKeyValueText() != null){
							ccList.add(cssCommonDataEntity.getKeyValueText().trim());
						}
					}
					if(email.equals(StringUtils.EMPTY) && ccList.size() > 0){
						email = ccList.get(0);
						ccList.remove(0);
					}
					if(!email.equals(StringUtils.EMPTY)){
						if(renameDmsDocument != null){
							sendEmailWithAttachFile(DEFAULT_EMAIL_SENDER_NAME, email, createEmailSubject(requestNo, "2"), createEmailBody(requestNo, emailPolicyNo, requestBy, "ขอเปลี่ยนแปลงแก้ไขชื่อ-นามสกุล ผู้เอาประกัน", sf.format(gcal.getTime())), ccList, reportDmsDocument, idCardDmsDocument, renameDmsDocument);
						}else{
							sendEmailWithAttachFile(DEFAULT_EMAIL_SENDER_NAME, email, createEmailSubject(requestNo, "2"), createEmailBody(requestNo, emailPolicyNo, requestBy, "ขอเปลี่ยนแปลงแก้ไขชื่อ-นามสกุล ผู้เอาประกัน", sf.format(gcal.getTime())), ccList, reportDmsDocument, idCardDmsDocument);
						}
					}
				}
			}
		}).run();
		
		
	}

	public String address() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On RequestChangeAction : address");
		}
		
		CssUser user = getSessionUser();
		if (sessionAttributes.get(SESSION_ATTRIBUTE_USER) == null) {
			PersonBean person = dwhService.getPersonData(user.getCardNo());
			sessionAttributes.put(SESSION_ATTRIBUTE_USER, person);
		}
		if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) == null) {
			Map<String,PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(),user.getEmail());
			sessionAttributes.put(SESSION_ATTRIBUTE_POLICY_LIST, policies);
		}
		if (sessionAttributes.get("sessionPolicyWithRegister") == null) {
			Map<String,PolicyBean> policyWithRegister = dwhService.getPolicyDataWithRegister(user.getCardNo(),user.getEmail());
			sessionAttributes.put("sessionPolicyWithRegister", policyWithRegister);
		}
		
		Gson g = new Gson();
		
		List<ProvinceBean> provinceBeanList = cssMasterDataService.getActiveProvince();
		
		requestAttributes.put("province", g.toJson(provinceBeanList));
		
		Map<String, List<DistrictBean>> districtBeanList = cssMasterDataService.getActiveDistrict();
		
		requestAttributes.put("district", g.toJson(districtBeanList));
		
		Map<String, List<SubDistrictBean>> subdistrictBeanList = cssMasterDataService.getActiveSubDistrict();
		
		requestAttributes.put("subdistrict", g.toJson(subdistrictBeanList));
		
		if(sessionAttributes.get("sessionPolicyWithRegister") != null){
			HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get("sessionPolicyWithRegister");
			List<PolicyBean> policyList = new ArrayList<PolicyBean>(policies.values());
			if(policyList != null){
				Collections.sort(policyList);
			}
			
			Map<String,PolicyBean> rqcpList = new HashMap<String,PolicyBean>();
			int f = 0;
			for (PolicyBean policyBean : policyList) {
				if(checkRecordActiveForPetitionChange(policyBean)){
					rqcpList.put(policyBean.getPolicyNo(), policyBean);
					if(f == 0){
						PolicyBean lastestPolicy = policyBean;
						requestAttributes.put("lastestPolicy", lastestPolicy);
					}
					f++;
				}
			}
			requestAttributes.put("petitionPolicy", rqcpList);
			sessionAttributes.put("petitionPolicy", rqcpList);
			
		}
		
		return super.index();
	}
	
	public String saveAddress() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On RequestChangeAction : saveAddress");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				PetitionChangeAddressBean bean = new PetitionChangeAddressBean();
				
				HashMap<String, PolicyBean> policies = null;
				if(sessionAttributes.get("petitionPolicy") != null){
					policies = (HashMap<String, PolicyBean>) sessionAttributes.get("petitionPolicy");
				}
				
				CssUser user = getSessionUser();
				
				SystemAuditLog  systemAuditLog = new SystemAuditLog();
				systemAuditLog.setCreateBy(user.getFullname());
				
				GregorianCalendar gcal = new GregorianCalendar();
			    XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			    systemAuditLog.setCreateDate(xgcal);
				
				systemAuditLog.setCreateSystem("CSS Web");
				
				File[] fileArr = (File[]) requestParameters.get("file");
				String[] nameArr = (String[])requestParameters.get("fileFileName");
				String[] typeArr =  (String[])requestParameters.get("fileContentType");
				
				SimpleDateFormat chk = new SimpleDateFormat("dd/MM/yyyy");
				boolean b = checkRequestedEformType2And3(user.getCardNo(), "PTN03", chk.parse(chk.format(new Date())));
				
				//edit 28/12/59 (pentest)
				if(checkFileName(nameArr) && checkFileExtension(typeArr) && !b){
				
					PetitionAttachmentIdCardBean idCardAttachment = null;
					try(InputStream is = new FileInputStream(fileArr[0]);){
						byte[] fileIdNo = IOUtils.toByteArray(is);
						idCardAttachment = new PetitionAttachmentIdCardBean();
						
						idCardAttachment.setFileNameIdCard(nameArr[0].trim());
						idCardAttachment.setFileObjectIdCard(fileIdNo);
						idCardAttachment.setFileTypeIdCard(typeArr[0].trim());
					}
					
					PetitionAttachmentConsensusBean consensusAttachment = null;
					try(InputStream is = new FileInputStream(fileArr[1]);){
						byte[] fileConsensus = IOUtils.toByteArray(is);
						consensusAttachment = new PetitionAttachmentConsensusBean();
						
						consensusAttachment.setFileNameConsensus(nameArr[1].trim());
						consensusAttachment.setFileObjectConsensus(fileConsensus);
						consensusAttachment.setFileTypeConsensus(typeArr[1].trim());
					}
					
					
					List<PetitionPolicyBean> ppbList = new ArrayList<PetitionPolicyBean>();
					List<PolicyBean> policyList = new ArrayList<PolicyBean>(policies.values());
					
					String emailPolicyNo = "";
					if(policyList.size() == 1){
						emailPolicyNo = policyList.get(0).getPolicyNo();
					}else if(policyList.size() > 1){
						emailPolicyNo = policyList.get(0).getPolicyNo();
						for (int i = 1; i < policyList.size(); i++) {
							emailPolicyNo = emailPolicyNo + ", "+policyList.get(i).getPolicyNo();
						}
					}
					for (PolicyBean p : policyList) {
						PetitionPolicyBean ppb = new PetitionPolicyBean();
						ppb.setPlanCode(p.getPrdCode());
						ppb.setPlanDescription(p.getPrdName());
						ppb.setPlanName(p.getPrdName());
						if(p.getBranchNo() == null){
							if(String.valueOf(p.getPolicyOrg()).contains("0001")){
								ppb.setPolicyBranchCode("0001");
							}
						}else{
							ppb.setPolicyBranchCode(p.getBranchNo());
						}
						
						BranchesEntity branch = branchesService.queryBranchNameByBranchCode(ppb.getPolicyBranchCode());
						ppb.setPolicyBranchName(branch == null ? null : branch.getBrnName());
						
						ppb.setPolicyNo(p.getPolicyNo());
						ppb.setPolicyType(PolicyType.fromValue(p.getPolicyType()));
						ppbList.add(ppb);					
					}
					
					String addr = StringUtils.contains(param.address, "\r\n") ? StringUtils.replace(param.address, "\r\n", "") : param.address;
					
					//bean.setAddress(param.address);
					bean.setAddress(addr);
					bean.setProvince(param.povDesc);
					bean.setDistrict(param.disDesc);
					bean.setSubDistrict(param.subdistrictName);
					bean.setZipCode(param.zipcode);
					
					bean.setSystemAuditLog(systemAuditLog);
					String requestBy = (StringUtils.isNotBlank(user.getFullname()) ? user.getFullname() : "CSS Web");
					bean.setRequestBy(requestBy);
					bean.setRequestorEmail(user.getEmail());
					bean.setRequestorIdNo(user.getCardNo());
					bean.setRequestorTel(user.getTelNo());
					bean.setIdCardAttachment(idCardAttachment);
					bean.setConsensusAttachment(consensusAttachment);
					bean.getPetitionPolicyList().addAll(ppbList);
					
					DmsDocument idCardDmsDocument = new DmsDocument();
					idCardDmsDocument.setFileName(idCardAttachment.getFileNameIdCard());
					idCardDmsDocument.setMimeType(idCardAttachment.getFileTypeIdCard());
					idCardDmsDocument.setContent(idCardAttachment.getFileObjectIdCard());
					
					DmsDocument consensusDocument = new DmsDocument();
					consensusDocument.setFileName(consensusAttachment.getFileNameConsensus());
					consensusDocument.setMimeType(consensusAttachment.getFileTypeConsensus());
					consensusDocument.setContent(consensusAttachment.getFileObjectConsensus());
					
					String requestNo = eformService.insertEformPetitionChangeAddress(bean);
	//				String requestNo = "REQ201610000002";
					requestAttributes.put("reqNo", requestNo);
					/////////////////////////////////////////////////////////////////////////////////////  Save CssPetitionRequest
					if(requestNo != null){
						CssPetitionRequestEntity entity = new CssPetitionRequestEntity();
						entity.setCustomerCardNo(bean.getRequestorIdNo());
						entity.setCustomerName(user.getFirstName());
						entity.setCustomerSName(user.getLastName());
						entity.setCustomerTitle(user.getTitleDesc());
						entity.setCustomerId(user.getCustCode());
						entity.setRequestNo(requestNo);
						entity.setRequestType("PTN03");
						entity.setRequestBy(requestBy);
						entity.setCreateDate(new Date());
						
						List<CssPetitionPolicyEntity> cssPetitionPolicyList = new ArrayList<CssPetitionPolicyEntity>();
						for (PetitionPolicyBean petitionPolicyBean : ppbList) {
							CssPetitionPolicyEntity pEntity = new CssPetitionPolicyEntity();
							pEntity.setPlanCode(petitionPolicyBean.getPlanCode());
							pEntity.setPlanName(petitionPolicyBean.getPlanName());
							pEntity.setPolicyNo(petitionPolicyBean.getPolicyNo());
							pEntity.setPolicyType(petitionPolicyBean.getPolicyType().name());
							pEntity.setCreateDate(new Date());
							cssPetitionPolicyList.add(pEntity);
						}
						entity.setPetitionPolicyList(cssPetitionPolicyList);
						cssEFormService.saveCssPetitionRequest(entity);
					}
					/////////////////////////////////////////////////////////////////////////////////////  Call Web Service EForm -> Create Report
					InputStream inputStream = exportReport(bean, ppbList);
					DmsDocument reportDmsDocument = new DmsDocument();
					reportDmsDocument.setFileName("report-petition-modify.pdf");
					reportDmsDocument.setMimeType("text/plain");
					reportDmsDocument.setContent(IOUtils.toByteArray(inputStream));
					
					/////////////////////////////////////////////////////////////////////////////////////  Create Report -> Send Email
					SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
					List<String> ccList = new ArrayList<String>();
					String email = StringUtils.EMPTY;
					CssCommonDataEntity commonDataEmail = cssMasterDataService.getCommonDataEmail(KEY_GROUP, KEY_CODE_3);
					if(commonDataEmail != null && commonDataEmail.getKeyValueText() != null){
						email = commonDataEmail.getKeyValueText().trim();
					}
					List<CssCommonDataEntity> commonDataCCEmail = cssMasterDataService.getCommonDataCCEmail(KEY_GROUP, KEY_CODE_CC_3);
					for (CssCommonDataEntity cssCommonDataEntity : commonDataCCEmail) {
						if(cssCommonDataEntity.getKeyValueText() != null){
							ccList.add(cssCommonDataEntity.getKeyValueText().trim());
						}
					}
					if(email.equals(StringUtils.EMPTY) && ccList.size() > 0){
						email = ccList.get(0);
						ccList.remove(0);
					}
					if(!email.equals(StringUtils.EMPTY)){
						sendEmailWithAttachFile(DEFAULT_EMAIL_SENDER_NAME, email, createEmailSubject(requestNo, "3"), createEmailBody(requestNo, emailPolicyNo, requestBy, "ขอเปลี่ยนแปลงที่อยู่ตามทะเบียนบ้าน", sf.format(gcal.getTime())), ccList, reportDmsDocument, idCardDmsDocument, consensusDocument);
					}
				}
			}
		}).run();
		
		
	}
	
	public String receive() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On RequestChangeAction : receive");
		}
		
		CssUser user = getSessionUser();
		if (sessionAttributes.get(SESSION_ATTRIBUTE_USER) == null) {
			PersonBean person = dwhService.getPersonData(user.getCardNo());
			sessionAttributes.put(SESSION_ATTRIBUTE_USER, person);
		}
		if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) == null) {
			Map<String,PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(),user.getEmail());
			sessionAttributes.put(SESSION_ATTRIBUTE_POLICY_LIST, policies);
		}
	
		Gson g = new Gson();
//		List<BankBean> bankBeanList = cssMasterDataService.getBank();
//		requestAttributes.put("bankList", g.toJson(bankBeanList));
		List<ThailandBank> bankList = dwhService.getActiveBank();
		requestAttributes.put("bankList", g.toJson(bankList));
		
		if(sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null){
			HashMap<String, PolicyBean> policies = (HashMap<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
			List<PolicyBean> policyList = new ArrayList<PolicyBean>(policies.values());
			Map<String,PolicyBean> policyMap = new HashMap<String,PolicyBean>();
			for (PolicyBean policyBean : policyList) {
				List<ClaimChannel> rList = dwhService.getClaimChannelByPolicyNoAndPolicyType(policyBean.getPolicyNo(), policyBean.getPolicyType());
				if(rList != null && rList.size() > 0){
					ClaimChannel cc = rList.get(0);
					policyBean.setBenefitChannel(StringUtils.trimToEmpty(cc.getAcName())+" "+StringUtils.trimToEmpty(cc.getAcNo())+" "+StringUtils.trimToEmpty(cc.getBankNameThai())+" "+StringUtils.trimToEmpty(cc.getBrnName()));
					policyMap.put(policyBean.getPolicyNo(), policyBean);
				}else{
					policyMap.put(policyBean.getPolicyNo(), policyBean);
				}
			}
			sessionAttributes.put(SESSION_ATTRIBUTE_POLICY_LIST, policyMap);
			
			if(policyList != null){
				Collections.sort(policyList);
			}
			
			Map<String,PolicyBean> rqcpList = new HashMap<String,PolicyBean>();
			int f = 0;
			for (PolicyBean policyBean : policyList) {
				if(checkRecordActiveForPetitionChange(policyBean)){
					rqcpList.put(policyBean.getPolicyNo(), policyBean);
					if(f == 0){
						PolicyBean lastestPolicy = policyBean;
						requestAttributes.put("lastestPolicy", lastestPolicy);
					}
					f++;
				}
			}
			requestAttributes.put("petitionPolicy", rqcpList);
			sessionAttributes.put("petitionPolicy", rqcpList);
		}
		
		return super.index();
	}
	
	public String saveReceive() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On RequestChangeAction : saveReceive");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				PetitionChangeBenefitPaymentBean bean = new PetitionChangeBenefitPaymentBean();
				
				HashMap<String, PolicyBean> policies = null;
				if(sessionAttributes.get("petitionPolicy") != null){
					policies = (HashMap<String, PolicyBean>) sessionAttributes.get("petitionPolicy");
				}
				
				CssUser user = getSessionUser();
				
				SystemAuditLog  systemAuditLog = new SystemAuditLog();
				systemAuditLog.setCreateBy(user.getFullname());
				
				GregorianCalendar gcal = new GregorianCalendar();
			    XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			    systemAuditLog.setCreateDate(xgcal);
				
				systemAuditLog.setCreateSystem("CSS Web");
				
				File[] fileArr = (File[]) requestParameters.get("file");
				String[] nameArr = (String[])requestParameters.get("fileFileName");
				String[] typeArr =  (String[])requestParameters.get("fileContentType");
				
				String policy[] = request.getParameterValues("policy");
				
				boolean b = false;
				for (String p : policy) {
					SimpleDateFormat chk = new SimpleDateFormat("dd/MM/yyyy");
					b = checkRequestedEformType1And4(user.getCardNo(), p, "PTN04", chk.parse(chk.format(new Date())));
				}
				
				//edit 28/12/59 (pentest)
				if(checkFileName(nameArr) && checkFileExtension(typeArr) && !b){
				
					PetitionAttachmentIdCardBean petitionAttachmentBean = null;
					try(InputStream is = new FileInputStream(fileArr[0]);){
						byte[] file = IOUtils.toByteArray(is);
						petitionAttachmentBean = new PetitionAttachmentIdCardBean();
						
						petitionAttachmentBean.setFileNameIdCard(nameArr[0]);
						petitionAttachmentBean.setFileObjectIdCard(file);
						petitionAttachmentBean.setFileTypeIdCard(typeArr[0]);
					}
					
					PetitionAttachmentBookBankBean petitionAttachmentBookBean = null;
					try(InputStream is = new FileInputStream(fileArr[1]);){
						byte[] file = IOUtils.toByteArray(is);
						petitionAttachmentBookBean = new PetitionAttachmentBookBankBean();
						
						petitionAttachmentBookBean.setFileNameBookBank(nameArr[1]);
						petitionAttachmentBookBean.setFileObjectBookBank(file);
						petitionAttachmentBookBean.setFileTypeBookBank(typeArr[1]);
					}
					
					
					String emailPolicyNo = "";
					if(policy != null){
						if(policy.length == 1){
							emailPolicyNo = policy[0];
						}else if(policy.length > 1){
							emailPolicyNo = policy[0];
							for (int i = 1; i < policy.length; i++) {
								emailPolicyNo = emailPolicyNo + ", "+policy[i];
							}
						}
					}
					List<PetitionPolicyBean> ppbList = new ArrayList<PetitionPolicyBean>();
					for (String p : policy) {
						PolicyBean policyBean = policies.get(p);
						if(policyBean!=null){
							PetitionPolicyBean ppb = new PetitionPolicyBean();
							ppb.setPlanCode(policyBean.getPrdCode());
							ppb.setPlanDescription(policyBean.getPrdName());
							ppb.setPlanName(policyBean.getPrdName());
							if(policyBean.getBranchNo() == null){
								if("8130001".equals(String.valueOf(policyBean.getPolicyOrg()))){
									ppb.setPolicyBranchCode("0001");
								}
							}else{
								ppb.setPolicyBranchCode(policyBean.getBranchNo());
							}
							
							BranchesEntity branch = branchesService.queryBranchNameByBranchCode(ppb.getPolicyBranchCode());
							ppb.setPolicyBranchName(branch == null ? null : branch.getBrnName());
							
							ppb.setPolicyNo(policyBean.getPolicyNo());
							ppb.setPolicyType(PolicyType.fromValue(policyBean.getPolicyType()));
							ppbList.add(ppb);				
						}
					}
					bean.setAccount(param.account);
					bean.setAccountNo(param.accountNo);
					bean.setBankName(param.bankDesc);
					bean.setBranch(param.branch);
					bean.setSystemAuditLog(systemAuditLog);
					String requestBy = (StringUtils.isNotBlank(user.getFullname()) ? user.getFullname() : "CSS Web");
					bean.setRequestBy(requestBy);
					bean.setRequestorEmail(user.getEmail());
					bean.setRequestorIdNo(user.getCardNo());
					bean.setRequestorTel(user.getTelNo());
					bean.setIdCardAttachment(petitionAttachmentBean);
					bean.setBookBankAttachment(petitionAttachmentBookBean);
					bean.getPetitionPolicyList().addAll(ppbList);
					
					DmsDocument idCardDmsDocument = new DmsDocument();
					idCardDmsDocument.setFileName(petitionAttachmentBean.getFileNameIdCard());
					idCardDmsDocument.setMimeType(petitionAttachmentBean.getFileTypeIdCard());
					idCardDmsDocument.setContent(petitionAttachmentBean.getFileObjectIdCard());
					
					DmsDocument bookDmsDocument = new DmsDocument();
					bookDmsDocument.setFileName(petitionAttachmentBookBean.getFileNameBookBank());
					bookDmsDocument.setMimeType(petitionAttachmentBookBean.getFileTypeBookBank());
					bookDmsDocument.setContent(petitionAttachmentBookBean.getFileObjectBookBank());
					
					String requestNo = eformService.insertEformPetitionChangeBenefitPayment(bean);
	//				String requestNo = "REQ201610000002";
					requestAttributes.put("reqNo", requestNo);
					///////////////////////////////////////////////////////////////////////////////////// Save CssPetitionRequest
					if(requestNo != null){
						CssPetitionRequestEntity entity = new CssPetitionRequestEntity();
						entity.setCustomerCardNo(bean.getRequestorIdNo());
						entity.setCustomerName(user.getFirstName());
						entity.setCustomerSName(user.getLastName());
						entity.setCustomerTitle(user.getTitleDesc());
						entity.setCustomerId(user.getCustCode());
						entity.setRequestNo(requestNo);
						entity.setRequestType("PTN04");
						entity.setRequestBy(requestBy);
						entity.setCreateDate(new Date());
						
						List<CssPetitionPolicyEntity> cssPetitionPolicyList = new ArrayList<CssPetitionPolicyEntity>();
						for (PetitionPolicyBean petitionPolicyBean : ppbList) {
							CssPetitionPolicyEntity pEntity = new CssPetitionPolicyEntity();
							pEntity.setPlanCode(petitionPolicyBean.getPlanCode());
							pEntity.setPlanName(petitionPolicyBean.getPlanName());
							pEntity.setPolicyNo(petitionPolicyBean.getPolicyNo());
							pEntity.setPolicyType(petitionPolicyBean.getPolicyType().name());
							pEntity.setCreateDate(new Date());
							cssPetitionPolicyList.add(pEntity);
						}
						entity.setPetitionPolicyList(cssPetitionPolicyList);
						cssEFormService.saveCssPetitionRequest(entity);
					}
					/////////////////////////////////////////////////////////////////////////////////////  Call Web Service EForm -> Create Report
					InputStream inputStream = exportReport(bean, ppbList);
					DmsDocument reportDmsDocument = new DmsDocument();
					reportDmsDocument.setFileName("report-petition-modify.pdf");
					reportDmsDocument.setMimeType("text/plain");
					reportDmsDocument.setContent(IOUtils.toByteArray(inputStream));
					
					/////////////////////////////////////////////////////////////////////////////////////  Create Report -> Send Email
					SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
					List<String> ccList = new ArrayList<String>();
					String email = StringUtils.EMPTY;
					CssCommonDataEntity commonDataEmail = cssMasterDataService.getCommonDataEmail(KEY_GROUP, KEY_CODE_4);
					if(commonDataEmail != null && commonDataEmail.getKeyValueText() != null){
						email = commonDataEmail.getKeyValueText().trim();
					}
					List<CssCommonDataEntity> commonDataCCEmail = cssMasterDataService.getCommonDataCCEmail(KEY_GROUP, KEY_CODE_CC_4);
					for (CssCommonDataEntity cssCommonDataEntity : commonDataCCEmail) {
						if(cssCommonDataEntity.getKeyValueText() != null){
							ccList.add(cssCommonDataEntity.getKeyValueText().trim());
						}
					}
					if(email.equals(StringUtils.EMPTY) && ccList.size() > 0){
						email = ccList.get(0);
						ccList.remove(0);
					}
					if(!email.equals(StringUtils.EMPTY)){
						String emailSubject = createEmailSubject(requestNo, "4");
						String emailBody = createEmailBody(requestNo, emailPolicyNo, requestBy, "ขอเปลี่ยนแปลงวิธีรับเงินผลประโยชน์", sf.format(gcal.getTime()));
						sendEmailWithAttachFile(DEFAULT_EMAIL_SENDER_NAME, email, emailSubject, emailBody, ccList, reportDmsDocument, idCardDmsDocument ,bookDmsDocument);
					}
				}
			}
		}).run();
	}
	
	private String createEmailSubject(String requestNo, String type){
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
		String nDate = sf.format(new Date());
		String subject = "";
		if(type.equals("1")){
			subject = "แจ้งการขอเปลี่ยนงวดการชำระเบี้ยผ่านช่องทาง CSS Web คำร้องเลขที่ "+requestNo+" วันที่ "+ nDate;
		}else if(type.equals("2")){
			subject = "แจ้งการขอเปลี่ยนแปลงแก้ไขชื่อ-นามสกุล ผู้เอาประกันผ่านช่องทาง CSS Web คำร้องเลขที่ "+requestNo+" วันที่ "+ nDate;
		}else if(type.equals("3")){
			subject = "แจ้งการขอเปลี่ยนแปลงที่อยู่ตามทะเบียนบ้านผ่านช่องทาง CSS Web คำร้องเลขที่ "+requestNo+" วันที่ "+ nDate;
		}else if(type.equals("4")){
			subject = "แจ้งการขอเปลี่ยนแปลงวิธีรับเงินผลประโยชน์ผ่านช่องทาง CSS Web คำร้องเลขที่ "+requestNo+" วันที่ "+ nDate;
		}
		return subject;
	}
	
	private InputStream exportReport(PetitionChangePaymentPeriodBean bean, List<PetitionPolicyBean> ppbList){
		Map<String, Object> rParams = new HashMap<String, Object>();
		
		/////////////////////////////////////////////////////////////////////////////////////// set parameter
		List<PetitionPolicyBean> pList = ppbList;		
		String policyNo = "";
		String policyOther = "";
		String branchName = "";
		String branchCode = "";
		if(pList != null && pList.size() > 1){
			policyNo= pList.get(0).getPolicyNo();
			branchName = pList.get(0).getPolicyBranchName();
			branchCode = pList.get(0).getPolicyBranchCode();

//			for (int i = 1; i < pList.size(); i++) {
//				policyNo = policyNo + ", " + pList.get(i).getPolicyNo();
//			}
		}else if(pList != null && pList.size() == 1){
			policyNo = pList.get(0).getPolicyNo();
			branchName = pList.get(0).getPolicyBranchName();
			branchCode = pList.get(0).getPolicyBranchCode();
		}
		rParams.put("policyNo", policyNo);
		rParams.put("branchName", branchName);
		rParams.put("branchCode", branchCode);
		
		//telephone
		String[] telephoneArr = bean.getRequestorTel().split("");
		int index=1;
		for(int i=0; i<(telephoneArr.length-1); i++){
			rParams.put("telephone"+(index),telephoneArr[index]);
			index++;
		}
		
		
		if(bean.getSystemAuditLog().getCreateDate() != null){
			SimpleDateFormat sfDate = new SimpleDateFormat("dd", new Locale("th", "TH"));
			SimpleDateFormat sfMonth = new SimpleDateFormat("MMMM", new Locale("th", "TH"));
			SimpleDateFormat sfYear = new SimpleDateFormat("yyyy", new Locale("th", "TH"));
			Date processDate = bean.getSystemAuditLog().getCreateDate().toGregorianCalendar().getTime();
			String date = sfDate.format(processDate);
			String month = sfMonth.format(processDate);
			String year = sfYear.format(processDate);
			
			rParams.put("day", date);
			rParams.put("month", month);
			rParams.put("year", year);
		}
		
		rParams.put("insured", bean.getRequestBy());
		if(bean.getRequestorIdNo() != null){
			String[] idNoArr = bean.getRequestorIdNo().split("");
			int count = 1;
			for (int i = 0; i < (idNoArr.length-1); i++) {
				rParams.put("idNo"+count, idNoArr[count]);
				count++;
			}
		}
		
		rParams.put("requestType", "1");
		if(bean.getNewPeriod() == 1){
			rParams.put("mode", "4");
		}else if(bean.getNewPeriod() == 3){
			rParams.put("mode", "3");
		}else if(bean.getNewPeriod() == 6){
			rParams.put("mode", "2");
		}else if(bean.getNewPeriod() == 12){
			rParams.put("mode", "1");
		}

		////////////////////////////////////////////////////////////////////////////////////////////
		
		
		JasperRender jasperRender = new JasperRender();
		jasperRender.setParams(rParams);
		List<PetitionPolicyBean> reportList = new ArrayList<PetitionPolicyBean>();
		int i = 0;
		for (PetitionPolicyBean p : pList) {
			if(i != 0){
				reportList.add(p);
			}
			i++;
		}
		jasperRender.setBeanList(reportList);

		try {
			InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page01.jrxml");
			InputStream inp2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page02.jrxml");
			InputStream inp3 = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page03.jrxml");
			
			byte[] rr = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp));
			byte[] rr2 = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp2));
			ByteArrayInputStream inputStream = null;
			if(reportList.size() > 0){
				byte[] rr3 = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp3));
				ByteArrayInputStream is = PdfMergeUtils.merge(new ByteArrayInputStream(rr),new ByteArrayInputStream(rr2));
				inputStream = PdfMergeUtils.merge(is, new ByteArrayInputStream(rr3));
			}else{
				inputStream = PdfMergeUtils.merge(new ByteArrayInputStream(rr),new ByteArrayInputStream(rr2));
			}
			return inputStream;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private InputStream exportReport(PetitionChangeCustomerNameBean bean, List<PetitionPolicyBean> ppbList){
		Map<String, Object> rParams = new HashMap<String, Object>();
		
		/////////////////////////////////////////////////////////////////////////////////////// set parameter
		List<PetitionPolicyBean> pList = ppbList;		
		String policyNo = "";
		String policyOther = "";
		String branchName = "";
		String branchCode = "";
		if(pList != null && pList.size() > 1){
			policyNo= pList.get(0).getPolicyNo();
			branchName = pList.get(0).getPolicyBranchName();
			branchCode = pList.get(0).getPolicyBranchCode();

//			for (int i = 1; i < pList.size(); i++) {
//				policyNo = policyNo + ", " + pList.get(i).getPolicyNo();
//			}
		}else if(pList != null && pList.size() == 1){
			policyNo = pList.get(0).getPolicyNo();
			branchName = pList.get(0).getPolicyBranchName();
			branchCode = pList.get(0).getPolicyBranchCode();
		}
		rParams.put("policyNo", policyNo);
		rParams.put("branchName", branchName);
		rParams.put("branchCode", branchCode);
		
		///telephone
		String[] telephoneArr = bean.getRequestorTel().split("");
		int index=1;
		for(int i=0; i<(telephoneArr.length-1); i++){
			rParams.put("telephone"+(index),telephoneArr[index]);
			index++;
		}
		
		
		if(bean.getSystemAuditLog().getCreateDate() != null){
			SimpleDateFormat sfDate = new SimpleDateFormat("dd", new Locale("th", "TH"));
			SimpleDateFormat sfMonth = new SimpleDateFormat("MMMM", new Locale("th", "TH"));
			SimpleDateFormat sfYear = new SimpleDateFormat("yyyy", new Locale("th", "TH"));
			Date processDate = bean.getSystemAuditLog().getCreateDate().toGregorianCalendar().getTime();
			String date = sfDate.format(processDate);
			String month = sfMonth.format(processDate);
			String year = sfYear.format(processDate);
			
			rParams.put("day", date);
			rParams.put("month", month);
			rParams.put("year", year);
		}
		
		rParams.put("insured", bean.getRequestBy());
		if(bean.getRequestorIdNo() != null){
			String[] idNoArr = bean.getRequestorIdNo().split("");
			int count = 1;
			for (int i = 0; i < (idNoArr.length-1); i++) {
				rParams.put("idNo"+count, idNoArr[count]);
				count++;
			}
		}
		
		rParams.put("requestType", "2");
		rParams.put("changeFullname", bean.getTitle()+bean.getName()+ " " + bean.getSname());

		////////////////////////////////////////////////////////////////////////////////////////////
		
		JasperRender jasperRender = new JasperRender();
		jasperRender.setParams(rParams);
		List<PetitionPolicyBean> reportList = new ArrayList<PetitionPolicyBean>();
		int i = 0;
		for (PetitionPolicyBean p : pList) {
			if(i != 0){
				reportList.add(p);
			}
			i++;
		}
		jasperRender.setBeanList(reportList);
		try {
			InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page01.jrxml");
			InputStream inp2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page02.jrxml");
			InputStream inp3 = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page03.jrxml");
			
			byte[] rr = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp));
			byte[] rr2 = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp2));
			ByteArrayInputStream inputStream = null;
			if(reportList.size() > 0){
				byte[] rr3 = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp3));
				ByteArrayInputStream is = PdfMergeUtils.merge(new ByteArrayInputStream(rr),new ByteArrayInputStream(rr2));
				inputStream = PdfMergeUtils.merge(is, new ByteArrayInputStream(rr3));
			}else{
				inputStream = PdfMergeUtils.merge(new ByteArrayInputStream(rr),new ByteArrayInputStream(rr2));
			}
			return inputStream;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private InputStream exportReport(PetitionChangeAddressBean bean, List<PetitionPolicyBean> ppbList){
		Map<String, Object> rParams = new HashMap<String, Object>();
		
		/////////////////////////////////////////////////////////////////////////////////////// set parameter
		List<PetitionPolicyBean> pList = ppbList;		
		String policyNo = "";
		String policyOther = "";
		String branchName = "";
		String branchCode = "";
		if(pList != null && pList.size() > 1){
			policyNo= pList.get(0).getPolicyNo();
			branchName = pList.get(0).getPolicyBranchName();
			branchCode = pList.get(0).getPolicyBranchCode();
			
//			policyOther = pList.get(1).getPolicyNo();
//			for (int i = 2; i < pList.size(); i++) {
//				policyOther = policyOther + ", " + pList.get(i).getPolicyNo();
//			}
		}else if(pList != null && pList.size() == 1){
			policyNo = pList.get(0).getPolicyNo();
			branchName = pList.get(0).getPolicyBranchName();
			branchCode = pList.get(0).getPolicyBranchCode();
		}
		rParams.put("policyNo", policyNo);
		rParams.put("policyOther", policyOther);
		rParams.put("branchName", branchName);
		rParams.put("branchCode", branchCode);
		
		//telephone
		String[] telephoneArr = bean.getRequestorTel().split("");
		int index=1;
		for(int i=0; i<(telephoneArr.length-1); i++){
			rParams.put("telephone"+(index),telephoneArr[index]);
			index++;
		}
		
		
		if(bean.getSystemAuditLog().getCreateDate() != null){
			SimpleDateFormat sfDate = new SimpleDateFormat("dd", new Locale("th", "TH"));
			SimpleDateFormat sfMonth = new SimpleDateFormat("MMMM", new Locale("th", "TH"));
			SimpleDateFormat sfYear = new SimpleDateFormat("yyyy", new Locale("th", "TH"));
			Date processDate = bean.getSystemAuditLog().getCreateDate().toGregorianCalendar().getTime();
			String date = sfDate.format(processDate);
			String month = sfMonth.format(processDate);
			String year = sfYear.format(processDate);
			
			rParams.put("day", date);
			rParams.put("month", month);
			rParams.put("year", year);
		}
		
		rParams.put("insured", bean.getRequestBy());
		if(bean.getRequestorIdNo() != null){
			String[] idNoArr = bean.getRequestorIdNo().split("");
			int count = 1;
			for (int i = 0; i < (idNoArr.length-1); i++) {
				rParams.put("idNo"+count, idNoArr[count]);
				count++;
			}
		}
		
		rParams.put("requestType", "3");
		rParams.put("address", bean.getAddress());
		rParams.put("subdistrict", bean.getSubDistrict());
		rParams.put("district", bean.getDistrict());
		rParams.put("province", bean.getProvince());
		rParams.put("zipcode", bean.getZipCode());
		rParams.put("mobile", bean.getRequestorTel());
		rParams.put("telephone", "");

		////////////////////////////////////////////////////////////////////////////////////////////
		
		JasperRender jasperRender = new JasperRender();
		jasperRender.setParams(rParams);
		List<PetitionPolicyBean> reportList = new ArrayList<PetitionPolicyBean>();
		int i = 0;
		for (PetitionPolicyBean p : pList) {
			if(i != 0){
				reportList.add(p);
			}
			i++;
		}
		jasperRender.setBeanList(reportList);
		try {
			InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page01.jrxml");
			InputStream inp2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page02.jrxml");
			InputStream inp3 = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page03.jrxml");
			
			byte[] rr = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp));
			byte[] rr2 = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp2));
			ByteArrayInputStream inputStream = null;
			if(reportList.size() > 0){
				byte[] rr3 = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp3));
				ByteArrayInputStream is = PdfMergeUtils.merge(new ByteArrayInputStream(rr),new ByteArrayInputStream(rr2));
				inputStream = PdfMergeUtils.merge(is, new ByteArrayInputStream(rr3));
			}else{
				inputStream = PdfMergeUtils.merge(new ByteArrayInputStream(rr),new ByteArrayInputStream(rr2));
			}
			return inputStream;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private InputStream exportReport(PetitionChangeBenefitPaymentBean bean, List<PetitionPolicyBean> ppbList){
		Map<String, Object> rParams = new HashMap<String, Object>();
		
		/////////////////////////////////////////////////////////////////////////////////////// set parameter
		List<PetitionPolicyBean> pList = ppbList;		
		String policyNo = "";
		String policyOther = "";
		String branchName = "";
		String branchCode = "";
		if(pList != null && pList.size() > 1){
			policyNo= pList.get(0).getPolicyNo();
			branchName = pList.get(0).getPolicyBranchName();
			branchCode = pList.get(0).getPolicyBranchCode();
			
//			policyOther = pList.get(1).getPolicyNo();
//			for (int i = 2; i < pList.size(); i++) {
//				policyOther = policyOther + ", " + pList.get(i).getPolicyNo();
//			}
		}else if(pList != null && pList.size() == 1){
			policyNo = pList.get(0).getPolicyNo();
			branchName = pList.get(0).getPolicyBranchName();
			branchCode = pList.get(0).getPolicyBranchCode();
		}
		rParams.put("policyNo", policyNo);
		rParams.put("policyOther", policyOther);
		rParams.put("branchName", branchName);
		rParams.put("branchCode", branchCode);
		
		//telephone
		String[] telephoneArr = bean.getRequestorTel().split("");
		int index=1;
		for(int i=0; i<(telephoneArr.length-1); i++){
			rParams.put("telephone"+(index),telephoneArr[index]);
			index++;
		}
		
		
		if(bean.getSystemAuditLog().getCreateDate() != null){
			SimpleDateFormat sfDate = new SimpleDateFormat("dd", new Locale("th", "TH"));
			SimpleDateFormat sfMonth = new SimpleDateFormat("MMMM", new Locale("th", "TH"));
			SimpleDateFormat sfYear = new SimpleDateFormat("yyyy", new Locale("th", "TH"));
			Date processDate = bean.getSystemAuditLog().getCreateDate().toGregorianCalendar().getTime();
			String date = sfDate.format(processDate);
			String month = sfMonth.format(processDate);
			String year = sfYear.format(processDate);
			
			rParams.put("day", date);
			rParams.put("month", month);
			rParams.put("year", year);
		}
		
		rParams.put("insured", bean.getRequestBy());
		if(bean.getRequestorIdNo() != null){
			String[] idNoArr = bean.getRequestorIdNo().split("");
			int count = 1;
			for (int i = 0; i < (idNoArr.length-1); i++) {
				rParams.put("idNo"+count, idNoArr[count]);
				count++;
			}
		}
		
		rParams.put("requestType", "4");
		rParams.put("accountName", bean.getAccount());
		rParams.put("accountNo", bean.getAccountNo());
		rParams.put("bankName", bean.getBankName());
		rParams.put("bankBranchName", bean.getBranch());

		////////////////////////////////////////////////////////////////////////////////////////////
		
		JasperRender jasperRender = new JasperRender();
		jasperRender.setParams(rParams);
		List<PetitionPolicyBean> reportList = new ArrayList<PetitionPolicyBean>();
		int i = 0;
		for (PetitionPolicyBean p : pList) {
			if(i != 0){
				reportList.add(p);
			}
			i++;
		}
		jasperRender.setBeanList(reportList);

		try {
			InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page01.jrxml");
			InputStream inp2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page02.jrxml");
			InputStream inp3 = Thread.currentThread().getContextClassLoader().getResourceAsStream("jrxml/new-report-petition-modify-page03.jrxml");
			
			byte[] rr = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp));
			byte[] rr2 = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp2));
			ByteArrayInputStream inputStream = null;
			if(reportList.size() > 0){
				byte[] rr3 = jasperRender.processStream(StreamType.PDF, JasperCompileManager.compileReport(inp3));
				ByteArrayInputStream is = PdfMergeUtils.merge(new ByteArrayInputStream(rr),new ByteArrayInputStream(rr2));
				inputStream = PdfMergeUtils.merge(is, new ByteArrayInputStream(rr3));
			}else{
				inputStream = PdfMergeUtils.merge(new ByteArrayInputStream(rr),new ByteArrayInputStream(rr2));
			}
			return inputStream;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private String createEmailBodyChangePaymentPeriod(CssUser user, PetitionChangePaymentPeriodBean petition) throws Exception {
		
		StringBuilder sb = new StringBuilder(1000);
		sb.append("เรียน ").append("เจ้าหน้าที่ที่เกี่ยวข้อง");
		sb.append("<p>บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอแจ้งให้ท่านทราบว่า ").append("คุณ ").append(removeTitle(user)).append(" ได้ดำเนินการเปลี่ยนงวดการชำระเบี้ยประกัน ตามข้อมูลที่แสดงดังนี้</p>");
		sb.append("<p>----------------------------------------------------------------------------------------------------</p>");
		for(PetitionPolicyBean p: petition.getPetitionPolicyList()){
			sb.append("<p>เลขที่กรมธรรม์ ").append(p.getPolicyNo()).append(" แบบประกัน ").append(p.getPlanName()).append("</p>");
			sb.append("<p>งวดชำระเบี้ยประกัน จาก ").append(petition.getCurrentPeriod()).append(" เดือน เป็น ").append(petition.getNewPeriod()).append(" เดือน</p>");
			sb.append("<p>----------------------------------------------------------------------------------------------------</p>");
		}

		return sb.toString();
	}
	
	private String createEmailBodyChangeCustomerName(CssUser user, PetitionChangeCustomerNameBean petition) throws Exception {
		
		StringBuilder sb = new StringBuilder(1000);
		sb.append("เรียน ").append("เจ้าหน้าที่ที่เกี่ยวข้อง");
		sb.append("<p>บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอแจ้งให้ท่านทราบว่า ").append("คุณ ").append(removeTitle(user)).append(" ได้ดำเนินการเปลี่ยนแปลงแก้ไขชื่อ-นามสกุล ผู้เอาประกัน ตามข้อมูลที่แสดงดังนี้</p>");
		sb.append("<p>----------------------------------------------------------------------------------------------------</p>");
		for(PetitionPolicyBean p: petition.getPetitionPolicyList()){
			sb.append("<p>เลขที่กรมธรรม์ ").append(p.getPolicyNo()).append(" แบบประกัน ").append(p.getPlanName()).append("</p>");
			sb.append("<p>ผู้เอาประกัน ").append(petition.getTitle()).append(petition.getName()).append(" ").append(petition.getSname()).append("</p>");
			sb.append("<p>----------------------------------------------------------------------------------------------------</p>");
		}
		
		return sb.toString();
	}
	
	private String createEmailBodyChangeAddress(CssUser user, PetitionChangeAddressBean petition) throws Exception {
		
		StringBuilder sb = new StringBuilder(1000);
		sb.append("เรียน ").append("เจ้าหน้าที่ที่เกี่ยวข้อง");
		sb.append("<p>บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอแจ้งให้ท่านทราบว่า ").append("คุณ ").append(removeTitle(user)).append(" ได้ดำเนินการเปลี่ยนแปลงสถานที่ติดต่อและที่อยู่ตามทะเบียนบ้าน ตามข้อมูลที่แสดงดังนี้</p>");
		sb.append("<p>----------------------------------------------------------------------------------------------------</p>");
		for(PetitionPolicyBean p: petition.getPetitionPolicyList()){
			sb.append("<p>เลขที่กรมธรรม์ ").append(p.getPolicyNo()).append(" แบบประกัน ").append(p.getPlanName()).append("</p>");
			sb.append("<p>ที่อยู่ ").append(StringUtils.defaultString(petition.getAddress(),"-")).append("</p>");
			sb.append("<p>");
			sb.append("ตำบล ");
			
			sb.append(StringUtils.defaultString(petition.getSubDistrict(),"-")).append("</p>");
			sb.append("<p>");
			sb.append("อำเภอ ");
			
			sb.append(StringUtils.defaultString(petition.getDistrict(),"-")).append("</p>");
			sb.append("<p>จังหวัด ").append(StringUtils.defaultString(petition.getProvince(),"-")).append("</p>");
			sb.append("<p>รหัสไปรษณีย์ ").append(StringUtils.defaultString(petition.getZipCode().toString(),"-")).append("</p>");
//			sb.append("<p>หมายเลขโทรศัพท์มือถือ 1 ").append(StringUtils.defaultString(petition.getMobile1(),"-")).append("</p>");
//			sb.append("<p>หมายเลขโทรศัพท์มือถือ 2 ").append(StringUtils.defaultString(petition.getMobile2(),"-")).append("</p>");
//			sb.append("<p>หมายเลขโทรศัพท์บ้าน ").append(StringUtils.defaultString(petition.getPhone1(),"-")).append(" ต่อ ").append(StringUtils.defaultString(petition.getPhoneExt1(),"-")).append("</p>");
//			sb.append("<p>หมายเลขโทรศัพท์ที่ทำงาน ").append(StringUtils.defaultString(petition.getPhone2(),"-")).append(" ต่อ ").append(StringUtils.defaultString(petition.getPhoneExt2(),"-")).append("</p>");
//			sb.append("<p>อีเมล ").append(StringUtils.defaultString(petition.getEmail(),"-")).append("</p>");
			sb.append("<p>----------------------------------------------------------------------------------------------------</p>");

		}
	
		return sb.toString();
	}
	
	private String createEmailBodyChangeBenefitPayment(CssUser user, PetitionChangeBenefitPaymentBean petition) throws Exception {
		
		StringBuilder sb = new StringBuilder(1000);
		sb.append("เรียน ").append("เจ้าหน้าที่ที่เกี่ยวข้อง");
		sb.append("<p>บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอแจ้งให้ท่านทราบว่า ").append("คุณ ").append(removeTitle(user)).append(" ได้ดำเนินการเปลี่ยนแปลงวิธีรับเงินผลประโยชน์ ตามข้อมูลที่แสดงดังนี้</p>");
		sb.append("<p>----------------------------------------------------------------------------------------------------</p>");
		for(PetitionPolicyBean p: petition.getPetitionPolicyList()){
			sb.append("<p>เลขที่กรมธรรม์ ").append(p.getPolicyNo()).append(" แบบประกัน ").append(p.getPlanName()).append("</p>");
			sb.append("<p>บัญชี ").append(petition.getAccountNo()).append(" ").append(petition.getBankName()).append(" ").append(petition.getBranch()).append("</p>");
			sb.append("<p>----------------------------------------------------------------------------------------------------</p>");
		}
	
		return sb.toString();
	}
	
	private String createEmailBody(String requestNo, String policyNo, String requestBy, String requestType, String requestDate){
		StringBuilder sb = new StringBuilder(1000);
		sb.append("เรียน ").append("เจ้าหน้าที่ที่เกี่ยวข้อง");
		sb.append("<br>");
		sb.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;เนื่องจากผู้เอาประกันได้ขอดำเนินการเปลี่ยนแปลงเกี่ยวกับกรมธรรม์ผ่าน Customer web portal</p>");
		sb.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ขอให้ท่านโปรดดำเนินการตามรายละเอียดที่แนบมา ดังนี้</p>");
		sb.append("<p>");

		sb.append("<table style='width: 100%;'>");
		sb.append("<tr>");
		sb.append("<td align ='center' style='border: 1px solid black;'>เลขที่ใบคำขอ</td>");
		sb.append("<td align ='center' style='border: 1px solid black;'>หมายเลขกรมธรรม์</td>");
		sb.append("<td align ='center' style='border: 1px solid black;'>ผู้ยื่นคำร้อง</td>");
		sb.append("<td align ='center' style='border: 1px solid black;'>แบบคำร้อง</td>");
		sb.append("<td align ='center' style='border: 1px solid black;'>วันที่ส่งคำร้อง</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td align ='left' style='border: 1px solid black;'>"+requestNo+"</td>");
		sb.append("<td align ='left' style='border: 1px solid black;'>"+policyNo+"</td>");
		sb.append("<td align ='left' style='border: 1px solid black;'>"+requestBy+"</td>");
		sb.append("<td align ='left' style='border: 1px solid black;'>"+requestType+"</td>");
		sb.append("<td align ='left' style='border: 1px solid black;'>"+requestDate+"</td>");
		sb.append("</tr>");
		sb.append("</table>");
		
		sb.append("</p>");
		sb.append("<p>ขอแสดงความนับถือ</p>");
		sb.append("<p>Customer web portal</p>");
		return sb.toString();
	}
	
	private Integer convertInstallmentType(String installmentType){
		Integer result = null;
		if(installmentType.equals("year")){
			result = 12;
		}else if(installmentType.equals("sixMonth")){
			result = 6;
		}else if(installmentType.equals("threeMonth")){
			result = 3;
		}else if(installmentType.equals("month")){
			result = 1;
		}
		return result;
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
	
	private boolean checkRecordActiveForPetitionChange(PolicyBean policyBean){
		boolean result = false;
		if(policyBean.getPolicyType().equals("O")){
			List<String> statusList = Arrays.asList("I", "F", "O", "W", "E", "R", "P");
			result = statusList.contains(policyBean.getPolicyStatus());
		}else if(policyBean.getPolicyType().equals("I") || policyBean.getPolicyType().equals("G")){
			List<String> statusList = Arrays.asList("0", "1", "2", "3", "4", "5", "6");
			result = statusList.contains(policyBean.getPolicyStatus());
		}else if(policyBean.getPolicyType().equals("P")){
			List<String> statusList = Arrays.asList("I", "L");
			result = statusList.contains(policyBean.getPolicyStatus());
		}
		return result;
	}
	
	private boolean checkRequestedEformType1And4(String idNo, String policyNo, String requestType, Date requestDate){
		boolean result = false;
		try {
			List<CssPetitionRequestEntity> cpreList = cssEFormService.findCssPetitionRequestByIdNoAndRequestDateAndRequestType(idNo, requestDate, requestType);
			for (CssPetitionRequestEntity entity : cpreList) {
				CssPetitionPolicyEntity cppEntity = cssEFormService.findCssPetitionPolicyByRequestIdAndPolicyNoAndRequestDate(entity.getId(), policyNo, requestDate);
				if(cppEntity != null){
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean checkRequestedEformType2And3(String idNo, String requestType, Date requestDate){
		boolean result = false;
		try {
			List<CssPetitionRequestEntity> cpreList = cssEFormService.findCssPetitionRequestByIdNoAndRequestDateAndRequestType(idNo, requestDate, requestType);
			if(cpreList != null && cpreList.size() > 0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public class Parameter{
		private String installmentType;
		private File installmentIdCardFile;
		private String[] policy;
		
		private InputStream inputStream;
		private String radioValue;
		
		private String titleName;
		private String titleNameOther;
		private String firstname;
		private String lastname;
		private String titleDesc;
		////////////////////////////////////////
		private String address;
		private String province;
		private String district;
		private String subdistrict;
		private String zipcode;
		
		private String disDesc;
		private String povDesc;
		private String subdistrictName;

		///////////////////////////////////////
		private String account;
		private String accountNo;
		private String bankName;
		private String branch;
		private String bankDesc;
		///////////////////////////////////////
		private String selectedPolicy;
		private String selectedPaymentMode;
		private String selectedProvince;
		private String selectedDistrict;
		private String selectedRequestType;
		
		
		public String getRadioValue() {
			return radioValue;
		}
		public void setRadioValue(String radioValue) {
			this.radioValue = radioValue;
		}
		public InputStream getInputStream() {
		    return inputStream;
		}
		public void setInputStream(InputStream inputStream) {
		    this.inputStream= inputStream;
		}
		public String getInstallmentType() {
			return installmentType;
		}
		public void setInstallmentType(String installmentType) {
			this.installmentType = installmentType;
		}
		public File getInstallmentIdCardFile() {
			return installmentIdCardFile;
		}
		public void setInstallmentIdCardFile(File installmentIdCardFile) {
			this.installmentIdCardFile = installmentIdCardFile;
		}
		public String[] getPolicy() {
			return policy;
		}
		public void setPolicy(String[] policy) {
			this.policy = policy;
		}
		public String getTitleName() {
			return titleName;
		}
		public void setTitleName(String titleName) {
			this.titleName = titleName;
		}
		public String getTitleNameOther() {
			return titleNameOther;
		}
		public void setTitleNameOther(String titleNameOther) {
			this.titleNameOther = titleNameOther;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public String getTitleDesc() {
			return titleDesc;
		}
		public void setTitleDesc(String titleDesc) {
			this.titleDesc = titleDesc;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
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
		public String getSubdistrict() {
			return subdistrict;
		}
		public void setSubdistrict(String subdistrict) {
			this.subdistrict = subdistrict;
		}
		public String getZipcode() {
			return zipcode;
		}
		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getAccountNo() {
			return accountNo;
		}
		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public String getBranch() {
			return branch;
		}
		public void setBranch(String branch) {
			this.branch = branch;
		}
		public String getSelectedPolicy() {
			return selectedPolicy;
		}
		public void setSelectedPolicy(String selectedPolicy) {
			this.selectedPolicy = selectedPolicy;
		}
		public String getSelectedPaymentMode() {
			return selectedPaymentMode;
		}
		public void setSelectedPaymentMode(String selectedPaymentMode) {
			this.selectedPaymentMode = selectedPaymentMode;
		}
		public String getSelectedProvince() {
			return selectedProvince;
		}
		public void setSelectedProvince(String selectedProvince) {
			this.selectedProvince = selectedProvince;
		}
		public String getSelectedDistrict() {
			return selectedDistrict;
		}
		public void setSelectedDistrict(String selectedDistrict) {
			this.selectedDistrict = selectedDistrict;
		}
		public String getDisDesc() {
			return disDesc;
		}
		public void setDisDesc(String disDesc) {
			this.disDesc = disDesc;
		}
		public String getPovDesc() {
			return povDesc;
		}
		public void setPovDesc(String povDesc) {
			this.povDesc = povDesc;
		}
		public String getSubdistrictName() {
			return subdistrictName;
		}
		public void setSubdistrictName(String subdistrictName) {
			this.subdistrictName = subdistrictName;
		}
		public String getBankDesc() {
			return bankDesc;
		}
		public void setBankDesc(String bankDesc) {
			this.bankDesc = bankDesc;
		}
		public String getSelectedRequestType() {
			return selectedRequestType;
		}
		public void setSelectedRequestType(String selectedRequestType) {
			this.selectedRequestType = selectedRequestType;
		}
		
	}
	
	public class PolicyParameter{
		private Long policyId;
		private String policyNo;
		private String policyName;
		private String installmentType;	
		
		public Long getPolicyId() {
			return policyId;
		}
		public void setPolicyId(Long policyId) {
			this.policyId = policyId;
		}
		public String getPolicyNo() {
			return policyNo;
		}
		public void setPolicyNo(String policyNo) {
			this.policyNo = policyNo;
		}
		public String getPolicyName() {
			return policyName;
		}
		public void setPolicyName(String policyName) {
			this.policyName = policyName;
		}
		public String getInstallmentType() {
			return installmentType;
		}
		public void setInstallmentType(String installmentType) {
			this.installmentType = installmentType;
		}
	}
}
