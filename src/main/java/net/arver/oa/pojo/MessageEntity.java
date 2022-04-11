package net.arver.oa.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息实体.
 * @author leegvv
 */
@Data
@Document(collection = "message")
public class MessageEntity implements Serializable {

    /**
     * id.
     */
    @SuppressWarnings("checkstyle:MemberName")
    @Id
    private String _id;

    /**
     * uuid.
     */
    @Indexed(unique = true)
    private String uuid;

    /**
     * 发送人id.
     */
    @Indexed
    private Integer senderId;

    /**
     * 发送人头像.
     */
    private String senderPhoto = "https://static-1258386385.cos.ap-beijin";

    /**
     * 发送人姓名.
     */
    private String senderName;

    /**
     * 发送时间.
     */
    @Indexed
    private Date sendTime;

    /**
     * 消息.
     */
    private String msg;
}
