package net.arver.oa.dao;

import java.util.List;
import net.arver.oa.pojo.TbMeeting;
import net.arver.oa.pojo.TbMeetingExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbMeetingDao {
    long countByExample(TbMeetingExample example);

    int deleteByExample(TbMeetingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbMeeting record);

    int insertSelective(TbMeeting record);

    List<TbMeeting> selectByExample(TbMeetingExample example);

    TbMeeting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbMeeting record, @Param("example") TbMeetingExample example);

    int updateByExample(@Param("record") TbMeeting record, @Param("example") TbMeetingExample example);

    int updateByPrimaryKeySelective(TbMeeting record);

    int updateByPrimaryKey(TbMeeting record);
}