package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Holidays;
import net.arver.oa.pojo.gen.HolidaysExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

/**
 * HolidaysMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface HolidaysMapper extends BaseMapper<Holidays, Integer, HolidaysExample> {

    /**
     * 查询今天是否是节假日.
     * @return 记录id
     */
    Integer searchTodayIsHoliday();

    /**
     * 查询是否节假日.
     * @param map map
     * @return 是否节假日
     */
    ArrayList<String> searchHolidaysInRange(Map<String, Object> map);

}