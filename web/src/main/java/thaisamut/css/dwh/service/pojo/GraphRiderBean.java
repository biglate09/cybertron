package thaisamut.css.dwh.service.pojo;

public class GraphRiderBean {
	private double all = 0d;
	private double medAll = 0d;
	private double medSep = 0d;
	private double disease = 0d;
	private double epa = 0d;
	private double pa = 0d;
	private double other = 0d;
	public double getAll() {
		return all;
	}
	public void setAll(double all) {
		this.all = all;
	}
	public double getMedAll() {
		return medAll;
	}
	public void setMedAll(double medAll) {
		this.medAll = medAll;
	}
	public double getMedSep() {
		return medSep;
	}
	public void setMedSep(double medSep) {
		this.medSep = medSep;
	}
	public double getDisease() {
		return disease;
	}
	public void setDisease(double disease) {
		this.disease = disease;
	}
	
	public double getEpa() {
		return epa;
	}
	public void setEpa(double epa) {
		this.epa = epa;
	}
	public double getPa() {
		return pa;
	}
	public void setPa(double pa) {
		this.pa = pa;
	}
	public double getOther() {
		return other;
	}
	public void setOther(double other) {
		this.other = other;
	}
	public void addAll(double all){
		this.all += all;
	}
	public void addtMedAll(double medAll) {
		this.medAll += medAll;
	}
	public void addMedSep(double medSep) {
		this.medSep += medSep;
	}
	public void addDisease(double disease) {
		this.disease += disease;
	}
	public double addEpa(double epa) {
		return this.epa +=epa;
	}
	public double addPa(double pa) {
		return this.pa +=pa;
	}
	public double addOther(double other) {
		return this.other +=other;
	}
}
