package thaisamut.cybertron.ejbweb.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sun.security.ssl.Debug;
import thaisamut.cybertron.ejbweb.model.MarkChoicesEntity;
import thaisamut.cybertron.ejbweb.remote.MarkChoicesService;

@Component("MarkChoicesServiceImpl")
@Transactional
public class MarkChoicesServiceImpl implements MarkChoicesService {
	private static final Logger LOG = LoggerFactory.getLogger(MarkChoicesServiceImpl.class);

	@Autowired
	private EntityManager em;

	@Override
	public List<MarkChoicesEntity> findByIdNo(String idNo) throws Exception {
		String sql = "SELECT o FROM "+MarkChoicesEntity.class.getName()+" o WHERE o.idNo = :idNo";
		Query query = em.createQuery(sql, MarkChoicesEntity.class);
		query.setParameter("idNo", idNo);
		List<MarkChoicesEntity> list =  query.getResultList();
		return list;
	}

	@Override
	public void saveChoicesId(String idNo,List<Integer> allChoices) throws Exception {
		for(int ac : allChoices) {
			MarkChoicesEntity mc = new MarkChoicesEntity();
			mc.setIdNo(idNo);
			mc.setChoicesId(ac);
			LOG.debug("HELLO INSERT : "+mc);
			em.persist(mc);
		}
	}

	@Override
	public void removeById(String idNo) throws Exception {
		String sql = "DELETE FROM "+MarkChoicesEntity.class.getName()+" o WHERE o.idNo = :idNo";
		Query query = em.createQuery(sql);
		query.setParameter("idNo",idNo);
		query.executeUpdate();
	}

	@Override
	public void editChoicesId(List<Integer> allChoices, String idNo) throws Exception {
		removeById(idNo);
		saveChoicesId(idNo,allChoices);
	}
}
