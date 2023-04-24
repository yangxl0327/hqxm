package com.yang.service;

import com.yang.dao.UserDao;
import com.yang.entify.MapVO;
import com.yang.entify.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    public List<MapVO> findByAddress() {
        return userDao.selectByAddress();
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer findCountBySexAndDay(String sex, Integer day) {
        return userDao.selectCountBySexAndDay(sex,day);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> findAll() {
        return userDao.selectAll();
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User findOne(String id) {
        return userDao.selectByPrimaryKey(id);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> findAllf(Integer page, Integer size) {
        return userDao.selectByRowBounds(new User(),new RowBounds((page-1)*size,size));
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User findOneByPhone(String phone) {
        return userDao.selectOne(new User().setPhone(phone));
    }

    @Override
    public void addOne(User user) {
      userDao.insert(user);
    }

    @Override
    public void removeOne(String id) {
     userDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateOne(User user) {
       userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public User login(String phone, String password) {
        return userDao.selectOne(new User().setPhone(phone).setPassWord(password));
    }

    @Override
    public void regist(String code) {

    }
}
