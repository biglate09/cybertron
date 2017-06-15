package thaisamut.cybertron.ejbweb.remote;

import thaisamut.cybertron.ejbweb.model.CampaignReserveEntity;

public interface CampaignReserveService {
	boolean save(int campaignId) throws Exception;
	boolean removeByReserveId(int reserveId) throws Exception;
	CampaignReserveEntity findByIdNo(int idNo) throws Exception;
}