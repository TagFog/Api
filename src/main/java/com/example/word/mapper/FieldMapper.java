package com.example.word.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.word.service.Filed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FieldMapper extends BaseMapper<Filed> {
    @Select("select column_name from information_schema.columns where table_schema = (SELECT DATABASE()) and table_name = #{tableName} ORDER BY column_name DESC")
    public List<Filed> getColumnNames(String tableName);
}
