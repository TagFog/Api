package com.example.word.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName("JavaData")
public class Filed {

    @TableField("COLUMN_NAME")
    public String ColumnName;

    public String getColumnName() {
        return ColumnName;
    }

    public void setColumnName(String columnName) {
        ColumnName = columnName;
    }

    @Override
    public String toString() {
        return "Filed{" +
                "ColumnName='" + ColumnName + '\'' +
                '}';
    }
}
