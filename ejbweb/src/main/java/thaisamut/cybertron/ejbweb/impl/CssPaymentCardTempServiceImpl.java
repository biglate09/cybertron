package thaisamut.cybertron.ejbweb.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.model.CssPaymentCardTempEntity;
import thaisamut.cybertron.ejbweb.remote.CssPaymentCardTempService;
@Component("cssPaymentCardTempServiceImpl")
@Transactional
public class CssPaymentCardTempServiceImpl implements CssPaymentCardTempService {
	private static final Logger LOG = LoggerFactory.getLogger(CssPaymentCardTempServiceImpl.class);

	@Autowired 
	private EntityManager em;
	
	
	@Override
	public void insertCssPaymentCardTemp(CssPaymentCardTempEntity cp) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On insertCssPaymentCardTemp : insertCssPaymentCardTemp");
		}
			em.persist(cp);
	}


	@Override
	public CssPaymentCardTempEntity findCssPaymentCardTempByPolicyNo(String policyNo) throws Exception {
		String sql = "select o from thaisamut.cybertron.ejbweb.model.CssPaymentCardTempEntity o where o.policyNo = :policyNo and o.sendStatus = 'N'";
		Query query = em.createQuery(sql, CssPaymentCardTempEntity.class);
		query.setParameter("policyNo", policyNo);
		List<CssPaymentCardTempEntity> list =  query.getResultList();
		return list!=null&&!list.isEmpty()?list.get(0):null;
	}
	
	
}
