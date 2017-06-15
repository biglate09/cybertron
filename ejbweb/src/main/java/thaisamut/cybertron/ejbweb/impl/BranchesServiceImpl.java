package thaisamut.cybertron.ejbweb.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import thaisamut.cybertron.ejbweb.model.BankEntity;
import thaisamut.cybertron.ejbweb.model.BranchesEntity;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.remote.BranchesService;

@Component("branchesServiceImpl")
@Transactional
public class BranchesServiceImpl implements BranchesService{

	private static final Logger LOG = LoggerFactory.getLogger(BranchesServiceImpl.class);

	@Autowired 
	private EntityManager em;
	@Override
	public List<BranchesEntity> findbranches() throws Exception {
		// TODO Auto-generated method stub
		
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.BranchesEntity o WHERE o.brnNo NOT IN ('0001','0100','9000','9999') and o.status='A' ";
		Query query = em.createQuery(sql, BranchesEntity.class);
		List<BranchesEntity> list =  query.getResultList();

		LOG.debug("Result is 5555+ eiei===>>>"+list);
		return list;
	}
	@Override
	public BranchesEntity queryBranchNameByBranchCode(String branchCode)
			throws Exception {
		LOG.debug("~On BranchesServiceImpl : queryBranchNameByBranchCode");
		BranchesEntity result = null;
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.BranchesEntity o WHERE o.brnNo = :brnNo ";
		Query query = em.createQuery(sql, BranchesEntity.class);
		query.setParameter("brnNo", branchCode);
		List<BranchesEntity> list =  query.getResultList();
		if(list != null && !list.isEmpty()){
			result = list.get(0);
		}
		return result;
	}
	
}
