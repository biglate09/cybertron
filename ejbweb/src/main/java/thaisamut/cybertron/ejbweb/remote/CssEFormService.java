package thaisamut.cybertron.ejbweb.remote;

import java.util.Date;
import java.util.List;

import thaisamut.cybertron.ejbweb.model.CssPetitionPolicyEntity;
import thaisamut.cybertron.ejbweb.model.CssPetitionRequestEntity;

public interface CssEFormService {
	
	public void saveCssPetitionRequest(CssPetitionRequestEntity entity) throws Exception;
	public List<CssPetitionRequestEntity> findCssPetitionRequestByIdNoAndRequestDateAndRequestType(String idNo, Date requestDate, String requestType) throws Exception;
	public CssPetitionPolicyEntity findCssPetitionPolicyByRequestIdAndPolicyNoAndRequestDate(Long requestId, String policyNo, Date requestDate) throws Exception;
}
