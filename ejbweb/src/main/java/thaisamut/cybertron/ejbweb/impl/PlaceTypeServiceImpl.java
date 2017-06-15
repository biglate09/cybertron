package thaisamut.cybertron.ejbweb.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import thaisamut.cybertron.ejbweb.model.PlaceTypeEntity;
import thaisamut.cybertron.ejbweb.remote.PlaceTypeService;

@Component("PlaceTypeServiceImpl")
@Transactional
public class PlaceTypeServiceImpl implements PlaceTypeService {
	@Autowired
	private EntityManager em;

	@Override
	public PlaceTypeEntity findAll() throws Exception {
		String sql = "SELECT place_type_name FROM import thaisamut.cybertron.ejbweb.model.PlaceTypeEntity";
		Query query = em.createQuery(sql, PlaceTypeEntity.class);
		List<PlaceTypeEntity> list =  query.getResultList();
		return list != null && !list.isEmpty()? list.get(0):null;
	}
	
}