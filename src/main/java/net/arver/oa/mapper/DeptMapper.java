package net.arver.oa.mapper;

import net.arver.oa.mapper.gen.BaseMapper;
import net.arver.oa.pojo.Dept;
import net.arver.oa.pojo.gen.DeptExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * DeptMapper继承基类.
 * 由MybatisGenerator自动生成请勿修改
 * @author leegvv
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept, Integer, DeptExample> {
}