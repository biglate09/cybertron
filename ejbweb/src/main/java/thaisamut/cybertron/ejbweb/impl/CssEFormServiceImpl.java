package thaisamut.cybertron.ejbweb.impl;

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
import thaisamut.cybertron.ejbweb.model.CssPetitionPolicyEntity;
import thaisamut.cybertron.ejbweb.model.CssPetitionRequestEntity;
import thaisamut.cybertron.ejbweb.remote.CssEFormService;

@Component("cssEFormServiceImpl")
@Transactional
public class CssEFormServiceImpl implements CssEFormService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CssEFormServiceImpl.class);

	@Autowired 
	private EntityManager em;

	@Override
	public void saveCssPetitionRequest(CssPetitionRequestEntity entity)
			throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssEFormServiceImpl : saveCssPetitionRequest");
		}
		try{
			em.persist(entity);
			
			for (CssPetitionPolicyEntity pEntity : entity.getPetitionPolicyList()) {
				pEntity.setPetitionId(entity.getId());
				
				em.persist(pEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<CssPetitionRequestEntity> findCssPetitionRequestByIdNoAndRequestDateAndRequestType(
			String idNo, Date requestDate, String requestType) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssEFormServiceImpl : findCssPetitionRequestByIdNoAndRequestDateAndRequestType");
		}
		try{
			String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssPetitionRequestEntity o WHERE o.customerCardNo = :idNo and DATE(o.createDate) = DATE(:requestDate) and o.requestType = :requestType ";
			Query query = em.createQuery(sql, CssPetitionRequestEntity.class);

			query.setParameter("idNo", idNo);
			query.setParameter("requestDate", requestDate);
			query.setParameter("requestType", requestType);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public CssPetitionPolicyEntity findCssPetitionPolicyByRequestIdAndPolicyNoAndRequestDate(
			Long requestId, String policyNo, Date requestDate) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssEFormServiceImpl : findCssPetitionPolicyByRequestIdAndPolicyNoAndRequestDate");
		}
		try{
			String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssPetitionPolicyEntity o WHERE o.petitionId = :requestId and o.policyNo = :policyNo and DATE(o.createDate) = DATE(:requestDate)";
			Query query = em.createQuery(sql, CssPetitionPolicyEntity.class);

			query.setParameter("requestId", requestId);
			query.setParameter("policyNo", policyNo);
			query.setParameter("requestDate", requestDate);
			List<CssPetitionPolicyEntity> policyList = query.getResultList();
			if(policyList != null && policyList.size() > 0){
				return policyList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
