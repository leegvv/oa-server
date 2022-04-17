package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Meeting;
import net.arver.oa.pojo.gen.MeetingExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * MeetingMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface MeetingMapper extends BaseMapper<Meeting, Long, MeetingExample> {

    /**
     * 根据条件查询我的会议列表.
     * @param params 查询参数
     * @return 会议列表
     */
    List<HashMap<String, Object>> searchMyMeetingListByPage(HashMap<String, Object> params);
}