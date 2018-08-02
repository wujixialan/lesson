package com.biz.lesson.business.stu.impl;

import com.biz.lesson.business.stu.LessionSerivce;
import com.biz.lesson.dao.stu.LessionRepository;
import com.biz.lesson.model.stu.Lession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kevin zhao
 * @date 2018/7/28
 */
@Service
public class LessionSerivceImpl implements LessionSerivce {

    @Qualifier("lessionRepository")
    @Autowired
    private LessionRepository lessionRepository;
    private static final Logger logger = LoggerFactory.getLogger(LessionSerivceImpl.class);

    /**
     * 通过分页显示所有的课程数据
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Page<Lession> findByPage(Integer page, Integer limit) {
        PageRequest pageRequest = new PageRequest(page - 1, limit);
        return lessionRepository.findAll(pageRequest);
    }

    /**
     * 根据 id 查询课程信息。
     *
     * @param id
     * @return
     */
    @Override
    public Lession findById(Integer id) {
        return lessionRepository.findOne(id);
    }

    /**
     * 保存一个课程信息。
     *
     * @param lession
     */
    @Override
    @Transactional
    public void add(Lession lession) {
        lessionRepository.save(lession);
    }

    /**
     * 修改课程信息
     *
     * @param lession
     */
    @Override
    @Transactional
    public void modify(Lession lession) {
        logger.debug(lession.toString());
        lessionRepository.modify(lession.getSubName(), lession.getId());
    }

    /**
     * 根据 Id 删除课程信息
     *
     * @param id
     */
    @Override
    @Transactional
    public void del(Integer id) {
        lessionRepository.delete(id);
    }

    @Override
    public List<Lession> findAll() {
        return (List<Lession>) lessionRepository.findAll();
    }
}
