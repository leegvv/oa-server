package net.arver.oa.service.impl;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import net.arver.oa.mapper.CityMapper;
import net.arver.oa.pojo.City;
import net.arver.oa.pojo.gen.CityExample;
import net.arver.oa.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 城市服务实现.
 * @author leegvv
 */
@Slf4j
@Service
public class CityServiceImpl implements CityService {

    /**
     * 城市mapper.
     */
    @Autowired
    private CityMapper cityMapper;

    @Override
    public String searchCodeByName(final String city) {
        final CityExample example = new CityExample();
        example.createCriteria().andCityEqualTo(city);
        final List<City> cities = cityMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(cities)) {
            return cities.get(0).getCode();
        }
        return null;
    }
}
