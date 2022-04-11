package net.arver.oa.task;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import lombok.extern.slf4j.Slf4j;
import net.arver.oa.exception.ServiceException;
import net.arver.oa.pojo.MessageEntity;
import net.arver.oa.pojo.MessageRefEntity;
import net.arver.oa.service.MessageService;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息任务.
 * @author leegvv
 */
@Slf4j
@Component
public class MessageTask {

    /**
     * 连接工厂.
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * 消息服务.
     */
    @Autowired
    private MessageService messageService;

    /**
     * 同步发送消息.
     * @param topic topic
     * @param entity 消息实体
     */
    public void send(final String topic, final MessageEntity entity) {
        final String id = messageService.insertMessage(entity);
        try (Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false)) {
            channel.queueDeclare(topic, true, false, false, null);
            final HashMap<String, Object> map = new HashMap<>();
            map.put("messageId", id);
            final AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().headers(map).build();
            channel.basicPublish("", topic, properties, entity.getMsg().getBytes());
            log.debug("消息发送成功");
        } catch (final Exception e) {
            log.error("执行异常", e);
            throw new ServiceException("向MQ发送消息失败");
        }
    }

    /**
     * 异步发送消息.
     * @param topic topic
     * @param entity 消息实体
     */
    @Async
    public void sendAsync(final String topic, final MessageEntity entity) {
        send(topic, entity);
    }

    /**
     * 接口消息.
     * @param topic topic
     * @return 发送消息数量
     */
    public int receive(final String topic) {
        int i = 0;
        try (Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false)) {
            channel.queueDeclare(topic, true, false, false, null);
            while (true) {
                final GetResponse response = channel.basicGet(topic, false);
                if (response == null) {
                    break;
                }
                final AMQP.BasicProperties props = response.getProps();
                final Map<String, Object> headers = props.getHeaders();
                final String messageId = headers.get("messageId").toString();
                final byte[] body = response.getBody();
                final String message = new String(body);
                log.debug("从RabbitMQ接收的消息：" + message);
                final MessageRefEntity entity = new MessageRefEntity();
                entity.setMessageId(messageId);
                entity.setReceiverId(Integer.parseInt(topic));
                entity.setReadFlag(false);
                entity.setLastFlag(true);
                messageService.insertRef(entity);

                final long deliveryTag = response.getEnvelope().getDeliveryTag();
                channel.basicAck(deliveryTag, false);
                i++;
            }
            log.debug("消息发送成功");
        } catch (final Exception e) {
            log.error("执行异常", e);
            throw new ServiceException("接收MQ消息失败");
        }
        return i;
    }

    /**
     * 异步接收消息.
     * @param topic topic
     * @return 消息数量
     */
    @Async
    public int receiveAsync(final String topic) {
        return receive(topic);
    }

    /**
     * 删除队列.
     * @param topic topic
     */
    public void deleteQueue(final String topic) {
        try (Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false)) {
            channel.queueDelete(topic);
            log.debug("消息队列成功删除");
        } catch (final Exception e) {
            log.error("执行异常", e);
            throw new ServiceException("删除消息队列失败");
        }
    }

    /**
     * 异步删除队列.
     * @param topic topic
     */
    @Async
    public void deleteQueueAsync(final String topic) {
        deleteQueue(topic);
    }
}
