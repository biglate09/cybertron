/*
 * Copyrights (C) 2013 Ocean Life Insurance Public Company Limited.
 * All rights reserved.
 */


package thaisamut.commons.struts2.interceptor;

import thaisamut.commons.security.UserSession;
import thaisamut.commons.struts2.SpringUtils;

import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.SystemUtils.*;
import org.apache.commons.lang3.text.StrBuilder;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public class UserCredentialInterceptor
        extends AbstractInterceptor
{
    //~ Static fields/initializers ·············································

    private static final Logger LOG = LoggerFactory.getLogger(UserCredentialInterceptor.class);

    public static final String DEFAULT_FORBIDDEN_RESULT = "forbidden";

    //~ Instance fields ························································

    private String credential;

    private Pattern pattern;

    private String matchResult;

    private String unmatchResult;

    //~ Constructors ···························································

    /**
     * Creates a new UserCredentialInterceptor object.
     */
    public UserCredentialInterceptor()
    {
    }

    //~ Lifecycle Methods ······················································

    public void init()
    {
    }

    public void destroy()
    {
    }

    //~ Methods ································································

    public void setCredential(final String cred)
    {
        credential = StringUtils.stripToNull(cred);
    }

    public void setPattern(final String patt)
    {
        pattern = Pattern.compile(patt);
    }

    public void setMatchResult(final String t)
    {
        matchResult = StringUtils.stripToNull(t);
    }

    public void setUnmatchResult(final String t)
    {
        unmatchResult = StringUtils.stripToNull(t);
    }

    public String intercept(final ActionInvocation invocation)
            throws Exception
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        ActionContext context = ServletActionContext.getActionContext(request);
        Map<String, Object> session = context.getSession();
        UserSession user = (UserSession) session.get(UserSession.KEY);
        String result = null;

        session.put("remoteAddress", request.getRemoteAddr());

        if (LOG.isDebugEnabled())
        {
            LOG.debug("Remote Address: " + session.get("remoteAddress"));
        }

        if ((null != user) && StringUtils.isNotBlank(credential))
        {
            Map<String, Object> credentials = user.getCredentials();
            boolean matches = false;

            if (null == pattern)
            {
                boolean not = credential.startsWith("!");
                boolean exists = false;
                String cred = credential;

                if (not) cred = credential.substring(1);

                exists = StringUtils.isNotBlank((String) credentials.get(cred));

                matches = ((not && !exists) || (!not && exists));

                if (LOG.isTraceEnabled())
                {
                    LOG.trace(new StrBuilder("{ matches: ")
                            .append(matches).append(", not: ")
                            .append(not).append(", exists: ")
                            .append(exists).append(", credential: ")
                            .append(credential).append(", principal: ")
                            .append(request.getUserPrincipal())
                            .append(" }")
                            .toString());
                }
            }
            else 
            {
                //matches = pattern.matcher(credential).matches();
            	String cre = (String)credentials.get(credential);

            	if(StringUtils.isNotBlank(cre))
                {
            		matches = pattern.matcher(cre).matches();
            	}
            }

            if (matches)
            {
                if (StringUtils.isNotBlank(matchResult)) 
                    result = matchResult;
            }
            else
            {
                result = StringUtils.isBlank(unmatchResult)
                    ? DEFAULT_FORBIDDEN_RESULT 
                    : unmatchResult;
            }
        }
        else if (LOG.isTraceEnabled())
        {
            LOG.trace("No user session object found or no given credential name");
        }

        if (LOG.isTraceEnabled() && (null != user))
        {
            try
            {
                Map<String, Object> credentials = user.getCredentials();
                StrBuilder buffer = new StrBuilder(LINE_SEPARATOR)
                    .append("[ C R E D E N T I A L S ]")
                    .append(LINE_SEPARATOR);

                for (String key : credentials.keySet())
                {
                    buffer.append("\to ")
                          .append(key)
                          .append(": ")
                          .append(credentials.get(key) + "")
                          .append(LINE_SEPARATOR);
                }

                LOG.trace(buffer.toString());
            }
            catch (Exception ignored) { }
        }

        if (null == result) result = invocation.invoke();

        return result;
    }
}
