package thaisamut.css.data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.dwh.service.pojo.DistrictBean;
import thaisamut.css.dwh.service.pojo.ProvinceBean;
import thaisamut.css.dwh.service.pojo.RiderMapBean;
import thaisamut.css.dwh.service.pojo.SubDistrictBean;
import thaisamut.css.eform.service.pojo.BankBean;
import thaisamut.css.eform.service.pojo.CssConstantBean;
import thaisamut.cybertron.ejbweb.model.BankEntity;
import thaisamut.cybertron.ejbweb.model.CssCommonDataEntity;
import thaisamut.cybertron.ejbweb.model.CssConstantEntity;
import thaisamut.cybertron.ejbweb.model.CssMapRiderGroupEntity;
import thaisamut.cybertron.ejbweb.model.CssTxnSequenceEntity;
import thaisamut.cybertron.ejbweb.model.DistrictEntity;
import thaisamut.cybertron.ejbweb.model.ProvinceEntity;
import thaisamut.cybertron.ejbweb.model.ThailandZipcodeEntity;
import thaisamut.cybertron.ejbweb.remote.CssMasterService;

public class CssMasterDataServiceImpl implements CssMasterDataService {
    private static final Logger LOG = LoggerFactory.getLogger(CssMasterDataServiceImpl.class);

    final long REFRESH_PERIOD = 1000*60*60 /*1 hr.*/   ;
    
    List<ProvinceBean> provinceSeed = null;
    long provincQeuryTS = 0;
    
    Map<String,List<DistrictBean>> districtSeed = null;
    long districtQeuryTS = 0;
    Map<String,List<SubDistrictBean>> subDistrictSeed = null;
    long subDistrictQeuryTS = 0;

    List<RiderMapBean> riderGroupSeed = null;
    long riderGroupQeuryTS = 0;
    
    CssConstantBean allowDownloadSeed;
//    long allowDownloadQeuryTS = 0;
    
    @Autowired CssMasterService cssMasterService;
    
    private static final String prefixInvoiceNo = "INV";

	private static final String ALLOW_DOWNLOAD = "ALLOW_DOWNLOAD";
    
    @Override
    public List<ProvinceBean> getActiveProvince()throws Exception{
    	LOG.debug("~On CssMasterDateServiceImpl getActiveProvince");
    	long time = System.currentTimeMillis();
    	if(provincQeuryTS<time||provinceSeed == null){
    		List<ProvinceEntity> provinces = cssMasterService.queryProvince("A");
    		if(provinces!=null){
    			provinceSeed = new ArrayList<ProvinceBean>();
    			
    			for(ProvinceEntity p : provinces){
    				provinceSeed.add(new ProvinceBean(p.getPvCode(), p.getPvDesc()));
    			}
    			
    		}
    		provincQeuryTS = time + REFRESH_PERIOD;
    	}
    	
    	return provinceSeed;
    }
    

    @Override
    public Map<String,List<DistrictBean>> getActiveDistrict()throws Exception{
    	LOG.debug("~On CssMasterDateServiceImpl getActiveDistrict");
    	long time = System.currentTimeMillis();
    	if(districtQeuryTS<time||districtSeed == null){
    		List<DistrictEntity> distrinct = cssMasterService.queryDistrictGroupByPvCode("A");
    		if(distrinct!=null){
    			districtSeed = new HashMap<String, List<DistrictBean>>();
    			List<DistrictBean> tmpList;
    			for(DistrictEntity d : distrinct){
    				tmpList = districtSeed.get(d.getPvCode());
    				if(tmpList ==null){
    					tmpList = new ArrayList<DistrictBean>();
    					districtSeed.put(d.getPvCode(), tmpList);
    				}
    				tmpList.add(new DistrictBean(d.getPvCode(),d.getDistCode(),d.getDistDesc()));
    			}
    		}
    		districtQeuryTS = time + REFRESH_PERIOD;
    	}
    	
    	return districtSeed;
    }

    @Override
    public String getRiderMap(String riderCode,String policyType)throws Exception{
    	LOG.debug("~On CssMasterDateServiceImpl getRiderMap");
    	long time = System.currentTimeMillis();
    	if(riderGroupQeuryTS<time||riderGroupSeed == null){
    		List<CssMapRiderGroupEntity> cssMapRiderGroupList = cssMasterService.queryCssMapRiderGroup();
    		if(cssMapRiderGroupList!=null){
    			riderGroupSeed = new ArrayList<RiderMapBean>();
    			
    			for(CssMapRiderGroupEntity p : cssMapRiderGroupList){
    				riderGroupSeed.add(new RiderMapBean(p.getRiderCode(),p.getRiderGroup(),p.getPolicyType()));
    			}
    			
    		}
    		riderGroupQeuryTS = time + REFRESH_PERIOD;
    	}
    	
    	if(riderGroupSeed!=null&&!riderGroupSeed.isEmpty()){
    		for(RiderMapBean bean : riderGroupSeed){
    			if(bean.getRiderCode().equals(riderCode)&&bean.getPolicyType().equals(policyType)){
    				return bean.getRiderGroup();
    			}
    		}
    	}
    	return null;
    }


	@Override
	public Map<String, List<SubDistrictBean>> getActiveSubDistrict() throws Exception {
		LOG.debug("~On CssMasterDateServiceImpl getActiveSubDistrict");
    	long time = System.currentTimeMillis();
    	if(subDistrictQeuryTS<time||subDistrictSeed == null){
    		List<ThailandZipcodeEntity> distrinct = cssMasterService.queryThailandZipCodeByDisCode("A");
    		if(distrinct!=null){
    			subDistrictSeed = new HashMap<String, List<SubDistrictBean>>();
    			List<SubDistrictBean> tmpList;
    			String key;
    			for(ThailandZipcodeEntity d : distrinct){
    				key = String.format("p%sd%s",d.getProvinceAs400(), d.getDistrictAs400());
    				tmpList = subDistrictSeed.get(key);
    				if(tmpList ==null){
    					tmpList = new ArrayList<SubDistrictBean>();
    					subDistrictSeed.put(key, tmpList);
    				}
    				tmpList.add(new SubDistrictBean(d.getDistrictAs400(),d.getSubdistrictCode(),d.getSubdistrict(),d.getZipcode()));
    			}
    		}
    		subDistrictQeuryTS = time + REFRESH_PERIOD;
    	}
    	
    	return subDistrictSeed;
	}
	@Override
    public CssConstantBean getDowloadAllow()throws Exception{
    	LOG.debug("~On CssMasterDateServiceImpl getDowloadAllow");
//    	long time = System.currentTimeMillis();
//    	if(allowDownloadQeuryTS<time||provinceSeed == null){
    		CssConstantEntity entity = cssMasterService.queryCssConstantEntityByKey(ALLOW_DOWNLOAD);
    		if(entity!=null){
    			allowDownloadSeed = new CssConstantBean();
    			allowDownloadSeed.setKey(entity.getKey());
    			allowDownloadSeed.setAllowDownloadCert(entity.getValue1());
    			allowDownloadSeed.setAllowDownloadNoti(entity.getValue2());
    		}
//    		allowDownloadQeuryTS = time + REFRESH_PERIOD;
//    	}
    	
    	return allowDownloadSeed;
    }

	@Override
	public List<BankBean> getBank() throws Exception {
		LOG.debug("~On CssMasterDateServiceImpl getBank");
    	List<BankEntity> bankList = cssMasterService.queryBank();
    	List<BankBean> bankBeanList = new ArrayList<BankBean>();
    	for (BankEntity b : bankList) {
    		BankBean bb = new BankBean();
			PropertyUtils.copyProperties(bb, b);
			bankBeanList.add(bb);
		}
    	return bankBeanList;
	}


	@Override
	public boolean isExistEmail(String email, String username) throws Exception {
		LOG.debug("~On CssMasterDateServiceImpl isExistEamil");
		return cssMasterService.isExistEamil(email,username);
	}

	@Override
	public boolean isExistMobile1(String mobile1, String username) throws Exception {
		LOG.debug("~On CssMasterDateServiceImpl isExistMobile1");
		return cssMasterService.isExistMobile1(mobile1,username);
	}


	@Override
	public Map<String, String> getOrderIdAndInvoiceNo(String mode, String key)
			throws Exception {
		LOG.debug("~On CssMasterDateServiceImpl getOrderIdAndInvoiceNo");
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		CssTxnSequenceEntity seq = cssMasterService.querySeqByModeAndKey(mode, key);
		if(seq != null){
			String orderId = seq.getKey() + String.format("%06d", seq.getSeq());
			String invoiceNo = prefixInvoiceNo + orderId;
			
			resultMap.put("orderId", orderId);
			resultMap.put("invoiceNo", invoiceNo);
		}
		
		return resultMap;
	}


	@Override
	public CssConstantEntity getConstant(String key) throws Exception {
		LOG.debug("~On CssMasterDateServiceImpl getConstant");
		return cssMasterService.queryCssConstantEntityByKey(key);
	}


	@Override
	public CssCommonDataEntity getCommonDataEmail(String keyGroup,
			String keyCode) throws Exception {
		LOG.debug("~On CssMasterDateServiceImpl : getCommonDataEmail");
		return cssMasterService.queryCssCommonDataEmail(keyGroup, keyCode);
	}


	@Override
	public List<CssCommonDataEntity> getCommonDataCCEmail(String keyGroup,
			String keyCode) throws Exception {
		LOG.debug("~On CssMasterDateServiceImpl : getCommonDataCCEmail");
		return cssMasterService.queryCssCommonDataCCEmailBy(keyGroup, keyCode);
	}
}
