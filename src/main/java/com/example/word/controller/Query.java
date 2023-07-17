package com.example.word.controller;

import com.example.word.service.Data;
import com.example.word.service.Filed;
import com.example.word.mapper.FieldMapper;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
public class Query {
    @Autowired
    private Data dataMapper;


    @Autowired
    private FieldMapper fieldMapper;

    @GetMapping("/find")
    public String query() throws IOException {

        List<Map<String,Object>> list = dataMapper.getAllTablesData();

        String tableName = "data";
        List<Filed> list1 = fieldMapper.getColumnNames(tableName);

        // 创建一个新的Word文档
        XWPFDocument document = new XWPFDocument();

        String titleName = "数据表";
        // 添加标题
        XWPFParagraph titleParagraph = document.createParagraph(); //创建标题
        titleParagraph.setAlignment(ParagraphAlignment.CENTER); //标题居中
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText(titleName);
        titleRun.setBold(true);
        titleRun.setFontSize(14);

        // 获取行数和列数
        int rowCount = list.size();
        int title = list1.size();

        // 创建一个表格
        XWPFTable table = document.createTable(rowCount + 1, title);

        CTTblPr tblPr = table.getCTTbl().addNewTblPr();
        tblPr.addNewJc().setVal(STJc.CENTER);

        XWPFTableRow headerRow = table.getRow(0);

        // 设置表头行的背景颜色
        for (int i = 0; i < headerRow.getTableCells().size(); i++) {
            XWPFTableCell cell = headerRow.getCell(i);
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }
            CTShd ctShd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();
            ctShd.setFill("FFFF00"); // 设置为黄色
        }


        CTTblWidth tableWidth = tblPr.addNewTblW();
        tableWidth.setType(STTblWidth.DXA);
//        生成表格的宽度
        tableWidth.setW(BigInteger.valueOf(8000));

        // 添加表头行
        for (int i = 0; i < title; i++) {
            Filed filed = list1.get(i);
            XWPFTableRow fileRow = table.getRow(0);
            fileRow.getCell(i).setText(filed.getColumnName());
        }

        // 填充表头
        int columnIndex = 0;
        // 填充数据
        int rowIndex = 1;
        for (Map<String, Object> dataMap : list) {
            columnIndex = list.get(0).size() - 1; // 列索引从最后一列开始
            for (Object value : dataMap.values()) {
                XWPFTableCell cell = table.getRow(rowIndex).getCell(columnIndex);
                cell.setText(value.toString());
                columnIndex--;
            }
            rowIndex++;
        }


        // 保存Word文档
        FileOutputStream outputStream = new FileOutputStream("src\\template\\out.docx");
        document.write(outputStream);
        outputStream.close();

        System.out.println("Word文档生成成功！");
        return "查询用户并生成word文档";
    }
}
