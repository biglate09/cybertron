package thaisamut.cybertron.ejbweb.remote;

import thaisamut.cybertron.ejbweb.model.CssPaymentCardTempEntity;

public interface CssPaymentCardTempService {
	
	void insertCssPaymentCardTemp(CssPaymentCardTempEntity cp) throws Exception;

	CssPaymentCardTempEntity findCssPaymentCardTempByPolicyNo(String policyNo) throws Exception;
	
}
