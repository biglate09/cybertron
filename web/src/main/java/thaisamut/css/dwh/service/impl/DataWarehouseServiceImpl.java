package thaisamut.css.dwh.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.LoanPolicyBean;
import thaisamut.css.dwh.service.pojo.PersonBean;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.dwh.service.pojo.RiderMapBean;
import thaisamut.cssdwhwsclient.component.CssDwhWsClientFactory;
import thaisamut.dmswsclient.component.DmsWsClientFactory;
import thaisamut.cybertron.ejbweb.bean.CssLoanBean;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.remote.EasyLoanInfoService;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Assured;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ClaimChannel;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.HermesOtherChannelPayment;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.LoanPolicy;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.NotificationLetter;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Petition;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Policy;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ProductPaycardOrd;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Receipt;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ReceiptInd;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ReceiptOrd;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ReceiptPa;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Rider;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ThailandBank;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ThailandZipcode;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Title;
import thaisamut.osgi.targetbundles.dmsws.search.v1.DmsDocument;
import thaisamut.util.CssConvertUtils;
import thaisamut.util.XMLCalendarUtils;

public class DataWarehouseServiceImpl implements DataWarehouseService{
    private static final Logger LOG = LoggerFactory.getLogger(DataWarehouseServiceImpl.class);

	@Autowired CssDwhWsClientFactory cssDwhWsClientFlatory;
	@Autowired DmsWsClientFactory dmsWsClientFactory;
	@Autowired CssMasterDataService cssMasterDataService;
	@Autowired
	private EasyLoanInfoService easyLoanService;
	
	@Override
	public PersonBean getPersonData(String idNo) throws Exception {
		LOG.debug("Start DataWarehouseServiceImpl getPersonData");
		Assured assured = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryAssuredInfoByIdNo(idNo);
		PersonBean person = null;
		if(assured!=null){
			person = new PersonBean();
		    person.setTitleDesc(assured.getTitleDesc());
		    person.setFirstName(assured.getFirstName());
		    person.setLastName(assured.getLastName());
		    person.setIdNo(assured.getIdNo());
		    person.setBirthDate(assured.getBirthDate());
		    person.setCustCode(assured.getCustCode());
		    person.setAgeAtCommDate(assured.getAgeAtCommDate());
		    person.setOriginDesc(assured.getOriginDesc());
		    person.setNationalityDesc(assured.getNationalityDesc());
		    person.setReligionDesc(assured.getReligionDesc());
		    person.setMaritalStatusDesc(assured.getMaritalStatusDesc());
		    person.setEducationDesc(assured.getEducationDesc());
		    person.setOccupationDesc(assured.getOccupationDesc());
		    person.setMonthlyIncome(assured.getMonthlyIncome());
		    person.setGenderCode(assured.getGenderCode());
		}
		LOG.debug("End DataWarehouseServiceImpl getPersonData");
		
		return person;
	}
	@Override
	public Map<String,PolicyBean> getPolicyData(String idNo,String email) throws Exception {
		LOG.debug("Start DataWarehouseServiceImpl getPolicyData");
		List<Policy> policies = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryPolicyByIdNo(idNo);
		Map<String,PolicyBean> policyList = null;
		if(policies!=null&&!policies.isEmpty()){
			policyList = new HashMap<String,PolicyBean>();
			PolicyBean bean;
			for(Policy p : policies){
				bean = new PolicyBean();
				
				bean.setAgentCode(p.getAgentCode());

				bean.setPolicyNo(p.getPolicyNo());
				bean.setCustCode(p.getCustCode());
			    bean.setSumAssured(p.getSumAssured());
			    bean.setSubdistrictName(p.getSubdistrictName());
			    bean.setProvinceName(p.getProvinceName());
			    bean.setPremiumAmount(p.getPremiumAmount());
			    bean.setPrdName(p.getPrdName());
			    bean.setPostcode(p.getPostcode());
			    bean.setPolicyType(p.getPolicyType());
			    bean.setPolicyStatus(p.getPolicyStatus());
			    bean.setPhone1(p.getPhone1());
			    bean.setPhone2(p.getPhone2());
			    bean.setPersonType(p.getPersonType());
			    bean.setLapseDate(p.getLapseDate());
			    bean.setPaymentMode(p.getPaymentMode());
			    bean.setPaymentChannel(p.getPaymentChannel());
			    bean.setNextPaidDate("".equals(p.getNextPaidDate())?null:p.getNextPaidDate());
			    bean.setMobile2(p.getMobile2());
			    bean.setMobile1(p.getMobile1());
			    bean.setMaturityDate("".equals(p.getMaturityDate())?null:p.getMaturityDate());
			    bean.setFullyPaidDate("".equals(p.getFullyPaidDate())?null:p.getFullyPaidDate());
			    bean.setExt1(p.getExt1());
			    bean.setExt2(p.getExt2());
			    bean.setEmail(StringUtils.defaultIfBlank(p.getEmail(),email));
			    bean.setDistrictName(p.getDistrictName());
			    bean.setCommencementDate("".equals(p.getCommencementDate())?null:p.getCommencementDate());
			    bean.setAddressType(p.getAddressType());
			    bean.setAddressLine1(p.getAddressLine1());
			    bean.setBirthdate(p.getBirthdate());
			    bean.setRiders(createRiderList(p.getRiders()));
			    bean.setPrdCode(p.getPrdCode());
			    bean.setTitleDesc(p.getTitleDesc());
			    bean.setFirstName(p.getFirstName());
			    bean.setLastName(p.getLastName());
			    bean.setBranchNo(p.getBranchNo());
			    bean.setPolicyYear(p.getPolicyYear());
			    bean.setPolicyOrg(p.getPolicyOrg());
			    bean.setPaymentTerm(p.getPaymentTerm());
			    bean.setDwnLoanStatus("N");
			    LoanPolicy loanPolicy = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryLoanByPolicy(p.getPolicyNo(), p.getPolicyType());
			    if(null != loanPolicy && StringUtils.isNotBlank(loanPolicy.getPolicyNo())){
			    	LoanPolicyBean loanBean = new LoanPolicyBean();
			    	PropertyUtils.copyProperties(loanBean, loanPolicy);
			    	Double loanOld = getDoubleVal(loanPolicy.getLoanOld());
			    	Double apl = getDoubleVal(loanPolicy.getApl());
			    	Double intApl = getDoubleVal(loanPolicy.getIntApl());
			    	Double loanAmount = getDoubleVal(loanPolicy.getLoanAmount());
			    	Double loanAplInt = loanOld + apl + intApl;
			    	if(loanAplInt > 0 || loanAmount < 5000){
			    		 bean.setDwnLoanStatus("N");
			    	}else{
			    		bean.setDwnLoanStatus("Y");
			    	}
			    	loanBean.setLoanAmountTxt(setDoubleFormat(loanAmount));
			    	loanBean.setLoanDutyTxt(setIntegerFormat(loanBean.getLoanDuty()));
			    	loanBean.setLoanRateTxt(setDoubleFormat(loanBean.getLoanRate()));
			    	bean.setLoanBean(loanBean);
			    }
			    CssLoanBean cssloanBean = easyLoanService.findCssLoan(idNo, p.getPolicyNo());
			    if(null != cssloanBean){
			    	bean.setCssLoanStatus(cssloanBean.getStatus());
			    }
				policyList.put(bean.getPolicyNo(), bean);
			}
		}
		LOG.debug("End DataWarehouseServiceImpl getPolicyData");
		return policyList;
	}
	private List<RiderMapBean> createRiderList(List<Rider> riders) {
		List<RiderMapBean> list = null;
		if(riders!=null&&!riders.isEmpty()){
			list = new ArrayList<RiderMapBean>();
			for(Rider r : riders){
				try {
					list.add(new RiderMapBean(r.getRiderCode(), r.getRiderCode(), r.getPolicyType(),cssMasterDataService.getRiderMap(r.getRiderCode(), r.getPolicyType()),r.getSumAssured(),r.getPremiumAmount()));
				} catch (Exception e) {
					LOG.error(e.getMessage(),e);
				}
			}
		}
		return list;
	}
	@Override
	public Boolean isValidUser(String idCard, String birthDate, String telNo) {
		LOG.debug("Start DataWarehouseServiceImpl isValidUser");
		String result = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryPolicyAvailable(idCard, birthDate, telNo);
		return Boolean.valueOf(result);
	}
	
	@Override
	public DmsDocument findCertDocument(String policyNo, String year)throws Exception{
		DmsDocument document = dmsWsClientFactory.getDmsWsSearchV1ESBService().findY71006CertPaymentBy(policyNo, Integer.parseInt(year));
		return document;
	}
	
	@Override
	public DmsDocument findNoticeDocument(String policyNo, Date dueDate)throws Exception{
		DmsDocument document = dmsWsClientFactory.getDmsWsSearchV1ESBService().findC71004PaymentNoticeBy(policyNo,XMLCalendarUtils.create(dueDate));
		return document;
	}
	@Override
	public Petition countPolicyTypeORDByIdNo(String idNo) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl countPolicyTypeORDByIdNo");
		Petition result = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryCountPolicyTypeORDByIdNo(idNo);
		return result;
	}
	@Override
	public Petition countPolicyTypeAllByIdNo(String idNo) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl countPolicyTypeAllByIdNo");
		Petition result = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryCountPolicyTypeAllByIdNo(idNo);
		return result;
	}
	@Override
	public Petition changePaymentModeChecking(String planCode, String mode,
			Integer policyYear) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl changePaymentModeChecking");
		Petition result = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryChangePaymentModeChecking(planCode, mode, policyYear);
		return result;
	}
	@Override
	public List<Title> getActiveTitle() throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getActiveTitle");
		List<Title> titleList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryTitleData();
		return titleList;
	}
	@Override
	public List<ThailandZipcode> getActiveProvince() throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getActiveProvince");
		List<ThailandZipcode> thailandZipCodeList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryThailandZipcodeData();
		return thailandZipCodeList;
	}
	@Override
	public List<ThailandZipcode> getActiveDistrictByProvinceCode(
			Integer provinceCode) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getActiveDistrictByProvinceCode");
		List<ThailandZipcode> thailandZipCodeList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryDistrictData(provinceCode);
		return thailandZipCodeList;
	}
	@Override
	public List<ThailandZipcode> getActiveSubdistrictByProvinceCodeAndDistrictCode(
			Integer provinceCode, Integer districtCode) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getActiveSubdistrictByProvinceCodeAndDistrictCode");
		List<ThailandZipcode> thailandZipCodeList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquirySubDistrictData(provinceCode, districtCode);
		return thailandZipCodeList;
	}
	@Override
	public List<ThailandBank> getActiveBank() throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getActiveBank");
		List<ThailandBank> thailandBankList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryBankData();
		return thailandBankList;
	}
	@Override
	public List<Receipt> getReceipts(String policyNo, String policyType, int passyear) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl inquiryReceipt");
		List<Receipt> receiptList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryReceipt(policyNo, policyType,passyear);
		return receiptList;
	}
	@Override
	public HermesOtherChannelPayment inquiryOtherChannelPaymentChecking(String policyNo, String policyType) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl inquiryReceipt");
		HermesOtherChannelPayment receiptList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryOtherChannelPaymentChecking(policyNo, policyType);
		return receiptList;
	}
	@Override
	public List<NotificationLetter> getNotificationLetterList(String policyNo,
			String policyType, String branchCode7, String currentDate)
			throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getNotificationLetterList");
		List<NotificationLetter> notificationLetterList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryCheckNotificationLetter(policyNo, policyType, branchCode7, currentDate);
		return notificationLetterList;
	}
	@Override
	public List<ReceiptOrd> getReceiptOrdList(String policyNo,
			String paymentDueDate, String paymentDate) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getReceiptOrdList");
		List<ReceiptOrd> receiptOrdList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryCheckOrdReceipt(policyNo, paymentDueDate);
		return receiptOrdList;
	}
	@Override
	public List<ReceiptInd> getReceiptIndList(String policyNo,
			String paymentDueDate, String paymentDate) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getReceiptIndList");
		List<ReceiptInd> receiptIndList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryCheckIndReceipt(policyNo, paymentDueDate);
		return receiptIndList;
	}
	@Override
	public ReceiptInd getLastIndReceipt(String policyNo) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getLastIndReceipt");
		ReceiptInd receiptInd = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryCheckLastIndReceipt(policyNo);
		return receiptInd;
	}
	@Override
	public ReceiptOrd getLastOrdReceipt(String policyNo) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getLastOrdReceipt");
		ReceiptOrd receiptOrd = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryCheckLastOrdReceipt(policyNo);
		return receiptOrd;
	}
	@Override
	public List<ClaimChannel> getClaimChannelByPolicyNoAndPolicyType(
			String policyNo, String policyType) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getClaimChannelByPolicyNoAndPolicyType");
		List<ClaimChannel> rList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryGetClaimChannel(policyNo, policyType);
		return rList;
	}
	@Override
	public boolean canThisPlanRegisterPaymentCard(String prdCode) throws Exception {
		List<ProductPaycardOrd> result = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().getPaycardOrd(prdCode);
		return result != null && ! result.isEmpty();
	}
	@Override
	public Petition checkIndAdditionalRider(String policyNo) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl checkIndAdditionalRider");
		Petition p = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryCheckIndAdditionalRider(policyNo);
		return p;
	}
	@Override
	public Petition checkAbsorbFee(String planCode, String policyType,
			String feeType, Double premium) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl checkAbsorbFee");
		Petition p = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryCheckAbsorbFee(planCode, policyType, feeType, premium);
		return p;
	}
	@Override
	public List<ReceiptPa> getReceiptPaList(String policyNo,
			String beforeDueDate, String afterDueDate) throws Exception {
		LOG.debug("~DataWarehouseServiceImpl getReceiptPaList");
		List<ReceiptPa> rList = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryCheckPaReceipt(policyNo, beforeDueDate, afterDueDate);
		return rList;
	}
	@Override
	public Map<String, PolicyBean> getPolicyDataWithRegister(String idNo,
			String email) throws Exception {
		LOG.debug("Start DataWarehouseServiceImpl getPolicyDataWithRegister");
		List<Policy> policies = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryPolicyWithRegisterByIdNo(idNo);
		Map<String,PolicyBean> policyList = null;
		if(policies!=null&&!policies.isEmpty()){
			policyList = new HashMap<String,PolicyBean>();
			PolicyBean bean;
			for(Policy p : policies){
				bean = new PolicyBean();
				
				bean.setAgentCode(p.getAgentCode());

				bean.setPolicyNo(p.getPolicyNo());
				bean.setCustCode(p.getCustCode());
			    bean.setSumAssured(p.getSumAssured());
			    bean.setSubdistrictName(p.getSubdistrictName());
			    bean.setProvinceName(p.getProvinceName());
			    bean.setPremiumAmount(p.getPremiumAmount());
			    bean.setPrdName(p.getPrdName());
			    bean.setPostcode(p.getPostcode());
			    bean.setPolicyType(p.getPolicyType());
			    bean.setPolicyStatus(p.getPolicyStatus());
			    bean.setPhone1(p.getPhone1());
			    bean.setPhone2(p.getPhone2());
			    bean.setPersonType(p.getPersonType());
			    bean.setLapseDate(p.getLapseDate());
			    bean.setPaymentMode(p.getPaymentMode());
			    bean.setPaymentChannel(p.getPaymentChannel());
			    bean.setNextPaidDate("".equals(p.getNextPaidDate())?null:p.getNextPaidDate());
			    bean.setMobile2(p.getMobile2());
			    bean.setMobile1(p.getMobile1());
			    bean.setMaturityDate("".equals(p.getMaturityDate())?null:p.getMaturityDate());
			    bean.setFullyPaidDate("".equals(p.getFullyPaidDate())?null:p.getFullyPaidDate());
			    bean.setExt1(p.getExt1());
			    bean.setExt2(p.getExt2());
			    bean.setEmail(StringUtils.defaultIfBlank(p.getEmail(),email));
			    bean.setDistrictName(p.getDistrictName());
			    bean.setCommencementDate("".equals(p.getCommencementDate())?null:p.getCommencementDate());
			    bean.setAddressType(p.getAddressType());
			    bean.setAddressLine1(p.getAddressLine1());
			    bean.setBirthdate(p.getBirthdate());
			    bean.setRiders(createRiderList(p.getRiders()));
			    bean.setPrdCode(p.getPrdCode());
			    bean.setTitleDesc(p.getTitleDesc());
			    bean.setFirstName(p.getFirstName());
			    bean.setLastName(p.getLastName());
			    bean.setBranchNo(p.getBranchNo());
			    bean.setPolicyYear(p.getPolicyYear());
			    bean.setPolicyOrg(p.getPolicyOrg());
			    bean.setPaymentTerm(p.getPaymentTerm());
			    
				policyList.put(bean.getPolicyNo(), bean);
			}
		}
		LOG.debug("End DataWarehouseServiceImpl getPolicyDataWithRegister");
		return policyList;
	}
	@Override
	public boolean isMobileNeedUpdate(CssMemberEntity member) throws Exception {
		LOG.debug("Start DataWarehouseServiceImpl checkTelno");
		List<Policy> policies = cssDwhWsClientFlatory.getCssDwhPolicyInquiryV1ESBService().inquiryPolicyByIdNo(member.getCardNo());
		
		if(policies!=null&&!policies.isEmpty()){
			Date commenceDateSeed = null,commenceDateMove=null;
			String telno = null;
			for(Policy p : policies){
				if(p.getMobile1()!=null){
					commenceDateMove = CssConvertUtils.toDateFromStringDateThai(p.getCommencementDate());
					if(commenceDateSeed == null||CssConvertUtils.isAfter(commenceDateSeed,commenceDateMove)){
						telno = p.getMobile1();
						commenceDateSeed = commenceDateMove;	
					}
				}
			}
			if(telno!=null&&!telno.equals(member.getTelNo())){
				
				member.setTelNo(telno);
				return true;
			}
		}
		LOG.debug("End DataWarehouseServiceImpl checkTelno");
		return false;
	}
	
	private Double getDoubleVal(Double val) throws Exception {
		if(val == null) {
			return 0.0;
		}else {
			return val;
		}
	}
	
	private String setDoubleFormat(Double val) throws Exception {
		DecimalFormat df = new DecimalFormat("#,###,###.00");
		return df.format(getDoubleVal(val));
	}
	
	private String setIntegerFormat(Integer val) throws Exception {
		DecimalFormat df = new DecimalFormat("#,###,###.00");
		Integer o = val == null?0:val;
		return df.format(o);
	}
	
}
