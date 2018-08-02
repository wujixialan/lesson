package com.biz.lesson.dao.stu;

import com.biz.lesson.model.stu.Stu;
import com.biz.lesson.vo.StuVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kevin zhao
 * @date 2018/7/25
 */
@Repository
public interface StuRepository extends CrudRepository<Stu, Integer>, PagingAndSortingRepository<Stu, Integer>,
        JpaRepository<Stu, Integer>, JpaSpecificationExecutor<Stu> {
    /**
     * 修改用户信息。
     *
     * @param stu
     */
    @Modifying
    @Query("update #{#entityName} stu set stu.stuNum = :stuNum, stu.stuName = :stuName, stu.score = :score," +
            " stu.lession = :lession, stu.birth = :birth, stu.gender = :gender, stu.clazz.id = :clazzId" +
            " where stu.id = :id ")
    void modify(@Param("stuNum") String stuNum,
                @Param("stuName") String stuName,
                @Param("score") Float score,
                @Param("lession") Integer lession,
                @Param("birth") LocalDate birth,
                @Param("gender") String gender,
                @Param("clazzId") Integer clazzId,
                @Param("id") Integer id);

    /**
     * 通过执行本地 sql，完成对 Stu 和 Lession 之间的关联操作。
     *
     * @param stuId：学生   id
     * @param lessionId： 课程信息的 id。
     */
    @Modifying
    @Query(value = "insert into stu_lession(stu_id, lession_id) value(?1, ?2)", nativeQuery = true)
    void saveStuAndLession(Integer stuId, Integer lessionId);

    /**
     * 根据 id 更新选修课数目
     *
     * @param stuId
     * @param lession
     */
    @Modifying
    @Query("update #{#entityName} stu set stu.lession = :lession where stu.id = :stuId")
    void modifyLession(@Param("stuId") Integer stuId, @Param("lession") Integer lession);

    @Query(value = "select stu_id, lession_id from stu_lession where stu_id = :id", nativeQuery = true)
    List<Object[]> chooseList(@Param("id") Integer id);

    @Modifying
    @Query(value = "update #{#entityName} stu set stu.score = :score where stu.id = :id")
    void modifyScore(@Param("score") Float score, @Param("id") Integer stuId);
}