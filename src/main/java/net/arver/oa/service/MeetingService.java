package net.arver.oa.service;

import net.arver.oa.pojo.Meeting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MeetingService.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public interface MeetingService {

    /**
     * 插入.
     *
     * @param meeting 会议
     * @return 会议
     */
    Meeting insert(Meeting meeting);

    /**
     * 根据条件查询会议列表.
     * @param params 参数
     * @return 会议列表
     */
    List<? extends Map> searchMyMeetingListByPage(HashMap params);
}
