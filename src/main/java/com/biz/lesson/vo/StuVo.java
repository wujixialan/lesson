package com.biz.lesson.vo;

/**
 * Created by zxg on 2018/7/30.
 */
public class StuVo {
    private Integer stuId;
    private Integer lessionId;

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getLessionId() {
        return lessionId;
    }

    public void setLessionId(Integer lessionId) {
        this.lessionId = lessionId;
    }

    @Override
    public String toString() {
        return "StuVo{" +
                "stuId=" + stuId +
                ", lessionId=" + lessionId +
                '}';
    }
}
