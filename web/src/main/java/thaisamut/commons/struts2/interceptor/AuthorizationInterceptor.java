package thaisamut.commons.struts2.interceptor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.commons.permission.Function;
import thaisamut.commons.security.UserSession;
import thaisamut.commons.struts2.SpringUtils;
import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.model.CssUser;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public class AuthorizationInterceptor extends AbstractInterceptor {
	// ~ Static fields/initializers
	// ·············································

	private static final Logger LOG = LoggerFactory
			.getLogger(AuthorizationInterceptor.class);

	// ~ Instance fields
	// ························································

	private Set<Integer> requiredPermissions = Collections.EMPTY_SET;
	private Set<Integer> beneath = Collections.EMPTY_SET;

	// ~ Constructors
	// ···························································

	/**
	 * Creates a new AuthorizationInterceptor object.
	 */
	public AuthorizationInterceptor() {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("%1$s created.",
					AuthorizationInterceptor.class.getSimpleName()));
		}
	}

	// ~ Methods
	// ································································

	public void init() {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("%1$s initialized.",
					AuthorizationInterceptor.class.getSimpleName()));
		}
	}

	public void destroy() {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("%1$s destroyed.",
					AuthorizationInterceptor.class.getSimpleName()));
		}
	}

	public void setRequiredPermissions(final String perms) {
		if (StringUtils.isNotBlank(perms)) {
			requiredPermissions = new HashSet<Integer>();

			for (String t : StringUtils.stripToEmpty(perms).split("\\s*,\\s*"))
				requiredPermissions.add(new Integer(t));
		}
	}

	public void setAnyPermissionsBeneath(final String perms) {
		if (StringUtils.isNotBlank(perms)) {
			beneath = new HashSet<Integer>();

			for (String t : StringUtils.stripToEmpty(perms).split("\\s*,\\s*"))
				beneath.add(new Integer(t));
		}
	}

	private UserSession getUserSession(HttpSession session) {
		UserSession user = (UserSession) session.getAttribute(UserSession.KEY);

		if (null == user) {
			session.setAttribute(UserSession.KEY,
					user = SpringUtils.getBean(UserSession.class));
		}

		return user;
	}

	private UserSession getUserSession(Map<String, Object> session) {
		UserSession user = (UserSession) session.get(UserSession.KEY);

		if (null == user) {
			session.put(UserSession.KEY,
					user = SpringUtils.getBean(UserSession.class));
		}

		return user;
	}

	protected boolean isAllowed(final ActionInvocation invocation) {
		HttpServletRequest request = ServletActionContext.getRequest();
		ActionProxy proxy = invocation.getProxy();
		String namespace = proxy.getNamespace();
		String action = proxy.getActionName();
		ActionContext context = ServletActionContext.getActionContext(request);
		Map<String, Object> session = context.getSession();
		ActionMapping mapping = ServletActionContext.getActionMapping();
		String extension = mapping.getExtension();
		UserSession user = getUserSession(request.getSession());
		Set<Integer> perms = (Set<Integer>) user.getCredentials().get(
				"Permissions");

		if (null == perms) {
			perms = Collections.emptySet();
		}

		if (!requiredPermissions.isEmpty()) {
			for (Integer perm : requiredPermissions)
				if (!perms.contains(perm))
					return false;
		} else if (!beneath.isEmpty()) {
			for (Integer perm : beneath) {
				if (perms.contains(perm))
					return true;

				Function func = Function.locate(getFunctionTree(), perm);

				if (null != func) {
					for (Integer p : Function.getIDs(func.getCascadeChildren()))
						if (perms.contains(p))
							return true;
				}
			}

			return false;
		} else if (LOG.isDebugEnabled()) {
			LOG.debug(String
					.format("No permissions are required to perform action %1$s/%2$s.%3$s",
							namespace, action, extension));
		}

		return true;
	}

	private Function getFunctionTree() throws BeansException {
		return SpringUtils.getBean(Function.class);
	}

	public String intercept(final ActionInvocation invocation) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("Executing %1$s::intercept()",
					AuthorizationInterceptor.class.getSimpleName()));
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		ActionContext context = ServletActionContext.getActionContext(request);
		ActionProxy proxy = invocation.getProxy();
		Map<String, Object> session = context.getSession();
		ActionMapping mapping = ServletActionContext.getActionMapping();
		String namespace = proxy.getNamespace();
		String action = proxy.getActionName();
		String login_id = null;
		String result = null;

		if (null == request.getUserPrincipal()) {
			throw new Exception(new StrBuilder(
					"Guest access is not allowed for ").append(namespace)
					.append("/").append(action).append(".")
					.append(mapping.getExtension()).toString());
		}

		login_id = request.getUserPrincipal().getName();

		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format(
					"Checking required permissions for user '%1$s'...",
					login_id));
		}

		if (isAllowed(invocation)) {
			LOG.debug(String.format(
					"User '%1$s' is allowed to perform action %2$s/%3$s.%4$s.",
					login_id, namespace, action, mapping.getExtension()));
			//result = invocation.invoke();
			// active user check
			
			if (LOG.isDebugEnabled()) {
				LOG.debug(String.format(
						"Checking active status for user '%1$s'...", login_id));
			}

			if (isActivate(invocation)) {
				LOG.debug(String.format(
						"User '%1$s' is allowed to perform action %2$s/%3$s.%4$s.",
						login_id, namespace, action, mapping.getExtension()));

				result = invocation.invoke();
			} else {
				LOG.warn(String
						.format("User '%1$s' confirm activate account action %2$s/%3$s.%4$s, redirecting to target '%5$s'.",
								login_id, namespace, action,
								mapping.getExtension(), (result = "activatedUser")));
			}
			// active user check
		} else {
			LOG.warn(String
					.format("User '%1$s' has no sufficient permissions to perform action %2$s/%3$s.%4$s, redirecting to target '%5$s'.",
							login_id, namespace, action,
							mapping.getExtension(), (result = "forbidden")));
		}

		
		return result;
	}

	@Autowired
	CssMemberService cssMemberService;
	@Autowired
	DataWarehouseService dataWarehouseService;

	private boolean isActivate(ActionInvocation invocation) {
		HttpServletRequest request = ServletActionContext.getRequest();
		CssUser cssUser = (CssUser) request.getSession().getAttribute(CssUser.SESSION_CSS_USER);
		if(cssUser == null){
			try {
				UserSession user = getUserSession(request.getSession());
				CssMemberEntity member = cssMemberService.findMemberByUserName(user.getLoginID());
				if(member!=null){
					// update mobile no
					try{
						if(dataWarehouseService.isMobileNeedUpdate(member)){
							cssMemberService.updateCssMember(member,user.getLoginID());
//							request.getSession().setAttribute("error_message", 
//									String.format("มีการแก้ไขเบอร์โทรศัพท์ที่ใช้ติดต่อกับระบบ OceanLife iService เป็น %s หากไม่ต้องการ",member.getTelNo()));
						}
					}catch(Exception ex){
						LOG.error(ex.getMessage(),ex);
					}
					cssUser = setUser( member);
					request.getSession().setAttribute(CssUser.SESSION_CSS_USER, cssUser);
				}else{
					throw new Exception("could not find CssMember");
				}
			} catch (Exception e) {
				LOG.error(String.format("AuthorizationInterceptor inActivate copy cssUser fail : %1$s",e.getMessage()),e);
			}
		}
		if (cssUser == null || !"A".equals(cssUser.getStatus())) {
			return false;
		}
		return true;
	}

	private CssUser setUser( CssMemberEntity member) {
		CssUser user =  null;
		if(member!=null){
			user = new CssUser();
			user.setUsername(member.getUsername());
			user.setFullname(member.getFullname());
			user.setCardNo(member.getCardNo());
			user.setBirthDate(member.getBirthDate());
			user.setTelNo(member.getTelNo());
			user.setEmail(member.getEmail());
			user.setStatus(member.getStatus());
			user.setCustCode(member.getCustCode());
			user.setTitleDesc(member.getTitleDesc());
			user.setFirstName(member.getFirstName());
			user.setLastName(member.getLastName());
		}
		return user;
	}
}
