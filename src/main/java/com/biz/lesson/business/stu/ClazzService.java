package com.biz.lesson.business.stu;

/**
 * @author kevin zhao
 * @date 2018/7/27
 */

import com.biz.lesson.model.stu.Clazz;
import com.biz.lesson.model.stu.Stu;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 实现 Clazz 的 Service
 */
public interface ClazzService {
    /**
     * 查询所有班级信息。
     * @return
     */
    List<Clazz> findAllClazz();

    /**
     * 分页查询班级的所有信息
     * @param page
     * @param limit
     * @return
     */
    Page<Clazz> list(Integer page, Integer limit);

    /**
     * 添加班级信息。
     * @param clazz
     */
    void add(Clazz clazz);

    /**
     * 修改班级信息
     * @param clazz
     */
    void modify(Clazz clazz);

    /**
     * 根据 id 删除班级信息。
     * @param id
     */
    void del(Integer id);

    /**
     * 通过 id 查询班级信息。
     * @param id
     * @return
     */
    Clazz findById(Integer id);
}
