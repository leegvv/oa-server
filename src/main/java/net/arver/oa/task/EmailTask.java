package net.arver.oa.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 右键任务.
 * @author leegvv
 */
@Component
public class EmailTask implements Serializable {

    /**
     * java发送右键.
     */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送人地址.
     */
    @Value("${oa.email.system}")

    private String mailbox;

    /**
     * 发送右键.
     * @param message 发送内容
     */
    public void sendAsync(final SimpleMailMessage message) {
        message.setFrom(mailbox);
        javaMailSender.send(message);
    }
}
