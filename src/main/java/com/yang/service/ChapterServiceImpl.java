package com.yang.service;

import com.yang.dao.ChapterDao;
import com.yang.entify.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Override
    public List<Chapter> findAll() {
        return chapterDao.selectAll();
    }

    @Override
    public List<Chapter> findAllf(Integer page, Integer rows, String id) {
        page=(page-1)*rows;
        return chapterDao.selectByRowBounds(new Chapter().setAlbumId(id),new RowBounds(page,rows));
    }

    @Override
    public void addOne(Chapter chapter) {
       chapterDao.insert(chapter);
    }

    @Override
    public void removeOne(String id) {
      chapterDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateOne(Chapter chapter) {
     chapterDao.updateByPrimaryKeySelective(chapter);
    }
}
