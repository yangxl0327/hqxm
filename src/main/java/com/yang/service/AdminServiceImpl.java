package com.yang.service;

import com.yang.dao.AdminDao;
import com.yang.entify.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminDao adminDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin findOneByName(String name) {
        return adminDao.selectOne(new Admin().setName(name));
    }

    @Override
    public Admin findOneByUsername(String name) {
        return adminDao.selectOneByName(name);
    }

    @Override
    public List<Admin> findAllAdmin(Integer page, Integer size) {
        return adminDao.selectAllAdmin((page-1)*size,size);
    }

    @Override
    public List<Admin> findAll1() {
        return adminDao.selectAll1();
    }
}
