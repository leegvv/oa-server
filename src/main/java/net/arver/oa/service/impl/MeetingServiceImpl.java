package net.arver.oa.service.impl;

import net.arver.oa.exception.ServiceException;
import net.arver.oa.mapper.MeetingMapper;
import net.arver.oa.pojo.Meeting;
import net.arver.oa.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MeetingServiceImpl.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Service
public class MeetingServiceImpl implements MeetingService {

    /**
     * 会议mapper.
     */
    @Autowired
    private MeetingMapper meetingMapper;

    @Override
    public Meeting insert(final Meeting meeting) {
        meeting.setCreateTime(LocalDateTime.now());
        final int count = meetingMapper.insert(meeting);
        if (count != 1) {
            throw new ServiceException("会议添加失败");
        }
        // todo: 开启审批工作流
        return meeting;
    }

    @Override
    public List<? extends Map> searchMyMeetingListByPage(final HashMap params) {
        final List<HashMap<String, Object>> list = meetingMapper.searchMyMeetingListByPage(params);
        final List<HashMap<String, Object>> resultList = list.stream()
                .collect(Collectors.groupingBy(item -> item.get("date").toString()))
                .entrySet().stream().map(item -> {
                    final HashMap<String, Object> resultMap = new HashMap<>();
                    resultMap.put("date", item.getKey());
                    resultMap.put("list", item.getValue());
                    return resultMap;
                }).collect(Collectors.toList());
        return resultList;
    }
}
