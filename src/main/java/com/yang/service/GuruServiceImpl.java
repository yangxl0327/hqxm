package com.yang.service;

import com.yang.dao.GuruDao;
import com.yang.entify.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDao guruDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Guru> findAll() {
        return guruDao.selectAll();
    }

    @Override
    public void addOne(Guru guru) {

        guruDao.insert(guru);
    }

    @Override
    public void removeOne(String id) {
       guruDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateOne(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }
}
