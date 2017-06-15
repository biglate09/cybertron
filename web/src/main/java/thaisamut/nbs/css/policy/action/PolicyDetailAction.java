package thaisamut.nbs.css.policy.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.PersonBean;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.model.CssUser;
import thaisamut.css.struts.action.CssJsonAction;

public class PolicyDetailAction extends CssJsonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7810572012156774323L;
	private static final Logger LOG = LoggerFactory.getLogger(PolicyDetailAction.class);
	private String policyNo;
	private String ref;
	@Autowired
	DataWarehouseService dwhService;
	public String index() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("~On PolicyDetailAction : index looking for policy no %s",policyNo));
		}
		CssUser user = getSessionUser();
		if (sessionAttributes.get(SESSION_ATTRIBUTE_USER) == null) {
			PersonBean person = dwhService.getPersonData(user.getCardNo());
			sessionAttributes.put(SESSION_ATTRIBUTE_USER, person);
		}
//		if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) != null) {
//			Map<String,PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
			Map<String,PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(),user.getEmail());
			sessionAttributes.put(SESSION_ATTRIBUTE_POLICY_LIST, policies);
			PolicyBean policy = policies.get(policyNo);
			if(policy!=null){
				requestAttributes.put("policy", policy);
			}
			if("1".equals(ref)){
				requestAttributes.put("back", "secure/member/personal/info.html");
			}else{
				requestAttributes.put("back", "secure/member/policy/policyinfo.html");
			}
//		}
		return super.index();
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
}
