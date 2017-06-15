package thaisamut.commons.security;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public abstract class AbstractUserSession implements UserSession
{
    @Override public String toString()
    {
        return getLoginID();
    }
}
