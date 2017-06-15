package thaisamut.nbs.core.remote;

import thaisamut.nbs.core.model.UserEntity;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public interface UserService
{

    public UserEntity find(String username);

    public void add(UserEntity user);

    public UserEntity merge(UserEntity user);

    public void remove(UserEntity user);

}
