package thaisamut.nbs.struts.action;

import javax.security.auth.login.FailedLoginException;

import thaisamut.jboss.security.auth.spi.LdapAuthenticateFailureException;
import thaisamut.pantheon.servlet.ad.LDAPLoginErrorUtil;

import com.novell.ldap.LDAPException;

public class CssLDAPLoginErrorUtil extends LDAPLoginErrorUtil {
	
	  public static LdapDetailError parseLoginErrorMessage(Exception ex) {

	        if (ex != null && ex.getCause() != null) {
	            Throwable cause = ex.getCause();

	            if (cause instanceof LDAPException) {
	                LDAPException ldapEx = ((LDAPException) cause);
	                return new LdapDetailError(ldapEx.getResultCode(),
	                        ldapEx.getMessage(),
	                        ldapEx.getLocalizedMessage());
	            }else if(cause instanceof LdapAuthenticateFailureException){
	            	LdapAuthenticateFailureException ldapEx = ((LdapAuthenticateFailureException) cause);
	            	return new LdapDetailError(0,
	                        null != ldapEx.getAccountStateError() ? ldapEx.getAccountStateError().getMessage():ldapEx.getResultCode().name(),
	                        null != ldapEx.getAccountStateError() ? ldapEx.getAccountStateError().getMessage():ldapEx.getResultCode().name());
	            }else if(ex instanceof FailedLoginException){
	            	FailedLoginException ldapEx = ((FailedLoginException) ex);
	            	return new LdapDetailError(0,
	                        ldapEx.getMessage(),
	                        ldapEx.getMessage());
	            }
	        }

	        return LDAP_ERROR_UNKNOWN;
	    }
	  
}
