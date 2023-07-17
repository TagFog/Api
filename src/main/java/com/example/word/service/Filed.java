package com.example.word.service;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

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
