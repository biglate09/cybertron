package thaisamut.css.ws.address.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import thaisamut.css.dwh.service.pojo.PolicyBean;

public class PolicyBeanMapTransporter implements Serializable {
	private static final long serialVersionUID = -488209551539298139L;

	private Map<String, PolicyBean> policies = new HashMap<>();

	public Map<String, PolicyBean> getPolicies() {
		return policies;
	}

	public void setPolicies(Map<String, PolicyBean> policies) {
		this.policies = policies;
	}

}
