package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Workday;
import net.arver.oa.pojo.gen.WorkdayExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

/**
 * WorkdayMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface WorkdayMapper extends BaseMapper<Workday, Integer, WorkdayExample> {

    /**
     * 查询当天是否是工作日.
     * @return 记录id
     */
    Integer searchTodayIsWorkday();

    /**
     * 查询是否工作日.
     * @param map map
     * @return 是否工作日
     */
    ArrayList<String> searchWorkdayInRange(Map map);
}