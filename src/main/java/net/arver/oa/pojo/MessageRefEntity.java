package net.arver.oa.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 用户消息.
 * @author leegvv
 */
@Data
@Document(collection = "message_ref")
public class MessageRefEntity implements Serializable {

    /**
     * id.
     */
    @SuppressWarnings("checkstyle:MemberName")
    @Id
    private String _id;

    /**
     * 消息id.
     */
    @Indexed
    private String messageId;

    /**
     * 接收人id.
     */
    @Indexed
    private Integer receiverId;

    /**
     * 已读标识.
     */
    @Indexed
    private Boolean readFlag;

    /**
     * 最新消息.
     */
    @Indexed
    private Boolean lastFlag;
}