package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "mark_choices")

public class MarkChoicesEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8503026492278093539L;
	@Id
	@Column(name = "id_no")
	private String idNo;
	@Id
	@Column(name = "choices_id")
	private Integer choicesId;

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getChoicesId() {
		return choicesId;
	}

	public void setChoicesId(int choicesId) {
		this.choicesId = choicesId;
	}

	@Override
	public String toString() {
		return "MarkChoicesEntity{" +
				"idNo='" + idNo + '\'' +
				", choicesId=" + choicesId +
				'}';
	}
}