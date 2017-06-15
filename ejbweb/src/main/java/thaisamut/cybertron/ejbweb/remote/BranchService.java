package thaisamut.cybertron.ejbweb.remote;

import java.util.List;

import thaisamut.cybertron.ejbweb.model.BranchEntity;

public interface BranchService {
	List<BranchEntity> findAll() throws Exception;
	BranchEntity findPosition(int branchId) throws Exception;
}
