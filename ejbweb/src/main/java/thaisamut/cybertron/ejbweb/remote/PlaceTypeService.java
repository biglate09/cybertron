package thaisamut.cybertron.ejbweb.remote;

import thaisamut.cybertron.ejbweb.model.PlaceTypeEntity;

public interface PlaceTypeService {
	PlaceTypeEntity findAll() throws Exception;
}