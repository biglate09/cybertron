package thaisamut.nbs.css.claim.action;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.model.CssUser;
import thaisamut.css.struts.action.CssJsonAction;

public class ClaimAction extends CssJsonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6884176158816435183L;
	private static final Logger LOG = LoggerFactory.getLogger(ClaimAction.class);
	private String policyNo;
	
	public String history() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On ClaimAction : history");
		}
		return super.index();
	}
	
	public String detail() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("~On ClaimAction : detail looking for policy no %s",policyNo));
		}
		if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null) {
			Map<String,PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
			
			PolicyBean policy = policies.get(policyNo);
			boolean isRider = false;
			if(policy!=null){
				requestAttributes.put("policy", policy);
				
				if(policy.getRiders() != null && policy.getRiders().size() > 0){
					isRider = true;
				}
				requestAttributes.put("isRider", isRider);
			}
		}
		return super.index();
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	
}
