package com.siobhan.service.impl;

import com.siobhan.entity.CompanyDO;
import com.siobhan.proxy.MyResponse;
import com.siobhan.repository.CompanyRepository;
import com.siobhan.service.UploadService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by siobhan.zheng on 2019/3/28
 */
@Service
public class UploadServiceImpl implements UploadService{
    private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    private CompanyRepository companyRepository;

    private static final String xls = "xls";
    private static final String xlsx = "xlsx";

    /**
     * 解析指定模板excel
     * @param fileName
     * @param inputStream
     * @return
     */
    @Override
    public MyResponse uploadExcel(String fileName, InputStream inputStream) {
        MyResponse response = new MyResponse("1","上传成功", null);
        if (check(fileName)){
            try {
                // 获取工作簿对象
                Workbook workbook = getWorkbook(fileName, inputStream);
                List<CompanyDO> list = new ArrayList<>();
                if(workbook != null){
                    for (int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                        // 遍历所有sheet页
                        // 获取sheet对象
                        Sheet sheet = workbook.getSheetAt(sheetNum);
                        for (int rowNum = 1;rowNum < sheet.getPhysicalNumberOfRows();rowNum++){
                            // 遍历sheet页下的所有行 默认第二行开始
                            Row row = sheet.getRow(rowNum);
                            // TODO 循环里面访问数据库 效率低下（缓存）
                            if(row == null || companyRepository.findByCompanyUrlAndStatus(getCellValue(row.getCell(1)),"1") != null){
                                // 根据公司网址去重,如果已有该公司，则不上传
                                continue;
                            }
                            // 每行代表一个公司
                            CompanyDO companyDO = new CompanyDO();
                            for (int cellNum = 0;cellNum < row.getPhysicalNumberOfCells();cellNum++) {
                                // 遍历该行下的所有列
                                // 读取excel中的公司名称和网址到companyDO
                                setCompanyDO(row.getCell(cellNum), cellNum, companyDO);
                            }
                            setOthers(companyDO);
                            list.add(companyDO);
                        }
                    }
                    // TODO 批量插入 底层还是生成多条insert语句 （存储过程）
                    companyRepository.save(list);
                }
            } catch (IOException e) {
                logger.error("uploadExcel occur", e);
            }
        } else {
            // 不是excel文件
            response = new MyResponse("0","请上传excel文件", null);
        }
        return response;

    }
    private void setCompanyDO(Cell cell,int cellNum, CompanyDO companyDO){
        if (cell != null){
            switch (cellNum) {
                case 0:
                    companyDO.setCompanyName(getCellValue(cell).trim());
                    break;
                case 1:
                    // 表格有些url前后有空格，避免url解析为%20
                    companyDO.setCompanyUrl(getCellValue(cell).trim());
                    break;
                default:break;
            }
        }
    }
    private void setOthers(CompanyDO companyDO){
        companyDO.setStatus("1");
        companyDO.setCreateTime(new Date());
        companyDO.setCreateUser("1");
        companyDO.setUpdateTime(new Date());
        companyDO.setUpdateUser("1");
    }
    private boolean check(String fileName){
        boolean flag = true;
        // 判断文件是否是excel文件 自个添加其它检验.....
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            flag = false;
        }
        return flag;
    }

    private Workbook getWorkbook(String fileName, InputStream inputStream) throws IOException {
        Workbook workbook = null;
        if(fileName.endsWith(xls)){
            //2003
            workbook = new HSSFWorkbook(inputStream);
        }else if(fileName.endsWith(xlsx)){
            //2007
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }
    private String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}
