package com.yang.service;

import com.yang.dao.PictureDao;
import com.yang.entify.Picture;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureDao pictureDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Picture> findAll() {
        return pictureDao.selectAll();
    }

    @Override
    public void addOne(Picture picture) {
      pictureDao.insert(picture);
    }

    @Override
    public void deleteOne(String id) {
     pictureDao.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Picture findOne(String id) {
        return pictureDao.selectOne(new Picture().setId(id));
    }

    @Override
    public void modifyOne(Picture picture) {
       pictureDao.updateByPrimaryKeySelective(picture);
    }

    @Override
    public List<Picture> findAllByPage(Integer page, Integer size) {
        page = (page-1)*size;
        return pictureDao.selectByRowBounds(new Picture(),new RowBounds(page,size));
    }

    @Override
    public List<Picture> selectSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows) {
        page=(page-1)*rows;
        return pictureDao.selectSearch(searchField,searchString,searchOper,page,rows);
    }

    @Override
    public Long findTotalSearch(String searchField, String searchString, String searchOper) {
        return pictureDao.findTotalSearch(searchField,searchString,searchOper);
    }

    @Override
    public void deleteAll(List<String> list) {
        pictureDao.deleteByIdList(list);
    }
}
