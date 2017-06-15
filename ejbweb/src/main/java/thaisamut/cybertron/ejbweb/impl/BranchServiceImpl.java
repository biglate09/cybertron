package thaisamut.cybertron.ejbweb.impl;


import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import thaisamut.cybertron.ejbweb.remote.BranchService;
import thaisamut.cybertron.ejbweb.model.BranchEntity;
import thaisamut.cybertron.ejbweb.model.CampaignEntity;


@Component("BranchServiceImpl")
@Transactional
public class BranchServiceImpl implements BranchService {
	@Autowired
	private EntityManager em;

	@Override
	public List<BranchEntity> findAll() throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.BranchEntity o";
		List<BranchEntity> resultList = em.createQuery(sql, BranchEntity.class).getResultList();
		return resultList;
	}
	
	@Override
	public BranchEntity findPosition(int branchId) throws Exception {
		String sql = "SELECT o.latitude, o.longitude FROM thaisamut.cybertron.ejbweb.model.BranchEntity c "
				   + "WHERE o.branchId = :branchId";
		List<BranchEntity> resultList =  em.createQuery(sql,BranchEntity.class).setParameter("branchId", branchId).getResultList();
		
		if(!CollectionUtils.isEmpty(resultList)) {
			return resultList.get(0);
		}
		else {
			return null;
		}
	}
}
