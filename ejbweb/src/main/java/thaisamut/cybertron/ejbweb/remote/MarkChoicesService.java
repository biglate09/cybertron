package thaisamut.cybertron.ejbweb.remote;

import java.util.List;

import thaisamut.cybertron.ejbweb.model.MarkChoicesEntity;

public interface MarkChoicesService {
	List<MarkChoicesEntity> findByIdNo(String idNo) throws Exception;
	void saveChoicesId(String idNo,List<Integer> allChoices) throws Exception;
	void removeById(String idNo) throws Exception;
	void editChoicesId(List<Integer> allChoices, String idNo) throws Exception;
}