package com.yang.service;

import com.yang.dao.ArticleDao;
import com.yang.entify.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleDao articleDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Article> findAll() {
        return articleDao.selectAll();
    }

    @Override
    public List<Article> findAllf(Integer page, Integer size) {
        page=(page-1)*size;
        return articleDao.selectByRowBounds(new Article(),new RowBounds(page,size));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Article findOne(String id) {
        return articleDao.selectByPrimaryKey(id);
    }

    @Override
    public void modifyOne(Article article) {
       articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public void addOne(Article article) {
         articleDao.insert(article);
    }

    @Override
    public void removeOne(String id) {
      articleDao.deleteByPrimaryKey(id);
    }
}
