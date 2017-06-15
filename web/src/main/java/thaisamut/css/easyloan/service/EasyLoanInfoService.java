package thaisamut.css.easyloan.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.LoanPolicyBean;
import thaisamut.css.dwh.service.pojo.PersonBean;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.model.CssUser;
import thaisamut.eform.ws.EformEasyLoanAttachTransferBean;
import thaisamut.eform.ws.EformEasyLoanTransferBean;
import thaisamut.eform.ws.FileSeq;
import thaisamut.eformwsclient.component.EformWsClientFactory;
import thaisamut.cybertron.ejbweb.bean.CssLoanBean;
import thaisamut.cybertron.ejbweb.model.BranchesEntity;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.model.CssCommonDataEntity;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.remote.BranchesService;
import thaisamut.cybertron.ejbweb.remote.CssMasterService;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.cybertron.ejbweb.utils.CssLoanStatusEnum;
import thaisamut.omailwsclient.component.OMailWsClientFactory;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.ClaimChannel;
import thaisamut.osgi.targetbundles.omailws.sending.v2.Email;

@Component("easyLoanInfoService")
public class EasyLoanInfoService {
	private static final Logger LOG = LoggerFactory.getLogger(EasyLoanInfoService.class);
	
	@Autowired
	@Qualifier("localEasyLoanFilePath")
	private String FILE_PATH;
	@Autowired
	private OMailWsClientFactory email;
	@Autowired
	private DataWarehouseService dwhService;
//	@Autowired
//	private OTPHandler otpHandler;
	@Autowired
	private thaisamut.cybertron.ejbweb.remote.EasyLoanInfoService easyLoanInfoService;
//	@Autowired
//	private CssSMSConnectionService cssSMSConnectionService;
	
	@Autowired
	private EformWsClientFactory eformWsClientFactory;
	
	@Autowired private BranchesService branchesService;
	
	@Autowired
	private CssMasterService cssMasterService;
	@Autowired
	private CssMemberService cssMemberService;
	
	public ClaimChannel getClaimChannelByPolicyNoAndPolicyType(String policyNo, String policyType) throws Exception {
		List<ClaimChannel> claims = dwhService.getClaimChannelByPolicyNoAndPolicyType(policyNo, policyType);
		LOG.info("ClaimChannel Size : {} ",claims.size());
		if(!CollectionUtils.isEmpty(claims)){
			return claims.get(claims.size()-1);
		}
		return null;
	}
	
	public boolean save(List<Map<String,Object>> files,Map<String,String> params,PolicyBean policy,PersonBean person,CssUser user) throws Exception {
		LOG.info("~On EasyLoanInfoService.save ~");
		try {
//			String bankName    = params.get("bankName");//Code
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", new Locale("th","TH"));
			String bankName    = params.get("bankName");//Name
			String accountName = params.get("accountName");
			String accountNo   = params.get("accountNo");
			String bankBranch  = params.get("bankBranch");
//			String policyNo  = params.get("policyNo");
			String loanAmount  = params.get("loanAmount");
			
			CssLoanBean bean = new CssLoanBean();
			bean.setCreateDate(new Date());
			bean.setCustomerCardNo(user.getCardNo());
			bean.setCustomerName(user.getFirstName());
			bean.setCustomerSname(user.getLastName());
			bean.setCustomerTitle(user.getTitleDesc());
			bean.setPlanCode(policy.getPrdCode());
			bean.setPlanName(policy.getPrdName());
			bean.setPolicyNo(policy.getPolicyNo());
			bean.setPolicyType(policy.getPolicyType());
			bean.setRequestDate(new Date());
			bean.setAccount(accountName);
			bean.setAccountNo(accountNo);
			bean.setAddress(policy.getAddressLine1());
			bean.setBankName(bankName);
			bean.setBirthDate(sf.parse(person.getBirthDate()));
			bean.setBranch(bankBranch);
			if(StringUtils.isNotBlank(policy.getBranchNo())){
				bean.setBranchCode(policy.getBranchNo());
				BranchesEntity branch = branchesService.queryBranchNameByBranchCode(policy.getBranchNo());
				if(branch!=null){
					bean.setBranchName(branch.getBrnName());
				}
			}
			bean.setDistrict(policy.getDistrictName());
			CssAddressTempEntity addressTemp = cssMasterService.queryCssAddressTempByPolicyNo(policy.getPolicyNo());
			String email = null;
			if(null != addressTemp){
				email = addressTemp.getEmail();
			}else{
				if(StringUtils.isNotBlank(policy.getEmail())){
					email = policy.getEmail();
				}else{
					CssMemberEntity cssMember = cssMemberService.findMemberByCardId(user.getCardNo());
					if(null != cssMember){
						email = cssMember.getEmail();
					}
				}
			}
			bean.setEmail(email);
			LoanPolicyBean loan = policy.getLoanBean();
			if(loan != null){
				bean.setDuty(loan.getLoanDuty() == null?0:loan.getLoanDuty().doubleValue());
				Double loanRate = loan.getLoanRate();
				bean.setLoanRate(loanRate);
				loanAmount = loanAmount.replaceAll("([.]\\d{2})$","").replaceAll("(\\D)","");
				Double amount = Double.valueOf(loanAmount);
				Double loanDuty = loan.getLoanDuty() == null?0.0:loan.getLoanDuty().doubleValue();
				bean.setLoanAmount(amount);
				bean.setNetBalance(amount-loanDuty);
				bean.setInterestAmount(amount * (loanRate/100));
			}
			bean.setMobileNo(policy.getMobile1());
			bean.setPostcode(policy.getPostcode());
			bean.setProvince(policy.getProvinceName());
			bean.setRequestBy(String.format("%s%s %s", policy.getTitleDesc(),policy.getFirstName(),policy.getLastName()));
			bean.setStatus(CssLoanStatusEnum.N.name());
			bean.setSubDistrict(policy.getSubdistrictName());
			
			File path = new File(FILE_PATH);
			if(!path.exists()){
				path.mkdirs();
			}
			int fileIndex = 1;
			for(Map<String,Object> file : files){
				String filePath = StringUtils.EMPTY;
				if(null != file.get("file")){
					File srcFile = (File)file.get("file");
					String filename = file.get("filename") != null?String.format("%s_%s", genKeyDupicateFile(),file.get("filename").toString()):StringUtils.EMPTY;
					File destFile = new File(FILE_PATH, filename);
					filePath = destFile.getPath();
					FileUtils.copyFile(srcFile, destFile);
				}
				switch (fileIndex) {
				case 1:
					bean.setFileNameIdCard(filePath);
					break;
				case 2:
					bean.setFileNameOther1(filePath);
					break;
				case 3:
					bean.setFileNameOther2(filePath);
					break;
				case 4:
					bean.setFileNameBookBank(filePath);
					break;
				default:
					break;
				}
				fileIndex++;
			}
			//บันทึก css_loan
			easyLoanInfoService.save(bean);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("บันทึกข้อมูลที่ css_loan ผิดพลาด");
		}
		return true;
	}
	
	
	public void sendEmail(String policyNo,String policyType,String name, String loanAmount) {
		try {
			LOG.info("~On EasyLoanInfoService.sendEmail ~");
            Email m = new Email();
            m.setFrom("oceanlife@ocean.co.th");
//            String[] to = {"sarintip.kh@ocean.co.th","malakoza17@gmail.com"};
            m.getTo().addAll(setToMail());
            m.getCc().addAll(setCCMail());
//            m.getTo().add("sirisak.sn@ocean.co.th");
            m.setSubject("[CSS]: แจ้งตรวจสอบข้อมูล Easy Loan ของกรมธรรม์ "+policyNo+" ในบริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต");
            m.setIsHtmlMessage(true);
            StringBuilder sb = new StringBuilder();
            String desHead =
                            "เรียน ทุกท่าน<br><br> "
                            +"แจ้งตรวจสอบข้อมูล Easy Loan ของกรมธรรม์ "+policyNo +"<br>"
                            +"ในบริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต ดังนี้  <br><br>";
            sb.append(desHead);
            sb.append("<table style=\"font-family: arial, sans-serif;border-collapse: collapse;\">");
            sb.append("<tr style=\"background-color:rgb(21, 168, 188); text-align:center;\">");
            sb.append("<th style=\"border: 1px solid #dddddd;padding: 6px;\">ลำดับ</th>");
            sb.append("<th style=\"border: 1px solid #dddddd;padding: 6px;\">เลขที่กรมธรรม์</th>");
            sb.append("<th style=\"border: 1px solid #dddddd;padding: 6px;\">ประเภทกรมธรรม์</th>");
            sb.append("<th style=\"border: 1px solid #dddddd;padding: 6px;\">ชื่อ-นามสกุล ผู้เอาประกัน</th>");
            sb.append("<th style=\"border: 1px solid #dddddd;padding: 6px;\">จำนวนเงินที่ขอกู้</th>");
            sb.append("</tr>");

            int index = 1;
            sb.append("<tr>");
            sb.append("<td style=\"border: 1px solid #dddddd;padding: 6px;text-align:center;\">"+index+"</td>");
            sb.append("<td style=\"border: 1px solid #dddddd;padding: 6px;\">"+policyNo+"</td>");
            sb.append("<td style=\"border: 1px solid #dddddd;padding: 6px;\">"+policyType+"</td>");
            sb.append("<td style=\"border: 1px solid #dddddd;padding: 6px;\">"+name+"</td>");
            sb.append("<td style=\"border: 1px solid #dddddd;padding: 6px;\">"+loanAmount+"</td>");
            sb.append("</tr>");
            sb.append("</table>");

            sb.append("<br><br>");
            sb.append("--------------------------------------------------------<br>");
            String desSignature =
                    "<span style=\"font-weight:bold;\">ขอแสดงความนับถือ,</span><br>"
                    +"บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน)<br>"
                    +"เว็บไซต์ www.ocean.co.th<br>"
                    +"เฟสบุ๊ค www.facebook.com/oceanlifepage";

            sb.append(desSignature);
            m.setBody(sb.toString());
            email.getOmailWsSendingV2ESBService().sendEmail(m);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateCssLoanStatus(String idNo,String policyNo,String REQ_NO) throws Exception {
		CssLoanBean loan = easyLoanInfoService.updateCssLoanStatus(idNo, policyNo,REQ_NO);
		if(null != loan){
			DecimalFormat df = new DecimalFormat("#,###,###.00");
			String name = String.format("%s%s %s", loan.getCustomerTitle(),loan.getCustomerName(),loan.getCustomerSname());
			String loanAmount = df.format(loan.getLoanAmount());
			String policyType = loan.getPolicyType();
			String policyTypeTxt = StringUtils.EMPTY;
			if(StringUtils.isNotBlank(policyType)){
				if(StringUtils.equals(policyType, "I") || StringUtils.equals(policyType, "G")){
					policyTypeTxt = "อุตสาหกรรม";
				}else if(StringUtils.equals(policyType, "O")){
					policyTypeTxt = "สามัญ";
				}else if(StringUtils.equals(policyType, "P")){
					policyTypeTxt = "อุบัติเหตุส่วนบุคคล";
				}
			}
			sendEmail(policyNo, policyTypeTxt, name , loanAmount);
			removeFile(loan.getFileNameIdCard());
			removeFile(loan.getFileNameOther1());
			removeFile(loan.getFileNameOther2());
			removeFile(loan.getFileNameBookBank());
		}
	}
	
	private void removeFile(String filePath) throws Exception {
		if(StringUtils.isNotBlank(filePath)){
			File file = new File(filePath);
			if(file.isFile()){
				file.delete();
			}
		}
	}
	
	public String sendDataToEform(String policyNo,String idNo,PolicyBean policy,PersonBean person,CssUser user) throws Exception{
		CssLoanBean cssLoan = easyLoanInfoService.findCssLoan(idNo, policyNo);
		String REQ_NO = StringUtils.EMPTY;
		if(cssLoan != null){
			SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy", new Locale("th","TH"));
			EformEasyLoanTransferBean beanTransfer = new EformEasyLoanTransferBean();
			beanTransfer.setAccount(cssLoan.getAccount());
			beanTransfer.setAccountNo(cssLoan.getAccountNo());
			beanTransfer.setAddress(cssLoan.getAddress());
			beanTransfer.setBankName(cssLoan.getBankName());
			beanTransfer.setBirthDateText(sf.format(cssLoan.getBirthDate()));
			beanTransfer.setBranch(cssLoan.getBranch());
			beanTransfer.setBranchCode(cssLoan.getBranchCode());
			beanTransfer.setBranchName(cssLoan.getBranchName());
			beanTransfer.setCardNo(cssLoan.getCustomerCardNo());
			beanTransfer.setCreateBy(String.format("%s%s %s", cssLoan.getCustomerTitle(),cssLoan.getCustomerName(),cssLoan.getCustomerSname()));
			beanTransfer.setChannel("CSS_WEB");
			beanTransfer.setCreateSystem("Customer Self Service System");
			beanTransfer.setDistrict(cssLoan.getDistrict());
			beanTransfer.setDuty(cssLoan.getDuty());
			beanTransfer.setEmail(cssLoan.getEmail());
			beanTransfer.setInterestAmount(cssLoan.getInterestAmount());
			beanTransfer.setLoanAmount(cssLoan.getLoanAmount());
			beanTransfer.setLoanRate(cssLoan.getLoanRate());
			beanTransfer.setMobileNo(cssLoan.getMobileNo());
			beanTransfer.setNetBalance(cssLoan.getNetBalance());
			beanTransfer.setName(cssLoan.getCustomerName());
			beanTransfer.setPlanCode(cssLoan.getPlanCode());
			beanTransfer.setPlanName(cssLoan.getPlanName());
			beanTransfer.setPolicyNo(cssLoan.getPolicyNo());
			beanTransfer.setPolicyType(cssLoan.getPolicyType());
			beanTransfer.setProvince(cssLoan.getProvince());
			beanTransfer.setRequestBy(String.format("%s%s %s", cssLoan.getCustomerTitle(),cssLoan.getCustomerName(),cssLoan.getCustomerSname()));
			beanTransfer.setRequestDateText(sf.format(new Date()));
			beanTransfer.setSname(cssLoan.getCustomerSname());
			beanTransfer.setStatus(CssLoanStatusEnum.N.name());
			beanTransfer.setSubDistrict(cssLoan.getSubDistrict());
			beanTransfer.setPostcode(cssLoan.getPostcode());
			beanTransfer.setTitle(cssLoan.getCustomerTitle());
			beanTransfer.setUpdateBy("Customer Self Service System");
			beanTransfer.setUpdateSystem("Customer Self Service System");
			List<EformEasyLoanAttachTransferBean> files = new ArrayList<EformEasyLoanAttachTransferBean>();
			EformEasyLoanAttachTransferBean attIDCARD = setFileSend(FileSeq.IDCARD, cssLoan.getFileNameIdCard());
			EformEasyLoanAttachTransferBean attOTHER_1 = setFileSend(FileSeq.OTHER_1, cssLoan.getFileNameOther1());
			EformEasyLoanAttachTransferBean attOTHER_2 = setFileSend(FileSeq.OTHER_2, cssLoan.getFileNameOther2());
			EformEasyLoanAttachTransferBean attBOOKBANK = setFileSend(FileSeq.BOOKBANK, cssLoan.getFileNameBookBank());
			if(null != attIDCARD){
				files.add(attIDCARD);
			}
			if(null != attOTHER_1){
				files.add(attOTHER_1);
			}
			if(null != attOTHER_2){
				files.add(attOTHER_2);
			}
			if(null != attBOOKBANK){
				files.add(attBOOKBANK);
			}
			beanTransfer.getFiles().addAll(files);
			REQ_NO = eformWsClientFactory.getEformEasyLoanWSService().postEfromEasyLoanSave(beanTransfer);
		}
		return REQ_NO;
	}
	
	private EformEasyLoanAttachTransferBean setFileSend(FileSeq fileSeq,String filePath) throws Exception {
		if(StringUtils.isNotBlank(filePath)){
			File fileInput = new File(filePath);
			if(fileInput.exists()){
				try(InputStream is = new FileInputStream(fileInput);){
					byte[] file = IOUtils.toByteArray(is);
					EformEasyLoanAttachTransferBean fileAtt = new EformEasyLoanAttachTransferBean();
					String fileType = Files.probeContentType(fileInput.toPath());
					fileAtt.setFile(file);
					String fileName = StringUtils.defaultString(fileInput.getName());
					Pattern regex =  Pattern.compile("^(.+_)(.+)$");
					LOG.info("~file name before send : {}",fileInput.getName());
					LOG.info("~file name used send : {}",fileName);
					Matcher m = regex.matcher(fileName);
					if(m.matches()){
						fileAtt.setFileName(m.group(2));
					}
					fileAtt.setFileSeq(fileSeq);
					fileAtt.setFileType(fileType);
					return fileAtt;
				}
			}
		}
		return null;
	}
	
	private List<String> setCCMail() throws Exception{
		List<String> rs = new ArrayList<String>();
		 List<CssCommonDataEntity> ccMail = cssMasterService.queryCssCommonDataCCEmailBy("LOAN_MAIL", "LOAN_MAIL_CC");
		 if(!CollectionUtils.isEmpty(ccMail)){
			 for(CssCommonDataEntity mail : ccMail){
				 String mail_list = mail.getKeyValueText();
				 if(StringUtils.isNotBlank(mail_list)){
					 String[] mailArr = mail_list.split(",");
					 rs.addAll(Arrays.asList(mailArr));
				 }
			 }
		 }
		 return rs;
	}
	
	private List<String> setToMail() throws Exception{
		List<String> rs = new ArrayList<String>();
         CssCommonDataEntity toMail = cssMasterService.queryCssCommonDataEmail("LOAN_MAIL", "LOAN_MAIL");
         if(null != toMail){
        	 String mail_list = toMail.getKeyValueText();
        	 if(StringUtils.isNotBlank(mail_list)){
        		 String[] mailArr = mail_list.split(",");
        		 rs.addAll(Arrays.asList(mailArr));
        	 }
         }
		return rs;
	}
	
	private String genKeyDupicateFile(){
		final char[] fileSeq = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
		final int keyLength = 10;
		StringBuilder sb = new StringBuilder(keyLength);
		Random random = new Random();
		for (int i = 0; i < keyLength; i++) {
		    char c = fileSeq[random.nextInt(fileSeq.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
}
