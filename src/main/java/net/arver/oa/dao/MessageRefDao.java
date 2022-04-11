package net.arver.oa.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import net.arver.oa.pojo.MessageRefEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * 用户消息dao.
 * @author leegvv
 */
@Repository
public class MessageRefDao {

    /**
     * mongonTemplate.
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入.
     * @param entity 用户消息实体
     * @return id
     */
    public String insert(final MessageRefEntity entity) {
        mongoTemplate.save(entity);
        return entity.get_id();
    }

    /**
     * 查询未读消息数量.
     * @param userId 用户id
     * @return 消息数量
     */
    public long searchUnreadCount(final int userId) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("readFlag").is(false).and("receiverId").is(userId));
        final long count = mongoTemplate.count(query, MessageRefEntity.class);
        return count;
    }

    /**
     * 查询最新消息数量.
     * @param userId 用户id
     * @return 数量
     */
    public long searchLastCount(final int userId) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("lastFlag").is(true).and("receiverId").is(userId));
        final Update update = new Update();
        update.set("lastFlag", false);
        final UpdateResult result = mongoTemplate.updateMulti(query, update, "message_ref");
        final long count = result.getModifiedCount();
        return count;
    }

    /**
     * 更新未读消息.
     * @param id id
     * @return 消息数量
     */
    public long updateUnreadMessage(final String id) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        final Update update = new Update();
        update.set("readFlag", true);
        final UpdateResult result = mongoTemplate.updateFirst(query, update, "message_ref");
        final long count = result.getModifiedCount();
        return count;
    }

    /**
     * 根据id删除用户消息.
     * @param id id
     * @return 消息数量
     */
    public long deleteMessageRefById(final String id) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        final DeleteResult result = mongoTemplate.remove(query, "message_ref");
        final long count = result.getDeletedCount();
        return count;
    }

    /**
     * 删除用户所有用户消息.
     * @param userId 用户id
     * @return 删除数量
     */
    public long deleteMessageRefByUserId(final int userId) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("receiverId").is(userId));
        final DeleteResult result = mongoTemplate.remove(query, "message_ref");
        final long count = result.getDeletedCount();
        return count;
    }

}
