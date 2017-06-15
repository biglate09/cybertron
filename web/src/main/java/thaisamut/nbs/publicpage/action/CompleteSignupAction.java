package thaisamut.nbs.publicpage.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.email.CssEmailConnectionService;
import thaisamut.css.sms.CssSMSConnectionService;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.core.model.UserEntity;
import thaisamut.nbs.core.remote.UserService;
import thaisamut.nbs.struts.action.JsonAction;

public class CompleteSignupAction extends CssJsonAction {
	private static final long serialVersionUID = -5056536390018371130L;

	private static final Logger LOG = LoggerFactory.getLogger(CompleteSignupAction.class);
    
	@Autowired CssMemberService cssMemberService;
	
	@Autowired private UserService userService;
	
	public String sessionId = null;
	
    public String index() throws Exception{
    	if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CompleteSignupAction : index ");
		}
    	setSessionId(null == request.getParameter("sessionId") ? StringUtils.EMPTY : request.getParameter("sessionId"));
    	return SUCCESS;
    }
	
	public String completesignup() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CompleteSignupAction : completesignup ");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				//checkTokenExpire();
				Map<String, Object> data = new HashMap<String, Object>();
				Boolean success = false;
				String resultsMsg = StringUtils.EMPTY;
				String sessionId = request.getParameter("sessionId");
				CssResetPasswordEntity userInfo = cssMemberService.findResetPasswordByToken(sessionId);
				
				if(null != userInfo){
					CssMemberEntity member = cssMemberService.findMemberByUserName(userInfo.getUsername());
					if(null != member){
						member.setStatus("A");
						member.setUpdateBy(userInfo.getUsername());
						member.setUpdateDate(new Date());
						success = cssMemberService.updateMember(member);

						if(success){
							createUser(member);
							// ลบข้อมูล Token ใน DB
							cssMemberService.removeResetPasswordInfoByEntity(userInfo);
							resultsMsg = "<h3>ท่านได้สมัครสมาชิกบริการ OceanLife iService เรียบร้อยแล้ว</h3>";
							
							// Send SMS success 
							sendSmsSuccess(member.getTelNo(), CssSMSConnectionService.MAP_SMS_METHOD_ACTIVATE);
						}else{
							resultsMsg = "เกิดข้อผิดพลาดกรุณาติดต่อผู้ดูแลระบบ";
						}
					}
				}else{
					resultsMsg = "Session ของท่านหมดอายุ กรุณาทำรายการใหม่";
				}
				data.put("resultsMsg", resultsMsg);
				data.put("success", success);
				result.setData(data);
			}
		}).run();
	}

	private void createUser(CssMemberEntity member) {
		UserEntity user = null;
		user = userService.find(member.getUsername());
		if(null == user){
			user = new UserEntity();
		}
		user.setAccessPermitTime("2147483647");
        user.setPermissions("0");

        user.setUsername(StringUtils.lowerCase(member.getUsername()));
        user.setEmail(member.getEmail());
        user.setLastLoginTime(new Date());
        user.setCreatedDate(new Date());
		userService.merge(user);
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
