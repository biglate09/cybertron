package thaisamut.cybertron.ejbweb.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import thaisamut.cybertron.ejbweb.bean.CssLoanBean;
import thaisamut.cybertron.ejbweb.model.CssLoanEntity;
import thaisamut.cybertron.ejbweb.remote.EasyLoanInfoService;
import thaisamut.cybertron.ejbweb.utils.CssLoanStatusEnum;

@Component("easyLoanInfoServiceImpl")
@Transactional
public class EasyLoanInfoServiceImpl implements EasyLoanInfoService {
	private static final Logger LOG = LoggerFactory.getLogger(EasyLoanInfoServiceImpl.class);
	@Autowired 
	private EntityManager em;

	@Override
	public CssLoanBean findCssLoan(String id, String policyNo) throws Exception {
		LOG.info("~On EasyLoanInfoServiceImpl.findCssLoan ~");
		CssLoanBean dest = null;
		CssLoanEntity orig = findCssLoanEntity(id, policyNo);
		if(null != orig){
			dest = new CssLoanBean();
			PropertyUtils.copyProperties(dest, orig);
		}
		return dest;
	}

	private CssLoanEntity findCssLoanEntity(String id, String policyNo)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		LOG.info("~On EasyLoanInfoServiceImpl.findCssLoanEntity ~");
		String  sql = "select o from "+CssLoanEntity.class.getName()+" o where o.policyNo =:policyNo and o.customerCardNo =:customerCardNo";
		List<CssLoanEntity> cssloan = em.createQuery(sql, CssLoanEntity.class)
				.setParameter("customerCardNo", id)
				.setParameter("policyNo", policyNo)
				.getResultList();
		if(!CollectionUtils.isEmpty(cssloan)){
			return cssloan.get(cssloan.size()-1);
		}
		return null;
	}

	@Override
	public void save(CssLoanBean bean) throws Exception {
		LOG.info("~On EasyLoanInfoServiceImpl.save ~");
		CssLoanEntity ent = new CssLoanEntity();
		PropertyUtils.copyProperties(ent, bean);
		em.persist(ent);
	}

	@Override
	public CssLoanBean updateCssLoanStatus(String idNo, String policyNo,String REQ_NO) throws Exception {
		LOG.info("~On EasyLoanInfoServiceImpl.updateCssLoanStatus ~");
		CssLoanEntity orig = findCssLoanEntity(idNo, policyNo);
		CssLoanBean dest = new CssLoanBean();
		PropertyUtils.copyProperties(dest, orig);
		orig.setStatus(CssLoanStatusEnum.C.toString());
		orig.setRequestNo(REQ_NO);
		orig.setFileNameIdCard(null);
		orig.setFileNameOther1(null);
		orig.setFileNameOther2(null);
		orig.setFileNameBookBank(null);
		em.merge(orig);
		return dest;
	}

}
