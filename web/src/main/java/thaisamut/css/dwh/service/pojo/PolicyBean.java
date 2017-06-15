package thaisamut.css.dwh.service.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;

import thaisamut.util.CssConvertUtils;

public class PolicyBean implements Serializable, Comparable<PolicyBean> {

	private final String STATUS_NOT_ALLOW_TO_DOWNLOAD = "0";
	/**
	 * 
	 */
	private static final long serialVersionUID = 7160662223901089392L;
	private String policyNo;
	private Double sumAssured;
	private String subdistrictName;
	private String provinceName;
	private Double premiumAmount;
	private String prdName;
	private String postcode;
	private String policyType;
	private String policyStatus;
	private String phone1;
	private String phone2;
	private String personType;
	private Integer paymentMode;
	private String paymentChannel;
	private String nextPaidDate;
	private String agentCode;
	private String mobile2;
	private String mobile1;
	private String maturityDate;
	private String dtCode;
	private String pvCode;
	private String fullyPaidDate;
	private String ext1;
	private String ext2;
	private String email;
	private String districtName;
	private String commencementDate;
	private String lapseDate;
	private String addressType;
	private String addressLine1;
	private String custCode;
	private String birthdate;
	private String fullAddress = null;
	private boolean temp = false;;
	private List<RiderMapBean> riders;
	private String prdCode;
	private String titleDesc;
	private String firstName;
	private String lastName;
	private String branchNo;
	private Integer policyYear;
	private Integer policyOrg;
	private Double allPermium;
	private Integer paymentTerm;
	private String dwnLoanStatus;
	private LoanPolicyBean loanBean;
	
	private String benefitChannel;
	private String cssLoanStatus = StringUtils.EMPTY;

	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public Double getSumAssured() {
		return sumAssured;
	}
	public void setSumAssured(Double sumAssured) {
		this.sumAssured = sumAssured;
	}
	public String getSubdistrictName() {
		return subdistrictName;
	}
	public void setSubdistrictName(String subdistrictName) {
		this.subdistrictName = subdistrictName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Double getPremiumAmount() {
		return premiumAmount;
	}
	public void setPremiumAmount(Double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}
	public String getPrdName() {
		return prdName;
	}
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getPolicyStatus() {
		return policyStatus;
	}
	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public Integer getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(Integer paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	public String getNextPaidDate() {
		return nextPaidDate;
	}
	public void setNextPaidDate(String nextPaidDate) {
		this.nextPaidDate = nextPaidDate;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getFullyPaidDate() {
		return fullyPaidDate;
	}
	public void setFullyPaidDate(String fullyPaidDate) {
		this.fullyPaidDate = fullyPaidDate;
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
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getCommencementDate() {
		return commencementDate;
	}
	public void setCommencementDate(String commencementDate) {
		this.commencementDate = commencementDate;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
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

	public String getLapseDate() {
		return lapseDate;
	}
	public void setLapseDate(String lapseDate) {
		this.lapseDate = lapseDate;
	}
	public String getFullAddress() {
		// if(fullAddress == null){
		if (StringUtils.isNotBlank(addressLine1) && StringUtils.isNotBlank(provinceName) && StringUtils.isNotBlank(districtName) && StringUtils.isNotBlank(subdistrictName)) {
			boolean isbangkok = StringUtils.defaultString(provinceName).contains("กรุงเทพ") || StringUtils.defaultString(provinceName).contains("กทม");
			fullAddress = String.format("%s %s %s %s %s จังหวัด %s %s", StringUtils.defaultString(addressLine1), isbangkok ? "แขวง" : "ตำบล", StringUtils.defaultString(subdistrictName), isbangkok ? "เขต" : "อำเภอ",
					StringUtils.defaultString(districtName), StringUtils.defaultString(provinceName), StringUtils.defaultString(postcode));
		} else {
			fullAddress = "ไม่พบข้อมูล";
		}
		// }

		return fullAddress;
	}
	public String getPaymentChannelDesc() {
		if (paymentChannel == null) {
			return null;
		} else
			switch (paymentChannel) {
				case "1" :
					return "สาขา";
				case "2" :
					return "ชำระเองที่สำนักงานใหญ่";
				case "3" :
					return "ผ่านเคาน์เตอร์ธนาคาร";
				case "4" :
					return "หักบัญชีธนาคาร (Direc Debit)";
				case "5" :
					return "short";
				case "6" :
					return "ผ่านเคาน์เตอร์เซอวิส";
				case "7" :
					return "มรณกรรม";
				case "8" :
					return "โอนผ่านบัญชีรับฝาก";
				case "9" :
					return "ปิดบัญชีอัตโนมัติ และ เวนคืนอัตโนมัติ";
				case "A" :
					return "ต่างสาขา";
				case "B" :
					return "mPay";
				case "C" :
					return "ตัดบัตรเครดิต";
				case "D" :
					return "ชำระจากเงินทรงชีพ";
				case "E" :
					return "KTB BizPayment ";
				case "F" :
					return "ชำระผ่าน สนง.ตัวแทน ";
				case "G" :
					return "True Money";
				case "H" :
					return "Tesco Lotus";
				case "I" :
					return "Big C";
				default :
					return paymentChannel;
			}
	}
	public boolean isDownloadAbility() {

		return !"P".equals(policyType);
	}
	public String getPaymentModeDesc() {
		if (paymentMode != null && paymentMode > 0) {
			if (!"I".equalsIgnoreCase(policyType) && paymentMode == 12) {
				return "รายปี";
			} else if (!"I".equalsIgnoreCase(policyType) && paymentMode % 12 == 0) {
				return String.format("ราย %d ปี", paymentMode / 12);
			} else {
				return String.format("ราย %d เดือน", paymentMode);
			}
		} else {
			return null;
		}
	}
	public String getGroup() {
		if ("O".equals(policyType)) {
			return "I, O, W, F".indexOf(policyStatus) > -1 ? "effective" : "E".indexOf(policyStatus) > -1 ? "effective2" : "R".indexOf(policyStatus) > -1 ? "effective3" : "P".indexOf(policyStatus) > -1 ? "effective4" : "L, C, Z, S, A, T".indexOf(policyStatus) > -1 ? "expired" : "M".indexOf(policyStatus) > -1 ? "contract" : "other";
		} else if ("P".equals(policyType)) {
			return "I".indexOf(policyStatus) > -1 ? "effective" : "L, B, C".indexOf(policyStatus) > -1 ? "expired" : "M".indexOf(policyStatus) > -1 ? "contract" : "other";
		} else {
			return "0, 1, 2, 3, 4, 5, 8".indexOf(policyStatus) > -1 ? "effective" : "6".indexOf(policyStatus) > -1 ? "effective3" : "7, S, A".indexOf(policyStatus) > -1 ? "expired" : "M".indexOf(policyStatus) > -1 ? "contract" : "other";
		}
	}
	public String getGroupDescription() {
		if ("O".equals(policyType)) {
			return "I".equals(policyStatus) ? "มีผลบังคับ" : "F".equals(policyStatus) ? "มีผลบังคับ" : "O".equals(policyStatus) ? "มีผลบังคับ" : "W".equals(policyStatus) ? "มีผลบังคับ" : "E".equals(policyStatus) ? "มีผลบังคับ/กรมธรรม์แบบขยายเวลา" : "L"
					.equals(policyStatus) ? "ขาดผลบังคับ" : "M".equals(policyStatus) ? "ครบกำหนดสัญญา" : "R".equals(policyStatus) ? "มีผลบังคับ/กรมธรรม์แบบใช้เงินสำเร็จ": "P".equals(policyStatus) ? "มีผลบังคับ/กรมธรรม์แบบใช้เงินสำเร็จ(อัตโนมัติ)": 
					"C".equals(policyStatus) ? "ขาดผลบังคับ" : "Z".equals(policyStatus) ? "ขาดผลบังคับ" : "S".equals(policyStatus) ? "ขาดผลบังคับ" : "A".equals(policyStatus) ? "ขาดผลบังคับ" : "T".equals(policyStatus) ? "ขาดผลบังคับ" : "ไม่ได้จัดหมวดหมู่";
		} else if ("P".equals(policyType)) {
			return "I".equals(policyStatus) ? "มีผลบังคับ" : "L".equals(policyStatus) ? "ขาดผลบังคับ" : "M".equals(policyStatus) ? "ครบกำหนดสัญญา" : "B".equals(policyStatus) ? "ขาดผลบังคับ" : "C".equals(policyStatus) ? "ขาดผลบังคับ" : "ไม่ได้จัดหมวดหมู่";
		} else {
			return "0".equals(policyStatus) ? " มีผลบังคับ" : "1".equals(policyStatus) ? "มีผลบังคับ" : "2".equals(policyStatus) ? "มีผลบังคับ" : "3".equals(policyStatus) ? "มีผลบังคับ" : "4"
					.equals(policyStatus) ? "มีผลบังคับ" : "5".equals(policyStatus) ? "มีผลบังคับ" : "7".equals(policyStatus) ? "ขาดผลบังคับ" : "8".equals(policyStatus) ? "มีผลบังคับ" : "6".equals(policyStatus) ? "มีผลบังคับ/กรมธรรม์แบบใช้เงินสำเร็จ" : 
					"M".equals(policyStatus) ? "ครบกำหนดสัญญา" : "S".equals(policyStatus) ? "ขาดผลบังคับ" : "A".equals(policyStatus) ? "ขาดผลบังคับ" :	"ไม่ได้จัดหมวดหมู่"	;
		}
	}

	public String getCanDownload() {
		if ("O".equals(policyType)) {
			return "I, O, W".indexOf(policyStatus) > -1 ? "effective" : "E".indexOf(policyStatus) > -1 ? "effective2" : "R".indexOf(policyStatus) > -1 ? "effective3" : "P".indexOf(policyStatus) > -1 ? "effective4" : "L, C, Z, S, A, T".indexOf(policyStatus) > -1 ? "expired" : "M".indexOf(policyStatus) > -1 ? "contract" : "other";
		} else if ("P".equals(policyType)) {
			return "I".indexOf(policyStatus) > -1 ? "effective" : "L, B, C".indexOf(policyStatus) > -1 ? "expired" : "M".indexOf(policyStatus) > -1 ? "contract" : "other";
		} else {
			return "0, 1, 2, 3, 4, 5".indexOf(policyStatus) > -1 ? "effective" : "6".indexOf(policyStatus) > -1 ? "effective3" : "7, S, A".indexOf(policyStatus) > -1 ? "expired" : "M".indexOf(policyStatus) > -1 ? "contract" : "other";
		}
	}

	public String getCanPayment() {
		if ("O".equals(policyType)) {
			return "I, O".indexOf(policyStatus) > -1 ? "effective" :  "other";
		} else if ("P".equals(policyType)) {
			return "I".indexOf(policyStatus) > -1 ? "effective" :  "other";
		} else {
			return "0, 1, 2, 3, 4, 5".indexOf(policyStatus) > -1 ? "effective" : "other";
		}
	}
	public boolean isExpired() {
		if ("O".equals(policyType)) {
			return "F".equals(policyStatus);
		} else if ("P".equals(policyType)) {
			return "L".equals(policyStatus);
		} else {
			return false;
		}
	}
	
	public String getNotifyData() {
		Date compareDate,upperDate,lowerDate;
		String returnDate;
		int lowRange = 30,upRange = 30;
		try {
			if ("P".equals(policyType)) {
				compareDate = CssConvertUtils.toDateFromStringDateThai(this.lapseDate);
				returnDate = this.lapseDate;
			} else {
				compareDate = CssConvertUtils.toDateFromStringDateThai(this.nextPaidDate);
				returnDate =this.nextPaidDate;
			}
			lowerDate = CssConvertUtils.addDate(compareDate,-lowRange);
			upperDate = CssConvertUtils.addDate(compareDate,upRange);
			
			if(CssConvertUtils.isBetween(new Date(),lowerDate,upperDate)){
				return returnDate;
			}
			
		} catch (Exception ex) {

		}
		return STATUS_NOT_ALLOW_TO_DOWNLOAD;
	}
	public String getCertData() {
		Date compareDate = new Date();
		StringBuilder returnDate= new StringBuilder(12);
		try {
//			if ("P".equals(policyType)) {
//				compareDate = CssConvertUtils.toDateFromStringDateThai(this.lapseDate);
//			} else {
//				compareDate = CssConvertUtils.toDateFromStringDateThai(this.nextPaidDate);
//			}
			int year = CssConvertUtils.getThaiYear(compareDate);
			
			year--;//ย้อนหลัง 1 ปี
			Calendar cal = Calendar.getInstance(new Locale("TH","th"));
			cal.setTime(compareDate);
			cal.set(Calendar.DAY_OF_MONTH, 15);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			if(!CssConvertUtils.isAfter(cal.getTime(),compareDate)){
				// ก่อน 15 มค ย้อนหลัง 2 ปี
				year--;
			}
			
			for(int i = 0 ;i<2;i++){
				if(year-i<2559){
					returnDate.append(year-i).append("-|");
				}else{
					returnDate.append(year-i).append("|");
				}
			}
			return returnDate.toString();
			
		} catch (Exception ex) {

		}
		return STATUS_NOT_ALLOW_TO_DOWNLOAD;
	}

	public String getDtCode() {
		return dtCode;
	}
	public void setDtCode(String dtCode) {
		this.dtCode = dtCode;
	}
	public String getPvCode() {
		return pvCode;
	}
	public void setPvCode(String pvCode) {
		this.pvCode = pvCode;
	}
	public List<RiderMapBean> getRiders() {
		return riders;
	}
	public void setRiders(List<RiderMapBean> riders) {
		this.riders = riders;
	}
	public String getPrdCode() {
		return prdCode;
	}
	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}
	public String getTitleDesc() {
		return titleDesc;
	}
	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public Integer getPolicyYear() {
		return policyYear;
	}
	public void setPolicyYear(Integer policyYear) {
		this.policyYear = policyYear;
	}
	public boolean isTemp() {
		return temp;
	}
	public void setTemp(boolean temp) {
		this.temp = temp;
	}
	enum PAYCHANNEL {

	}
	@Override
	public int compareTo(PolicyBean o) {
		Date dThis, dThat;
		try {
			dThis = CssConvertUtils.toDateFromStringDateThai(this.commencementDate);
		} catch (Exception ex) {
			return 1;
		}
		try {
			dThat = CssConvertUtils.toDateFromStringDateThai(o.getCommencementDate());
		} catch (Exception ex) {
			return -1;
		}
		try {
			return dThat.compareTo(dThis);
		} catch (Exception ex) {
			return 1;
		}
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public Integer getPolicyOrg() {
		return policyOrg;
	}
	public void setPolicyOrg(Integer policyOrg) {
		this.policyOrg = policyOrg;
	}
	public String getBenefitChannel() {
		return benefitChannel;
	}
	public void setBenefitChannel(String benefitChannel) {
		this.benefitChannel = benefitChannel;
	}
	public Double getAllPermium() {
		if(allPermium == null){
			if("P".equals(this.policyType)){
				allPermium = this.premiumAmount;
			}else{
				BigDecimal tmp = BigDecimal.valueOf(this.premiumAmount);
				if(this.riders!=null&&!this.riders.isEmpty()){
					for(RiderMapBean r : this.riders){
						tmp = tmp.add(r.getPremiumAmout()==null?BigDecimal.ZERO:BigDecimal.valueOf(r.getPremiumAmout()));
					}
				}
				
				allPermium = tmp.doubleValue();
			}
		}
		return allPermium;
	}
	public void setAllPermium(Double allPermium) {
		this.allPermium = allPermium;
	}
	public Integer getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(Integer paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	public String getCssLoanStatus() {
		return cssLoanStatus;
	}
	public void setCssLoanStatus(String cssLoanStatus) {
		this.cssLoanStatus = cssLoanStatus;
	}
	public LoanPolicyBean getLoanBean() {
		return loanBean;
	}
	public void setLoanBean(LoanPolicyBean loanBean) {
		this.loanBean = loanBean;
	}
	public String getDwnLoanStatus() {
		return dwnLoanStatus;
	}
	public void setDwnLoanStatus(String dwnLoanStatus) {
		this.dwnLoanStatus = dwnLoanStatus;
	}
	
}
