package com.biz.lesson.model.stu;

import javax.persistence.*;
import java.util.Set;

/**
 * @author kevin zhao
 * @date 2018/7/25
 */
@Table(name = "lession")
@Entity
public class Lession {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "sub_name")
    private String subName;
    @Column(name = "per_Num")
    private Integer perNum;
    private Float score;


    @ManyToMany(mappedBy = "lessions")
    private Set<Stu> stus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Set<Stu> getStus() {
        return stus;
    }

    public void setStus(Set<Stu> stus) {
        this.stus = stus;
    }

    public Integer getPerNum() {
        return perNum;
    }

    public void setPerNum(Integer perNum) {
        this.perNum = perNum;
    }

    @Override
    public String toString() {
        return "Lession{" +
                "id=" + id +
                ", subName='" + subName + '\'' +
                ", perNum=" + perNum +
                ", score=" + score +
                '}';
    }
}
