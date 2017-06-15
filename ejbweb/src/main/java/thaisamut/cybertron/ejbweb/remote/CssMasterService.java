package thaisamut.cybertron.ejbweb.remote;

import java.util.List;

import thaisamut.cybertron.ejbweb.model.BankEntity;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.model.CssCommonDataEntity;
import thaisamut.cybertron.ejbweb.model.CssConstantEntity;
import thaisamut.cybertron.ejbweb.model.CssMapRiderGroupEntity;
import thaisamut.cybertron.ejbweb.model.CssNotififyLimitEntity;
import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;
import thaisamut.cybertron.ejbweb.model.CssTxnSequenceEntity;
import thaisamut.cybertron.ejbweb.model.DistrictEntity;
import thaisamut.cybertron.ejbweb.model.ProvinceEntity;
import thaisamut.cybertron.ejbweb.model.ThailandZipcodeEntity;

public interface CssMasterService {

	List<ProvinceEntity> queryProvince(String status) throws Exception;
	List<DistrictEntity> queryDistrictGroupByPvCode(String status) throws Exception;
	List<CssMapRiderGroupEntity> queryCssMapRiderGroup() throws Exception;
	List<ThailandZipcodeEntity> queryThailandZipCodeByDisCode(String string)throws Exception;
	List<CssResetPasswordEntity> queryCssResetPasswordNotExprired()throws Exception;
	void setExpriedCssResetPassword(CssResetPasswordEntity cssResetPassword)throws Exception;
	void addCssResetPassword(CssResetPasswordEntity cssResetPassword)throws Exception;
	List<BankEntity> queryBank() throws Exception;
	boolean isExistEamil(String email, String username)throws Exception;
	boolean isExistMobile1(String mobile, String username) throws Exception;
	
	CssTxnSequenceEntity querySeqByModeAndKey(String mode, String key) throws Exception;
	CssConstantEntity queryCssConstantEntityByKey(String key) throws Exception;
	
	CssCommonDataEntity queryCssCommonDataEmail(String keyGroup, String keyCode) throws Exception;
	List<CssCommonDataEntity> queryCssCommonDataCCEmailBy(String keyGroup, String keyCode) throws Exception;
	CssNotififyLimitEntity queryCssNotififyLimit(String userName, String mobile, String email) throws Exception;
	void insertCssNotififyLimit(CssNotififyLimitEntity cssNotififyLimitEntity) throws Exception;
	void updateCssNotififyLimit(CssNotififyLimitEntity cssNotififyLimitEntity) throws Exception;
	ThailandZipcodeEntity queryCssThailandZipcodeBySubDistrictAndDistrictAndProvince(String subDistrict, String district, String province) throws Exception;
	CssAddressTempEntity queryCssAddressTempByIdNo(String idNo) throws Exception;
	public CssAddressTempEntity queryCssAddressTempByPolicyNo(String policyNo) throws Exception;
	
}
