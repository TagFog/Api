package com.example.word.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.word.service.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataMapper{
    @Select("${sql}")
    List<Map<String,Object>> executeQuery(@Param("sql")String sql);
}
