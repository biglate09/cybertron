package thaisamut.cybertron.ejbweb.remote;

import java.util.Date;
import java.util.List;

import thaisamut.cybertron.ejbweb.model.CssAddressSendEntity;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.model.CssLoginLogEntity;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;


public interface CssMemberService {
	Boolean checkEmail(String email);
	Boolean addMember(CssMemberEntity cssMemberEntity);
	Boolean updateMember(CssMemberEntity cssMemberEntity);
	CssMemberEntity findMemberByEmail(String email);
	Boolean addResetPasswordInfo(CssResetPasswordEntity cssResetPasswordEntity);
	CssResetPasswordEntity findResetPasswordByToken(String token);
	void removeResetPasswordInfoByEntity(CssResetPasswordEntity entity);
	CssMemberEntity findMemberByCardId(String cardId);
	CssMemberEntity findMemberByUserName(String UserName);
	CssMemberEntity findMemberByTelNo(String telNo);
	Long addTokenInfoReturnId(CssResetPasswordEntity cssResetPasswordEntity);
	CssResetPasswordEntity findTokenById(Long id);
	Boolean updateToken(CssResetPasswordEntity cssResetPasswordEntity);
	String processAddTokenForOtp(String refNo, String otp, String username, Date expireDate) throws Exception ;
	Boolean processAddTokenForEmail(String sessionId, String userId);
	String nextSessionId(String mode) throws Exception;
	String getEmailContent(String sessionId, String method, String baseUrl, String username) throws Exception;
	Boolean isDuplicateUser(String userId, String idCard);
	void updateCssAddressTemp(List<CssAddressTempEntity> addressTemp)throws Exception;
	CssAddressTempEntity findCssAddressTempByCondition(String policyNo, String policyType)throws Exception;
	List<CssAddressTempEntity> getCssAddressTempsByToken(String token)throws Exception;
	List<CssAddressTempEntity> queryCssAddressTempEntityByUserName(String username,String status)throws Exception;
	List<CssAddressTempEntity> queryCssAddressTempEntityNotSendByUserNameAndPolicy(String policyNo, String username)throws Exception;
	List<CssAddressTempEntity> queryCssAddressTempEntityByUserNameNotStataus(String username, String status)throws Exception;
	List<CssAddressTempEntity> queryCssAddressTempEntityNotSendStatus()throws Exception;
	void updateCssAddressTempEntity(CssAddressTempEntity bean)throws Exception;
	List<CssAddressSendEntity> queryCssAddressSendEntityNotSendStatus()throws Exception;
	CssAddressTempEntity queryCssAddressTempEntityByKey(Long refId)throws Exception;
	void updateCssAddressSendEntity(CssAddressSendEntity send)throws Exception;
	void clearExpiredToken();
	Boolean addCssLoginLog(CssLoginLogEntity logEntity);
	Boolean updateCssLoginLog(CssLoginLogEntity logEntity);
	CssLoginLogEntity findLastLoginLog(String username);
	List<CssAddressTempEntity> queryCssAddressTempEntityByPolicyAndNotSendStatus(String policyNo, String string)throws Exception;
	List<CssAddressTempEntity> queryCssAddressTempEntityByPolicyAndSendStatus(String policyNo, String string)throws Exception;
	void updateCssMember(CssMemberEntity member, String userName)throws Exception;
}
