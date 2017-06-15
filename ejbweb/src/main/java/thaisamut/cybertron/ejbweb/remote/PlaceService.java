package thaisamut.cybertron.ejbweb.remote;

import java.util.List;

import thaisamut.cybertron.ejbweb.model.PlaceEntity;

public interface PlaceService {
	List<PlaceEntity> findAllPlace() throws Exception;
	PlaceEntity findByProvinceId(String provinceId) throws Exception;
	PlaceEntity findByPlaceNameLike(String placeName) throws Exception;
	PlaceEntity findByPlaceId(int placeId) throws Exception;
}