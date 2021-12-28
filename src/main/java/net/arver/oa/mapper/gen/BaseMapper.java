package net.arver.oa.mapper.gen;

import java.io.Serializable;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * DAO公共基类，由MybatisGenerator自动生成请勿修改.
 * @param <Entity> The Model Class 这里是泛型不是Model类
 * @param <PK> The Primary Key Class 如果是无主键，则可以用Model来跳过，如果是多主键则是Key类
 * @param <Example> The Example Class
 * @author leegvv
 */
public interface BaseMapper<Entity, PK extends Serializable, Example> {
    /**
     * countByExample.
     * @param example example
     * @return long
     */
    long countByExample(Example example);

    /**
     * deleteByExample.
     * @param example example
     * @return int
     */
    int deleteByExample(Example example);

    /**
     * deleteByPrimaryKey.
     * @param id id
     * @return int
     */
    int deleteByPrimaryKey(PK id);

    /**
     * insert.
     * @param entity entity
     * @return int
     */
    int insert(Entity entity);

    /**
     * insertSelective.
     * @param entity entity
     * @return int
     */
    int insertSelective(Entity entity);

    /**
     * selectByExampleWithBLOBs.
     * @param example example
     * @return List<Entity>
     */
    List<Entity> selectByExampleWithBLOBs(Example example);

    /**
     * selectByExample.
     * @param example example
     * @return List<Entity>
     */
    List<Entity> selectByExample(Example example);

    /**
     * selectByPrimaryKey.
     * @param id id
     * @return Entity
     */
    Entity selectByPrimaryKey(PK id);

    /**
     * updateByExampleSelective.
     * @param entity entity
     * @param example example
     * @return int
     */
    int updateByExampleSelective(@Param("entity") Entity entity, @Param("example") Example example);

    /**
     * updateByExampleWithBLOBs.
     * @param entity entity
     * @param example example
     * @return int
     */
    int updateByExampleWithBLOBs(@Param("entity") Entity entity, @Param("example") Example example);

    /**
     * updateByExample.
     * @param entity entity
     * @param example example
     * @return int
     */
    int updateByExample(@Param("entity") Entity entity, @Param("example") Example example);

    /**
     * updateByPrimaryKeySelective.
     * @param entity entity
     * @return int
     */
    int updateByPrimaryKeySelective(Entity entity);

    /**
     * updateByPrimaryKeyWithBLOBs.
     * @param entity entity
     * @return int
     */
    int updateByPrimaryKeyWithBLOBs(Entity entity);

    /**
     * updateByPrimaryKey.
     * @param entity entity
     * @return int
     */
    int updateByPrimaryKey(Entity entity);
}