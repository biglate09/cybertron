package thaisamut.cybertron.ejbweb.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import thaisamut.cybertron.ejbweb.model.CampaignEntity;
import thaisamut.cybertron.ejbweb.model.PlaceEntity;
import thaisamut.cybertron.ejbweb.remote.PlaceService;

@Component("PlaceServiceImpl")
@Transactional
public class PlaceServiceImpl implements PlaceService {
	@Autowired
	private EntityManager em;
	
	@Override
	public List<PlaceEntity> findAllPlace() throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.PlaceEntity o";
		List<PlaceEntity> resultList = em.createQuery(sql, PlaceEntity.class).getResultList();
		return resultList;
	}

	@Override
	public PlaceEntity findByProvinceId(String provinceId) throws Exception {
		String sql = "SELECT o FROM import thaisamut.cybertron.ejbweb.model.PlaceEntity o"
				   + "WHERE o.provinceId = :provinceId";
		Query query = em.createQuery(sql, PlaceEntity.class);
		query.setParameter("provinceId", provinceId);
		List<PlaceEntity> list =  query.getResultList();
		return list != null && !list.isEmpty()? list.get(0):null;
	}

	@Override
	public PlaceEntity findByPlaceNameLike(String placeName) throws Exception {
		String sql = "SELECT o FROM import thaisamut.cybertron.ejbweb.model.PlaceEntity o"
				   + "WHERE o.placeName LIKE %:placeName%";
		Query query = em.createQuery(sql, PlaceEntity.class);
		query.setParameter("placeName", placeName);
		List<PlaceEntity> list =  query.getResultList();
		return list != null && !list.isEmpty()? list.get(0):null;
	}


	@Override
	public PlaceEntity findByPlaceId(int placeId) throws Exception {
		
		try {
			String sql = "SELECT p FROM thaisamut.cybertron.ejbweb.model.PlaceEntity p where p.placeId = :placeId";
			List<PlaceEntity> resultList =  em.createQuery(sql,PlaceEntity.class)
					.setParameter("placeId", placeId).getResultList();
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