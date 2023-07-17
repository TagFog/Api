package com.example.word.service;

import com.example.word.mapper.DataMapper;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class Data {
    private final DataMapper dataMapper;
    @Autowired
    public Data(DataMapper dataMapper){
        this.dataMapper = dataMapper;
    }

    public List<Map<String, Object>> getAllTablesData() {

        String sql = "SELECT * FROM information_schema.tables WHERE table_schema = 'JavaData'";

        List<Map<String, Object>> tableList = dataMapper.executeQuery(sql);

            String a = "data";//参数
            String querySql = "SELECT * FROM " + a;
            List<Map<String, Object>> tableData = dataMapper.executeQuery(querySql);;
        return tableData;
    }
}
