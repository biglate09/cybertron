package thaisamut.css.ws.address.model;

import java.io.Serializable;

import thaisamut.css.dwh.service.pojo.PolicyBean;

public class PolicyBeanTransporter implements Serializable {
	private static final long serialVersionUID = -5442170274167290735L;

	private String policyNo;
	private PolicyBean policy;

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public PolicyBean getPolicy() {
		return policy;
	}

	public void setPolicy(PolicyBean policy) {
		this.policy = policy;
	}

}
