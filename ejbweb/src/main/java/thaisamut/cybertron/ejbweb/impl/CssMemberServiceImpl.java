package thaisamut.cybertron.ejbweb.impl;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import thaisamut.cybertron.ejbweb.model.CssAddressSendEntity;
import thaisamut.cybertron.ejbweb.model.CssAddressTempEntity;
import thaisamut.cybertron.ejbweb.model.CssLoginLogEntity;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;

@Component("cssMemberServiceImpl")
@Transactional
public class CssMemberServiceImpl implements CssMemberService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CssMemberServiceImpl.class);

	@Autowired
	private EntityManager em;
	
	private static final String GEN_TOKEN_MODE = "1";
	private static final String GEN_REF_MODE = "2";
	private static final String GEN_OTP_MODE = "3";
	
	private static final String MAP_EMAIL_CONFIRM_SIGNUP_METHOD = "0";
	private static final String MAP_EMAIL_RESET_PASSWORD_METHOD = "1";
	private static final String MAP_EMAIL_GET_USER_ID_METHOD = "2";
	private static final String MAP_EMAIL_CHANGE_ADDRESS_METHOD = "3";
	private static final String MAP_EMAIL_CHANGE_PASSWORD_METHOD = "4";
	
	private static final String EMAIL_ENTER = "<br/>";
	private static final String EMAIL_DOUNLE_ENTER = "<br/><br/>";
	private static final String EMAIL_ENTER_NEW_PARAGRAPH = "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	
	@Override
	public Boolean checkEmail(String email) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : checkEmail");
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT EMAIL  ");
		sql.append("FROM CSS_MEMBER   ");
		sql.append("WHERE EMAIL = :email  ");
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("email", email);
		return (!CollectionUtils.isEmpty(query.getResultList()));
	}

	@Override
	public Boolean addMember(CssMemberEntity cssMemberEntity) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : addMember");
		}
		try{
			em.persist(cssMemberEntity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public Boolean updateMember(CssMemberEntity cssMemberEntity) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : updateMember");
		}
		try{
			em.merge(cssMemberEntity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public CssMemberEntity findMemberByEmail(String email) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : findMemberByEmail");
		}
		try {
			List<CssMemberEntity> resultList = em.createNamedQuery("CssMemberEntity.findMemberByEmail",CssMemberEntity.class)
					.setParameter("email", email).getResultList();
			if(!CollectionUtils.isEmpty(resultList)){
				return resultList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean addResetPasswordInfo(CssResetPasswordEntity cssResetPasswordEntity) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : addResetPasswordInfo");
		}
		try{
			em.persist(cssResetPasswordEntity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public CssResetPasswordEntity findResetPasswordByToken(String token) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : findResetPasswordByToken");
		}
		try {
			return em.createNamedQuery("CssResetPasswordEntity.findResetPasswordInfoByToken",CssResetPasswordEntity.class)
					.setParameter("token", token).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void removeResetPasswordInfoByEntity(CssResetPasswordEntity entity) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : removeResetPasswordInfoByEntity");
		}
		try {
			String jpql = "delete from "+CssResetPasswordEntity.class.getName()+" o where o.id="+entity.getId();
			em.createQuery(jpql).executeUpdate();
			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public CssMemberEntity findMemberByCardId(String cardId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : findMemberByCardId");
		}
		try {
			List<CssMemberEntity> resultList =  em.createNamedQuery("CssMemberEntity.findMemberByCardId",CssMemberEntity.class)
					.setParameter("cardNo", cardId).getResultList();
			if(!CollectionUtils.isEmpty(resultList)){
				return resultList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public CssMemberEntity findMemberByUserName(String userName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : findMemberByUsername");
		}
		try {
			List<CssMemberEntity> memberList = em.createNamedQuery("CssMemberEntity.findMemberByUsername",CssMemberEntity.class)
					.setParameter("username", userName).getResultList();
			if(!CollectionUtils.isEmpty(memberList)){
				if(memberList.size() > 1){
					int maxLength = memberList.size()-1;
					for (int i=0; i< maxLength; i++) {
						String jpql = "delete from "+CssMemberEntity.class.getName()+" o where o.id="+memberList.get(i).getId();
						em.createQuery(jpql).executeUpdate();
						em.flush();
					}
				}
				return memberList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public CssMemberEntity findMemberByTelNo(String telNo) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : findMemberByTelNo");
		}
		try {
			return em.createNamedQuery("CssMemberEntity.findMemberByTelNo",CssMemberEntity.class)
					.setParameter("telNo", telNo).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Long addTokenInfoReturnId(CssResetPasswordEntity cssResetPasswordEntity) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : addTokenInfoReturnId");
		}
		try{
			em.persist(cssResetPasswordEntity);
			return cssResetPasswordEntity.getId();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public CssResetPasswordEntity findTokenById(Long id) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : findTokenById");
		}
		try {
			return em.createNamedQuery("CssResetPasswordEntity.findTokenById",CssResetPasswordEntity.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Boolean updateToken(CssResetPasswordEntity cssResetPasswordEntity) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : updateToken");
		}
		try{
			em.merge(cssResetPasswordEntity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String processAddTokenForOtp(String refNo, String otp, String username, Date expireDate) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssMemberServiceImpl : processAddTokenForOtp ");
		}
		// Set value for token entity
		CssResetPasswordEntity tokenEntity = new CssResetPasswordEntity();
		tokenEntity.setUsername(username);
		tokenEntity.setToken(refNo.concat(":").concat(otp));
		
		// Expire in 15 minutes
		tokenEntity.setExpireDate(expireDate);
		Long id = addTokenInfoReturnId(tokenEntity);
		
		return null != id ? String.valueOf(id) : StringUtils.EMPTY;
		
	}
	
	@Override
	public Boolean processAddTokenForEmail(String sessionId, String userId){
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssMemberServiceImpl : processAddTokenForEmail ");
		}
		Boolean addMemberWaiting = false;
		try {
			// Add token(member info) for check
			CssMemberEntity member = findMemberByUserName(userId);
			CssResetPasswordEntity waitingInfo = new CssResetPasswordEntity();
			waitingInfo.setUsername(member.getUsername());
			waitingInfo.setToken(sessionId);
			
			// Expire date in 1 month
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			waitingInfo.setExpireDate(cal.getTime());
			addResetPasswordInfo(waitingInfo);
			
			addMemberWaiting = true;
		} catch (Exception e) {
			return addMemberWaiting;
		}
		return addMemberWaiting;
	}

	@Override
	public String nextSessionId(String mode) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssMemberServiceImpl : nextSessionId ");
		}
		StringBuilder sb = new StringBuilder();
		char[] chars = null;
		int length = 0;
		if(mode.equals(GEN_TOKEN_MODE)){
			chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
			length = 40;
		}else if(mode.equals(GEN_REF_MODE)){
			chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			length = 4;
		}else if(mode.equals(GEN_OTP_MODE)){
			int randomNumber = new Random().nextInt((1000000 - 100000) + 1) + 100000;
			return String.valueOf(randomNumber).substring(0, 6);
		}
		if(null == chars){
			throw new Exception();
		}
		Random random = new Random();
		for (int i = 0; i < length; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}

	@Override
	public String getEmailContent(String sessionId, String method, String baseUrl, String username) throws Exception{
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssMemberServiceImpl : getEmailContent ");
		}
		StringBuilder content = new StringBuilder();
		
		CssMemberEntity member = findMemberByUserName(username);
		
		// Content Header
		String fullname = (StringUtils.isNotBlank(member.getFirstName()) && StringUtils
				.isNotBlank(member.getLastName())) ? "คุณ"
				.concat(member.getFirstName()).concat(" ")
				.concat(member.getLastName()) : member.getFullname();
		content.append("เรียน ").append(fullname).append(EMAIL_ENTER_NEW_PARAGRAPH);

		// Content Body
		if (method.equalsIgnoreCase(MAP_EMAIL_CONFIRM_SIGNUP_METHOD)){
			String link = baseUrl.concat("pub/page/complete/index.html?sessionId=".concat(sessionId));
			content.append("บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน)");
			content.append("ขอต้อนรับท่านสู่บริการ OceanLife iService ข้อมูลบัญชีผู้ใช้งาน (User ID) ของท่านคือ").append(EMAIL_ENTER);
			content.append("บัญชีผู้ใช้งาน (User ID) : ").append(username).append(EMAIL_ENTER_NEW_PARAGRAPH);
			content.append("กรุณายืนยันการสมัครใช้บริการ OceanLife iService โดยคลิก ");
			content.append("<a href=\"".concat(link).concat("\">").concat(link).concat("</a>"));
			content.append(" เมื่อทำการยืนยันการสมัครแล้ว ท่านสามารถเข้าบริการ OceanLife iService");
			content.append("เพื่อตรวจสอบข้อมูลส่วนตัวและรายละเอียดของผลิตภัณฑ์ของท่าน").append(EMAIL_ENTER_NEW_PARAGRAPH);
			content.append("ทางบริษัทฯ ขอขอบพระคุณท่านเป็นอย่างสูงที่ให้ความไว้วางใจ");
			content.append("ในการทำประกันชีวิตกับบริษัทฯ ท่านสามารถติดตามข่าวสารดีๆ ");
			content.append("จากไทยสมุทรได้ที่ www.facebook.com/oceanlifepage").append(EMAIL_ENTER_NEW_PARAGRAPH);
		} else if (method.equalsIgnoreCase(MAP_EMAIL_RESET_PASSWORD_METHOD)){
			content.append("บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอแจ้งให้ท่านทราบว่า ตามที่ท่านได้แจ้งลืมรหัสผ่าน (Password) ในการทำธุรกรรมของบริการ OceanLife iService บริษัทฯ ขอแจ้งลิงค์เพื่อรีเซ็ตรหัสผ่าน (Password) ให้ท่านทราบ ดังนี้").append(EMAIL_DOUNLE_ENTER);  
			content.append("ลิงค์รีเซ็ตรหัสผ่าน (Password) : <a href=\"".concat(baseUrl).concat("pub/page/changepassword.html?sessionId=".concat(sessionId)).concat("\">").concat(baseUrl).concat("pub/page/changepassword.html?sessionId=").concat(sessionId).concat("</a>")).append(EMAIL_ENTER_NEW_PARAGRAPH); 
			content.append("ทั้งนี้ ลิงค์รีเซ็ตรหัสผ่าน (Password)  ที่บริษัทฯ ส่งให้ท่านในครั้งนี้  บริษัทฯ ใคร่ขอให้ท่านเข้าสู้ลิงค์รีเซ็ตรหัสผ่าน (Password) และทำการเปลี่ยนรหัสผ่าน (Password) เพื่อความปลอดภัยในการใช้บริการ OceanLife iService บริษัทฯ ขอให้ท่านเก็บรหัสผ่าน (Password) ไว้ในที่ปลอดภัย และถือเป็นความลับเฉพาะตัวเท่านั้น").append(EMAIL_ENTER_NEW_PARAGRAPH);  
			content.append("ลิงค์รีเซ็ตรหัสผ่าน (Password) จะมีอายุการใช้งาน 7 วันนับตั้งแต่วันที่ท่านได้รับอีเมลฉบับนี้ หากท่านไม่ทำการ Login เข้าระบบ เพื่อรีเซ็ตรหัสผ่าน (Password) ภายใน 7 วัน ลิงค์รีเซ็ตรหัสผ่าน (Password) จะหมดอายุลงทันที").append(EMAIL_ENTER_NEW_PARAGRAPH);
		} else if (method.equalsIgnoreCase(MAP_EMAIL_GET_USER_ID_METHOD)){
			content.append("บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอแจ้งให้ท่านทราบว่า ตามที่ท่านแจ้งขอให้บริษัทฯ ทำการจัดส่งบัญชีผู้ใช้งาน (User ID) ในการทำธุรกรรมของบริการ OceanLife iService บริษัทฯ ขอแจ้งบัญชีผู้ใช้งาน (User ID) ของท่านให้ทราบ ดังนี้").append(EMAIL_DOUNLE_ENTER);        
			content.append("บัญชีผู้ใช้งาน (User ID) : ").append(username).append(EMAIL_ENTER);                                                                            
			content.append("ท่านสามารถใช้บัญชีผู้ใช้งาน (User ID) นี้ เข้าสู่บริการ OceanLife iService ได้ทันที").append(EMAIL_ENTER_NEW_PARAGRAPH);    
		} else if (method.equalsIgnoreCase(MAP_EMAIL_CHANGE_PASSWORD_METHOD)){
			content.append("บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอแจ้งให้ท่านทราบว่า ท่านได้ดำเนินการเปลี่ยนรหัสผ่าน (Password) ของบริการ OceanLife iService เรียบร้อยแล้ว ").append(EMAIL_ENTER_NEW_PARAGRAPH);
		}  else if (method.equalsIgnoreCase(MAP_EMAIL_CHANGE_ADDRESS_METHOD)){
			content.append("บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ขอแจ้งให้ท่านทราบว่า ท่านได้ดำเนินการแก้ไขข้อมูลการติดต่อของบริการ OceanLife iService เรียบร้อยแล้ว ตามข้อมูลที่แสดงดังนี้").append(EMAIL_DOUNLE_ENTER);
			
			content.append("เลขที่กรมธรรม์  1216435            แบบประกัน  เมคชัวร์ 10/2").append(EMAIL_ENTER);
			content.append("ที่อยู่   170/74-83   อาคารโอเชี่ยนทาวเวอร์ 1  ชั้น 27   ถนน รัชดาภิเษก").append(EMAIL_ENTER);
			content.append("แขวง คลองเตย").append(EMAIL_ENTER);
			content.append("เขต คลองเตย").append(EMAIL_ENTER);
			content.append("จังหวัด กรุงเทพมหานคร   ").append(EMAIL_ENTER);          
			content.append("รหัสไปรษณีย์  10110").append(EMAIL_ENTER);
			content.append("หมายเลขโทรศัพท์มือถือ 1   0817777889").append(EMAIL_ENTER);
			content.append("หมายเลขโทรศัพท์มือถือ 2   -").append(EMAIL_ENTER);
			content.append("หมายเลขโทรศัพท์บ้าน –                  ต่อ –").append(EMAIL_ENTER);
			content.append("หมายเลขโทรศัพท์ที่ทำงาน –             ต่อ –").append(EMAIL_ENTER);
			content.append("อีเมล  oceanlife@gmail.com").append(EMAIL_ENTER);
			content.append("--------------------------------------------------------------------------").append(EMAIL_ENTER);
			
			content.append("เลขที่กรมธรรม์  1214989            แบบประกัน  เมคมันนี่ 2/1").append(EMAIL_ENTER);
			content.append("ที่อยู่   170/74-83   อาคารโอเชี่ยนทาวเวอร์ 1  ชั้น 27   ถนน รัชดาภิเษก").append(EMAIL_ENTER);
			content.append("แขวง คลองเตย").append(EMAIL_ENTER);
			content.append("เขต คลองเตย").append(EMAIL_ENTER);
			content.append("จังหวัด กรุงเทพมหานคร").append(EMAIL_ENTER);             
			content.append("รหัสไปรษณีย์  10110").append(EMAIL_ENTER);
			content.append("หมายเลขโทรศัพท์มือถือ 1   0817777889").append(EMAIL_ENTER);
			content.append("หมายเลขโทรศัพท์มือถือ 2   -").append(EMAIL_ENTER);
			content.append("หมายเลขโทรศัพท์บ้าน –                  ต่อ –").append(EMAIL_ENTER);
			content.append("หมายเลขโทรศัพท์ที่ทำงาน –             ต่อ –").append(EMAIL_ENTER);
			content.append("อีเมล  oceanlife@gmail.com").append(EMAIL_ENTER);
			content.append("--------------------------------------------------------------------------").append(EMAIL_ENTER);
		} 
		
		content.append("หากท่านต้องการสอบถามข้อมูลเพิ่มเติม หรือมีข้อสงสัยใดๆ  กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์  โทร: 0-2207-8888 ทุกวันจันทร์ - วันศุกร์ เวลา 8.00 น. - 18.00 น. และวันเสาร์ เวลา 8.00 – 17.00 น. อีเมล: info@ocean.co.th");
		
		// Content Footer
		content.append(EMAIL_DOUNLE_ENTER);
		content.append("ขอแสดงความนับถือ").append(EMAIL_ENTER);
		content.append("บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน)").append(EMAIL_ENTER);
		content.append("เว็บไซต์ www.ocean.co.th").append(EMAIL_ENTER);
		content.append("เฟสบุ๊ค www.facebook.com/oceanlifepage").append(EMAIL_DOUNLE_ENTER).append(EMAIL_DOUNLE_ENTER);
		
		// Content Remark
		content.append("<font size=\"2\">หมายเหตุ").append(EMAIL_ENTER);
		if (method.equalsIgnoreCase(MAP_EMAIL_CONFIRM_SIGNUP_METHOD)){
			content.append("- อีเมลฉบับนี้เป็นการแจ้งข้อมูลโดยอัตโนมัติ เพื่อยืนยันการสมัครสมาชิกบริการ OceanLife iService ของท่านที่ได้ดำเนินการเรียบร้อยแล้ว กรุณาอย่าตอบกลับอีเมลฉบับนี้").append(EMAIL_ENTER);
		}else if (method.equalsIgnoreCase(MAP_EMAIL_GET_USER_ID_METHOD)){
			content.append("- อีเมลฉบับนี้เป็นการแจ้งข้อมูลโดยอัตโนมัติ เพื่อการยืนยันการขอรับบัญชีผู้ใช้งาน (User ID) ของท่าน ในบริการ OceanLife iService ที่ได้ดำเนินการเรียบร้อยแล้ว กรุณาอย่าตอบกลับอีเมลฉบับนี้").append(EMAIL_ENTER);
		} else if (method.equalsIgnoreCase(MAP_EMAIL_RESET_PASSWORD_METHOD)){
			content.append("- อีเมลฉบับนี้เป็นการแจ้งข้อมูลโดยอัตโนมัติ เพื่อการยืนยันการขอรับรหัสผ่าน (Password) ของท่าน ในบริการ OceanLife iService ที่ได้ดำเนินการเรียบร้อยแล้ว กรุณาอย่าตอบกลับอีเมลฉบับนี้").append(EMAIL_ENTER);
		}else if (method.equalsIgnoreCase(MAP_EMAIL_CHANGE_PASSWORD_METHOD)){
			content.append("- อีเมลฉบับนี้เป็นการแจ้งข้อมูลโดยอัตโนมัติ เพื่อการยืนยันการเปลี่ยนรหัสผ่าน (Password) ของท่าน ในบริการ OceanLife iService ที่ได้ดำเนินการเรียบร้อยแล้ว กรุณาอย่าตอบกลับอีเมลฉบับนี้").append(EMAIL_ENTER);
		}else if (method.equalsIgnoreCase(MAP_EMAIL_CHANGE_ADDRESS_METHOD)){
			content.append("- อีเมลฉบับนี้เป็นการแจ้งข้อมูลโดยอัตโนมัติ เพื่อยืนยันการแก้ไขข้อมูลการติดต่อในกรมธรรม์ของท่าน ในบริการ OceanLife iService ที่ได้ดำเนินการเรียบร้อยแล้ว กรุณาอย่าตอบกลับอีเมลฉบับนี้").append(EMAIL_ENTER);
		}
		content.append("- หากท่านไม่ได้ทำรายการตามรายละเอียดข้างต้น ");
		content.append("หรือหากพบว่ามีข้อมูลหรือสิ่งผิดปกติใดๆที่ไม่ถูกต้อง โปรดติดต่อบริษัทฯ").append(EMAIL_ENTER);
		content.append("- บริษัทฯ ไม่มีนโยบายในการติดต่อ ท่านไม่ว่าผ่านช่องทางใดๆ ");
		content.append("เพื่อสอบถามหรือขอข้อมูลเฉพาะ เช่น บัญชีผู้ใช้งาน (User ID) และ/หรือ รหัสผ่าน ");
		content.append("(Password) สำหรับการใช้บริการ OceanLife iService หากพบบุคคลใดกระทำการดังกล่าว");
		content.append("โปรดแจ้งกับทางบริษัทฯ และให้ข้อมูลเพื่อให้เจ้าหน้าที่ของทางบริษัทฯ ทำการตรวจสอบ ");
		content.append("เพื่อความปลอดภัยของท่าน</font>");
		
		return content.toString();
	}

	@Override
	public Boolean isDuplicateUser(String userId, String idCard) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssMemberServiceImpl : isDuplicateUser ");
		}
		if(StringUtils.isNotBlank(userId)){
			CssMemberEntity entity = findMemberByUserName(userId);
			if(null != entity){
				return true;
			}
		}
		if(StringUtils.isNotBlank(idCard)){
			CssMemberEntity entity = findMemberByCardId(idCard);
			if(null != entity){
				return true;
			}
		}
		return false;
	}
	@Override
	public void updateCssAddressTemp(List<CssAddressTempEntity> addressTemp) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssMemberServiceImpl : updateCssAddressTemp ");
		}
		if(addressTemp!=null){
			CssAddressTempEntity oldEntity;
			for(CssAddressTempEntity entity : addressTemp){
				oldEntity = findCssAddressTempByCondition(entity.getPolicyNo(),entity.getPolType());
				entity.setUpdateDate(new Date());
				if(oldEntity!=null){
					entity.setId(oldEntity.getId());
					entity.setCreateDate(oldEntity.getCreateDate());
					entity.setCreateBy(oldEntity.getCreateBy());
					em.merge(entity);
				}else{
					em.persist(entity);
				}
			}
		}
		
	}
	@Override
	public CssAddressTempEntity findCssAddressTempByCondition(String policyNo,String policyType)throws Exception{
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssMemberServiceImpl : findCssAddressTempByCondition ");
		}
		LOG.debug("~On CssMasterServiceImpl : queryProvince");
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssAddressTempEntity o "
				+ "WHERE (o.sendStatus = 'N' or o.sendStatus is null) and o.policyNo = :policyNo and o.polType = :policyType";
		Query query = em.createQuery(sql, CssAddressTempEntity.class);
		query.setParameter("policyNo", policyNo);
		query.setParameter("policyType", policyType);
		List<CssAddressTempEntity> list =  query.getResultList();
		return list!=null&&!list.isEmpty()?list.get(0):null;
	}

	@Override
	public List<CssAddressTempEntity> getCssAddressTempsByToken(String token) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssAddressTempEntity o WHERE o.token = :token";
		Query query = em.createQuery(sql, CssAddressTempEntity.class);
		query.setParameter("token", token);
		return query.getResultList();
	}

	@Override
	public List<CssAddressTempEntity> queryCssAddressTempEntityByUserName(String username,String status) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssAddressTempEntity o WHERE o.createBy = :username and o.sendStatus = :status";
		Query query = em.createQuery(sql, CssAddressTempEntity.class);
		query.setParameter("username", username);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<CssAddressTempEntity> queryCssAddressTempEntityNotSendByUserNameAndPolicy(String policyNo, String username) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssAddressTempEntity o WHERE o.sendStatus in ('N','P') and o.createBy = :username and o.policyNo = :policyNo order by o.updateDate desc";
		Query query = em.createQuery(sql, CssAddressTempEntity.class);
		query.setParameter("username", username);
		query.setParameter("policyNo", policyNo);
		return query.getResultList();
	}

	@Override
	public List<CssAddressTempEntity> queryCssAddressTempEntityByUserNameNotStataus(String username, String status) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssAddressTempEntity o WHERE o.createBy = :username and o.sendStatus <> :status";
		Query query = em.createQuery(sql, CssAddressTempEntity.class);
		query.setParameter("username", username);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<CssAddressTempEntity> queryCssAddressTempEntityNotSendStatus() throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssAddressTempEntity o WHERE o.sendStatus = 'N'";
		Query query = em.createQuery(sql, CssAddressTempEntity.class);
		return query.getResultList();
	}

	@Override
	public void updateCssAddressTempEntity(CssAddressTempEntity cssAddressTempEntity) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : updateCssAddressTempEntity");
		}
		em.merge(cssAddressTempEntity);
	}

	@Override
	public List<CssAddressSendEntity> queryCssAddressSendEntityNotSendStatus() throws Exception {
		LOG.debug("~On CssMemberServiceImpl : queryCssAddressSendEntityNotSendStatus");
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssAddressSendEntity o WHERE (o.sendStatus in ('S','F') or o.sendStatus is null)";
		Query query = em.createQuery(sql, CssAddressSendEntity.class);
		return query.getResultList();
	}

	@Override
	public CssAddressTempEntity queryCssAddressTempEntityByKey(Long refId) throws Exception {
		return em.find(CssAddressTempEntity.class, refId);
	}

	@Override
	public void updateCssAddressSendEntity(CssAddressSendEntity cssAddressSendEntity) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : updateCssAddressSendEntity");
		}
		em.merge(cssAddressSendEntity);
	}

	@Override
	public void clearExpiredToken() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : clearExpiredToken");
		}
		List<CssResetPasswordEntity> resultList = em.createNamedQuery("CssResetPasswordEntity.findExpiredToken",CssResetPasswordEntity.class).getResultList();
		if(!CollectionUtils.isEmpty(resultList)){
			for (CssResetPasswordEntity bean : resultList) {
				removeResetPasswordInfoByEntity(bean);
			}
		}
		
	}

	@Override
	public Boolean addCssLoginLog(CssLoginLogEntity logEntity) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : addCssLoginLog");
		}
		try{
			em.persist(logEntity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public Boolean updateCssLoginLog(CssLoginLogEntity logEntity) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : addCssLoginLog");
		}
		try{
			em.merge(logEntity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public CssLoginLogEntity findLastLoginLog(String username) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : findLoginLogByUsername");
		}
		try {
			List<CssLoginLogEntity> resultList = em.createNamedQuery("CssLoginLogEntity.findLoginLogByUsername",CssLoginLogEntity.class)
					.setParameter("username", username).getResultList();
			if(!CollectionUtils.isEmpty(resultList)){
				return resultList.get(0);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	@Override	
	public List<CssAddressTempEntity> queryCssAddressTempEntityByPolicyAndNotSendStatus(String policyNo, String status) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssAddressTempEntity o WHERE o.policyNo = :policyNo and o.sendStatus <> :status";
		Query query = em.createQuery(sql, CssAddressTempEntity.class);
		query.setParameter("policyNo", policyNo);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<CssAddressTempEntity> queryCssAddressTempEntityByPolicyAndSendStatus(String policyNo, String status) throws Exception {
		String sql = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssAddressTempEntity o WHERE o.policyNo = :policyNo and o.sendStatus = :status";
		Query query = em.createQuery(sql, CssAddressTempEntity.class);
		query.setParameter("policyNo", policyNo);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public void updateCssMember(CssMemberEntity member, String userName) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~On CssMemberServiceImpl : updateMember");
		}
		try{
			member.setUpdateBy(userName);
			member.setUpdateDate(new Date());
			em.merge(member);
		} catch (Exception e) {
		}
	}
	
}
