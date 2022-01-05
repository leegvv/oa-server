package net.arver.oa.service;

import net.arver.oa.pojo.Config;

import java.util.List;

/**
 * 系统配置service.
 * @author leegvv
 */
public interface ConfigService {

    /**
     * 查询所有有效参数.
     * @return 系统配置参数
     */
    List<Config> searchAllParam();
}
