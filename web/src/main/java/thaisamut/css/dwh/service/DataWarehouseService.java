package thaisamut.css.dwh.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import thaisamut.css.dwh.service.pojo.PersonBean;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ClaimChannel;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.HermesOtherChannelPayment;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.NotificationLetter;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Petition;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Receipt;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ReceiptInd;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ReceiptOrd;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ReceiptPa;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ThailandBank;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ThailandZipcode;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Title;
import thaisamut.osgi.targetbundles.dmsws.search.v1.DmsDocument;

public interface DataWarehouseService {
	PersonBean getPersonData(String idNo) throws Exception;

	Map<String, PolicyBean> getPolicyData(String idNo, String email) throws Exception;
	
	Boolean isValidUser(String idCard, String birthDate, String telNo);

	DmsDocument findCertDocument(String policyNo, String year) throws Exception;

	DmsDocument findNoticeDocument(String policyNo, Date dueDate) throws Exception;

	
	Petition countPolicyTypeORDByIdNo(String idNo) throws Exception;
	Petition countPolicyTypeAllByIdNo(String idNo) throws Exception;
	Petition changePaymentModeChecking(String planCode, String mode, Integer policyYear) throws Exception;
	
	List<Title> getActiveTitle() throws Exception;
	List<ThailandZipcode> getActiveProvince() throws Exception;
	List<ThailandZipcode> getActiveDistrictByProvinceCode(Integer provinceCode) throws Exception;
	List<ThailandZipcode> getActiveSubdistrictByProvinceCodeAndDistrictCode(Integer provinceCode, Integer districtCode) throws Exception;
	List<ThailandBank> getActiveBank() throws Exception;

	List<Receipt> getReceipts(String policyNo, String policyType, int passYear) throws Exception;

	HermesOtherChannelPayment inquiryOtherChannelPaymentChecking(String policyNo, String policyType) throws Exception;
	
	List<NotificationLetter> getNotificationLetterList(String policyNo, String policyType, String branchCode7, String currentDate) throws Exception;
	List<ReceiptOrd> getReceiptOrdList(String policyNo, String paymentDueDate, String paymentDate) throws Exception;
	List<ReceiptInd> getReceiptIndList(String policyNo, String paymentDueDate, String paymentDate) throws Exception;
	ReceiptInd getLastIndReceipt(String policyNo) throws Exception;
	ReceiptOrd getLastOrdReceipt(String policyNo) throws Exception;
	
	List<ClaimChannel> getClaimChannelByPolicyNoAndPolicyType(String policyNo, String policyType) throws Exception;

	boolean canThisPlanRegisterPaymentCard(String prdCode) throws Exception;
	
	Petition checkIndAdditionalRider(String policyNo) throws Exception;
	Petition checkAbsorbFee(String planCode, String policyType, String feeType, Double premium) throws Exception;
	List<ReceiptPa> getReceiptPaList(String policyNo, String beforeDueDate, String afterDueDate) throws Exception;
	
	
	Map<String, PolicyBean> getPolicyDataWithRegister(String idNo, String email) throws Exception;

	boolean isMobileNeedUpdate(CssMemberEntity member) throws Exception;
}
