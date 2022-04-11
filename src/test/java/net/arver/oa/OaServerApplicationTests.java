package net.arver.oa;

import cn.hutool.core.util.IdUtil;
import net.arver.oa.pojo.MessageEntity;
import net.arver.oa.pojo.MessageRefEntity;
import net.arver.oa.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class OaServerApplicationTests {

    @Autowired
    private MessageService messageService;

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

}
