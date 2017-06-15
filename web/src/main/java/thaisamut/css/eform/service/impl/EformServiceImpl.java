package thaisamut.css.eform.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import thaisamut.css.eform.service.EformService;
import thaisamut.eform.ws.PetitionChangeAddressBean;
import thaisamut.eform.ws.PetitionChangeBenefitPaymentBean;
import thaisamut.eform.ws.PetitionChangeCustomerNameBean;
import thaisamut.eform.ws.PetitionChangePaymentPeriodBean;
import thaisamut.eform.ws.RegisterPaymentCardBean;
import thaisamut.eformwsclient.component.EformWsClientFactory;


@Transactional
public class EformServiceImpl implements EformService {
	private static final Logger LOG = LoggerFactory.getLogger(EformServiceImpl.class);
	
	@Autowired EformWsClientFactory eformWsClientFactory;

	@Override
	public String insertEformPetitionChangePaymentPeriod(PetitionChangePaymentPeriodBean bean)
			throws Exception {
		LOG.debug("Start EformServiceImpl insertEformPetitionChangePaymentPeriod");
		String result = eformWsClientFactory.getEformPetitionWSService().postEformPetitionChangePaymentPeriod(bean);
		return result;                       
	}

	@Override
	public String insertEformPetitionChangeCustomerName(
			PetitionChangeCustomerNameBean bean) throws Exception {
		LOG.debug("Start EformServiceImpl insertEformPetitionChangeCustomerName");
		String result = eformWsClientFactory.getEformPetitionWSService().postEformPetitionChangeCustomerName(bean);
		return result;
	}

	@Override
	public String insertEformPetitionChangeAddress(
			PetitionChangeAddressBean bean) throws Exception {
		LOG.debug("Start EformServiceImpl insertEformPetitionChangeAddress");
		String result = eformWsClientFactory.getEformPetitionWSService().postEformPetitionChangeAddress(bean);
		return result;
	}

	@Override
	public String insertEformPetitionChangeBenefitPayment(
			PetitionChangeBenefitPaymentBean bean) throws Exception {
		LOG.debug("Start EformServiceImpl insertEformPetitionChangeBenefitPayment");
		String result = eformWsClientFactory.getEformPetitionWSService().postEformPetitionChangeBenefitPayment(bean);
		return result;
	}


	@Override
	public String insertEformRequestPaymentCard(RegisterPaymentCardBean  bean) throws Exception {
		LOG.debug("Start EformServiceImpl insertEformRequestPaymentCard");
		String result = eformWsClientFactory.getEformPetitionWSService().postEformRegisterPaymentCard(bean);
		return result;
	}

	@Override
	public List<RegisterPaymentCardBean> inquiryEformRegisterPaymentCard(String policyNo,String status) throws Exception {
		LOG.debug("Start EformServiceImpl insertEformRequestPaymentCard");
		List<RegisterPaymentCardBean> result = eformWsClientFactory.getEformPetitionWSService().inquiryEformRegisterPaymentCard(policyNo, status);
		return result;
	}
}
