package thaisamut.cybertron.ejbweb.impl;

import thaisamut.cybertron.ejbweb.model.ChoicesEntity;
import thaisamut.cybertron.ejbweb.remote.ChoicesService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("ChoicesServiceImpl")
@Transactional
public class ChoicesServiceImpl implements ChoicesService {
	@Autowired
	private EntityManager em;

	@Override
	public ChoicesEntity findAll() throws Exception {
		String sql = "SELECT * FROM thaisamut.cybertron.ejbweb.model.ChoicesEntity";
		Query query = em.createQuery(sql, ChoicesEntity.class);
		List<ChoicesEntity> list =  query.getResultList();
		return list != null && !list.isEmpty()? list.get(0):null;
	}
	
}