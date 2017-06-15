package thaisamut.cybertron.ejbweb.remote;

import java.util.List;

import thaisamut.cybertron.ejbweb.model.CampaignEntity;

public interface CampaignService {
	List<CampaignEntity> findAllCampaign() throws Exception;
	List<CampaignEntity> findCampaignByProvinceId(int provinceId) throws Exception;
	CampaignEntity findCampaignDetail(int campaignId) throws Exception;
	
}
