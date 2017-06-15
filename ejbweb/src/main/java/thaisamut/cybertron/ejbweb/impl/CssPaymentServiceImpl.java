package thaisamut.cybertron.ejbweb.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import thaisamut.cybertron.ejbweb.model.CssPaymentEntity;
import thaisamut.cybertron.ejbweb.remote.CssPaymentService;

@Component("cssPaymentServiceImpl")
@Transactional
public class CssPaymentServiceImpl implements CssPaymentService {
	private static final Logger LOG = LoggerFactory.getLogger(CssPaymentServiceImpl.class);

	@Autowired 
	private EntityManager em;

	
	@Override
	public void saveCssPayment(CssPaymentEntity entity) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssPaymentServiceImpl : saveCssPayment");
		}
		try{
			em.persist(entity);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public CssPaymentEntity findCssPaymentByOrderNo(String orderNo)
			throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssPaymentServiceImpl : findCssPaymentByOrderNo");
		}
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssPaymentEntity o WHERE o.orderNo = :orderNo";
		Query query = em.createQuery(sql, CssPaymentEntity.class);
		query.setParameter("orderNo", orderNo);
		return (CssPaymentEntity) query.getSingleResult();
	}


	@Override
	public void updateCssPayment(CssPaymentEntity entity)
			throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssPaymentServiceImpl : updateCssPaymentAfterCall2C2P");
		}
		try{
			em.merge(entity);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	@Override
	public List<CssPaymentEntity> findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate(
			String policyNo, String letterType, Date paymentDueDate)
			throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssPaymentServiceImpl : findCssPaymentByPolicyNoAndLetterTypeAndPaymentDueDate");
		}
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssPaymentEntity o WHERE o.policyNo = :policyNo and o.letterType = :letterType and o.paymentDueDate = :paymentDueDate and o.sendHydraStatus = :status ";
		Query query = em.createQuery(sql, CssPaymentEntity.class);
		query.setParameter("policyNo", policyNo);
		query.setParameter("letterType", letterType);
		query.setParameter("paymentDueDate", paymentDueDate);
		query.setParameter("status", "R");
		return query.getResultList();
	}
	

	@Override
	public List<CssPaymentEntity> findCssPaymentByPolicyNoAndNextPaidDate(
			String policyNo, Date nextPaidDate) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssPaymentServiceImpl : findCssPaymentByPolicyNoAndNextPaidDate");
		}
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssPaymentEntity o WHERE o.policyNo = :policyNo and o.paymentDueDate = :paymentDueDate and o.sendHydraStatus = :status ";
		
		Query query = em.createQuery(sql, CssPaymentEntity.class);
		query.setParameter("policyNo", policyNo);
		query.setParameter("paymentDueDate", nextPaidDate);
		query.setParameter("status", "R");
		return query.getResultList();
	}


}
