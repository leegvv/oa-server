package net.arver.oa.pojo.gen;

import java.io.Serializable;

/**
 * tb_workday.
 * 由MybatisGenerator自动生成请勿修改
 */
public class BaseWorkday implements Serializable {
    /**
     * 主键.
     */
    private Integer id;

    /**
     * 日期.
     */
    private java.time.LocalDate date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public java.time.LocalDate getDate() {
        return date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }
}