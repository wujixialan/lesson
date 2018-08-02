package com.biz.lesson.model.stu;

import javax.persistence.*;

/**
 * @author kevin zhao
 * @date 2018/7/25
 */
@Table(name = "clazz")
@Entity
public class Clazz {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "clazz_name")
    private String clazzName;
    @Column(name = "per_num")
    private Integer perNum;
    private Float score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getPerNum() {
        return perNum;
    }

    public void setPerNum(Integer perNum) {
        this.perNum = perNum;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", clazzName='" + clazzName + '\'' +
                ", perNum=" + perNum +
                ", score=" + score +
                '}';
    }
}
