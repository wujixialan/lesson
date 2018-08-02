package com.biz.lesson.model.stu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author kevin zhao
 * @date 2018/7/25
 */
@Table(name = "stu")
@Entity
public class Stu {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "stu_num")
    private String stuNum;
    @Column(name = "stu_name")
    private String stuName;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth")
    private LocalDate birth;
    private Float score;
    private Integer lession;
    @ManyToOne
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;
    @JsonIgnoreProperties(value = {"stus"})
    @ManyToMany
    @JoinTable(name = "stu_lession",
            joinColumns = {@JoinColumn(name = "stu_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "lession_id", referencedColumnName = "id")}
    )
    private Set<Lession> lessions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getLession() {
        return lession;
    }

    public void setLession(Integer lession) {
        this.lession = lession;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Set<Lession> getLessions() {
        return lessions;
    }

    public void setLessions(Set<Lession> lessions) {
        this.lessions = lessions;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "id=" + id +
                ", stuNum='" + stuNum + '\'' +
                ", stuName='" + stuName + '\'' +
                ", gender='" + gender + '\'' +
                ", birth=" + birth +
                ", score=" + score +
                ", lession=" + lession +
                ", clazz=" + clazz +
                ", lessions=" + lessions +
                '}';
    }
}
