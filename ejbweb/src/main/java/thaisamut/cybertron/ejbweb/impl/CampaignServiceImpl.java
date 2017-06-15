package thaisamut.cybertron.ejbweb.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import thaisamut.cybertron.ejbweb.remote.CampaignService;
import thaisamut.cybertron.ejbweb.model.CampaignEntity;


@Component("campaignServiceImpl")
@Transactional
public class CampaignServiceImpl implements CampaignService{

	private static final Logger LOG = LoggerFactory.getLogger(CampaignService.class);

	@Autowired
	private EntityManager em;
	
	
	@Override
	public List<CampaignEntity> findAllCampaign() throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CampaignEntity o";
		List<CampaignEntity> resultList = em.createQuery(sql, CampaignEntity.class).getResultList();
		return resultList;
	}
	
	@Override
	public List<CampaignEntity> findCampaignByProvinceId(int provinceId) throws Exception {
		String sql = "SELECT c FROM thaisamut.cybertron.ejbweb.model.CampaignEntity c where c.provinceId = :provinceId";
		List<CampaignEntity> resultList = em.createQuery(sql, CampaignEntity.class)
				.setParameter("provinceId", provinceId).getResultList();
		return resultList;
	}



	@Override
	public CampaignEntity findCampaignDetail(int campaignId) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CampaignServiceImpl : findCampaignDetail");
		}
		try {
			String sql = "SELECT c FROM thaisamut.cybertron.ejbweb.model.CampaignEntity c where c.campaignId = :campaignId";
			List<CampaignEntity> resultList =  em.createQuery(sql,CampaignEntity.class)
					.setParameter("campaignId", campaignId).getResultList();
			if(!CollectionUtils.isEmpty(resultList)){
				return resultList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	
}
