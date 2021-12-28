package net.arver.oa.pojo.gen;

import java.io.Serializable;

/**
 * 会议表.
 * 由MybatisGenerator自动生成请勿修改
 */
public class BaseMeeting implements Serializable {
    /**
     * 主键.
     */
    private Long id;

    /**
     * UUID.
     */
    private String uuid;

    /**
     * 会议题目.
     */
    private String title;

    /**
     * 创建人ID.
     */
    private Long creatorId;

    /**
     * 日期.
     */
    private java.time.LocalDate date;

    /**
     * 开会地点.
     */
    private String place;

    /**
     * 开始时间.
     */
    private java.time.LocalTime start;

    /**
     * 结束时间.
     */
    private java.time.LocalTime end;

    /**
     * 会议类型（1在线会议，2线下会议）.
     */
    private Integer type;

    /**
     * 会议内容.
     */
    private String desc;

    /**
     * 工作流实例ID.
     */
    private String instanceId;

    /**
     * 状态（1未开始，2进行中，3已结束）.
     */
    private Integer status;

    /**
     * 创建时间.
     */
    private java.time.LocalDateTime createTime;

    /**
     * 参与者.
     */
    private String members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public java.time.LocalDate getDate() {
        return date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public java.time.LocalTime getStart() {
        return start;
    }

    public void setStart(java.time.LocalTime start) {
        this.start = start;
    }

    public java.time.LocalTime getEnd() {
        return end;
    }

    public void setEnd(java.time.LocalTime end) {
        this.end = end;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public java.time.LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.time.LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}