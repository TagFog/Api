package com.example.word.controller;

import com.example.word.service.Data;
import com.example.word.service.Filed;
import com.example.word.mapper.FieldMapper;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.File;
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

    @PostMapping("/generate")
    public String generate(@RequestParam("TableName") String TableName,
                           @RequestParam("DataName") String DataName,
                           @RequestParam("TitleName") String TitleName
                           ) throws IOException {



        List<Map<String,Object>> list = dataMapper.getAllTablesData(TableName,DataName);

        List<Filed> list1 = fieldMapper.getColumnNames(TableName);

        // 创建一个新的Word文档
        XWPFDocument document = new XWPFDocument();

        // 添加标题
        XWPFParagraph titleParagraph = document.createParagraph(); //创建标题
        titleParagraph.setAlignment(ParagraphAlignment.CENTER); //标题居中
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText(TitleName);
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

        // 设置表格内容居中对齐
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                for (XWPFParagraph paragraph : cell.getParagraphs()) {
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                }
            }
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

        String folderPath = "src\\template";
        File folder = new File(folderPath);

        String filePath = "src\\template\\out.docx";
        File file = new File(filePath);

        if (!folder.exists()){
            // 文件夹不存在，创建文件夹
            boolean createdFolder = folder.mkdirs();
            if (createdFolder) {
                System.out.println("文件夹已创建成功！");
                if (!file.exists()){
                    boolean createdFile = file.createNewFile();
                    if (createdFile) {
                        System.out.println("文件已创建成功！");
                    } else {
                        System.out.println("文件创建失败！");
                    }
                }
            } else {
                System.out.println("文件夹创建失败！");
            }
        }


        // 保存Word文档
        FileOutputStream outputStream = new FileOutputStream(filePath);
        document.write(outputStream);
        outputStream.close();

        System.out.println("Word文档生成成功！");
        return "查询用户并生成word文档";
    }
}
