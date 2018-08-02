package com.biz.lesson.business.stu;


import com.biz.lesson.model.stu.Stu;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kevin zhao
 * @date 2018/7/25
 */
public interface StuService {
    /**
     * 添加学生信息
     * @param stu
     */
    void add(Stu stu);

    /**
     * 查询学生的信息
     * @param page ：当前页
     * @param limit ：每页显示的记录数
     * @param stuNum
     * @param stuName
     * @param start
     * @param end
     * @return
     */
    Page<Stu> list(Integer page, Integer limit, String stuNum, String stuName, LocalDate start, LocalDate end);

    /**
     * 通过 id 查询用户。
     * @param id
     * @return
     */
    Stu findById(Integer id);

    /**
     * 修改学生信息。
     * @param stu
     */
    void modify(Stu stu);

    /**
     * 根据 id 删除某个学生信息。
     * @param id
     */
    void del(Integer id);

    /**
     * 保存选课的相关系信息。
     * @param stuId
     * @param lessionIds
     */
    @Transactional
    void saveStuAndLession(Integer stuId, String lessionIds);


    List<Object[]> chooseList(Integer id);

    /**
     * 修改学生、班级、学科的平均分。
     * @param stuId：学生 id
     * @param lessionId：学科 Id
     * @param score：成绩
     */
    @Transactional
    void modifyScore(Integer stuId, Integer lessionId, Float score);
}
