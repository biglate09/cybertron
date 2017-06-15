package thaisamut.nbs.struts.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.ldap.LDAPConnectionService;
import thaisamut.cybertron.ejbweb.model.CssLoginLogEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.jboss.security.auth.spi.LdapAuthenticateFailureException;
import thaisamut.pantheon.servlet.ad.LDAPLoginErrorUtil;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public class AuthAction extends JsonAction
{
	public static final String SES_FORWARD_REQUEST_URI_FOR_LOGIN = "forwardedRequestUriForLogin";
    public static final String SES_ONCE_LOGIN_ERROR = "onceError";
    public static final String SES_LOGIN_ERROR_DETAIL = "loginErrorDetail";
	@Autowired LDAPConnectionService ldapService;
	@Autowired CssMemberService cssMemberService;

    public String login()
        throws Exception
    {
        String forwardUri = (String) requestAttributes.get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (forwardUri != null) {
            //Put to session
            sessionAttributes.put(SES_FORWARD_REQUEST_URI_FOR_LOGIN, forwardUri);
        }

        return SUCCESS;
    }
    

    public String logout()
    {
        try
        {
        	// Set logout log
        	CssLoginLogEntity lastLoginEntity = cssMemberService.findLastLoginLog(getLoginName());
        	if(null != lastLoginEntity){
        		lastLoginEntity.setLogoutDate(new Date());
        		cssMemberService.updateCssLoginLog(lastLoginEntity);
        	}
        	
            request.logout();
            request.getSession().invalidate();
        }
        catch (ServletException ignored) { }

        return SUCCESS;
    }
    
    public String loginNew() throws Exception
    {
        String forwardUri = (String) requestAttributes.get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (forwardUri != null) {
            //Put to session
            sessionAttributes.put(SES_FORWARD_REQUEST_URI_FOR_LOGIN, forwardUri);
        }

        return SUCCESS;
    }
    public String internalServerError() throws Exception {
        return SUCCESS;
    }

    public String loginError() throws IOException {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				sessionAttributes.put(SES_ONCE_LOGIN_ERROR, Boolean.TRUE);

				// extract error details
				Exception foundLoginException = (Exception) org.jboss.security.SecurityContextAssociation
						.getContextInfo("org.jboss.security.exception");

				thaisamut.pantheon.servlet.ad.LDAPLoginErrorUtil.LDAP_CAUSE_CODE cause = thaisamut.pantheon.servlet.ad.LDAPLoginErrorUtil
						.parseLoginError(foundLoginException);

				LDAPLoginErrorUtil.LdapDetailError ldapCause = CssLDAPLoginErrorUtil
						.parseLoginErrorMessage(foundLoginException);

				String errorCauseText = "";
				if (cause == LDAPLoginErrorUtil.LDAP_CAUSE_CODE.CODE_UNKNOWN) {
					errorCauseText = ldapCause.getLocalizedMessage();
				} else {
					errorCauseText = cause.getMsg();
				}

				if (!errorCauseText.equalsIgnoreCase("ACCOUNT_LOCKED")) {
					errorCauseText = checkAccountLocked(errorCauseText, foundLoginException);
				}
				sessionAttributes.put(SES_ONCE_LOGIN_ERROR, !StringUtils.isBlank(errorCauseText));
				sessionAttributes.put(SES_LOGIN_ERROR_DETAIL, errorCauseText);
				result.setData(errorCauseText);
			}
		}).run();
    }
    
    public String checkAccountLocked(String errorCauseText, Exception foundLoginException){
		Throwable throwable = foundLoginException.getCause();
		LdapAuthenticateFailureException ldapEx = ((LdapAuthenticateFailureException) throwable);
		if (null != ldapEx && StringUtils.isNotBlank(ldapEx.getUsername())) {
			if (ldapService.isAccountLocked(ldapEx.getUsername())) {
				errorCauseText = "ACCOUNT_LOCKED";
			}
		}
		return errorCauseText;
    }

    public String getForwardedUri() {
        return (String) sessionAttributes.get(SES_FORWARD_REQUEST_URI_FOR_LOGIN);
    }

    public Boolean getHasOnceError() {
    	boolean onceLoginError = (Boolean) sessionAttributes.get(SES_ONCE_LOGIN_ERROR);
//    	sessionAttributes.remove(SES_ONCE_LOGIN_ERROR);
        return onceLoginError;
        
    }

    public String getLoginErrorDetail() {
        return (String) sessionAttributes.get(SES_LOGIN_ERROR_DETAIL);
    }
}
