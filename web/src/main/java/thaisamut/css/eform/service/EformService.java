package thaisamut.css.eform.service;

import java.util.List;

import thaisamut.eform.ws.PetitionChangeAddressBean;
import thaisamut.eform.ws.PetitionChangeBenefitPaymentBean;
import thaisamut.eform.ws.PetitionChangeCustomerNameBean;
import thaisamut.eform.ws.PetitionChangePaymentPeriodBean;
import thaisamut.eform.ws.RegisterPaymentCardBean;


public interface EformService {
	public String insertEformPetitionChangePaymentPeriod(PetitionChangePaymentPeriodBean bean) throws Exception;
	public String insertEformPetitionChangeCustomerName(PetitionChangeCustomerNameBean bean) throws Exception;
	public String insertEformPetitionChangeAddress(PetitionChangeAddressBean bean) throws Exception;
	public String insertEformPetitionChangeBenefitPayment(PetitionChangeBenefitPaymentBean bean) throws Exception;
	String insertEformRequestPaymentCard(RegisterPaymentCardBean bean) throws Exception;
	List<RegisterPaymentCardBean> inquiryEformRegisterPaymentCard(String policyNo, String status) throws Exception;
}
