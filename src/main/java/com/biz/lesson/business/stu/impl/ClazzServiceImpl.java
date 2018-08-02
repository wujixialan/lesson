package com.biz.lesson.business.stu.impl;

import com.biz.lesson.business.stu.ClazzService;
import com.biz.lesson.dao.stu.ClazzRepository;
import com.biz.lesson.model.stu.Clazz;
import com.biz.lesson.model.stu.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kevin zhao
 * @date 2018/7/27
 */
@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzRepository clazzRepository;
    /**
     * 查询所有班级信息。
     *
     * @return
     */
    @Override
    public List<Clazz> findAllClazz() {
        return (List<Clazz>) clazzRepository.findAll();
    }

    /**
     * 分页查询班级的所有信息
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Page<Clazz> list(Integer page, Integer limit) {
        PageRequest pageRequest = new PageRequest(page - 1, limit);
        return clazzRepository.findAll(pageRequest);
    }

    /**
     * 添加班级信息。
     *
     * @param clazz
     */
    @Override
    @Transactional
    public void add(Clazz clazz) {
        clazzRepository.save(clazz);
    }

    /**
     * 修改班级信息
     *
     * @param clazz
     */
    @Override
    @Transactional
    public void modify(Clazz clazz) {
        clazzRepository.modify(clazz.getClazzName(), clazz.getId());
    }

    /**
     * 根据 id 删除班级信息。
     *
     * @param id
     */
    @Override
    @Transactional
    public void del(Integer id) {
        clazzRepository.delete(id);
    }

    /**
     * 通过 id 查询班级信息。
     *
     * @param id
     * @return
     */
    @Override
    public Clazz findById(Integer id) {
        return clazzRepository.findOne(id);
    }
}
