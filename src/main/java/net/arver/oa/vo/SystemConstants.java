package net.arver.oa.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 系统常量.
 * @author leegvv
 */
@Data
@Component
public class SystemConstants {

    /**
     * 签到开始时间.
     */
    private String attendanceStartTime;

    /**
     * 上班时间.
     */
    private String attendanceTime;

    /**
     * 签到截止时间.
     */
    private String attendanceEndTime;

    /**
     * 签退开始时间.
     */
    private String closingStartTime;

    /**
     * 下班时间.
     */
    private String closingTime;

    /**
     * 签退截止时间.
     */
    private String closingEndTime;
}
