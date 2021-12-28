package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Checkin;
import net.arver.oa.pojo.gen.CheckinExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * CheckinMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface CheckinMapper extends BaseMapper<Checkin, Integer, CheckinExample> {
}