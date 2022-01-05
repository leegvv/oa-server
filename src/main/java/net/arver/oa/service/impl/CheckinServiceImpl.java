package net.arver.oa.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import net.arver.oa.mapper.CheckinMapper;
import net.arver.oa.mapper.HolidaysMapper;
import net.arver.oa.mapper.WorkdayMapper;
import net.arver.oa.pojo.Checkin;
import net.arver.oa.pojo.gen.CheckinExample;
import net.arver.oa.service.CheckinService;
import net.arver.oa.vo.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 签到service实现类.
 * @author leegvv
 */
@Service
public class CheckinServiceImpl implements CheckinService {

    /**
     * 签到mapper.
     */
    @Autowired
    private CheckinMapper checkinMapper;

    /**
     * 节假日mapper.
     */
    @Autowired
    private HolidaysMapper holidaysMapper;

    /**
     * 工作日mapper.
     */
    @Autowired
    private WorkdayMapper workdayMapper;

    /**
     * 系统常量.
     */
    @Autowired
    private SystemConstants constants;

    @Override
    public Checkin haveCheckIn(final Integer userId, final LocalDateTime start, final LocalDateTime end) {
        final CheckinExample example = new CheckinExample();
        example.createCriteria().andUserIdEqualTo(userId).andDateEqualTo(LocalDate.now())
                        .andCreateTimeBetween(start, end);
        example.setLimit(1);
        final List<Checkin> checkins = checkinMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(checkins)) {
            return checkins.get(0);
        }
        return null;
    }

    @Override
    public String validCanCheckIn(final int userId, final String date) {
        final boolean isHoliday = holidaysMapper.searchTodayIsHoliday() != null;
        final boolean isWorkday = workdayMapper.searchTodayIsWorkday() != null;
        String type = "工作日";
        if (DateUtil.date().isWeekend()) {
            type = "节假日";
        }

        if (isHoliday) {
            type = "节假日";
        } else if (isWorkday) {
            type = "工作日";
        }

        if (type.endsWith("节假日")) {
            return "节假期不需要考勤";
        } else {
            final LocalDateTime now = LocalDateTime.now();
            final String start = DateUtil.today() + " " + constants.getAttendanceStartTime();
            final String end = DateUtil.today() + " " + constants.getAttendanceEndTime();

            final LocalDateTime attendanceStart = LocalDateTimeUtil.parse(start);
            final LocalDateTime attendanceEnd = LocalDateTimeUtil.parse(end);
            if (now.isBefore(attendanceStart)) {
                return "没到上班考勤开始时间";
            } else if (now.isAfter(attendanceEnd)) {
                return "超过了上班考勤结束时间";
            } else {
                final boolean hasCheckin = haveCheckIn(userId, attendanceStart, attendanceEnd) != null;
                return hasCheckin ? "今日已经考勤，不用重复考勤" : "可以考勤";
            }
        }
    }
}
