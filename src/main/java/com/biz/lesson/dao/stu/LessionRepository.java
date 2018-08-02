package com.biz.lesson.dao.stu;

import com.biz.lesson.model.stu.Lession;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 该 repository 主要实现对学科管理的操作。
 * @author kevin zhao
 * @date 2018/7/27
 */
@Repository
public interface LessionRepository extends PagingAndSortingRepository<Lession, Integer>,
        CrudRepository<Lession, Integer> {
    /**
     * 根据学科 id 更新学科名称
     * @param subName
     * @param id
     */
    @Modifying
    @Query("update #{#entityName} lession set lession.subName = :subName where lession.id = :id")
    void modify(@Param("subName") String subName, @Param("id") Integer id);

    /**
     * 根据学科 id 更新选修人数。
     * @param perNum
     * @param lessionId
     */
    @Modifying
    @Query("update #{#entityName} lession set lession.perNum = :perNum where lession.id = :lessionId")
    void modifyPerNum(@Param("perNum") Integer perNum, @Param("lessionId") Integer lessionId);

    @Modifying
    @Query("update #{#entityName} lession set lession.score = :score where lession.id = :lessionId")
    void modifyScore(@Param("score") Float oriStuScore, @Param("lessionId") Integer lessionId);
}
