package thaisamut.css.ldap;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPUnitTest {
	public static String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
	public static String MY_HOST = "ldap://177.17.11.30:10389";
	public static String MGR_DN = "uid=admin,ou=system";
	public static String MGR_PW = "css123";
	public static String status = "";

	public static void main(String[] args) throws Exception {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
		env.put(Context.PROVIDER_URL, MY_HOST);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, MGR_DN);
		env.put(Context.SECURITY_CREDENTIALS, MGR_PW);
		DirContext ctx = new InitialDirContext(env);
		
		// name
		String name = "uid=3600800288547,ou=Users,dc=css,dc=co,dc=th";
		
		// filter
		String filter = "(objectclass=*)";
		
		// searchControls
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setTimeLimit(30000);
		String[] attrIDs = { "pwdAccountLockedTime", "pwdFailureTime", "givenName", "userPassword" };
		searchControls.setReturningAttributes(attrIDs);
		
		NamingEnumeration<?> namingEnum = ctx.search(name, filter, searchControls);
		while (namingEnum.hasMore()) {
			SearchResult result = (SearchResult) namingEnum.next();
			Attributes attrs = result.getAttributes();
			if (attrs.get("pwdAccountLockedTime") != null) {
				//System.out.println(attrs.get("pwdAccountLockedTime"));
				
				SimpleDateFormat dateFormatFrom = new SimpleDateFormat("yyyyMMddHHmmss.SSSXXX",Locale.ENGLISH);
				dateFormatFrom.setTimeZone(TimeZone.getTimeZone("GMT"));
				
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
				
				// Lock time
				String strPwdAccountLockedTime = (String) attrs.get("pwdAccountLockedTime").get(0);     
				Calendar c1 = Calendar.getInstance();
				c1.setTime(dateFormatFrom.parse(strPwdAccountLockedTime));
				System.out.println("Locked time "+sdf.format(c1.getTime()));
				
				// Unlock time
				Calendar c2 = Calendar.getInstance();
				c2.setTime(dateFormatFrom.parse(strPwdAccountLockedTime));
				c2.add(Calendar.SECOND, Integer.parseInt("900")); // Lock time + 15 minutes 
				String strPwdAccountUnLockedTime = sdf.format(c2.getTime());
				System.out.println("Unlocked time " + strPwdAccountUnLockedTime);
				
				
				Date dteCurrentDateTime = new Date();
				String strCurrentDateTime = sdf.format(dteCurrentDateTime);
				
				System.out.println("Current " + strCurrentDateTime);
				
				if (strCurrentDateTime.compareTo(strPwdAccountUnLockedTime)>0) {
					System.out.println("Status ---> unlocked!!!");
				}else{
					System.out.println("Status ---> locked!!!");
				}
			}
			/*if (attrs.get("pwdFailureTime") != null) {
				System.out.println(attrs.get("pwdFailureTime"));
			}
			if (attrs.get("givenName") != null) {
				System.out.println(attrs.get("givenName"));
			}
			if (attrs.get("userPassword") != null) {
				System.out.println(attrs.get("userPassword"));
			}*/
			
			
			/*ModificationItem[] mods = new ModificationItem[2];
			Attribute mod0 = new BasicAttribute("pwdAccountLockedTime");
			Attribute mod1 = new BasicAttribute("pwdFailureTime");
			mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, mod0);
			mods[1] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, mod1);
			ctx.modifyAttributes(name, mods);*/
		}
		namingEnum.close();
        ctx.close();
	}
}
