package thaisamut.css.data.service;

import java.util.List;
import java.util.Map;

import thaisamut.css.model.CssUser;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.nbs.css.person.action.PersonalInfoAction.Parameter;

public interface CssPolicyDataService {


	void saveAddress(Parameter personInfoParam, CssUser user, String token) throws Exception;

	void resetAddressTempToken(String token, String newToken) throws Exception;

	Map<String, CssAddressTempEntity> queryConfirmationPolicies(String username)throws Exception;

	CssAddressTempEntity queryCssAddressTempEntityWaitingStatus(String policyNo, String username)throws Exception;

	List<CssAddressTempEntity> updateCssAddressTempStatusByToken(String sendStatus, String token, String updateBy) throws Exception;

	List<CssAddressTempEntity> prepareAddress(Parameter param, CssUser user, String token) throws Exception;

	List<CssAddressTempEntity> saveAddress(List<CssAddressTempEntity> addressUpdated, CssUser user) throws Exception;

}
