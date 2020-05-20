package com.lanzhou.yuanfen.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.lanzhou.yuanfen.response.ServerResponseResult;
import com.lanzhou.yuanfen.util.ExcelRead;
import com.lanzhou.yuanfen.util.ImportFiled;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/test/")
public class TestController {

    private static CellType[] type = {CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING, CellType.STRING};


    private static final String exportPath1 = "C:\\Users\\Administrator\\Desktop\\xzq_201907.xlsx";


    @GetMapping("/getCityJSON")
    public List<Map<String, Object>> getCityJSON() {
        Map<String, String> importFiled = ImportFiled.getImportFiled();
        String[] key = new String[importFiled.keySet().size()];
        String[] values = new String[importFiled.keySet().size()];
        Iterator<Map.Entry<String, String>> iterator = importFiled.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            key[index] = next.getValue();
            values[index] = next.getKey();
            index++;
        }
        List<Map<String, Object>> mapList = ExcelRead.readEcxcel(new File(exportPath1), key, values, type);
        return mapList;
    }


    /*public static void main(String[] args) {
        Map<String, String> importFiled = ImportFiled.getImportFiled();
        String[] key = new String[importFiled.keySet().size()];
        String[] values = new String[importFiled.keySet().size()];
        Iterator<Map.Entry<String, String>> iterator = importFiled.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            key[index] = next.getValue();
            values[index] = next.getKey();
            index++;
        }
        List<Map<String, Object>> mapList = ExcelRead.readEcxcel(new File(exportPath1), key, values, type);
        System.out.println(mapList);
    }*/

}
