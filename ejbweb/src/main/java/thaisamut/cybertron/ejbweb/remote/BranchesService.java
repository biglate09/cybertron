package thaisamut.cybertron.ejbweb.remote;

import java.util.List;

import thaisamut.cybertron.ejbweb.model.BankEntity;
import thaisamut.cybertron.ejbweb.model.BranchesEntity;
import thaisamut.cybertron.ejbweb.model.ProvinceEntity;

public interface BranchesService {
	List<BranchesEntity>  findbranches() throws Exception;
	BranchesEntity queryBranchNameByBranchCode(String branchCode) throws Exception;
}
