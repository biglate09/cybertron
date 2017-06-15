package thaisamut.nbs.css.policy.action;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.GraphRiderBean;
import thaisamut.css.dwh.service.pojo.PersonBean;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.dwh.service.pojo.RiderMapBean;
import thaisamut.css.model.CssUser;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.cybertron.ejbweb.remote.EasyLoanInfoService;

public class PolicyInfoAction extends CssJsonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3176111061189754723L;
	private static final Logger LOG = LoggerFactory.getLogger(PolicyInfoAction.class);
	@Autowired
	DataWarehouseService dwhService;
	@Autowired
	CssMemberService cssMemberService;
	
	public String index() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On PolicyInfoAction : index");
		}
		CssUser user = getSessionUser();
		if (sessionAttributes.get(SESSION_ATTRIBUTE_USER) == null) {
			PersonBean person = dwhService.getPersonData(user.getCardNo());
			sessionAttributes.put(SESSION_ATTRIBUTE_USER, person);
		}
//		if (sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST) == null) {
			Map<String,PolicyBean> policies = dwhService.getPolicyData(user.getCardNo(),user.getEmail());
			
			sessionAttributes.put(SESSION_ATTRIBUTE_POLICY_LIST, policies);
//		}
		//find riders 
//		Map<String,PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
		
		if(policies!=null){
			PolicyBean policy;
			GraphRiderBean graph = new GraphRiderBean();
			for(Entry<String,PolicyBean> entry : policies.entrySet()){
				policy = entry.getValue();
				LOG.info("KEY : {}",entry.getKey());
				graph.addAll( policy.getSumAssured()!=null? policy.getSumAssured().doubleValue():0d);
				//rider
				if(policy.getRiders()!=null&&!policy.getRiders().isEmpty()){
					for(RiderMapBean rider : policy.getRiders()){
						if("H3".equals(rider.getMappingGroup()))graph.addtMedAll(rider.getSumAssured());
						else if("H1".equals(rider.getMappingGroup()))graph.addMedSep(rider.getSumAssured());
						else if("H2".equals(rider.getMappingGroup()))graph.addDisease(rider.getSumAssured());
						else if("A1".equals(rider.getMappingGroup()))graph.addEpa(rider.getSumAssured());
						else if("A2".equals(rider.getMappingGroup()))graph.addPa(rider.getSumAssured());
						else graph.addOther(rider.getSumAssured());
					}
				}
			}
			sessionAttributes.put(SESSION_ATTRIBUTE_GRAPH, graph);
		}
		return super.index();
	}
}