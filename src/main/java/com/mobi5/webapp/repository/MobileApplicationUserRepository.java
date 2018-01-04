package com.mobi5.webapp.repository;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import com.mobi5.webapp.model.MobileApplicationUser;
import java.util.HashMap;
import java.util.Map;

public class MobileApplicationUserRepository extends AbstractRepository<MobileApplicationUser, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MobileApplicationUserRepository() {
        super(MobileApplicationUser.class);
    }

    public MobileApplicationUser findByLogin(String login) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("login", login);
        return getByNamedQuery("MobileApplicationUser.findByLogin", parameters);
    }

}
