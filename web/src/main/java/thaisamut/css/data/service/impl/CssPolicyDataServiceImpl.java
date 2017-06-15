package thaisamut.css.data.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.data.service.CssPolicyDataService;
import thaisamut.css.model.CssUser;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.css.person.action.PersonalInfoAction.Parameter;

public class CssPolicyDataServiceImpl implements CssPolicyDataService {
    private static final Logger LOG = LoggerFactory.getLogger(CssPolicyDataServiceImpl.class);
    @Autowired CssMemberService cssMemberService;
    
    Pattern patternPolicyFollowBy = Pattern.compile("^.+:.+$");
    
    @Override
    public void saveAddress(Parameter personInfoParam,CssUser user,String token)throws Exception{
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssPolicyDataServiceImpl : saveAddress");
		}
    	List<CssAddressTempEntity> addressTemp = prepareAddress(personInfoParam,user,token);
    	
    	cssMemberService.updateCssAddressTemp(addressTemp);
    }



	@Override
	public void resetAddressTempToken(String token, String newToken)throws Exception{
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssPolicyDataServiceImpl : resetAddressTempToken");
		}
		List<CssAddressTempEntity> list = cssMemberService.getCssAddressTempsByToken(token);
		if(list!=null&&!list.isEmpty()){
			for(CssAddressTempEntity entity: list){
				entity.setToken(newToken);
			}
			cssMemberService.updateCssAddressTemp(list);
		}
		
	}



	@Override
	public List<CssAddressTempEntity> updateCssAddressTempStatusByToken(String sendStatus, String token,String updateBy) throws Exception {
		List<CssAddressTempEntity> list = cssMemberService.getCssAddressTempsByToken(token);
		if(list!=null&&!list.isEmpty()){
			for(CssAddressTempEntity entity: list){
				entity.setSendStatus(sendStatus);
				entity.setUpdateBy(updateBy);
				entity.setUpdateDate(new Date());
			}
			cssMemberService.updateCssAddressTemp(list);
		}
		return list;
	}

	@Override
	public List<CssAddressTempEntity> prepareAddress(Parameter param,CssUser user, String token) throws Exception{
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssPolicyDataServiceImpl : prepareAddress");
		}
		List<CssAddressTempEntity> cssAddressTempList = null;
		if(param!=null){
			cssAddressTempList = new  ArrayList<CssAddressTempEntity>();
			
			CssAddressTempEntity entity = new CssAddressTempEntity();
			
			entity.setPolicyNo(param.getPolicyNo());
			entity.setPolType(param.getPolicyType());
			entity.setAddr(param.getAddressLine1());
			entity.setSdtDesc(param.getSubdistrictName());
			entity.setDtDesc(param.getDisDesc());
			entity.setDtCode(param.getDistrict());
			entity.setPvDesc(param.getPovDesc());
			entity.setPvCode(param.getProvince());
			entity.setZipCode(param.getPostcode());
			entity.setMobile1(param.getMobile1());
			entity.setMobile2(param.getMobile2());
			entity.setPhone1(param.getPhone1());
			entity.setExt1(param.getExt1());
			entity.setPhone2(param.getPhone2());
			entity.setExt2(param.getExt2());
			entity.setEmail(param.getEmail());
			entity.setCreateDate(new Date());
			entity.setCreateBy(user.getUsername());
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(user.getUsername());
			entity.setToken(token);
			entity.setSendStatus("N");
			
			cssAddressTempList.add(entity);
			
			if(param.getFollowPolicy()!=null&&param.getFollowPolicy().length>0){
				CssAddressTempEntity subEntity;
				String policyNo,policyType;
				Matcher matcher;
				for(String policyNoAndType : param.getFollowPolicy()){
					matcher = patternPolicyFollowBy.matcher(policyNoAndType);
					if(policyNoAndType!=null&&!"".equals(policyNoAndType)&&matcher.matches()){
						try {
							policyNo = policyNoAndType.split(":")[0];
							policyType = policyNoAndType.split(":")[1];
							
							subEntity = new CssAddressTempEntity();
							
							BeanUtils.copyProperties(subEntity, entity);
							subEntity.setId(null);
							subEntity.setPolicyNo(policyNo);
							subEntity.setPolType(policyType);

							cssAddressTempList.add(subEntity);
							
						} catch (IllegalAccessException | InvocationTargetException e) {
							LOG.error(e.getMessage(),e);
						}
					}
				}
			}
		}
		return cssAddressTempList;
	}



	@Override
	public Map<String, CssAddressTempEntity> queryConfirmationPolicies(String username) throws Exception {
		List<CssAddressTempEntity> list =  cssMemberService.queryCssAddressTempEntityByUserNameNotStataus(username,"S");
		Map<String, CssAddressTempEntity> map = new HashMap<String, CssAddressTempEntity>();
		if(list!=null){
			for(CssAddressTempEntity bean : list){
				map.put(bean.getPolicyNo(), bean);
			}
		}
		return map;
	}



	@Override
	public CssAddressTempEntity queryCssAddressTempEntityWaitingStatus(String policyNo, String username) throws Exception {
		List<CssAddressTempEntity> list =  cssMemberService.queryCssAddressTempEntityNotSendByUserNameAndPolicy(policyNo, username);
		Map<String, CssAddressTempEntity> map = new HashMap<String, CssAddressTempEntity>();
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}



	@Override
	public List<CssAddressTempEntity> saveAddress(List<CssAddressTempEntity> addressTemp, CssUser user) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssPolicyDataServiceImpl : saveAddress");
		}
    	
    	cssMemberService.updateCssAddressTemp(addressTemp);
    	return addressTemp;
	}
	
}
