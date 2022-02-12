package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Checkin;
import net.arver.oa.pojo.gen.CheckinExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CheckinMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface CheckinMapper extends BaseMapper<Checkin, Integer, CheckinExample> {

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
     * @param map map
     * @return 周签到情况
     */
    ArrayList<HashMap<String, String>> searchWeekCheckin(Map<String, Object> map);
}