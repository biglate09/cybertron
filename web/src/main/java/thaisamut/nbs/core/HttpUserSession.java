/*
 * Copyright (C) 2013 Ocean Life Insurance Public Company Limited.
 * All rights reserved.
 */


package thaisamut.nbs.core;

import thaisamut.commons.struts2.SpringUtils;
import thaisamut.commons.security.UserSession;
import thaisamut.commons.permission.Function;
import thaisamut.cybertron.ejbweb.model.CssLoginLogEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.core.model.UserEntity;
import thaisamut.nbs.core.remote.UserService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import javax.ejb.EJB;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
@Transactional
public class HttpUserSession implements UserSession
{
    private static final Logger LOG = LoggerFactory.getLogger(HttpUserSession.class);

    protected String loginID;

    protected String defaultPermissions;

    protected String defaultAccessPermitTime;

    protected Map<String, Object> credentials = new HashMap<String, Object>();

    @EJB(lookup="java:global/UserService", beanInterface=UserService.class)
    protected UserService userService;
    
    @EJB(lookup="java:global/CssMemberService", beanInterface=CssMemberService.class)
    protected CssMemberService cssMemberService;

    public HttpUserSession()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        Principal principal = request.getUserPrincipal();

        if (null == principal)
        {
            throw new RuntimeException("Missing user principal or user did not properly login");
        }
        else
        {
            HttpSession session = request.getSession(true);

            try
            {
                Map<String, Object> cred = (Map<String, Object>)
                        PropertyUtils.getProperty(principal, "credentials");

                if (null != cred)
                {
                    credentials.putAll(cred);
                    credentials.put("ID",
                            StringUtils.trimToEmpty((String) cred.get("ID"))
                                    .replace("-", StringUtils.EMPTY));
                }
            }
            catch (Exception e)
            {
                LOG.warn(e.getMessage(), e);
            }

            loginID = principal.getName();

            session.setAttribute(UserSession.KEY, this);
        }
    }

    public String getLoginID() { return loginID; }

    public Map<String, Object> getCredentials() { return Collections.unmodifiableMap(credentials); }

    @Override
    public Map<String, Object> getModifiableCredentials() { return credentials; }

    public String getDefaultPermissions() { return defaultPermissions; }

    public void setDefaultPermissions(String rhs) { defaultPermissions = rhs; }

    public String getDefaultAccessPermitTime() { return defaultAccessPermitTime; }

    public void setDefaultAccessPermitTime(String rhs) { defaultAccessPermitTime = rhs; }

    /**
     * update This session object, so any modified properties will be persisted
     */
    @Override
    public void updateToSessionAttribute() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession(true);
        session.setAttribute(UserSession.KEY, this);
    }

    private UserEntity locateUser(final String username)
    {
        try
        {
            return userService.find(username);
        }
        catch (NoResultException ignored) { }
        catch (Exception e) {
            LOG.error("Error when try to locateUser", e);
        }

        return null;
    }

    public void initialize()
        throws Exception
    {
        UserEntity user = locateUser(loginID);
        boolean new_user = (null == user);
        Set<Integer> permissions = null;
        Function menu = null;
        Gson gson = new GsonBuilder().serializeNulls().create();

        if (new_user)
        {
            user = new UserEntity();
            user.setAccessPermitTime(defaultAccessPermitTime);
            user.setPermissions(defaultPermissions);
        }

        user.setUsername(StringUtils.lowerCase(loginID));
        user.setEmail((String) credentials.get("Email"));
        user.setFullname((String) credentials.get("Full Name"));
        user.setLastLoginTime((Date) credentials.get("Login Since"));
        String branchCode = (String) credentials.get("Branch Code");
        user.setBranchCode(branchCode);
        user.setIdNumber((String) credentials.get("ID"));
        //TODO get employee id from AD
        //user.setEmployeeId(credentials.get("Employee ID"));

        if (new_user)
        {
            userService.add(user);
            LOG.info(new StrBuilder("Created user '").append(loginID)
                    .append("'").toString());
        }
        else
        {
            userService.merge(user);
            LOG.info(new StrBuilder("Updated user '").append(loginID)
                    .append("'").toString());
        }

        permissions = getPermissions(user.getPermissions());
        menu = Function.subset(SpringUtils.getBean(Function.class), permissions);

        credentials.put("Access Permit Time", user.getAccessPermitTime());
        credentials.put("Permissions", permissions);
        credentials.put("Menu Json", gson.toJson(menu));
        // TODO the entity class does not have employeeId yet
        // credentials.put("Employee ID", user.getEmployeeId());
        
        // Add css_lgin_log
        addCssLoginLog(loginID, (Date) credentials.get("Login Since"));
    }

    private Set<Integer> getPermissions(final String perms)
    {
        Set<Integer> result = new HashSet<Integer>();
        Function root = SpringUtils.getBean(Function.class);

        for (String i : StringUtils.stripToEmpty(perms).split("\\s*,\\s*"))
        {
            try
            {
                Integer perm = new Integer(i);
                Function func = Function.locate(root, perm);

                if (null != func)
                {
                    List<Function> children = func.getCascadeChildren();

                    result.add(perm);

                    if (null != children)
                    {
                        result.addAll(Function.getIDs(children));
                    }
                }
            }
            catch (NumberFormatException ignored) { }
        }

        return Collections.unmodifiableSet(result);
    }
    
    private void addCssLoginLog(String username, Date lastLoginTime){
    	CssLoginLogEntity logEntity = new CssLoginLogEntity();
    	logEntity.setUsername(username);
    	logEntity.setCreateDate(lastLoginTime);
    	logEntity.setCreateBy("CSS");
    	logEntity.setLoginDate(lastLoginTime);
    	logEntity.setLogoutDate(lastLoginTime);
    	cssMemberService.addCssLoginLog(logEntity);
    }
}
