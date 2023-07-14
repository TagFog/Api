package com.example.word.controller;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.example.word.entity.Data;
import com.example.word.entity.Filed;
import com.example.word.mapper.DataMapper;
import com.example.word.mapper.FieldMapper;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.List;
@RestController
public class Query {
    @Autowired
    private DataMapper dataMapper;
    @Autowired
    private FieldMapper fieldMapper;

    @GetMapping("/find")
    public String query() throws IOException {
        List<Data> list = dataMapper.find();
        String tableName = "data";
        List<Filed> list1 = fieldMapper.getColumnNames(tableName);
        // 创建一个新的Word文档
        XWPFDocument document = new XWPFDocument();

        // 获取行数和列数
        int rowCount = list.size();
        int title = list1.size();
        int columnCount = 3; // 假设为3列，你可以根据实际需要进行调整

        // 创建一个表格
        XWPFTable table = document.createTable(rowCount + 1, columnCount);

        CTTblPr tblPr = table.getCTTbl().addNewTblPr();
        tblPr.addNewJc().setVal(STJc.CENTER);
        CTTblWidth tableWidth = tblPr.addNewTblW();
        tableWidth.setType(STTblWidth.DXA);
        tableWidth.setW(BigInteger.valueOf(11072));

//        // 添加表头行
        for (int i = 0; i < title; i++) {
            Filed filed = list1.get(i);
            XWPFTableRow fileRow = table.getRow(0);
            fileRow.getCell(i).setText(filed.getColumnName());
        }


        // 填充数据行
        for (int i = 0; i < rowCount; i++) {
            Data data = list.get(i);
            XWPFTableRow dataRow = table.getRow(i + 1);
            dataRow.getCell(0).setText(data.getUser());
            dataRow.getCell(1).setText(data.getSex());
            dataRow.getCell(2).setText(data.getPara());
        }

        // 保存Word文档
        FileOutputStream outputStream = new FileOutputStream("src\\template\\out.docx");
        document.write(outputStream);
        outputStream.close();

        System.out.println("Word文档生成成功！");
        return "查询用户并生成word文档";
    }
}
