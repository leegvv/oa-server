package net.arver.oa.service;

import net.arver.oa.pojo.MessageEntity;
import net.arver.oa.pojo.MessageRefEntity;

import java.util.HashMap;
import java.util.List;

/**
 * 消息服务接口.
 * @author leegvv
 */
public interface MessageService {

    /**
     * 插入消息.
     * @param entity 消息实体
     * @return id
     */
    String insertMessage(MessageEntity entity);

    /**
     * 查询用户分页消息.
     * @param userId 用户id
     * @param start 起始位置
     * @param length 长度
     * @return 消息信息
     */
    List<HashMap> searchMessageByPage(int userId, long start, int length);

    /**
     * 根据id查询消息.
     * @param id id
     * @return 消息信息
     */
    HashMap searchMessageById(String id);

    /**
     * 插入ref.
     * @param entity 实体
     * @return id
     */
    String insertRef(MessageRefEntity entity);

    /**
     * 查询未读消息数.
     * @param userId 用户id
     * @return 消息数量
     */
    long searchUnreadCount(int userId);

    /**
     * 查询最新消息数量.
     * @param userId 用户id
     * @return 消息数量
     */
    long searchLastCount(int userId);

    /**
     * 更新未读消息数量.
     * @param id id
     * @return 更新数量
     */
    long updateUnreadMessage(String id);

    /**
     * 根据id删除用户消息.
     * @param id id
     * @return 删除数量
     */
    long deleteMessageRefById(String id);

    /**
     * 根据用户id删除用户消息.
     * @param userId 用户id
     * @return 删除数量
     */
    long deleteMessageRefByUserId(int userId);
}
