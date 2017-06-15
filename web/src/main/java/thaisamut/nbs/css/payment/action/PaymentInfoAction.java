package thaisamut.nbs.css.payment.action;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.eform.service.EformService;
import thaisamut.css.model.Css2c2pRequest;
import thaisamut.css.model.CssUser;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.model.CssConstantEntity;
import thaisamut.cybertron.ejbweb.model.CssPaymentEntity;
import thaisamut.cybertron.ejbweb.model.IliReceiptBean;
import thaisamut.cybertron.ejbweb.remote.CssPaymentCardTempService;
import thaisamut.cybertron.ejbweb.remote.CssPaymentService;
import thaisamut.hermesapp.ws.CheckRegisterOtherChannelModel;
import thaisamut.hermesappwsclient.component.HermesAppWsClientFactory;
import thaisamut.hydrawsclient.component.HydraWsClientFactory;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.NotificationLetter;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Petition;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.Receipt;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ReceiptInd;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ReceiptOrd;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ReceiptPa;
import thaisamut.osgi.targetbundles.hydraws.csspayment.v1.CssPaymentBean;
import thaisamut.osgi.targetbundles.hydraws.csspayment.v1.HydraWsCssPaymentV1ESBService;
import thaisamut.osgi.targetbundles.hydraws.csspayment.v1.PaymentBean;
import thaisamut.osgi.targetbundles.hydraws.csspayment.v1.SystemAuditLog;
import thaisamut.util.CssConvertUtils;


public class PaymentInfoAction extends CssJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8662241486175862457L;
	private static final Logger LOG = LoggerFactory.getLogger(PaymentInfoAction.class);
	private String policyNo;
	private String ref;
	@Autowired private DataWarehouseService dataWarehouseService;
	@Autowired private CssMasterDataService cssMasterDataService;
	@Autowired private CssPaymentCardTempService cssPaymentCardTempService;
	@Autowired private CssPaymentService cssPaymentService;
	@Autowired private HydraWsClientFactory hydraWsClientFactory;

	@Autowired private thaisamut.policywsclient.component.PolicyWsClientFactory factory;
	@Autowired private HermesAppWsClientFactory hermesFactory;

	@Autowired private EformService eformService;
	
	@Autowired
	@Qualifier("payment2c2pResultUrl1")
	private String resultUrl1;
	@Autowired
	@Qualifier("payment2c2pResultUrl2")
	private String resultUrl2;
	@Autowired
	@Qualifier("payment2c2pAction")
	private String action2c2p;
	@Autowired
	@Qualifier("payment2c2pSecretKey")
	private String secretKey;
	@Autowired
	@Qualifier("payment2c2pMerchantId")
	private String merchantId;

	private static final String mode = "PAYMENT_CARD";
	
//	private static final String secretKey = "FCxFuafkpQOH";
	
	private static final String version = "6.9";
//	private static final String merchantId = "764764000000071";
	private static final String currency = "764";
	private static final String defaultLang = "th";
	
	private String rel;
	private static final String lp51 = "LP51";
	private static final String lp52 = "LP52";
	private static final String lp53 = "LP53";
	private static final String lp63 = "LP63";
	
	private Parameter params = new Parameter();
	
	public Parameter getParams() {
		return params;
	}

	public void setParams(Parameter params) {
		this.params = params;
	}

	public String index() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("~On PaymentInfoAction : index looking for policy no %s",policyNo));
		}
		if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null) {
			Map<String,PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
			
			final PolicyBean policy = policies.get(policyNo);
			if(policy!=null){
				requestAttributes.put("policy", policy);
			}			
			if("1".equals(ref)){
				requestAttributes.put("back", String.format("secure/member/policy/policydetail.html?policyNo=%s&ref=%s",policyNo,StringUtils.defaultString( rel)));
			}else{
				requestAttributes.put("back", "secure/member/policy/policyinfo.html");
			}
			int yearPass = 1;
			CssConstantEntity constain = cssMasterDataService.getConstant("HISTORY_PART_YEAR");
			if(constain!=null){
				try{
					yearPass = Integer.valueOf(constain.getValue1());
				}catch(Exception ignored){}
			}

			
			List<Receipt> receiptList =  dataWarehouseService.getReceipts(policyNo,policy.getPolicyType(),yearPass);
			if(LOG.isDebugEnabled()){
				LOG.debug("receiptList>>>>>>>{}",receiptList);
			}
			List<IliReceiptBean> list= new ArrayList<>();
			IliReceiptBean displayBean = new IliReceiptBean();
			final Date day = CssConvertUtils.toDateFromStringDateThai(policy.getCommencementDate());
			 SimpleDateFormat sfYY = new SimpleDateFormat("yy", new Locale("th", "TH"));
			 
			 /*if("P".equals(policy.getPolicyType())){
				 Collections.sort(receiptList,new Comparator<Receipt>() {

					@Override
					public int compare(Receipt o1, Receipt o2) {
						try{
						return o1.getRcPayDate().compare(o2.getRcPayDate());
						}catch(Exception ex){
							return -1;
						}
					}
					 
				 });
			 }*/
			 int runner = 0;
			 DecimalFormat df = new DecimalFormat("#,##0.00");
			for(Receipt receipt : receiptList){ 
				 displayBean = new IliReceiptBean();
				 
				 displayBean.setTerm(setPayFrom(policy,receipt, displayBean));
				 
				 Date paydate =receipt.getRcPayDate().toGregorianCalendar().getTime();
				 displayBean.setRcPayDate(CssConvertUtils.toDateThaiString(paydate));
				 
				 if(receipt.getPaymentChannel()!=null){
					 switch(receipt.getPaymentChannel()){
						 case "1" : displayBean.setPaymentChannel("สาขา");break;
						 case "2" : displayBean.setPaymentChannel("ชำระเองที่สำนักงานใหญ่");break;
						 case "3" : displayBean.setPaymentChannel("ผ่านเคาน์เตอร์ธนาคาร");break;
						 case "4" : displayBean.setPaymentChannel("หักบัญชีธนาคาร (Direc Debit)");break;
						 case "5" : displayBean.setPaymentChannel("short");break;
						 case "6" : displayBean.setPaymentChannel("ผ่านเคาน์เตอร์เซอวิส");break;
						 case "7" : displayBean.setPaymentChannel("มรณกรรม");break;
						 case "8" : displayBean.setPaymentChannel("โอนผ่านบัญชีรับฝาก");break;
						 case "9" : displayBean.setPaymentChannel("ปิดบัญชีอัตโนมัต และ เวนคืนอัตโนมัติ");break;
						 case "A" : displayBean.setPaymentChannel("ต่างสาขา ");break;
						 case "B" : displayBean.setPaymentChannel("mPay");break;
						 case "C" : displayBean.setPaymentChannel("ตัดบัตรเครดิต");break;
						 case "D" : displayBean.setPaymentChannel("ชำระจากเงินทรงชีพ");break;
						 case "E" : displayBean.setPaymentChannel("KTB BizPayment ");break;
						 case "F" : displayBean.setPaymentChannel("ชำระผ่าน สนง.ตัวแทน ");break;
						 case "G" : displayBean.setPaymentChannel("True Money");break;
						 case "H" : displayBean.setPaymentChannel("Tesco Lotus");break;
						 case "I" : displayBean.setPaymentChannel("Big C");break;
					 }
				 }
				 displayBean.setRcNo(receipt.getRcNo());
				 displayBean.setRcTerm(receipt.getRcTerm());
				 if(receipt.getTotalPrem()!=null){
					 displayBean.setTotalPrem(df.format(receipt.getTotalPrem()));
				 }else{
					 displayBean.setTotalPrem("0.00");
				 }
				 Date payTo =receipt.getPayTo()!=null?receipt.getPayTo().toGregorianCalendar().getTime():null;
				 displayBean.setPayTo(CssConvertUtils.toDateThaiString(payTo));
				 Date payFrom =receipt.getPayFrom()!=null?receipt.getPayFrom().toGregorianCalendar().getTime():null;
				 displayBean.setPayFrom(CssConvertUtils.toDateThaiString(payFrom));
				 list.add(displayBean);
			}
			Collections.sort(list, new Comparator<IliReceiptBean>() {
				public int compare(IliReceiptBean o1, IliReceiptBean o2) {
					return o2.getOrder()-o1.getOrder();
				}
			});
			
			requestAttributes.put("history", list);
		}
		return super.index();
	}
	
	private String setPayFrom(PolicyBean policy, Receipt receipt, IliReceiptBean displayBean) {
		String policyType = policy.getPolicyType();
		if("O".equals(policyType)){
			String rcyear = receipt.getRcYear();
			String rcterm = receipt.getRcTerm();
			int year = Integer.parseInt(rcyear);
			int term = Integer.parseInt(rcterm);
			displayBean.setOrder( (year*12)+term);
			return String.format("%s/%s", receipt.getRcYear(),receipt.getRcTerm());
		}else if("I".equals(policyType)||"G".equals(policyType)){
			String rcyear = receipt.getRcYear();
			String rcterm = receipt.getRcTerm();
			int year = Integer.parseInt(rcyear);
			int term = Integer.parseInt(rcterm);
			int mode = policy.getPaymentMode()!=null?policy.getPaymentMode()-1:0;

			displayBean.setOrder( (year*12)+term);
			
			term = term + mode;
			year = year + (term/13);
			term = (term%13);
			
			return String.format("%s/%s - %d/%d", receipt.getRcYear(),receipt.getRcTerm(),year,term);
		}else if("P".equals(policyType)){
			String rcterm = receipt.getRcTerm();
			int term = Integer.parseInt(rcterm);
			displayBean.setOrder(term);
			return rcterm;
		}
		return null;
	}

	public String main() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("~On PaymentInfoAction : main looking for policy no %s",policyNo));
		}
		if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null) {
			Map<String,PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
			
			PolicyBean policy = policies.get(policyNo);
			if(policy!=null){
				requestAttributes.put("policy", policy);
			}
			if("1".equals(ref)){
				requestAttributes.put("back", "secure/member/personal/info.html");
			}else{
				requestAttributes.put("back", "secure/member/policy/policyinfo.html");
			}
		}
		return super.index();
	}
	public String paymentCardAuthority() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				String errorMessage = authorizePaymentCard(policyNo);
				if(StringUtils.isBlank(errorMessage)){
					data.put("success", true);
					data.put("goto","secure/payment/paymentpaymentcard.html");
					data.put("policyNo", policyNo);
				}else{
					data.put("success", false);
					data.put("message",errorMessage);
			
				}
				result.setData(data);
			}
		}).run();
		
	}
	
	public String paymentValidate() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null) {
					Map<String,PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
					
					PolicyBean policy = policies.get(policyNo);
					if(policy != null){
//						String result = StringUtils.EMPTY;
						String result = paymentValidateFunction(policy);
						if(StringUtils.isEmpty(result)){
							data.put("success", true);
							data.put("goto","secure/payment/paymentcreditcard.html");
							data.put("policyNo", policyNo);
						}else{
							data.put("success", false);
							data.put("message", result);
					
						}
					}
				}
				result.setData(data);
			}
		}).run();
	}
	
	public String paymentgeneratedata() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On PaymentInfoAction : paymentgeneratedata");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				try{
					CssUser user = getSessionUser();
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					Css2c2pRequest css2c2pRequest = (Css2c2pRequest) sessionAttributes.get("css2c2pRequest");
					if(css2c2pRequest != null){
						CssPaymentEntity entity = new CssPaymentEntity();
						/////////////////////////////////////////////////////////////////////// Required Data
						//PropertyUtils.copyProperties(entity, css2c2pRequest);
						entity.setOrderNo(css2c2pRequest.getOrderId());
						entity.setInvoiceNo(css2c2pRequest.getInvoiceNo());
						entity.setCustomerId(user.getCustCode() == null ? " " : user.getCustCode());
						entity.setCustomerIdNo(user.getCardNo() == null ? " " : user.getCardNo());
						entity.setCustomerTitle(user.getTitleDesc()  == null ? " " : user.getTitleDesc());
						entity.setCustomerName(user.getFirstName()  == null ? " " : user.getFirstName());
						entity.setCustomerSname(user.getLastName()  == null ? " " : user.getLastName());
						entity.setCustomerTel(user.getTelNo()  == null ? " " : user.getTelNo());
						entity.setSend2c2pStatus("S");
						entity.setPolicyNo(css2c2pRequest.getPolicy().getPolicyNo());
						entity.setPolicyType(css2c2pRequest.getPolicy().getPolicyType());
						
						if(entity.getPolicyType().equals("P")){
							List<NotificationLetter> notificationLetter53List = dataWarehouseService.getNotificationLetterList(css2c2pRequest.getPolicy().getPolicyNo(), lp53, css2c2pRequest.getPolicy().getPolicyOrg().toString(), sf.format(new Date()));
							if(notificationLetter53List != null && notificationLetter53List.size() > 0){
								NotificationLetter nl = null;
								SimpleDateFormat sfP = new SimpleDateFormat("ddMMyyyy", new Locale("th", "TH"));
								for (NotificationLetter notificationLetter53 : notificationLetter53List) {
									Date nowDate = sfP.parse(sfP.format(new Date()));
									Date paymentDueDate = notificationLetter53.getPaymentDueDate().toGregorianCalendar().getTime();
									Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
									Date barcode1Ref2Date = sfP.parse(notificationLetter53.getBarcode1Ref2());
									boolean nlBoolean = true;
									if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
										/*( b ) ตรวจสอบว่ามีการชำระเบี้ยแล้วหรือไม่ จากข้อมูลใบเสร็จ*/
										List<ReceiptPa> paList = dataWarehouseService.getReceiptPaList(notificationLetter53.getPolicyNo(), sf.format(paymentDueDate), sf.format(barcode1Ref2Date));
										if(paList != null && paList.size() > 0){
											nlBoolean = false;
										}else{
											/*( c ) ตรวจสอบว่ามีการชำระเบี้ยผ่านเว็บ CSS แล้วหรือไม่*/
											List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(notificationLetter53.getPolicyNo(), notificationLetter53.getLetterType(), paymentDueDate);
											if(paymentList != null && paymentList.size() > 0){
												nlBoolean = false;
											}
										}
										if(nlBoolean){
											nl = notificationLetter53;
											break;
										}
									}
								}
								//NotificationLetter nl = notificationLetter53List.get(0);
								Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
								entity.setLetterType(lp53);
								entity.setPaymentDueDate(paymentDueDate);
								entity.setPolicyBranchCode7(String.valueOf(nl.getBranchNo7()));
							}else{
								List<NotificationLetter> notificationLetter63List = dataWarehouseService.getNotificationLetterList(css2c2pRequest.getPolicy().getPolicyNo(), lp63, css2c2pRequest.getPolicy().getPolicyOrg().toString(), sf.format(new Date()));
								if(notificationLetter63List != null && notificationLetter63List.size() > 0){
									SimpleDateFormat sfP = new SimpleDateFormat("ddMMyyyy", new Locale("th", "TH"));
									NotificationLetter nl = null;
									for (NotificationLetter notificationLetter63 : notificationLetter63List) {
										Date nowDate = sfP.parse(sfP.format(new Date()));
										Date paymentDueDate = notificationLetter63.getPaymentDueDate().toGregorianCalendar().getTime();
										Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
										Date barcode1Ref2Date = sfP.parse(notificationLetter63.getBarcode1Ref2());
										boolean nlBoolean = true;
										if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
											/*( b ) ตรวจสอบว่ามีการชำระเบี้ยแล้วหรือไม่ จากข้อมูลใบเสร็จ*/
											List<ReceiptPa> paList = dataWarehouseService.getReceiptPaList(notificationLetter63.getPolicyNo(), sf.format(paymentDueDate), sf.format(barcode1Ref2Date));
											if(paList != null && paList.size() > 0){
												nlBoolean = false;
											}else{
												/*( c ) ตรวจสอบว่ามีการชำระเบี้ยผ่านเว็บ CSS แล้วหรือไม่*/
												List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(notificationLetter63.getPolicyNo(), notificationLetter63.getLetterType(), paymentDueDate);
												if(paymentList != null && paymentList.size() > 0){
													nlBoolean = false;
												}
											}
											if(nlBoolean){
												nl = notificationLetter63;
												break;
											}
										}
									}
									//NotificationLetter nl = notificationLetter63List.get(0);
									Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
									entity.setLetterType(lp63);
									entity.setPaymentDueDate(paymentDueDate);
									entity.setPolicyBranchCode7(String.valueOf(nl.getBranchNo7()));
								}else{
									entity.setPaymentDueDate(CssConvertUtils.toDateFromStringDateThai(css2c2pRequest.getPolicy().getNextPaidDate()));
									entity.setPolicyBranchCode7(String.valueOf(css2c2pRequest.getPolicy().getPolicyOrg()));
								}
							}
						}else if(entity.getPolicyType().equals("O")){
							List<NotificationLetter> notificationLetterList = dataWarehouseService.getNotificationLetterList(css2c2pRequest.getPolicy().getPolicyNo(), lp51, css2c2pRequest.getPolicy().getPolicyOrg().toString(), sf.format(new Date()));
							if(notificationLetterList != null && notificationLetterList.size() > 0){
								SimpleDateFormat sfO = new SimpleDateFormat("ddMMyyyy", new Locale("th", "TH"));
								SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
								NotificationLetter nl = null;
								for (NotificationLetter notificationLetter : notificationLetterList) {
									Date nowDate = sfO.parse(sfO.format(new Date()));
									Date paymentDueDate = notificationLetter.getPaymentDueDate().toGregorianCalendar().getTime();
									Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
									Date barcode1Ref2Date = sfO.parse(notificationLetter.getBarcode1Ref2());
									boolean nlBoolean = true;
									if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
										/*( d )ตรวจสอบจากข้อมูลใบเสร็จ*/
										List<ReceiptOrd> receiptOrdList = dataWarehouseService.getReceiptOrdList(notificationLetter.getPolicyNo(), sf2.format(paymentDueDate), sf2.format(barcode1Ref2Date));
										if(receiptOrdList != null && receiptOrdList.size() > 0){
											//nlStatus = "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
											nlBoolean = false;
										}else{
											/*( e ) ตรวจสอบว่ามีการชำระเบี้ยผ่านเว็บ CSS แล้วหรือไม่*/
											List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(notificationLetter.getPolicyNo(), notificationLetter.getLetterType(), paymentDueDate);
											if(paymentList != null && paymentList.size() > 0){
												//nlStatus = "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
												nlBoolean = false;
											}
										}
										if(nlBoolean){
											nl = notificationLetter;
											break;
										}
									}
								}
								//NotificationLetter nl = notificationLetterList.get(0);
								Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
								entity.setLetterType(lp51);
								entity.setPaymentDueDate(paymentDueDate);
								entity.setPolicyBranchCode7(String.valueOf(nl.getBranchNo7()));
							}else{
								entity.setPaymentDueDate(CssConvertUtils.toDateFromStringDateThai(css2c2pRequest.getPolicy().getNextPaidDate()));
								entity.setPolicyBranchCode7(String.valueOf(css2c2pRequest.getPolicy().getPolicyOrg()));
							}
						}else if(entity.getPolicyType().equals("I") || entity.getPolicyType().equals("G")){
							List<NotificationLetter> notificationLetterList = dataWarehouseService.getNotificationLetterList(css2c2pRequest.getPolicy().getPolicyNo(), lp52, css2c2pRequest.getPolicy().getPolicyOrg().toString(), sf.format(new Date()));
							if(notificationLetterList != null && notificationLetterList.size() > 0){
								SimpleDateFormat sfI = new SimpleDateFormat("ddMMyy", new Locale("th", "TH"));
								SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
								NotificationLetter nl = null;
								for (NotificationLetter notificationLetter : notificationLetterList) {
									Date nowDate = sfI.parse(sfI.format(new Date()));
									Date paymentDueDate = notificationLetter.getPaymentDueDate().toGregorianCalendar().getTime();
									Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
									Date barcode1Ref2Date = sfI.parse(notificationLetter.getBarcode1Ref2());
									boolean nlBoolean = true;
									if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
										/*( d ) ตรวจสอบจากข้อมูลใบเสร็จ*/
										List<ReceiptInd> receiptIndList = dataWarehouseService.getReceiptIndList(notificationLetter.getPolicyNo(), sf2.format(paymentDueDate), sf2.format(barcode1Ref2Date));
										if(receiptIndList != null && receiptIndList.size() > 0){
											//return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
											nlBoolean = false;
										}else{
											/*( e ) ตรวจสอบว่ามีการชำระเบี้ยผ่านเว็บ CSS แล้วหรือไม่*/
											List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(notificationLetter.getPolicyNo(), notificationLetter.getLetterType(), paymentDueDate);
											if(paymentList != null && paymentList.size() > 0){
												//return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
												nlBoolean = false;
											}
										}
										if(nlBoolean){
											nl = notificationLetter;
											break;
										}
									}
								}
								//NotificationLetter nl = notificationLetterList.get(0);
								if(nl != null){
									Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
									entity.setLetterType(lp52);
									entity.setPaymentDueDate(paymentDueDate);
									entity.setPolicyBranchCode7(String.valueOf(nl.getBranchNo7()));
								}else{
									entity.setPaymentDueDate(CssConvertUtils.toDateFromStringDateThai(css2c2pRequest.getPolicy().getNextPaidDate()));
									entity.setPolicyBranchCode7(String.valueOf(css2c2pRequest.getPolicy().getPolicyOrg()));
								}
							}else{
								entity.setPaymentDueDate(CssConvertUtils.toDateFromStringDateThai(css2c2pRequest.getPolicy().getNextPaidDate()));
								entity.setPolicyBranchCode7(String.valueOf(css2c2pRequest.getPolicy().getPolicyOrg()));
							}
						}
						
						entity.setPolicyYear(css2c2pRequest.getPolicy().getPolicyYear());
						entity.setPlanCode(css2c2pRequest.getPolicy().getPrdCode());
						entity.setPlanName(css2c2pRequest.getPolicy().getPrdName());
						entity.setCreateBy(user.getFullname());
						entity.setCreateDate(new Date());
						entity.setCreateSystem("CSS");
						////////////////////////////////////////////////////////////////////////////////////
						entity.setCustomerEmail(user.getEmail());
						entity.setPaymentFee(0.0);
						entity.setPaymentDiscount(0.0);
						entity.setSendHydraStatus(null);
						entity.setPlanDescription(null);
						entity.setPolicyBranchCode(css2c2pRequest.getPolicy().getBranchNo());
						entity.setPolicyBranchName(null);
						entity.setPolicyBranchTel(null);
						
						Double premium = css2c2pRequest.getPremium().doubleValue();
						entity.setPaymentAmount(premium);
						entity.setNetPayment(premium);
						entity.setPremium(premium);
						entity.setPremiunPerMonth(css2c2pRequest.getPolicy().getPremiumAmount());
						
						entity.setAgentCode7(css2c2pRequest.getPolicy().getAgentCode());
						entity.setAgentCode5(null);
						
						entity.setFromYear(css2c2pRequest.getFromYear());
						entity.setFromPeriod(css2c2pRequest.getFromPeriod());
						entity.setToYear(css2c2pRequest.getToYear());
						entity.setToPeriod(css2c2pRequest.getToPeriod());
						cssPaymentService.saveCssPayment(entity);
						data.put("success", true);
					}
				}catch (Exception e) {
					data.put("success", false);
					data.put("message", "ไม่สามารถทำรายการได้");
				}
				result.setData(data);
			}
		}).run();
	}
	
	public String paymentcreditcard() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("~On PaymentInfoAction : paymentcreditcard looking for policy no %s",policyNo));
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null) {
					Map<String,PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
					PolicyBean policy = policies.get(policyNo);
					if(policy!=null){
						requestAttributes.put("policy", policy);
					}
					
					boolean success = true;
					
					SimpleDateFormat sf = new SimpleDateFormat("yyyyMM", Locale.US);
					String key = sf.format(new Date());
					
					SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");

					Integer premium = 0;
					Integer paymentMode = 0;
					Integer premiumPerMonth = 0;
					String letterYear = null;
					String letterMonth = null;
					String fromYear = null;
					String fromPeriod = null;
					String toYear = null;
					String toPeriod = null;
					String nlchk = null;
					
					if(policy.getPolicyType().equals("P")){
						List<NotificationLetter> notificationLetter53List = dataWarehouseService.getNotificationLetterList(policy.getPolicyNo(), lp53, policy.getPolicyOrg().toString(), sf2.format(new Date()));
						if(notificationLetter53List != null && notificationLetter53List.size() > 0){
							SimpleDateFormat sfP = new SimpleDateFormat("ddMMyyyy", new Locale("th", "TH"));
							for (NotificationLetter notificationLetter53 : notificationLetter53List) {
								NotificationLetter nl = notificationLetter53;
								Date nowDate = sfP.parse(sfP.format(new Date()));
								Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
								Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
								Date barcode1Ref2Date = sfP.parse(nl.getBarcode1Ref2());
								if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
									String[] allText = nl.getAllText().split(Pattern.quote("|"));
									premium = nl.getTotalPremiumAmount();
									premiumPerMonth = policy.getPremiumAmount().intValue();
									letterYear = allText[25];
									letterMonth = allText[26];
									paymentMode = Integer.parseInt(allText[10]);
									fromYear = letterYear;
									fromPeriod = letterMonth;
									nlchk = "Y";
									break;
								}
							}
							
//							NotificationLetter nl = notificationLetter53List.get(0);
//							String[] allText = nl.getAllText().split(Pattern.quote("|"));
//							premium = nl.getTotalPremiumAmount();
//							premiumPerMonth = policy.getPremiumAmount().intValue();
//							letterYear = allText[25];
//							letterMonth = allText[26];
//							paymentMode = Integer.parseInt(allText[10]);
//							fromYear = letterYear;
//							fromPeriod = letterMonth;
							
							Map<String, String> rMap = cssMasterDataService.getOrderIdAndInvoiceNo(mode, key);
							//prepare data to Payment
							String pDesc = policy.getPrdName();
							String orderId = rMap.get("orderId");			
							String invoiceNo = rMap.get("invoiceNo");					
							String amount = convertDoubleToString((double)premium); 			// 000000055050 = 550.50
							String cEmail = policy.getEmail();
							
							prepareDataTo2C2P(pDesc, orderId, invoiceNo, amount, cEmail, policy, String.format("%,.2f", (double)premium), String.format("%,.2f", (double)premiumPerMonth), paymentMode, letterYear, letterMonth, premium, premiumPerMonth, fromYear, fromPeriod, toYear, toPeriod, nlchk);
						}else{
							List<NotificationLetter> notificationLetter63List = dataWarehouseService.getNotificationLetterList(policy.getPolicyNo(), lp63, policy.getPolicyOrg().toString(), sf2.format(new Date()));
							if(notificationLetter63List != null && notificationLetter63List.size() > 0){
								SimpleDateFormat sfP = new SimpleDateFormat("ddMMyyyy", new Locale("th", "TH"));
								for (NotificationLetter notificationLetter63 : notificationLetter63List) {
									NotificationLetter nl = notificationLetter63;
									Date nowDate = sfP.parse(sfP.format(new Date()));
									Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
									Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
									Date barcode1Ref2Date = sfP.parse(nl.getBarcode1Ref2());
									if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
										String[] allText = nl.getAllText().split(Pattern.quote("|"));
										premium = nl.getTotalPremiumAmount();
										premiumPerMonth = policy.getPremiumAmount().intValue();
										letterYear = allText[25];
										letterMonth = allText[26];
										paymentMode = Integer.parseInt(allText[10]);
										fromYear = letterYear;
										fromPeriod = letterMonth;
										nlchk = "Y";
										break;
									}
								}
								
//								NotificationLetter nl = notificationLetter63List.get(0);
//								String[] allText = nl.getAllText().split(Pattern.quote("|"));
//								premium = nl.getTotalPremiumAmount();
//								premiumPerMonth = policy.getPremiumAmount().intValue();
//								letterYear = allText[25];
//								letterMonth = allText[26];
//								paymentMode = Integer.parseInt(allText[10]);
//								fromYear = letterYear;
//								fromPeriod = letterMonth;
								
								Map<String, String> rMap = cssMasterDataService.getOrderIdAndInvoiceNo(mode, key);
								//prepare data to Payment
								String pDesc = policy.getPrdName();
								String orderId = rMap.get("orderId");			
								String invoiceNo = rMap.get("invoiceNo");					
								String amount = convertDoubleToString((double)premium); 			// 000000055050 = 550.50
								String cEmail = policy.getEmail();
								
								prepareDataTo2C2P(pDesc, orderId, invoiceNo, amount, cEmail, policy, String.format("%,.2f", (double)premium), String.format("%,.2f", (double)premiumPerMonth), paymentMode, letterYear, letterMonth, premium, premiumPerMonth, fromYear, fromPeriod, toYear, toPeriod, nlchk);
							}
						}
					}else if(policy.getPolicyType().equals("O")){
						List<NotificationLetter> notificationLetterList = dataWarehouseService.getNotificationLetterList(policy.getPolicyNo(), lp51, policy.getPolicyOrg().toString(), sf2.format(new Date()));
						if(notificationLetterList != null && notificationLetterList.size() > 0){
							SimpleDateFormat sfO = new SimpleDateFormat("ddMMyyyy", new Locale("th", "TH"));
							
							for (NotificationLetter notificationLetter : notificationLetterList) {
								NotificationLetter nl = notificationLetter;
								Date nowDate = sfO.parse(sfO.format(new Date()));
								Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
								Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
								Date barcode1Ref2Date = sfO.parse(nl.getBarcode1Ref2());
								boolean nlBoolean = true;
								if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
									/*( d )ตรวจสอบจากข้อมูลใบเสร็จ*/
									List<ReceiptOrd> receiptOrdList = dataWarehouseService.getReceiptOrdList(nl.getPolicyNo(), sf2.format(paymentDueDate), sf2.format(barcode1Ref2Date));
									if(receiptOrdList != null && receiptOrdList.size() > 0){
										//nlStatus = "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
										nlBoolean = false;
									}else{
										/*( e ) ตรวจสอบว่ามีการชำระเบี้ยผ่านเว็บ CSS แล้วหรือไม่*/
										List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(nl.getPolicyNo(), nl.getLetterType(), paymentDueDate);
										if(paymentList != null && paymentList.size() > 0){
											//nlStatus = "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
											nlBoolean = false;
										}
									}
									if(nlBoolean){
										String[] allText = nl.getAllText().split(Pattern.quote("|"));
										premium = nl.getTotalPremiumAmount();
										premiumPerMonth = policy.getPremiumAmount().intValue();
										letterYear = allText[25];
										letterMonth = allText[26];
										paymentMode = Integer.parseInt(allText[10]);
										fromYear = letterYear;
										fromPeriod = letterMonth;
										nlchk = "Y";
										break;
									}
									
								}
							}
//							NotificationLetter nl = notificationLetterList.get(0);
//							String[] allText = nl.getAllText().split(Pattern.quote("|"));
//							premium = nl.getTotalPremiumAmount();
//							premiumPerMonth = policy.getPremiumAmount().intValue();
//							letterYear = allText[25];
//							letterMonth = allText[26];
//							paymentMode = Integer.parseInt(allText[10]);
//							fromYear = letterYear;
//							fromPeriod = letterMonth;
							
							Map<String, String> rMap = cssMasterDataService.getOrderIdAndInvoiceNo(mode, key);
							//prepare data to Payment
							String pDesc = policy.getPrdName();
							String orderId = rMap.get("orderId");			
							String invoiceNo = rMap.get("invoiceNo");					
							String amount = convertDoubleToString((double)premium); 			// 000000055050 = 550.50
							String cEmail = policy.getEmail();
							
							prepareDataTo2C2P(pDesc, orderId, invoiceNo, amount, cEmail, policy, String.format("%,.2f", (double)premium), String.format("%,.2f", (double)premiumPerMonth), paymentMode, letterYear, letterMonth, premium, premiumPerMonth, fromYear, fromPeriod, toYear, toPeriod, nlchk);
						}
					}else if(policy.getPolicyType().equals("I") || policy.getPolicyType().equals("G")){
						
						List<NotificationLetter> notificationLetterList = dataWarehouseService.getNotificationLetterList(policy.getPolicyNo(), lp52, policy.getPolicyOrg().toString(), sf2.format(new Date()));
						if(notificationLetterList != null && notificationLetterList.size() > 0){
							SimpleDateFormat sfI = new SimpleDateFormat("ddMMyy", new Locale("th", "TH"));

							NotificationLetter nl = null;
							for (NotificationLetter notificationLetter : notificationLetterList) {
								Date nowDate = sfI.parse(sfI.format(new Date()));
								Date paymentDueDate = notificationLetter.getPaymentDueDate().toGregorianCalendar().getTime();
								Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
								Date barcode1Ref2Date = sfI.parse(notificationLetter.getBarcode1Ref2());
								boolean nlBoolean = true;
								if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
									/*( d ) ตรวจสอบจากข้อมูลใบเสร็จ*/
									List<ReceiptInd> receiptIndList = dataWarehouseService.getReceiptIndList(notificationLetter.getPolicyNo(), sf2.format(paymentDueDate), sf2.format(barcode1Ref2Date));
									if(receiptIndList != null && receiptIndList.size() > 0){
										//return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
										nlBoolean = false;
									}else{
										/*( e ) ตรวจสอบว่ามีการชำระเบี้ยผ่านเว็บ CSS แล้วหรือไม่*/
										List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(notificationLetter.getPolicyNo(), notificationLetter.getLetterType(), paymentDueDate);
										if(paymentList != null && paymentList.size() > 0){
											//return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
											nlBoolean = false;
										}
									}
									if(nlBoolean){
										nl = notificationLetter;
										break;
									}
								}
							}
							
							if(nl != null){
								String[] allText = nl.getAllText().split(Pattern.quote("|"));
								paymentMode = Integer.parseInt(allText[11]);
								
								Date nowDate = sfI.parse(sfI.format(new Date()));
								Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
								Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
								Date barcode1Ref2Date = sfI.parse(nl.getBarcode1Ref2());
								if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
									if(paymentMode == 1){
										Date payment60DueDate = CssConvertUtils.addDate(paymentDueDate,60);
										if(nowDate.compareTo(payment60DueDate) <= 0){
											premium = nl.getTotalPremiumAmount();
											premiumPerMonth = policy.getPremiumAmount().intValue();
											letterYear = String.valueOf(nl.getLetterYear());
											letterMonth = String.valueOf(nl.getLetterMonth());
											fromYear = allText[12];
											fromPeriod = allText[13];
											toYear = allText[14];
											toPeriod = allText[15];
											nlchk = "Y";
										}
									}else if(paymentMode >= 1 && paymentMode <= 5){
										Date payment60DueDate = CssConvertUtils.addDate(paymentDueDate,60);
										if(nowDate.compareTo(payment60DueDate) <= 0){
											premium = nl.getTotalPremiumAmount();
											premiumPerMonth = policy.getPremiumAmount().intValue();
											letterYear = String.valueOf(nl.getLetterYear());
											letterMonth = String.valueOf(nl.getLetterMonth());	
											fromYear = allText[12];
											fromPeriod = allText[13];
											toYear = allText[14];
											toPeriod = allText[15];
											nlchk = "Y";
										}
									}else if(paymentMode >= 6 && paymentMode <= 11){
										Date payment30DueDate = CssConvertUtils.addDate(paymentDueDate,31);
										if(nowDate.compareTo(paymentDueDate) <= 0 || nowDate.compareTo(payment30DueDate) <= 0){
											premium = nl.getTotalPremiumAmount();
											premiumPerMonth = policy.getPremiumAmount().intValue();
											letterYear = String.valueOf(nl.getLetterYear());
											letterMonth = String.valueOf(nl.getLetterMonth());
											fromYear = allText[12];
											fromPeriod = allText[13];
											toYear = allText[14];
											toPeriod = allText[15];
											nlchk = "Y";
										}else{
											BigDecimal pmCal = new BigDecimal(policy.getPremiumAmount()*paymentMode);
											premium = pmCal.intValue();
											premiumPerMonth = policy.getPremiumAmount().intValue();
											letterYear = String.valueOf(nl.getLetterYear());
											letterMonth = String.valueOf(nl.getLetterMonth());
											fromYear = allText[12];
											fromPeriod = allText[13];
											toYear = allText[14];
											toPeriod = allText[15];
											nlchk = "Y";
										}
									}else if(paymentMode == 12){
										Date payment30DueDate = CssConvertUtils.addDate(paymentDueDate,31);
										if(nowDate.compareTo(payment30DueDate) <= 0){
											premium = nl.getTotalPremiumAmount();
											premiumPerMonth = policy.getPremiumAmount().intValue();
											letterYear = String.valueOf(nl.getLetterYear());
											letterMonth = String.valueOf(nl.getLetterMonth());
											fromYear = allText[12];
											fromPeriod = allText[13];
											toYear = allText[14];
											toPeriod = allText[15];
											nlchk = "Y";
										}else{
											BigDecimal pmCal = new BigDecimal((policy.getPremiumAmount()*11)*0.035);
											pmCal = pmCal.setScale(0, BigDecimal.ROUND_DOWN);
											BigDecimal ttPm = new BigDecimal(policy.getPremiumAmount()*11).subtract(pmCal);
											premium = policy.getPremiumAmount().intValue() + ttPm.intValue();
											premiumPerMonth = policy.getPremiumAmount().intValue();
											letterYear = String.valueOf(nl.getLetterYear());
											letterMonth = String.valueOf(nl.getLetterMonth());
											fromYear = allText[12];
											fromPeriod = allText[13];
											toYear = allText[14];
											toPeriod = allText[15];
											nlchk = "Y";
										}
									}
								}
							}else{
								/*( f ) ตรวจสอบงวดการชำระเบี้ยจากระบบ other channel
								 *( g ) ตรวจสอบจากข้อมูลใบเสร็จ*/
								Integer mode = getModeFromLastReceiptIndByPolicyNo(policy.getPolicyNo());
								if(mode != null){
									if(mode >= 1 && mode <= 5){
										BigDecimal pmCal = new BigDecimal(policy.getPremiumAmount());
										premium = pmCal.intValue();
										premiumPerMonth = policy.getPremiumAmount().intValue();
										letterYear = String.valueOf(policy.getPolicyYear());
										letterMonth = String.valueOf(policy.getPaymentTerm() + 1);
										paymentMode = 1;
										fromYear = letterYear;
										fromPeriod = letterMonth;
										toYear = letterYear;
										toPeriod = String.valueOf(policy.getPaymentTerm() + 1);
										nlchk = "N";
									}
								}
							}
						}else{
							/*( f ) ตรวจสอบงวดการชำระเบี้ยจากระบบ other channel
							 *( g ) ตรวจสอบจากข้อมูลใบเสร็จ*/
							Integer mode = getModeFromLastReceiptIndByPolicyNo(policy.getPolicyNo());
							if(mode != null){
								if(mode >= 1 && mode <= 5){
									BigDecimal pmCal = new BigDecimal(policy.getPremiumAmount());
									premium = pmCal.intValue();
									premiumPerMonth = policy.getPremiumAmount().intValue();
									letterYear = String.valueOf(policy.getPolicyYear());
									letterMonth = String.valueOf(policy.getPaymentTerm() + 1);
									paymentMode = 1;
									fromYear = letterYear;
									fromPeriod = letterMonth;
									toYear = letterYear;
									toPeriod = String.valueOf(policy.getPaymentTerm() + 1);
									nlchk = "N";
								}
							}
						}
						
						if(success){
							Map<String, String> rMap = cssMasterDataService.getOrderIdAndInvoiceNo(mode, key);
							//prepare data to Payment
							String pDesc = policy.getPrdName();
							String orderId = rMap.get("orderId");			
							String invoiceNo = rMap.get("invoiceNo");					
							String amount = convertDoubleToString((double)premium); 			// 000000055050 = 550.50
							String cEmail = policy.getEmail();
							
							prepareDataTo2C2P(pDesc, orderId, invoiceNo, amount, cEmail, policy, String.format("%,.2f", (double)premium), String.format("%,.2f", (double)premiumPerMonth), paymentMode, letterYear, letterMonth, premium, premiumPerMonth, fromYear, fromPeriod, toYear, toPeriod, nlchk);
						}
					}
				}
			}
		}).run();
	}
	
	public String recalculatepaymentcreditcard() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("~On PaymentInfoAction : recalculatepaymentcreditcard"));
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Css2c2pRequest css2c2pRequest = (Css2c2pRequest) sessionAttributes.get("css2c2pRequest");
				Map<String, Object> data = new HashMap<String, Object>();
				if(css2c2pRequest != null){
					double paymentPeriod = Double.parseDouble(params.selectedPaymentPeriod);
					double premiumAmount = Double.parseDouble(params.selectedPremiumAmount);
					
					String newAmount = convertDoubleToString(paymentPeriod*premiumAmount);
					String fromYear = css2c2pRequest.getFromYear();
					String fromPeriod = css2c2pRequest.getFromPeriod();

					if(fromYear != null && fromPeriod != null){
						Integer fromYearInt = Integer.parseInt(fromYear);
						Integer fromPeriodInt = Integer.parseInt(fromPeriod);
						Integer resultPeriod = fromPeriodInt + ((int)premiumAmount-1);
						if(resultPeriod > 12){
							Integer m = (resultPeriod%12);
							css2c2pRequest.setToYear(String.valueOf((fromYearInt + 1)));
							css2c2pRequest.setToPeriod(String.valueOf(m));
						}else{
							css2c2pRequest.setToYear(String.valueOf(fromYearInt));
							css2c2pRequest.setToPeriod(String.valueOf(resultPeriod));
						}
					}
					
					sessionAttributes.remove("css2c2pRequest");
					requestAttributes.remove("css2c2pRequest");
					
					prepareDataTo2C2P(css2c2pRequest.getPaymentDescription(), 
							css2c2pRequest.getOrderId(), 
							css2c2pRequest.getInvoiceNo(), 
							newAmount, 
							css2c2pRequest.getCustomerEmail(), 
							css2c2pRequest.getPolicy(), 
							String.format("%,.2f", (paymentPeriod*premiumAmount)), 
							css2c2pRequest.getPremiumPerMonthTemp(), 
							css2c2pRequest.getPaymentMode(), 
							css2c2pRequest.getLetterYear(), 
							css2c2pRequest.getLetterMonth(), 
							Integer.parseInt(css2c2pRequest.getPremiumShow()), 
							Integer.parseInt(css2c2pRequest.getPremiumPerMonthShow()),
							css2c2pRequest.getFromYear(),
							css2c2pRequest.getFromPeriod(),
							css2c2pRequest.getToYear(),
							css2c2pRequest.getToPeriod(),
							css2c2pRequest.getNlChk());
					
					data.put("session", sessionAttributes.get("css2c2pRequest"));
					data.put("success", true);
					result.setData(data);
				}
			}
		}).run();
	}
	
	public String paymentsuccess() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On PaymentInfoAction : paymentsuccess");
		}
		String responseCode = request.getParameter("channel_response_code");
		//0036 = Cancel from 2C2P Page
		if(!responseCode.equals("0036")){
			String orderNo = request.getParameter("order_id");
			CssPaymentEntity entity = cssPaymentService.findCssPaymentByOrderNo(orderNo);
			
			entity.setAmount(request.getParameter("amount"));
			entity.setApprovalCode(request.getParameter("approval_code"));
			entity.setBackendInvoice(request.getParameter("backend_invoice"));
			entity.setBrowserInfo(request.getParameter("browser_info"));
			entity.setChannelResponseCode(request.getParameter("channel_response_code"));
			entity.setChannelResponseDesc(request.getParameter("channel_response_desc"));
			entity.setCurrency(request.getParameter("currency"));
			String eci = StringUtils.trimToNull(request.getParameter("eci"));
			entity.setEci(eci == null ? 0 : Integer.parseInt(request.getParameter("eci")));
			entity.setHashValue(request.getParameter("hash_value"));
			entity.setInvoiceNo(request.getParameter("invoice_no"));
			entity.setIppInterestRate(request.getParameter("ippInterestRate"));
			entity.setIppInterestType(request.getParameter("ippInterestType"));
			entity.setIppMerchantAbsorbRate(request.getParameter("ippMerchantAbsorbRate"));
			entity.setIppPeriod(request.getParameter("ippPeriod"));
			entity.setMaskedPan(request.getParameter("masked_pan"));
			entity.setMerchantId(request.getParameter("merchant_id"));
			entity.setOrderNo(request.getParameter("order_id"));
			entity.setPaidAgent(request.getParameter("paid_agent"));
			entity.setPaidChannel(request.getParameter("paid_channel"));
			
			String paymentChannel = StringUtils.trimToNull(request.getParameter("payment_channel"));
			entity.setPaymentChannel(paymentChannel == null ? 0 : Integer.parseInt(request.getParameter("payment_channel")));
			
			String paymentStatus = StringUtils.trimToNull(request.getParameter("payment_status"));
			entity.setPaymentStatus(paymentStatus == null ? 0 : Integer.parseInt(request.getParameter("payment_status")));
			
			String recurringUniqueId = StringUtils.trimToNull(request.getParameter("recurring_unique_id"));
			entity.setRecurringUniqueId(recurringUniqueId == null ? 0 : Integer.parseInt(request.getParameter("recurring_unique_id"))); 
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //2016-10-12 14:31:48
			entity.setRequestTimestamp(sf.parse(request.getParameter("request_timestamp")));
			entity.setStoredCardUniqueId(request.getParameter("stored_card_unique_id"));
			entity.setTransactionDatetime(request.getParameter("transaction_datetime"));
			entity.setTransactionRef(request.getParameter("transaction_ref"));
			entity.setUserDefined1(request.getParameter("user_defined_1"));
			entity.setUserDefined2(request.getParameter("user_defined_2"));
			entity.setUserDefined3(request.getParameter("user_defined_3"));
			entity.setUserDefined4(request.getParameter("user_defined_4"));
			entity.setUserDefined5(request.getParameter("user_defined_5"));
			entity.setVersion(request.getParameter("version"));
			entity.setSend2c2pStatus("R");
	
			cssPaymentService.updateCssPayment(entity);
			
			CssPaymentBean paymentBean = prepareDataToHydra(entity);
			HydraWsCssPaymentV1ESBService ws = hydraWsClientFactory.getHydraWsCssPaymentV1ESBService();
			Boolean result = ws.postSaveCssPayment(paymentBean);
			if(result){
				entity.setSendHydraStatus("R");
			}else{
				entity.setSendHydraStatus("S");
			}
			
			cssPaymentService.updateCssPayment(entity);
			
			return "success";
		}else{		
			return "cancel";
		}	
	}
	
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	private static String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();
		
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}

		return formatter.toString();
	}

	public static String calculateHMAC(String data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		return toHexString(mac.doFinal(data.getBytes()));
	}
	
	public static String convertDoubleToString(Double premiumValue){
		BigDecimal b = new BigDecimal(premiumValue).setScale(2, BigDecimal.ROUND_HALF_UP);
		Double source = b.doubleValue();
		String[] rArr = (String.valueOf(source)).split(Pattern.quote("."));
		String r1 = String.format("%010d", Integer.parseInt(rArr[0]));
		String r2 = String.format("%-2d", Integer.parseInt(rArr[1])).replace(' ', '0');
		String result = r1+r2;
		return result;
	}
	
	public void prepareDataTo2C2P(String pDesc, String orderId, String invoiceNo, String amount, String cEmail, PolicyBean policy, String premiumTemp, String premiumPerMonthTemp, Integer paymentMode, String letterYear, String letterMonth, Integer premiumShow, Integer premiumPerMonthShow, String fromYear, String fromPeriod, String toYear, String toPeriod, String nlChk){
		try {
			String resultUrl1Str = resultUrl1.equals("#")?null:resultUrl1;
			
			String strSignatureString = version + 		// version
					merchantId + 						// merchant_id
					(pDesc == null ? "":pDesc)+								// payment_description
					(orderId == null ? "":orderId)+ 							// order_id
					(invoiceNo == null ? "":invoiceNo)+ 						// invoice_no
					currency + 							// currency
					(amount == null ? "":amount) + 							// amount
					(cEmail == null ? "":cEmail) +							// customer_email
					resultUrl1Str + 						// result_url_1
					resultUrl2 +
					defaultLang;							// result_url_2
			
			String hashValue = calculateHMAC(strSignatureString, secretKey);
			
			//put to session
			Css2c2pRequest css2c2pRequest = new Css2c2pRequest();
			css2c2pRequest.setVersion(version);
			css2c2pRequest.setMerchantId(merchantId);
			css2c2pRequest.setPaymentDescription(pDesc);
			css2c2pRequest.setOrderId(orderId);
			css2c2pRequest.setInvoiceNo(invoiceNo);
			css2c2pRequest.setCurrency(currency);
			css2c2pRequest.setAmount(amount);
			css2c2pRequest.setCustomerEmail(cEmail);
			css2c2pRequest.setResultUrl1(resultUrl1Str);
			css2c2pRequest.setResultUrl2(resultUrl2);
			css2c2pRequest.setHashValue(hashValue);
			css2c2pRequest.setPolicy(policy);
			css2c2pRequest.setPremiumTemp(premiumTemp);
			css2c2pRequest.setPremiumPerMonthTemp(premiumPerMonthTemp);
			css2c2pRequest.setPaymentMode(paymentMode);
			css2c2pRequest.setLetterYear(letterYear);
			css2c2pRequest.setLetterMonth(letterMonth);
			css2c2pRequest.setFromYear(fromYear);
			css2c2pRequest.setFromPeriod(fromPeriod);
			css2c2pRequest.setToYear(toYear);
			css2c2pRequest.setToPeriod(toPeriod);
			css2c2pRequest.setNlChk(nlChk);
			
			css2c2pRequest.setPremiumShow(String.valueOf(premiumShow));
			css2c2pRequest.setPremiumPerMonthShow(String.valueOf(premiumPerMonthShow));
			
			DecimalFormat formatter = new DecimalFormat("#,###.00");
			Number premiumNum = formatter.parse(premiumTemp);
			Number premiumPerMonthNum = formatter.parse(premiumPerMonthTemp);
			
			
			css2c2pRequest.setPremium(premiumNum.intValue());
			css2c2pRequest.setPremiumPerMonth(premiumPerMonthNum.intValue());
			requestAttributes.put("css2c2pRequest", css2c2pRequest);
			sessionAttributes.put("css2c2pRequest", css2c2pRequest);
		} catch (InvalidKeyException | SignatureException
				| NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public CssPaymentBean prepareDataToHydra(CssPaymentEntity entity){
		CssPaymentBean bean = null;
		try {
			PaymentBean paymentBean = new PaymentBean();
			paymentBean.setCustomerEmail(StringUtils.trimToEmpty(entity.getCustomerEmail()));
			paymentBean.setCustomerIdNo(StringUtils.trimToEmpty(entity.getCustomerIdNo()));
			paymentBean.setCustomerName(StringUtils.trimToEmpty(entity.getCustomerName()));
			paymentBean.setCustomerNo(StringUtils.trimToEmpty(entity.getCustomerId()));
			paymentBean.setCustomerSname(StringUtils.trimToEmpty(entity.getCustomerSname()));
			paymentBean.setCustomerTel(StringUtils.trimToEmpty(entity.getCustomerTel()));
			paymentBean.setCustomerTitle(StringUtils.trimToEmpty(entity.getCustomerTitle()));
			paymentBean.setInvoiceNo(StringUtils.trimToEmpty(entity.getInvoiceNo()));
			paymentBean.setOrderNo(StringUtils.trimToEmpty(entity.getOrderNo()));
			paymentBean.setPaymentAmount(entity.getPaymentAmount() != null ? entity.getPaymentAmount().toString() : "0.00");
			paymentBean.setFromPeriod(StringUtils.trimToEmpty(entity.getFromPeriod()));
			paymentBean.setFromYear(StringUtils.trimToEmpty(entity.getFromYear()));
			paymentBean.setToPeriod(StringUtils.trimToEmpty(entity.getToPeriod()));
			paymentBean.setToYear(StringUtils.trimToEmpty(entity.getToYear()));
			
			
			GregorianCalendar gcal = new GregorianCalendar();
		    XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			paymentBean.setPaymentDate(xgcal);
			
			paymentBean.setPaymentFee(entity.getPaymentFee() != null ? entity.getPaymentFee().toString() : "0.00");
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			thaisamut.osgi.targetbundles.hydraws.csspayment.v1.PolicyBean policyBean = new thaisamut.osgi.targetbundles.hydraws.csspayment.v1.PolicyBean();
			policyBean.setAgentCode5(StringUtils.trimToEmpty(entity.getAgentCode5()));
			policyBean.setAgentCode7(StringUtils.trimToEmpty(entity.getAgentCode7()));
			policyBean.setPlanCode(StringUtils.trimToEmpty(entity.getPlanCode()));
			policyBean.setPlanDescription(StringUtils.trimToEmpty(entity.getPlanDescription()));
			policyBean.setPlanName(StringUtils.trimToEmpty(entity.getPlanName()));
			policyBean.setPolicyBranchCode(StringUtils.trimToEmpty(entity.getPolicyBranchCode()));
			policyBean.setPolicyBranchCode7(StringUtils.trimToEmpty(entity.getPolicyBranchCode7()));
			policyBean.setPolicyBranchName(StringUtils.trimToEmpty(entity.getPolicyBranchName()));
			policyBean.setPolicyBranchTel(StringUtils.trimToEmpty(entity.getPolicyBranchTel()));
			policyBean.setPolicyNo(StringUtils.trimToEmpty(entity.getPolicyNo()));
			policyBean.setPolicyType(thaisamut.osgi.targetbundles.hydraws.csspayment.v1.PolicyType.fromValue(entity.getPolicyType()));
			policyBean.setPolicyYear(entity.getPolicyYear());
			policyBean.setPremium(entity.getPremium());
			policyBean.setPremiumPerMonth(entity.getPremiunPerMonth());
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			CssUser user = getSessionUser();
			
			SystemAuditLog systemAuditLogBean = new SystemAuditLog();
			systemAuditLogBean.setCreateBy(StringUtils.trimToEmpty(user.getFullname()));
			systemAuditLogBean.setCreateDate(xgcal);
			systemAuditLogBean.setCreateSystem("CSS");
			
			bean = new CssPaymentBean();
			bean.setPayment(paymentBean);
			bean.setPolicy(policyBean);
			bean.setSystemAuditLog(systemAuditLogBean);
			
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return bean;
	}

	
	public String paymentValidateFunction(PolicyBean policy){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		try{
			if(policy.getPolicyType().equals("P")){
				//1. ตรวจสอบแบบประกันว่าบริษัท absorb ค่าธรรมเนียมหรือไม่
				Petition p = dataWarehouseService.checkAbsorbFee(policy.getPrdCode(), policy.getPolicyType(), "2", policy.getPremiumAmount());
				if(p != null && !p.isStatus()){
					return "กรมธรรม์นี้ไม่สามารถชำระเบี้ยประกันภัยผ่านบัตรเครดิตได้ กรุณาติดต่อสาขาเพื่อทำการชำระเบี้ยต่อไป";
				}
				
				//2. ตรวจสอบข้อมูลจดหมายแจ้งเตือน case LP53
				List<NotificationLetter> notificationLetter53List = dataWarehouseService.getNotificationLetterList(policy.getPolicyNo(), lp53, policy.getPolicyOrg().toString(), sf.format(new Date()));
				if(notificationLetter53List != null && notificationLetter53List.size() > 0){
					SimpleDateFormat sfP = new SimpleDateFormat("ddMMyyyy", new Locale("th", "TH"));
					boolean nlBoolean = true;
					for (NotificationLetter notificationLetter53 : notificationLetter53List) {
						NotificationLetter nl = notificationLetter53;
						Date nowDate = sfP.parse(sfP.format(new Date()));
						Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
						Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
//						Date payment30DueDate = CssConvertUtils.addDate(paymentDueDate,31);
						Date barcode1Ref2Date = sfP.parse(nl.getBarcode1Ref2());
						/*( a ) ตรวจสอบว่าใบแจ้งเตือนถึง due ที่สามารถชำระได้หรือไม่*/
						if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
							/*( b ) ตรวจสอบว่ามีการชำระเบี้ยแล้วหรือไม่ จากข้อมูลใบเสร็จ*/
							List<ReceiptPa> paList = dataWarehouseService.getReceiptPaList(nl.getPolicyNo(), sf.format(paymentDueDate), sf.format(barcode1Ref2Date));
							if(paList != null && paList.size() > 0){
								return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
							}else{
								/*( c ) ตรวจสอบว่ามีการชำระเบี้ยผ่านเว็บ CSS แล้วหรือไม่*/
								List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(nl.getPolicyNo(), nl.getLetterType(), paymentDueDate);
								if(paymentList != null && paymentList.size() > 0){
									return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
								}
							}
							nlBoolean = true;
							break;
						}else{
							nlBoolean = false;
						}
					}
					if(!nlBoolean){
						return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
					}
				}else{
					//2. ตรวจสอบข้อมูลจดหมายแจ้งเตือน case LP63
					List<NotificationLetter> notificationLetter63List = dataWarehouseService.getNotificationLetterList(policy.getPolicyNo(), lp63, policy.getPolicyOrg().toString(), sf.format(new Date()));
					if(notificationLetter63List != null && notificationLetter63List.size() > 0){
						SimpleDateFormat sfP = new SimpleDateFormat("ddMMyyyy", new Locale("th", "TH"));
						boolean nlBoolean = true;
						for (NotificationLetter notificationLetter63 : notificationLetter63List) {
							NotificationLetter nl = notificationLetter63;
							Date nowDate = sfP.parse(sfP.format(new Date()));
							Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
							Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
//							Date payment30DueDate = CssConvertUtils.addDate(paymentDueDate,31);
							Date barcode1Ref2Date = sfP.parse(nl.getBarcode1Ref2());
							/*( a ) ตรวจสอบว่าใบแจ้งเตือนถึง due ที่สามารถชำระได้หรือไม่*/
							if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
								/*( b ) ตรวจสอบว่ามีการชำระเบี้ยแล้วหรือไม่ จากข้อมูลใบเสร็จ*/
								List<ReceiptPa> paList = dataWarehouseService.getReceiptPaList(nl.getPolicyNo(), sf.format(paymentDueDate), sf.format(barcode1Ref2Date));
								if(paList != null && paList.size() > 0){
									return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
								}else{
									/*( c ) ตรวจสอบว่ามีการชำระเบี้ยผ่านเว็บ CSS แล้วหรือไม่*/
									List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(nl.getPolicyNo(), nl.getLetterType(), paymentDueDate);
									if(paymentList != null && paymentList.size() > 0){
										return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
									}
								}
								nlBoolean = true;
								break;
							}else{
								nlBoolean = false;
							}
						}
						if(!nlBoolean){
							return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
						}
					}else{
						return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
					}
				}
			}else{
				/*1. ตรวจสอบข้อมูลกรมธรรม์ที่ถูกเลือกโดยเงื่อนไขเบื้องต้น
				( a ) ตรวจสอบข้อมูลวันที่ชำระครั้งถัดไป และ โหมดการชำระ*/
				if(policy.getNextPaidDate() == null || (policy.getPaymentMode() == null || policy.getPaymentMode() == 0)){
					return "ข้อมูลกรมธรรม์ไม่ครบถ้วน กรุณาติดต่อสาขาเพื่อทำการชำระเบี้ยต่อไป";
				}else{
					/*( b ) ตรวจสอบว่าวันที่ชำระเบี้ยถัดไปอยู่ใน due ที่สามารถชำระได้หรือไม่*/
					if(policy.getNextPaidDate() != null){
						Date nextPaidDate = CssConvertUtils.toDateFromStringDateThai(policy.getNextPaidDate());
						Date start30DueDate = CssConvertUtils.addDate(nextPaidDate, -31);
						Date end30DueDate = CssConvertUtils.addDate(nextPaidDate, 31);
						Date end60DueDate = CssConvertUtils.addDate(nextPaidDate, 61);
						Date nowDate = sf.parse(sf.format(new Date()));
						if(policy.getPolicyType().equals("O")){
							if(nowDate.compareTo(start30DueDate) == -1 || nowDate.compareTo(end30DueDate) == 1){
								return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
							}
						}else{
							if(nowDate.compareTo(start30DueDate) == -1 || nowDate.compareTo(end60DueDate) == 1){
								return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
							}
						}
					}
				}
				/*( c ) ตรวจสอบแบบประกันว่าบริษัท absorb ค่าธรรมเนียมหรือไม่*/
				Petition p = dataWarehouseService.checkAbsorbFee(policy.getPrdCode(), (policy.getPolicyType().equals("G") ? "I" : policy.getPolicyType()), "2", policy.getPremiumAmount());
				if(p != null && !p.isStatus()){
					return p.getMessage();
				}
				
				/*2. ตรวจสอบข้อมูลจดหมายแจ้งเตือน*/
				if(policy.getPolicyType().equals("O")){
					List<NotificationLetter> notificationLetterList = dataWarehouseService.getNotificationLetterList(policy.getPolicyNo(), lp51, policy.getPolicyOrg().toString(), sf.format(new Date()));
					if(notificationLetterList != null && notificationLetterList.size() > 0){
						SimpleDateFormat sfO = new SimpleDateFormat("ddMMyyyy", new Locale("th", "TH"));
						/*2.1 ถ้าพบข้อมูล ให้ตรวจสอบเพิ่มเติมว่ามีการชำระเบี้ยแล้วหรือไม่*/
						boolean nlBoolean = true;
						String nlStatus = StringUtils.EMPTY;
						for (NotificationLetter notificationLetter : notificationLetterList) {
							NotificationLetter nl = notificationLetter;
							Date nowDate = sfO.parse(sfO.format(new Date()));
							Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
							Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
//							Date paymentDate = nl.getCreatedDate().toGregorianCalendar().getTime();
							Date barcode1Ref2Date = sfO.parse(nl.getBarcode1Ref2());
							
							nlBoolean = true;
							nlStatus = StringUtils.EMPTY;
							
							if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
								/*( d )ตรวจสอบจากข้อมูลใบเสร็จ*/
								List<ReceiptOrd> receiptOrdList = dataWarehouseService.getReceiptOrdList(nl.getPolicyNo(), sf.format(paymentDueDate), sf.format(barcode1Ref2Date));
								if(receiptOrdList != null && receiptOrdList.size() > 0){
									nlStatus = "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
									nlBoolean = false;
								}else{
									/*( e ) ตรวจสอบว่ามีการชำระเบี้ยผ่านเว็บ CSS แล้วหรือไม่*/
									List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(nl.getPolicyNo(), nl.getLetterType(), paymentDueDate);
									if(paymentList != null && paymentList.size() > 0){
										nlStatus = "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
										nlBoolean = false;
									}
								}
								if(nlBoolean){
									break;
								}
							}else{
								nlBoolean = false;
								nlStatus = "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
							}
						}
						if(!nlBoolean){
							return nlStatus;
						}
					}else{
						return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
					}
				}else if(policy.getPolicyType().equals("I") || policy.getPolicyType().equals("G")){
					//ตรวจสอบกรมธรรม์ที่มี Rider เพิ่ม *** สำหรับกรมธรรม์อุตสาหกรรม ปช. เท่านั้น ***
					if(policy.getPolicyType().equals("I")){
						Petition p2 = dataWarehouseService.checkIndAdditionalRider(policy.getPolicyNo());
						if(p2 != null && !p2.isStatus()){
							return p2.getMessage();
						}
					}
					/*3. ตรวจสอบข้อมูลจดหมายแจ้งเตือน*/
					List<NotificationLetter> notificationLetterList = dataWarehouseService.getNotificationLetterList(policy.getPolicyNo(), lp52, policy.getPolicyOrg().toString(), sf.format(new Date()));
					if(notificationLetterList != null && notificationLetterList.size() > 0){
						SimpleDateFormat sfI = new SimpleDateFormat("ddMMyy", new Locale("th", "TH"));
						
						boolean nlBoolean = true;
						String nlStatus = StringUtils.EMPTY;
						NotificationLetter nl = null;
						for (NotificationLetter notificationLetter : notificationLetterList) {
							Date nowDate = sfI.parse(sfI.format(new Date()));
							Date paymentDueDate = notificationLetter.getPaymentDueDate().toGregorianCalendar().getTime();
							Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
							Date barcode1Ref2Date = sfI.parse(notificationLetter.getBarcode1Ref2());
							
							nlBoolean = true;
							nlStatus = StringUtils.EMPTY;
							
							if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
								/*( d ) ตรวจสอบจากข้อมูลใบเสร็จ*/
								List<ReceiptInd> receiptIndList = dataWarehouseService.getReceiptIndList(notificationLetter.getPolicyNo(), sf.format(paymentDueDate), sf.format(barcode1Ref2Date));
								if(receiptIndList != null && receiptIndList.size() > 0){
									//return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
									nlBoolean = false;
									nlStatus = "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
								}else{
									/*( e ) ตรวจสอบว่ามีการชำระเบี้ยผ่านเว็บ CSS แล้วหรือไม่*/
									List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(notificationLetter.getPolicyNo(), notificationLetter.getLetterType(), paymentDueDate);
									if(paymentList != null && paymentList.size() > 0){
										//return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
										nlBoolean = false;
										nlStatus = "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
									}
								}
								if(nlBoolean){
									nl = notificationLetter;
									break;
								}
							}
						}
						
						if(!nlBoolean){
							return nlStatus;
						}
						
						if(nl == null){
							/*( f ) ตรวจสอบจากข้อมูลใบเสร็จ*/
							Integer mode = null;
							ReceiptInd lastReceiptInd = dataWarehouseService.getLastIndReceipt(policy.getPolicyNo());
							if(lastReceiptInd != null){
								mode = lastReceiptInd.getRcMode();
								if(mode >= 1 && mode <= 5){
									/*( g ) ตรวจสอบงวดการชำระเบี้ยจากระบบ other channel*/
									CheckRegisterOtherChannelModel crocm = new CheckRegisterOtherChannelModel();
									crocm.setPolicyNo(policyNo);
									CheckRegisterOtherChannelModel rCrocm = hermesFactory.getOtherChannelPaymentService().getPolicyActiveByPolicyNo(crocm);
									if(rCrocm != null && rCrocm.getPaymentMode() != null){
										mode = Integer.parseInt(rCrocm.getPaymentMode());
										if(mode >= 1 && mode <= 5){
											/*( h ) ตรวจสอบจากข้อมูลใบเสร็จ*/
											Date nextPaidDate = CssConvertUtils.toDateFromStringDateThai(policy.getNextPaidDate());
											List<ReceiptInd> receiptIndList = dataWarehouseService.getReceiptIndList(policy.getPolicyNo(), sf.format(nextPaidDate), sf.format(new Date()));
											if(receiptIndList != null && receiptIndList.size() > 0){
												return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
											}else{
												/*( i ) ตรวจสอบว่ามีการชำระเบี้ยแล้วหรือไม่*/
												List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndNextPaidDate(policy.getPolicyNo(), nextPaidDate);
												if(paymentList != null && paymentList.size() > 0){
													return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
												}
											}
										}else if(mode >= 6 && mode <= 12){
											return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
										}
									}
								}else if(mode >= 6 && mode <= 12){
									return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
								}
							}else{
								return "กรมธรรม์นี้ไม่สามารถชำระเบี้ยประกันภัยผ่านบัตรเครดิตได้ เนื่องจากไม่พบข้อมูลใบเสร็จล่าสุด กรุณาติดต่อสาขาเพื่อทำการชำระเบี้ยต่อไป";
							}
						}
						
//						Date nowDate = sfI.parse(sfI.format(new Date()));
//						Date paymentDueDate = nl.getPaymentDueDate().toGregorianCalendar().getTime();
//						Date paymentPlus30DueDate = CssConvertUtils.addDate(paymentDueDate,-30);
//						Date barcode1Ref2Date = sfI.parse(nl.getBarcode1Ref2());
//						if(nowDate.compareTo(paymentPlus30DueDate) != -1 && nowDate.compareTo(barcode1Ref2Date) != 1){
//
//						}else{
//							/*( f ) ตรวจสอบจากข้อมูลใบเสร็จ*/
//							Integer mode = null;
//							ReceiptInd lastReceiptInd = dataWarehouseService.getLastIndReceipt(policy.getPolicyNo());
//							if(lastReceiptInd != null){
//								mode = lastReceiptInd.getRcMode();
//								if(mode >= 1 && mode <= 5){
//									/*( g ) ตรวจสอบงวดการชำระเบี้ยจากระบบ other channel*/
//									CheckRegisterOtherChannelModel crocm = new CheckRegisterOtherChannelModel();
//									crocm.setPolicyNo(policyNo);
//									CheckRegisterOtherChannelModel rCrocm = hermesFactory.getOtherChannelPaymentService().getPolicyActiveByPolicyNo(crocm);
//									if(rCrocm != null && rCrocm.getPaymentMode() != null){
//										mode = Integer.parseInt(rCrocm.getPaymentMode());
//										if(mode >= 1 && mode <= 5){
//											/*( h ) ตรวจสอบจากข้อมูลใบเสร็จ*/
//											Date nextPaidDate = CssConvertUtils.toDateFromStringDateThai(policy.getNextPaidDate());
//											List<ReceiptInd> receiptIndList = dataWarehouseService.getReceiptIndList(policy.getPolicyNo(), sf.format(nextPaidDate), sf.format(new Date()));
//											if(receiptIndList != null && receiptIndList.size() > 0){
//												return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
//											}else{
//												/*( i ) ตรวจสอบว่ามีการชำระเบี้ยแล้วหรือไม่*/
//												List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndNextPaidDate(policy.getPolicyNo(), nextPaidDate);
//												if(paymentList != null && paymentList.size() > 0){
//													return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
//												}
//											}
//										}else if(mode >= 6 && mode <= 12){
//											return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
//										}
//									}
//								}else if(mode >= 6 && mode <= 12){
//									return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
//								}
//							}else{
//								return "กรมธรรม์นี้ไม่สามารถชำระเบี้ยประกันภัยผ่านบัตรเครดิตได้ เนื่องจากไม่พบข้อมูลใบเสร็จล่าสุด กรุณาติดต่อสาขาเพื่อทำการชำระเบี้ยต่อไป";
//							} 
//						}
					}else{
						/*( f ) ตรวจสอบจากข้อมูลใบเสร็จ*/
						Integer mode = null;
						ReceiptInd lastReceiptInd = dataWarehouseService.getLastIndReceipt(policy.getPolicyNo());
						if(lastReceiptInd != null){
							mode = lastReceiptInd.getRcMode();
							if(mode >= 1 && mode <= 5){
								/*( g ) ตรวจสอบงวดการชำระเบี้ยจากระบบ other channel*/
								CheckRegisterOtherChannelModel crocm = new CheckRegisterOtherChannelModel();
								crocm.setPolicyNo(policyNo);
								CheckRegisterOtherChannelModel rCrocm = hermesFactory.getOtherChannelPaymentService().getPolicyActiveByPolicyNo(crocm);
								if(rCrocm != null && rCrocm.getPaymentMode() != null){
									mode = Integer.parseInt(rCrocm.getPaymentMode());
									if(mode >= 1 && mode <= 5){
										/*( h ) ตรวจสอบจากข้อมูลใบเสร็จ*/
										Date nextPaidDate = CssConvertUtils.toDateFromStringDateThai(policy.getNextPaidDate());
										List<ReceiptInd> receiptIndList = dataWarehouseService.getReceiptIndList(policy.getPolicyNo(), sf.format(nextPaidDate), sf.format(new Date()));
										if(receiptIndList != null && receiptIndList.size() > 0){
											return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว";
										}else{
											/*( i ) ตรวจสอบว่ามีการชำระเบี้ยแล้วหรือไม่*/
											List<CssPaymentEntity> paymentList = cssPaymentService.findCssPaymentByPolicyNoAndNextPaidDate(policy.getPolicyNo(), nextPaidDate);
											if(paymentList != null && paymentList.size() > 0){
												return "กรมธรรม์รายนี้ได้รับการชำระเบี้ยเรียบร้อยแล้ว ขณะนี้อยู่ในขั้นตอนจัดส่งใบเสร็จ";
											}
										}
									}else if(mode >= 6 && mode <= 12){
										return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
									}
								}
							}else if(mode >= 6 && mode <= 12){
								return "กรมธรรม์รายนี้ยังไม่ถึงรอบการชำระเบี้ยประกันภัย";
							}
						}else{
							return "กรมธรรม์นี้ไม่สามารถชำระเบี้ยประกันภัยผ่านบัตรเครดิตได้ เนื่องจากไม่พบข้อมูลใบเสร็จล่าสุด กรุณาติดต่อสาขาเพื่อทำการชำระเบี้ยต่อไป";
						}
					}
				}
			}
			//////////////////////////////////////////////////////////////////////////////////////////////////////
 			return StringUtils.EMPTY;
		}catch(Exception e){
			return "เกิดข้อผิดพลาด";
		}
		
	}
	
	public Integer getModeFromLastReceiptOrdByPolicyNo(String policyNo){
		try{
			/* ตรวจสอบงวดการชำระเบี้ยจากระบบ other channel*/
			CheckRegisterOtherChannelModel crocm = new CheckRegisterOtherChannelModel();
			crocm.setPolicyNo(policyNo);
			CheckRegisterOtherChannelModel rCrocm = hermesFactory.getOtherChannelPaymentService().getPolicyActiveByPolicyNo(crocm);
			if(rCrocm != null){
				Integer mode = Integer.parseInt(rCrocm.getPaymentMode());
				return mode;
			}else{
				/* ตรวจสอบจากข้อมูลใบเสร็จ*/
				ReceiptOrd lastReceiptOrd = dataWarehouseService.getLastOrdReceipt(policyNo);
				if(lastReceiptOrd != null){
					Integer mode = lastReceiptOrd.getRcTerm();
					return mode;
				}else{
					return null;
				}
			}
		}catch(Exception e){
			return null;
		}
	}
	
	public Integer getModeFromLastReceiptIndByPolicyNo(String policyNo){
		try{
			/* ตรวจสอบงวดการชำระเบี้ยจากระบบ other channel*/
			CheckRegisterOtherChannelModel crocm = new CheckRegisterOtherChannelModel();
			crocm.setPolicyNo(policyNo);
			CheckRegisterOtherChannelModel rCrocm = hermesFactory.getOtherChannelPaymentService().getPolicyActiveByPolicyNo(crocm);
			if(rCrocm != null && rCrocm.getPaymentMode() != null){
				Integer mode = Integer.parseInt(rCrocm.getPaymentMode());
				return mode;
			}else{
				/* ตรวจสอบจากข้อมูลใบเสร็จ*/
				ReceiptInd lastReceiptInd = dataWarehouseService.getLastIndReceipt(policyNo);
				if(lastReceiptInd != null){
					Integer mode = lastReceiptInd.getRcMode();
					return mode;
				}else{
					return null;
				}
			}
		}catch(Exception e){
			return null;
		}
	}
	
	public class Parameter {
		private String selectedPremiumAmount;
		private String selectedPaymentPeriod;
		public String getSelectedPremiumAmount() {
			return selectedPremiumAmount;
		}
		public void setSelectedPremiumAmount(String selectedPremiumAmount) {
			this.selectedPremiumAmount = selectedPremiumAmount;
		}
		public String getSelectedPaymentPeriod() {
			return selectedPaymentPeriod;
		}
		public void setSelectedPaymentPeriod(String selectedPaymentPeriod) {
			this.selectedPaymentPeriod = selectedPaymentPeriod;
		}
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getResultUrl1() {
		return resultUrl1;
	}

	public void setResultUrl1(String resultUrl1) {
		this.resultUrl1 = resultUrl1;
	}

	public String getResultUrl2() {
		return resultUrl2;
	}

	public void setResultUrl2(String resultUrl2) {
		this.resultUrl2 = resultUrl2;
	}

	public String getAction2c2p() {
		return action2c2p;
	}

	public void setAction2c2p(String action2c2p) {
		this.action2c2p = action2c2p;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
}
