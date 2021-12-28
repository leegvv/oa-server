package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Meeting;
import net.arver.oa.pojo.gen.MeetingExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * MeetingMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface MeetingMapper extends BaseMapper<Meeting, Long, MeetingExample> {
}