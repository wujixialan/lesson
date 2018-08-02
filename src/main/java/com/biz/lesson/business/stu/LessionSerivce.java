package com.biz.lesson.business.stu;

import com.biz.lesson.model.stu.Lession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kevin zhao
 * @date 2018/7/28
 */
public interface LessionSerivce {
    /**
     * 通过分页显示所有的课程数据
     * @param page
     * @param limit
     * @return
     */
    Page<Lession> findByPage(Integer page, Integer limit);

    /**
     * 根据 id 查询课程信息。
     * @param id
     * @return
     */
    Lession findById(Integer id);

    /**
     * 保存一个课程信息。
     * @param lession
     */
    void add(Lession lession);

    /**
     * 修改课程信息
     * @param lession
     */
    void modify(Lession lession);

    /**
     * 根据 Id 删除课程信息
     * @param id
     */
    void del(Integer id);

    /**
     * 查询所有的课程。
     * @return
     */
    List<Lession> findAll();
}
