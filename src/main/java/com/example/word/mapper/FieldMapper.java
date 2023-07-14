package com.example.word.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.word.entity.Data;
import com.example.word.entity.Filed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FieldMapper extends BaseMapper<Filed> {
    @Select("select column_name from information_schema.columns where table_schema = (SELECT DATABASE()) and table_name = #{tableName}")
    public List<Filed> getColumnNames(String tableName);
}
