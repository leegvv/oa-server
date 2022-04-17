package net.arver.oa;

import cn.hutool.core.util.IdUtil;
import net.arver.oa.pojo.Meeting;
import net.arver.oa.pojo.MessageEntity;
import net.arver.oa.pojo.MessageRefEntity;
import net.arver.oa.service.MeetingService;
import net.arver.oa.service.MessageService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class OaServerApplicationTests {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MeetingService meetingService;

    @Test
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            final MessageEntity message = new MessageEntity();
            message.setUuid(IdUtil.simpleUUID());
            message.setSenderId(0);
            message.setSenderName("系统消息");
            message.setMsg("这是第" + i + "条测试消息");
            message.setSendTime(new Date());
            final String id = messageService.insertMessage(message);

            final MessageRefEntity messageRef = new MessageRefEntity();
            messageRef.setMessageId(id);
            messageRef.setReceiverId(7);
            messageRef.setLastFlag(true);
            messageRef.setReadFlag(false);
            messageService.insertRef(messageRef);
        }
    }

    @Test
    void createMeetingData() {
        for (int i = 0; i < 100; i++) {
            final Meeting meeting = new Meeting();
            meeting.setId((long) i);
            meeting.setUuid(IdUtil.simpleUUID());
            meeting.setTitle("测试会议" + i);
            meeting.setCreatorId(7L);
            meeting.setDate(LocalDate.now());
            meeting.setPlace("线上会议室");
            meeting.setStart("08:30");
            meeting.setEnd("10:30");
            meeting.setType(1);
            meeting.setMembers("[7,8,9]");
            meeting.setRemark("会议研讨项目上线");
            meeting.setInstanceId(IdUtil.simpleUUID());
            meeting.setStatus(3);
            meeting.setCreateTime(LocalDateTime.now());
            meetingService.insert(meeting);
        }
    }

}
