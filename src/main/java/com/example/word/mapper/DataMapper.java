package com.example.word.mapper;

import com.example.word.entity.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface DataMapper {
    @Select("Select * from data")
    public List<Data> find();
}
