package thaisamut.css.dwh.service.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.Years;

public class PersonBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2926302633637489113L;
	
    private String titleDesc;
    private String firstName;
    private String lastName;
    private String idNo;
    private String birthDate;
    private Integer ageAtCommDate;
    private String originDesc;
    private Integer age;
    private String nationalityDesc;
    private String religionDesc;
    private String maritalStatusDesc;
    private String educationDesc;
    private String occupationDesc;
    private Integer monthlyIncome;
    public String genderCode;
    private String custCode;
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
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public Integer getAgeAtCommDate() {
		return ageAtCommDate;
	}
	public void setAgeAtCommDate(Integer ageAtCommDate) {
		this.ageAtCommDate = ageAtCommDate;
	}
	public String getOriginDesc() {
		return originDesc;
	}
	public void setOriginDesc(String originDesc) {
		this.originDesc = originDesc;
	}
	public String getNationalityDesc() {
		return nationalityDesc;
	}
	public void setNationalityDesc(String nationalityDesc) {
		this.nationalityDesc = nationalityDesc;
	}
	public String getReligionDesc() {
		return religionDesc;
	}
	public void setReligionDesc(String religionDesc) {
		this.religionDesc = religionDesc;
	}
	public String getMaritalStatusDesc() {
		return maritalStatusDesc;
	}
	public void setMaritalStatusDesc(String maritalStatusDesc) {
		this.maritalStatusDesc = maritalStatusDesc;
	}
	public String getEducationDesc() {
		return educationDesc;
	}
	public void setEducationDesc(String educationDesc) {
		this.educationDesc = educationDesc;
	}
	public String getOccupationDesc() {
		return occupationDesc;
	}
	public void setOccupationDesc(String occupationDesc) {
		this.occupationDesc = occupationDesc;
	}
	public Integer getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(Integer monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getFullName(){
		return String.format("%s%s %s",StringUtils.defaultString(titleDesc),StringUtils.defaultString(firstName),StringUtils.defaultString(lastName));
	}
	public Integer getAge() {
		calAge();
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	private void calAge() {
		try{
		if(age==null &&StringUtils.isNotBlank(birthDate)){
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy",new Locale("th","TH"));
			Date bdate = sf.parse(birthDate);
			Years years = Years.yearsBetween(new LocalDate(bdate.getTime()), new LocalDate());
			age = years.getYears();
		}
		}catch(Exception ignored){}
	}
	public String getGenderCode() {
		return genderCode;
	}
	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
	public String getGenderDesc() {
		return ("M".equalsIgnoreCase(genderCode)) ? "ชาย" : ("F".equalsIgnoreCase(genderCode) ? "หญิง" : null);
	}
}
