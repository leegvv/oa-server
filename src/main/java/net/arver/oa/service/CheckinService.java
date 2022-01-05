package net.arver.oa.service;

import net.arver.oa.pojo.Checkin;

import java.time.LocalDateTime;

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
}
