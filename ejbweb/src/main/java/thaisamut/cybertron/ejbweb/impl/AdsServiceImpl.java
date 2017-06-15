package thaisamut.cybertron.ejbweb.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import thaisamut.cybertron.ejbweb.remote.AdsService;

@Component("adsServiceImpl")
@Transactional
public class AdsServiceImpl implements AdsService{

    @Autowired
    private EntityManager em;
}
