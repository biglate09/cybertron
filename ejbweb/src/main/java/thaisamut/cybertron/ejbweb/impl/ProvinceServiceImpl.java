package thaisamut.cybertron.ejbweb.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import thaisamut.cybertron.ejbweb.model.ProvinceEntity;
import thaisamut.cybertron.ejbweb.remote.ProvinceService;

@Component("ProvinceServiceImpl")
@Transactional
public class ProvinceServiceImpl implements ProvinceService {
	@Autowired
	EntityManager em;

	@Override
	public ProvinceEntity findAll() throws Exception {
		String sql = "SELECT province_name FROM import thaisamut.cybertron.ejbweb.model.ProvinceEntity";
		Query query = em.createQuery(sql, ProvinceEntity.class);
		List<ProvinceEntity> list =  query.getResultList();
		return list != null && !list.isEmpty()? list.get(0):null;
	}
}