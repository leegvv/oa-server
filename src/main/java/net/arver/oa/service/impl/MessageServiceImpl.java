package net.arver.oa.service.impl;

import net.arver.oa.dao.MessageDao;
import net.arver.oa.dao.MessageRefDao;
import net.arver.oa.pojo.MessageEntity;
import net.arver.oa.pojo.MessageRefEntity;
import net.arver.oa.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 消息服务实现.
 * @author leegvv
 */
@Service
public class MessageServiceImpl implements MessageService {

    /**
     * 消息dao.
     */
    @Autowired
    private MessageDao messageDao;

    /**
     * 消息ref dao.
     */
    @Autowired
    private MessageRefDao messageRefDao;

    @Override
    public String insertMessage(final MessageEntity entity) {
        return messageDao.insert(entity);
    }

    @Override
    public List<HashMap> searchMessageByPage(final int userId, final long start, final int length) {
        return messageDao.searchMessageByPage(userId, start, length);
    }

    @Override
    public HashMap searchMessageById(final String id) {
        return messageDao.searchMessageById(id);
    }

    @Override
    public String insertRef(final MessageRefEntity entity) {
        return messageRefDao.insert(entity);
    }

    @Override
    public long searchUnreadCount(final int userId) {
        return messageRefDao.searchUnreadCount(userId);
    }

    @Override
    public long searchLastCount(final int userId) {
        return messageRefDao.searchLastCount(userId);
    }

    @Override
    public long updateUnreadMessage(final String id) {
        return messageRefDao.updateUnreadMessage(id);
    }

    @Override
    public long deleteMessageRefById(final String id) {
        return messageRefDao.deleteMessageRefById(id);
    }

    @Override
    public long deleteMessageRefByUserId(final int userId) {
        return messageRefDao.deleteMessageRefByUserId(userId);
    }
}
