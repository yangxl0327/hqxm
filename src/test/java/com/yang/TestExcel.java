package com.yang;

import com.yang.entify.DemoData;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
//导出 表格
public class TestExcel {
    @Test
    public  void  test1 (){
        //创建表格
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建页对象
        HSSFSheet sheet = workbook.createSheet("学生信息");
        //创建行对象
        HSSFRow row = sheet.createRow(0);
        //创建单元格对象
        //HSSFCell cell = row.createCell(0);
        //创建表头
        String[] strs = {"ID","姓名","年龄","生日"};
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            row.createCell(i).setCellValue(str);
        }
        DemoData demoData1 = new DemoData("1","xiaohei","22",new Date());
        DemoData demoData2 = new DemoData("1","xiaohei","22",new Date());
        DemoData demoData3 = new DemoData("1","xiaohei","22",new Date());
        DemoData demoData4 = new DemoData("1","xiaohei","22",new Date());
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);
        List<DemoData> demoData = Arrays.asList(demoData1, demoData2, demoData3, demoData4);
        for (int i = 0; i < strs.length; i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(demoData.get(i).getId());
            row1.createCell(1).setCellValue(demoData.get(i).getName());
            row1.createCell(2).setCellValue(demoData.get(i).getAge());
            HSSFCell cell = row1.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(demoData.get(i).getBir());

        }
        //将表格对象写入磁盘
        try {
            workbook.write(new File("D://IDEA-WORK/Excel/text.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //关流  磁盘会爆
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //导入表格
    @Test
    public  void testPoiImport() throws IOException {
        //读取表格
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("D://IDEA-WORK/Excel/text.xls")));
        //读入页
        HSSFSheet sheet = workbook.getSheet("学生信息");
        //读入行数据
        for(int i=1;i<=sheet.getLastRowNum();i++){
            HSSFRow row = sheet.getRow(i);
            String stringCellValue = row.getCell(1).getStringCellValue();
            System.out.println(stringCellValue);
            //强转 数据类型
            double stringCellValue1 = row.getCell(2).getNumericCellValue();
            System.out.println((int)stringCellValue1);
            Date stringCellValue2 = row.getCell(3).getDateCellValue();
            System.out.println(stringCellValue2);
        }

    }

}
