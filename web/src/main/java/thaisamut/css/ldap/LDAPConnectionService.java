package thaisamut.css.ldap;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.novell.ldap.LDAPConnection;

@Component
public class LDAPConnectionService {
    private static final Logger LOG = LoggerFactory.getLogger(LDAPConnectionService.class);

	private String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private String url;
	private String authentication;
	private String userDn;
	private String password;
	private String ouUser;

	@Autowired
	public LDAPConnectionService(
			@Value("#{systemProperties['ldapProperties']}") String ldapProperties) {
		LOG.debug("constructer to LDAP bind");
		try {

			LOG.debug("Bind successful");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	// Attributes to be set for new entry creation
	public boolean addEntry(LDAPUser ldapUser) {
		boolean flag = false;
		DirContext dirContext = null;
		try {
			dirContext = getDirContext();

			// ObjectClass attributes
			Attribute oc = new BasicAttribute("objectClass");
			oc.add("top");
			oc.add("person");
			oc.add("organizationalPerson");
			oc.add("simpleSecurityObject");
			oc.add("inetOrgPerson");

			Attributes entry = new BasicAttributes();
			entry.put(new BasicAttribute("sn", ldapUser.getUserId() ));
			entry.put(new BasicAttribute("cn",ldapUser.getCardNumber()));
			entry.put(new BasicAttribute("givenName", ldapUser.getFullName()));
			entry.put(new BasicAttribute("mail", StringUtils.defaultString(ldapUser.getEmail()).toLowerCase()));
			entry.put(new BasicAttribute("mobile", ldapUser.getMobilePhone()));
			entry.put(new BasicAttribute("userPassword",hash(ldapUser.getPassword(),ldapUser.getUserId())));
			entry.put(oc);
			String entryDN = String.format("uid=%s,%s",ldapUser.getUserId(),ouUser);
			LOG.debug("entryDN :" + entryDN);
			dirContext.createSubcontext(entryDN, entry);
			flag = true;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			return flag;
		}finally{
			if(dirContext!=null){
				try {
					dirContext.close();
				} catch (NamingException e) {
				}
			}
		}
		return flag;
	}

	public boolean updatePassword(String userId, String newPassword) {
		boolean flag = false;
		DirContext dirContext = null;
		try {
			dirContext = getDirContext();
			ModificationItem[] mods = new ModificationItem[1];

			Attribute mod0 = new BasicAttribute("userPassword", hash(
					newPassword, userId));

			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);

			dirContext.modifyAttributes(
					String.format("uid=%s,%s", userId.trim(), ouUser), mods);
			flag = true;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			return flag;
		}finally{
			if(dirContext!=null){
				try {
					dirContext.close();
				} catch (NamingException e) {
				}
			}
		}
		return flag;
	}
	
	public boolean updateEmail(String userId, String email) {
		boolean flag = false;
		DirContext dirContext = null;
		try {
			dirContext = getDirContext();

			ModificationItem[] mods = new ModificationItem[1];

			Attribute mod0 = new BasicAttribute("mail", StringUtils.defaultString(email).toLowerCase());

			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);

			dirContext.modifyAttributes(
					String.format("uid=%s,%s", userId.trim(), ouUser), mods);
			flag = true;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			return flag;
		}finally{
			if(dirContext!=null){
				try {
					dirContext.close();
				} catch (NamingException e) {
				}
			}
		}
		return flag;
	}

	private DirContext getDirContext() throws NamingException {
		Hashtable<String, String> environment = new Hashtable<String, String>();

		environment.put(Context.INITIAL_CONTEXT_FACTORY,
				INITIAL_CONTEXT_FACTORY);
		environment.put(Context.PROVIDER_URL, url);
		environment.put(Context.SECURITY_AUTHENTICATION,
				authentication);
		environment.put(Context.SECURITY_PRINCIPAL, userDn);
		environment.put(Context.SECURITY_CREDENTIALS, password);

		DirContext dirContext = new InitialDirContext(environment);
		return dirContext;
	}

	private String hash(String password, String salt) throws Exception {

		SSHA ssha = new SSHA("SHA");
		String sshaStr = "{SSHA}" + ssha.createDigest(salt, password);

		return sshaStr;

	}

	final Pattern pattern = Pattern.compile("ldaps?\\:\\/\\/(localhost|\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d*)");
	 public boolean validatePassword(String username,String inputPassword)
	    {
	        if (StringUtils.isNotBlank(username)
	            && StringUtils.isNotBlank(inputPassword))
	        {

	            try
	            {
		        	final Matcher matcher = pattern.matcher(url);
		        	if(matcher.find()){
		            String host = (String) matcher.group(1);
		            int port = Integer.parseInt((String) matcher.group(2));
		            LDAPConnection ldap = new LDAPConnection();
	                ldap.connect(host, port);


	                try
	                {

	                    	LOG.trace("Binding using DN (LDAP)");

	                        String userNameAttr = "uid";
	                        String base_dn = ouUser;

	                        String composingDn = userNameAttr + "=" + username + "," + base_dn;

	                        LOG.trace("Composing DN, and about to bind " + composingDn);

	                        ldap.bind(LDAPConnection.LDAP_V3,
	                                composingDn,
	                                inputPassword.getBytes());

	                        LOG.trace("Bound successfully.");

	                        return true;
	                }
	                finally
	                {
	                    ldap.disconnect();
	                }
		        	}
	            }
	            catch (Exception e)
	            {
	                LOG.error(e.getMessage(), e);
	            }
	            
	        }

	        return false;
	    }
	
	public Boolean isAccountLocked(String username) {
		Boolean isAccountLocked = false;
		try {
			// name
			String name = String.format("uid=%s,%s", username.trim(), ouUser);
			
			// filter
			String filter = "(objectclass=*)";
			
			// searchControls
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchControls.setTimeLimit(30000);
			String[] attrIDs = { "pwdAccountLockedTime" };
			searchControls.setReturningAttributes(attrIDs);
			
			DirContext ctx = getDirContext();
			NamingEnumeration<?> namingEnum = ctx.search(name, filter, searchControls);
			while (namingEnum.hasMore()) {
				SearchResult result = (SearchResult) namingEnum.next();
				Attributes attrs = result.getAttributes();
				if (attrs.get("pwdAccountLockedTime") != null) {
					isAccountLocked = true;
				}
			}
			namingEnum.close();
	        ctx.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return isAccountLocked;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public String getUserDn() {
		return userDn;
	}

	public void setUserDn(String userDn) {
		this.userDn = userDn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOuUser() {
		return ouUser;
	}

	public void setOuUser(String ouUser) {
		this.ouUser = ouUser;
	}
}
