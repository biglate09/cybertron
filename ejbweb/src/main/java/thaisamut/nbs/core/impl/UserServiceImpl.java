package thaisamut.nbs.core.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.cxf.common.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import thaisamut.nbs.core.model.UserEntity;
import thaisamut.nbs.core.remote.UserService;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
@Component("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService
{
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private EntityManager em;

    @Override
    public UserEntity find(String username)
    {
        try
        {
            TypedQuery<UserEntity> query = em.createNamedQuery("UserEntity.findByUsername", UserEntity.class);

            query.setParameter("username", username);

            return query.getSingleResult();
        }
        catch (NoResultException ignored)
        {
            if (LOG.isTraceEnabled())
            {
                LOG.trace(new StrBuilder("User '").append(username)
                        .append("' not found")
                        .toString());
            }
        }
        catch (NonUniqueResultException ignored)
        {
        	TypedQuery<UserEntity> query = em.createNamedQuery("UserEntity.findByUsername", UserEntity.class);

            query.setParameter("username", username);
          
            List<UserEntity> resultList =  query.getResultList();
        	if(!CollectionUtils.isEmpty(resultList)){
        		for (UserEntity user : resultList) {
        			remove(user);
				}
        	}
        }

        return null;
    }

    @Override
    public void add(final UserEntity user)
    {
        user.setCreatedDate(new Date());

        em.persist(user);
    }

    @Override
    public UserEntity merge(final UserEntity user)
    {
        return em.merge(user);
    }

    @Override
    public void remove(final UserEntity user)
    {
        em.remove(user);
    }

}
