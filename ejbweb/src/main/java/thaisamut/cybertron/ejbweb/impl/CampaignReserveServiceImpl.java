package thaisamut.cybertron.ejbweb.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import thaisamut.cybertron.ejbweb.model.CampaignReserveEntity;
import thaisamut.cybertron.ejbweb.remote.CampaignReserveService;

@Component("CampaignReserveServiceImpl")
@Transactional
public class CampaignReserveServiceImpl implements CampaignReserveService {
	@Autowired
	EntityManager em;

	@Override
	public boolean save(int campaignId) throws Exception {
		CampaignReserveEntity tempCre = null;
		int idNo = tempCre.getIdNo();
		
		
		String sql = "UPDATE FROM thaisamut.cybertron.ejbweb.model.CampaignReserveEntity o "
				   + "SET o.campaignId = campaignId"
				   + "WHERE o.idNo = :idNo and o.campaignId <> :campaignId";
		Query query = em.createQuery(sql, CampaignReserveEntity.class);
		query.setParameter("idNo", idNo);
		query.setParameter("campaignId", campaignId);
		
		if(query.executeUpdate() == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean removeByReserveId(int reserveId) throws Exception {
		String sql = "DELETE FROM thaisamut.cybertron.ejbweb.model.CampaignReserveEntity o "
				   + "WHERE o.reserveId = :reserveId";
		Query query = em.createQuery(sql, CampaignReserveEntity.class);
		query.setParameter("reserveId", reserveId);
		
		if(query.executeUpdate() == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public CampaignReserveEntity findByIdNo(int idNo) throws Exception {
		String sql = "SELECT o FROM import thaisamut.cybertron.ejbweb.model.CampaignReserveEntity o "
				   + "WHERE o.idNo = :idNo";
		Query query = em.createQuery(sql, CampaignReserveEntity.class);
		query.setParameter("idNo", idNo);
		List<CampaignReserveEntity> list =  query.getResultList();
		return list != null && !list.isEmpty()? list.get(0):null;
	}
}