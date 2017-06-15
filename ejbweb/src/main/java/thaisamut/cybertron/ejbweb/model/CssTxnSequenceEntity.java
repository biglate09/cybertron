package thaisamut.cybertron.ejbweb.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CSS_TXN_SEQUENCE")
@SequenceGenerator(name = "CssTxnSequenceId", sequenceName = "seq_css_txn_sequence", allocationSize = 1)
public class CssTxnSequenceEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3076653402964306159L;
	
	@Id
	@Column(name = "TXN_SEQUENCE_ID")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssTxnSequenceId")
	private Long txnSequenceId;
	
	@Column(name = "MODE")
	private String mode;
	
	@Column(name = "KEY")
	private String key;
	
	@Column(name = "SEQ")
	private Integer seq;
	
	@Column(name = "TIMESTAMP")
	private Date timestamp;


	public Long getTxnSequenceId() {
		return txnSequenceId;
	}

	public void setTxnSequenceId(Long txnSequenceId) {
		this.txnSequenceId = txnSequenceId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
