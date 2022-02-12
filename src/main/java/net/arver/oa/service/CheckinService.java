package net.arver.oa.service;

import net.arver.oa.form.CheckinForm;
import net.arver.oa.pojo.Checkin;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到服务.
 * @author leegvv
 */
public interface CheckinService {

    /**
     * 判断是否已签到.
     * @param userId 用户id
     * @param start 开始时间
     * @param end 截止时间
     * @return 记录id
     */
    Checkin haveCheckIn(Integer userId, LocalDateTime start, LocalDateTime end);

    /**
     * 校验是否能签到.
     * @param userId 用户id
     * @param date 日期
     * @return 是否能签到
     */
    String validCanCheckIn(int userId, String date);

    /**
     * 签到记录.
     * @param checkin 签到
     * @return 签到记录
     */
    Checkin save(Checkin checkin);

    /**
     * 签到.
     * @param checkinForm 签到参数
     */
    void checkin(CheckinForm checkinForm);

    /**
     * 查询当日签到信息.
     * @param userId 用户id
     * @return 签到信息
     */
    HashMap<String, Object> searchTodayCheckin(int userId);

    /**
     * 查询签到天数.
     * @param userId 用户id
     * @return 签到天数
     */
    long searchCheckinDays(int userId);

    /**
     * 查询本周签到情况.
     * @param params params
     * @return 周签到情况
     */
    List<Map<String, Object>> searchWeekCheckin(Map<String, Object> params);

    /**
     * 查询本月签到情况.
     * @param params params
     * @return 周签到情况
     */
    List<Map<String, Object>> searchMonthCheckin(Map<String, Object> params);

}
