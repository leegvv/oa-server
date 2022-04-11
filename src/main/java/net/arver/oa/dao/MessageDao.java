package net.arver.oa.dao;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import net.arver.oa.pojo.MessageEntity;
import net.arver.oa.pojo.MessageRefEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 消息dao.
 * @author leegvv
 */
@Repository
public class MessageDao {

    /**
     * mongoTemplate.
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入.
     * @param entity 消息实体
     * @return id
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public String insert(final MessageEntity entity) {
        Date sendTime = entity.getSendTime();
        sendTime = DateUtil.offset(sendTime, DateField.HOUR, 8);
        entity.setSendTime(sendTime);
        final MessageEntity dbEntity = mongoTemplate.save(entity);
        return dbEntity.get_id();
    }

    /**
     * 查询消息信息.
     * @param userId 用户id
     * @param start 其实位置
     * @param length 长度
     * @return 消息信息
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public List<HashMap> searchMessageByPage(final int userId, final long start, final int length) {
        final JSONObject json = new JSONObject();
        json.set("$toString", "$_id");
        final Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.addFields().addField("id").withValue(json).build(),
                Aggregation.lookup("message_ref", "id", "messageId", "ref"),
                Aggregation.match(Criteria.where("ref.receiverId").is(userId)),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "sendTime")),
                Aggregation.skip(start),
                Aggregation.limit(length)
        );
        final AggregationResults<HashMap> results = mongoTemplate.aggregate(aggregation, "message", HashMap.class);
        final List<HashMap> list = results.getMappedResults();
        list.forEach(one -> {
            final List<MessageRefEntity> refList = (List<MessageRefEntity>) one.get("ref");
            final MessageRefEntity entity = refList.get(0);
            final Boolean readFlag = entity.getReadFlag();
            final String refId = entity.get_id();
            one.put("readFlag", readFlag);
            one.put("refId", refId);
            one.remove("ref");
            one.remove("_id");
            Date sendTime = (Date) one.get("sendTime");
            sendTime = DateUtil.offset(sendTime, DateField.HOUR, -8);
            final String today = DateUtil.today();
            if (today.equals(DateUtil.date(sendTime).toDateStr())) {
                one.put("sendTime", DateUtil.format(sendTime, "HH:mm"));
            } else {
                one.put("sendTime", DateUtil.format(sendTime, "yyyy/MM/dd"));
            }
        });
        return list;
    }

    /**
     * 根据id查询消息.
     * @param id id
     * @return 消息
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public HashMap searchMessageById(final String id) {
        final HashMap map = mongoTemplate.findById(id, HashMap.class, "message");
        Date sendTime = (Date) map.get("sendTime");
        sendTime = DateUtil.offset(sendTime, DateField.HOUR, -8);
        map.replace("sendTime", DateUtil.format(sendTime, "yyyy-MM-dd HH:mm"));
        return map;
    }
}
