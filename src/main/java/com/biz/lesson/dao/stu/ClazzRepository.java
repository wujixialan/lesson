package com.biz.lesson.dao.stu;

import com.biz.lesson.model.stu.Clazz;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author kevin zhao
 * @date 2018/7/27
 */
@Repository
public interface ClazzRepository extends PagingAndSortingRepository<Clazz, Integer>,
        CrudRepository<Clazz, Integer> {
    /**
     * 修改班级信息的 Repository
     *
     * @param clazzName
     * @param id
     */
    @Modifying
    @Query("update #{#entityName} clazz set clazz.clazzName = :clazzName where clazz.id = :id")
    void modify(@Param("clazzName") String clazzName, @Param("id") Integer id);

    /**
     * 根据 id 更新班级人数。
     * @param perNum
     * @param id
     */
    @Modifying
    @Query("update #{#entityName} clazz set clazz.perNum = :perNum where clazz.id = :id")
    void modifyPerNum(@Param("perNum") Integer perNum, @Param("id") Integer id);

    /**
     * 根据 班级id 更新学生的平均分
     * @param oriStuScore：计算后的平均分，
     * @param id：班级id
     */
    @Modifying
    @Query("update #{#entityName} clazz set clazz.score = :score where clazz.id = :id")
    void modifyScore(@Param("score") Float oriStuScore,@Param("id") Integer id);
}
