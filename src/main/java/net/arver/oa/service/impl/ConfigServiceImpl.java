package net.arver.oa.service.impl;

import net.arver.oa.mapper.ConfigMapper;
import net.arver.oa.pojo.Config;
import net.arver.oa.pojo.gen.ConfigExample;
import net.arver.oa.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配置service实现.
 * @author leegvv
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    /**
     * 系统配置mapper.
     */
    @Autowired
    private ConfigMapper configMapper;

    @Override
    public List<Config> searchAllParam() {
        final ConfigExample example = new ConfigExample();
        example.createCriteria().andStatusEqualTo(1);
        return configMapper.selectByExample(example);
    }
}
