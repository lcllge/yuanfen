package com.lanzhou.yuanfen.util;

import java.util.LinkedHashMap;
import java.util.Map;


public class ImportFiled {


    private final static String[] head = {"省代码", "省名称", "地级市代码", "地级市名称", "区县代码", "区县名称"};
    private final static String[] value = {"provinceCode", "province", "cityCode", "city", "countyCode", "county"};


    /**
     * 获取默认配置信息, 注意必须是保持顺序的
     *
     * @return
     */
    public static Map<String, String> getImportFiled() {
        Map<String, String> importFiled = new LinkedHashMap<>();
        for (int i = 0; i < head.length; i++) {
            importFiled.put(value[i], head[i]);
        }
        return importFiled;
    }


}
