package net.arver.oa.service;

/**
 * 城市service.
 * @author leegvv
 */
public interface CityService {

    /**
     * 根据城市名称查询城市code.
     * @param city 城市名称
     * @return code
     */
    String searchCodeByName(String city);
}
