package thaisamut.cybertron.ejbweb.remote;

import java.util.Date;
import java.util.List;

import thaisamut.cybertron.ejbweb.model.CssPaymentEntity;
import thaisamut.cybertron.ejbweb.model.IliReceiptBean;

public interface CssPaymentService {
	
	public void saveCssPayment(CssPaymentEntity entity) throws Exception;
	public CssPaymentEntity findCssPaymentByOrderNo(String orderNo) throws Exception;
	public void updateCssPayment(CssPaymentEntity entity) throws Exception;
	public List<CssPaymentEntity> findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(String policyNo, String letterType, Date paymentDueDate) throws Exception;
	
	public List<CssPaymentEntity> findCssPaymentByPolicyNoAndNextPaidDate(String policyNo, Date nextPaidDate) throws Exception;
}
