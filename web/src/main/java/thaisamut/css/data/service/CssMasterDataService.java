package thaisamut.css.data.service;

import java.util.List;
import java.util.Map;

import thaisamut.css.dwh.service.pojo.DistrictBean;
import thaisamut.css.dwh.service.pojo.ProvinceBean;
import thaisamut.css.dwh.service.pojo.SubDistrictBean;
import thaisamut.css.eform.service.pojo.BankBean;
import thaisamut.css.eform.service.pojo.CssConstantBean;
import thaisamut.cybertron.ejbweb.model.CssCommonDataEntity;
import thaisamut.cybertron.ejbweb.model.CssConstantEntity;

public interface CssMasterDataService {

	List<ProvinceBean> getActiveProvince() throws Exception;

	Map<String, List<DistrictBean>> getActiveDistrict() throws Exception;

	String getRiderMap(String riderCode, String policyType) throws Exception;

	Map<String, List<SubDistrictBean>> getActiveSubDistrict()throws Exception;
	
	List<BankBean> getBank() throws Exception;

	boolean isExistEmail(String email, String username) throws Exception;

	boolean isExistMobile1(String mobile1, String username) throws Exception;

	Map<String, String> getOrderIdAndInvoiceNo(String mode, String key) throws Exception;
	
	CssConstantEntity getConstant(String key) throws Exception;
	
	CssCommonDataEntity getCommonDataEmail(String keyGroup, String keyCode) throws Exception;
	List<CssCommonDataEntity> getCommonDataCCEmail(String keyGroup, String keyCode) throws Exception;

	CssConstantBean getDowloadAllow() throws Exception;
}
