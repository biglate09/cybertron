package thaisamut.commons.struts2.interceptor;

import thaisamut.commons.permission.PermitTimeUtils;
import thaisamut.commons.security.UserSession;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.SystemUtils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import static java.lang.String.format;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public class AccessPermitTimeInterceptor
        extends AbstractInterceptor
{
    //~ Static fields/initializers ·············································

    private static final Logger LOG = LoggerFactory.getLogger(AccessPermitTimeInterceptor.class);

    public static final String ACCESS_DENIED = "access-denied";

    //~ Methods ································································

    @Override
    public String intercept(ActionInvocation invocation)
            throws Exception
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        ActionContext context = invocation.getInvocationContext();
        Map<String, Object> session = context.getSession();
        UserSession user = (UserSession) session.get(UserSession.KEY);
        String permtime = StringUtils.EMPTY;

        if (null != user)
        {
            Map<String, Object> credentials = user.getCredentials();

            permtime = StringUtils.stripToEmpty((String) credentials.get("Access Permit Time"));
        }

        if (LOG.isDebugEnabled())
        {
            drawGraph(request.getUserPrincipal().getName(), permtime);
        }

        if (StringUtils.isBlank(permtime)
            || PermitTimeUtils.isPermitted(permtime))
        {
            return invocation.invoke();
        }

        ((SessionMap) session).invalidate();
        request.logout();

        return ACCESS_DENIED;
    }

    private void drawGraph(String loginID, String permitTime)
    {
        StrBuilder buffer = new StrBuilder(LINE_SEPARATOR);

        buffer.append("A C C E S S · P E R M I T · T I M E")
              .append(LINE_SEPARATOR)
              .append("User: ").append(loginID)
              .append(LINE_SEPARATOR)
              .append("Encoded Text: ")
              .append(PermitTimeUtils.normalizeAsString(permitTime))
              .append(PermitTimeUtils.drawGraph(permitTime));

        LOG.debug(buffer.toString());
    }
}
