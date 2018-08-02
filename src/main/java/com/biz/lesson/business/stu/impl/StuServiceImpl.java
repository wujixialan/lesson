package com.biz.lesson.business.stu.impl;

import com.biz.lesson.business.stu.StuService;
import com.biz.lesson.dao.stu.ClazzRepository;
import com.biz.lesson.dao.stu.LessionRepository;
import com.biz.lesson.dao.stu.StuRepository;
import com.biz.lesson.model.stu.Clazz;
import com.biz.lesson.model.stu.Lession;
import com.biz.lesson.model.stu.Stu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kevin zhao
 * @date 2018/7/25
 */
@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private StuRepository stuRepository;
    @Qualifier("clazzRepository")
    @Autowired
    private ClazzRepository clazzRepository;
    @Qualifier("lessionRepository")
    @Autowired
    private LessionRepository lessionRepository;
    private static final Logger logger = LoggerFactory.getLogger(StuServiceImpl.class);


    /**
     * 添加学生信息
     *
     * @param stu
     */
    @Override
    @Transactional
    public void add(Stu stu) {
        stuRepository.save(stu);
        /**
         * 查询当前班级 id 的总人数。
         */
        Clazz clazz = clazzRepository.findOne(stu.getClazz().getId());
        /**
         * 调用 modifyPerNum() 方法，完成在添加人数的同时，班级人数加 1 操作。
         */
        clazzRepository.modifyPerNum(clazz.getPerNum() + 1, clazz.getId());
    }

    /**
     * 查询学生的信息
     *
     * @param page    ：当前页
     * @param limit   ：每页显示的记录数
     * @param stuNum
     * @param stuName
     * @param start
     * @param end
     * @return
     */
    @Override
    public Page<Stu> list(Integer page, Integer limit, String stuNum, String stuName, LocalDate start, LocalDate end) {
        PageRequest pageRequest = new PageRequest(page - 1, limit);
        Specification<Stu> spe = new Specification<Stu>() {
            @Override
            public Predicate toPredicate(Root<Stu> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.like(root.get("stuNum"), stuNum + "%"));
                predicates.add(criteriaBuilder.like(root.get("stuName"), "%" + stuName + "%"));
                predicates.add(criteriaBuilder.between(root.get("birth"), start, end));
                Predicate and = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                return and;
            }
        };

        return stuRepository.findAll(spe, pageRequest);
    }

    /**
     * 通过 id 查询用户。
     *
     * @param id
     * @return
     */
    @Override
    public Stu findById(Integer id) {
        return stuRepository.findOne(id);
    }

    /**
     * 修改学生信息。
     * stu.getStuNum(): 学号
     * stu.getStuName()：学生姓名
     * stu.getScore()：平均分
     * stu.getLession()：选课数目
     * stu.getBirth()：出生年月
     * stu.getGender()：性别
     * stu.getClazz().getId()：班级 id
     * stu.getId()：id
     *
     * @param stu
     */
    @Override
    @Transactional
    public void modify(Stu stu) {
        stuRepository.modify(stu.getStuNum(), stu.getStuName(), stu.getScore(), stu.getLession(), stu.getBirth(), stu.getGender(), stu.getClazz().getId(), stu.getId());
    }

    /**
     * 根据 id 删除某个学生信息。
     *
     * @param id
     */
    @Override
    @Transactional
    public void del(Integer id) {
        stuRepository.delete(id);
    }

    /**
     * 保存选课的相关系信息。
     *
     * @param stuId
     * @param lessionIds
     */
    @Override
    @Transactional
    public void saveStuAndLession(Integer stuId, String lessionIds) {
        String[] strings = lessionIds.split(",");
        Arrays.stream(strings).forEach(ele -> {
            Integer lessionId = Integer.parseInt(ele);
            /**
             * 保存学生和学科之间的关系。
             */
            stuRepository.saveStuAndLession(stuId, lessionId);
            /**
             * 更新学生的 lession。
             */
            Stu stu = stuRepository.findOne(stuId);
            logger.debug(stu.toString());
            stuRepository.modifyLession(stuId, stu.getLession() + strings.length);
            /**
             * 更新学科的选修人数。
             */
            Lession result = lessionRepository.findOne(lessionId);
            lessionRepository.modifyPerNum(result.getPerNum() + 1, lessionId);
        });
    }

    @Override
    public List<Object[]> chooseList(Integer id) {
        return stuRepository.chooseList(id);
    }

    /**
     * 修改学生、班级、学科的平均分。
     *
     * @param stuId     ：学生 id
     * @param lessionId ：学科 Id
     * @param score     ：成绩
     */
    @Override
    @Transactional
    public void modifyScore(Integer stuId, Integer lessionId, Float score) {
        /**
         * 根据学生id查找学生成绩
         */
        Stu stu = stuRepository.findOne(stuId);
        /**
         * 计算并修改学生的平均分。
         */
        Float oriStuScore = stu.getScore() * stu.getLession();
        oriStuScore = (oriStuScore + score) / stu.getLession();
        stuRepository.modifyScore(oriStuScore, stuId);
        /**
         * 根据 班级id 查询班级对象。
         */
        Clazz clazz = clazzRepository.findOne(stu.getClazz().getId());
        /**
         * 计算并更新班级的平均分。
         */
        oriStuScore = clazz.getScore() * clazz.getPerNum();
        oriStuScore = (oriStuScore + score) / clazz.getPerNum();
        clazzRepository.modifyScore(oriStuScore, clazz.getId());
        /**
         * 根据学科id查询学科对象
         */
        Lession lession = lessionRepository.findOne(lessionId);
        oriStuScore = lession.getScore() * lession.getPerNum();
        oriStuScore = (oriStuScore +score) / lession.getPerNum();
        lessionRepository.modifyScore(oriStuScore, lessionId);
    }
}
