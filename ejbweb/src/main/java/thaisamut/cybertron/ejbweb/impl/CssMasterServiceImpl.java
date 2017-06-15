package thaisamut.cybertron.ejbweb.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import thaisamut.cybertron.ejbweb.model.BankEntity;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.model.CssCommonDataEntity;
import thaisamut.cybertron.ejbweb.model.CssConstantEntity;
import thaisamut.cybertron.ejbweb.model.CssMapRiderGroupEntity;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.model.CssNotififyLimitEntity;
import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;
import thaisamut.cybertron.ejbweb.model.CssTxnSequenceEntity;
import thaisamut.cybertron.ejbweb.model.DistrictEntity;
import thaisamut.cybertron.ejbweb.model.ProvinceEntity;
import thaisamut.cybertron.ejbweb.model.ThailandZipcodeEntity;
import thaisamut.cybertron.ejbweb.remote.CssMasterService;
@Component("cssMasterServiceImpl")
@Transactional
public class CssMasterServiceImpl implements CssMasterService{

	private static final Logger LOG = LoggerFactory.getLogger(CssMasterServiceImpl.class);

	@Autowired 
	private EntityManager em;
	
	@Override
	public List<ProvinceEntity> queryProvince(String status)throws Exception{
		LOG.debug("~On CssMasterServiceImpl : queryProvince");
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.ProvinceEntity o WHERE o.status = :status ORDER BY substr(o.pvDesc,1,1), o.pvDesc";
		Query query = em.createQuery(sql, ProvinceEntity.class);
		query.setParameter("status", status);
		return query.getResultList();
	}
	
	@Override
	public List<DistrictEntity> queryDistrictGroupByPvCode(String status)throws Exception{
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.DistrictEntity o WHERE o.status = :status ORDER BY o.pvCode,substr(o.distDesc,1,1),o.distDesc";
		Query query = em.createQuery(sql, DistrictEntity.class);
		query.setParameter("status", status);
		return query.getResultList();
	}
	
	@Override
	public List<CssMapRiderGroupEntity> queryCssMapRiderGroup()throws Exception{
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssMapRiderGroupEntity o";
		Query query = em.createQuery(sql, CssMapRiderGroupEntity.class);
		return query.getResultList();
	}

	@Override
	public List<ThailandZipcodeEntity> queryThailandZipCodeByDisCode(String status) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.ThailandZipcodeEntity o WHERE o.status = :status ORDER BY o.districtAs400,substr(o.subdistrict,1,1), o.subdistrict";
		Query query = em.createQuery(sql, ThailandZipcodeEntity.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<CssResetPasswordEntity> queryCssResetPasswordNotExprired() throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity o WHERE o.expireDate > :dateNow";
		Query query = em.createQuery(sql, CssResetPasswordEntity.class);
		query.setParameter("dateNow", new Date());
		return query.getResultList();
	}

	@Override
	public void setExpriedCssResetPassword(CssResetPasswordEntity cssResetPassword) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity o WHERE o.token = :token";
		Query query = em.createQuery(sql, CssResetPasswordEntity.class);
		query.setParameter("token", cssResetPassword.getToken());
		CssResetPasswordEntity reset = null;
		try{
			reset = (CssResetPasswordEntity) query.getSingleResult();
		}catch(NoResultException ignored){}
		if(reset!=null){
			reset.setExpireDate(new Date());
			em.merge(reset);
		}
	}

	@Override
	public void addCssResetPassword(CssResetPasswordEntity cssResetPassword) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity o WHERE o.token = :token";
		Query query = em.createQuery(sql, CssResetPasswordEntity.class);
		query.setParameter("token", cssResetPassword.getToken());
		List<CssResetPasswordEntity> reset =  query.getResultList();
		if(reset==null||reset.isEmpty()){
			em.persist(cssResetPassword);
		}else{
			throw new DuplicateKeyException("CssResetPasswordEntity token was owned.");
		}
	}

	@Override
	public List<BankEntity> queryBank() throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.BankEntity o ORDER BY o.bankName";
		Query query = em.createQuery(sql, BankEntity.class);
		return query.getResultList();
	}

	@Override
	public boolean isExistEamil(String email, String username) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssMemberEntity o WHERE o.username <> :username and o.email = :email ";
		Query query = em.createQuery(sql, CssMemberEntity.class);
		query.setParameter("username",username);
		query.setParameter("email", email);
		List<CssMemberEntity> list =  query.getResultList();
		
		return list!=null&&!list.isEmpty();
	}

	@Override
	public boolean isExistMobile1(String mobile, String username) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssMemberEntity o WHERE o.username <> :username and o.telNo = :telNo ";
		Query query = em.createQuery(sql, CssMemberEntity.class);
		query.setParameter("username",username);
		query.setParameter("telNo", mobile);
		List<CssMemberEntity> list =  query.getResultList();
		
		return list!=null&&!list.isEmpty();
	}

	@Override
	public CssTxnSequenceEntity querySeqByModeAndKey(String mode, String key)
			throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssTxnSequenceEntity o WHERE o.mode = :mode and o.key = :key ";
		Query query = em.createQuery(sql, CssTxnSequenceEntity.class);
		query.setParameter("mode",mode);
		query.setParameter("key",key);
		List<CssTxnSequenceEntity> list = query.getResultList();
		if(list != null && list.size() > 0){
			CssTxnSequenceEntity entity = list.get(0);
			Integer seq = entity.getSeq();
			seq = seq + 1;
			entity.setSeq(seq);
			
			em.merge(entity);
			return entity;
		}else{
			CssTxnSequenceEntity entity = new CssTxnSequenceEntity();
			entity.setMode(mode);
			entity.setKey(key);
			entity.setSeq(1);
			entity.setTimestamp(new Date());
			
			em.persist(entity);
			return entity;
		}
	}
	@Override
	public CssConstantEntity queryCssConstantEntityByKey(String key) throws Exception{
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssConstantEntity o WHERE o.key = :key ";
		Query query = em.createQuery(sql, CssConstantEntity.class);
		query.setParameter("key",key);
		List<CssConstantEntity> list = query.getResultList();
		if(list != null && list.size() > 0){
			CssConstantEntity entity = list.get(0);
			return entity;
		}
		return null;
	}

	@Override
	public CssCommonDataEntity queryCssCommonDataEmail(String keyGroup,
			String keyCode) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssCommonDataEntity o WHERE o.keyGroup = :keyGroup and o.keyCode = :keyCode and o.status = :status ";
		Query query = em.createQuery(sql, CssCommonDataEntity.class);
		query.setParameter("keyGroup",keyGroup);
		query.setParameter("keyCode",keyCode);
		query.setParameter("status", "A");
		List<CssCommonDataEntity> list = query.getResultList();
		if(list != null && list.size() > 0){
			CssCommonDataEntity entity = list.get(0);
			return entity;
		}
		return null;
	}

	@Override
	public List<CssCommonDataEntity> queryCssCommonDataCCEmailBy(
			String keyGroup, String keyCode) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssCommonDataEntity o WHERE o.keyGroup = :keyGroup and o.keyCode = :keyCode and o.status = :status ";
		Query query = em.createQuery(sql, CssCommonDataEntity.class);
		query.setParameter("keyGroup",keyGroup);
		query.setParameter("keyCode",keyCode);
		query.setParameter("status", "A");
		List<CssCommonDataEntity> list = query.getResultList();
		return list;
	}

	@Override
	public CssNotififyLimitEntity queryCssNotififyLimit(String userName, String mobile, String email) throws Exception {
		StringBuilder sql = new StringBuilder("SELECT o FROM thaisamut.cybertron.ejbweb.model.CssNotififyLimitEntity o WHERE 1=1 ");
		
		if(userName!=null&&!"".equals(userName)){
			sql.append(" and o.username = :username ");
		}
		if("PUBLIC_EVENT".equals(userName)&&mobile!=null&&!"".equals(mobile)){
			sql.append(" and o.mobile = :mobile ");
		}
		if("PUBLIC_EVENT".equals(userName)&&email!=null&&!"".equals(email)){
			sql.append(" and o.email = :email ");
		}
		
		Query query = em.createQuery(sql.toString(), CssNotififyLimitEntity.class);
		
		if(userName!=null&&!"".equals(userName)){
			query.setParameter("username",userName);
		}
		if("PUBLIC_EVENT".equals(userName)&&mobile!=null&&!"".equals(mobile)){
			query.setParameter("mobile",mobile);
		}
		if("PUBLIC_EVENT".equals(userName)&&email!=null&&!"".equals(email)){
			query.setParameter("email",email);
		}
		List<CssNotififyLimitEntity> list = query.getResultList();
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	@Override
	public void insertCssNotififyLimit(CssNotififyLimitEntity cssNotififyLimitEntity) throws Exception {
		em.persist(cssNotififyLimitEntity);
	}
	@Override
	public void updateCssNotififyLimit(CssNotififyLimitEntity cssNotififyLimitEntity) throws Exception {
		em.merge(cssNotififyLimitEntity);
	}

	@Override
	public ThailandZipcodeEntity queryCssThailandZipcodeBySubDistrictAndDistrictAndProvince(
			String subDistrict, String district, String province)
			throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.ThailandZipcodeEntity o WHERE o.status = :status and o.subdistrict = :subDistrict and o.district = :district and o.province = :province ";
		Query query = em.createQuery(sql, ThailandZipcodeEntity.class);
		query.setParameter("status", "A");
		query.setParameter("subDistrict", subDistrict);
		query.setParameter("district", district);
		query.setParameter("province", province);
		List<ThailandZipcodeEntity> queryList = query.getResultList();
		return queryList != null && queryList.size() > 0 ? queryList.get(0) : null;
	}

	@Override
	public CssAddressTempEntity queryCssAddressTempByIdNo(String idNo)
			throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssAddressTempEntity o WHERE o.createBy = :idNo ORDER BY updateDate desc ";
		Query query = em.createQuery(sql, CssAddressTempEntity.class);
		query.setParameter("idNo", idNo);
		List<CssAddressTempEntity> queryList = query.getResultList();
		return queryList != null && queryList.size() > 0 ? queryList.get(0) : null;
	}

	@Override
	public CssAddressTempEntity queryCssAddressTempByPolicyNo(String policyNo) throws Exception {
		String sql = " select o from "+CssAddressTempEntity.class.getName()+" o where o.policyNo =:policyNo and date(o.updateDate) = date(now()) order by o.updateDate desc";
		Query query = em.createQuery(sql, CssAddressTempEntity.class);
		query.setParameter("policyNo", policyNo);
		List<CssAddressTempEntity> queryList = query.getResultList();
		return queryList != null && queryList.size() > 0 ? queryList.get(0) : null;
	}

}
