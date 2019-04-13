package com.clt.websocket.HTTP;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.clt.websocket.HTTP.Base64Utils.encodeBase64File;

public class importSeal {
    public static String unitId = "02510207151721b38649b4bd1f8";
    public static String unitName = "四分局";
    public static String city = "南京市";
    public static void main(String[] args) throws Exception {

        List<SealData> sealDataList = readData("E:\\南京市\\四分局\\");
        int i = 0;
        String url = "http://localhost:8080/ess_make_system_war/make/importSeal.html";
        Long l1 = System.currentTimeMillis();
        if (sealDataList != null) {
            for(SealData sealData:sealDataList){
                Map<String,Object> dataMap = new HashMap<>();
                dataMap.put("unitId",sealData.getUnitId());
                dataMap.put("imgBase64",sealData.getImgBase64());
                dataMap.put("sealName",sealData.getSealName());
                dataMap.put("idNum",sealData.getIdNum());
                dataMap.put("city",sealData.getCity());
                dataMap.put("unitName",sealData.getUnitName());
                dataMap.put("departName",sealData.getDepartName());
                dataMap.put("certName",sealData.getCertName());
                i+=1;
                String HttpResult = HttpClient.doPost(url,dataMap);
                System.out.println(HttpResult);
//                System.out.println(dataMap);
            }
        }
        Long l2 = System.currentTimeMillis();
        System.out.println(l2-l1);
        System.out.println(i);
    }

    public static List<SealData> readData(String path) throws Exception {
        List<SealData> sealDataList = new ArrayList<>();
        //循环取值
        File file = new File(path);
        if (file.exists()) {
            //遍历部门文件夹
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return null;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        //在此处遍历文件夹下文件
//                        System.out.println(file2.getName());
                        //遍历部门文件夹下的图片文件
                        File[] files_1 = file2.listFiles();
                        if (files_1 != null) {
                            for (File file3 : files_1) {
                                SealData sealData = new SealData();
                                String data = file3.getName();
                                String idNum = data.substring(data.length()-22,data.length()-4);
                                String reg = "[^\u4e00-\u9fa5]";
                                String sealName = data.replaceAll(reg, "");
                                String imgBase64 = encodeBase64File(file3.getAbsolutePath());
                                sealData.setCity(city);
                                sealData.setUnitName(unitName);
                                sealData.setUnitId(unitId);
                                sealData.setImgBase64(imgBase64);
                                sealData.setDepartName(file2.getName());
                                sealData.setCertName(sealName);
                                sealData.setIdNum(idNum);
                                sealData.setSealName(sealName);
                                sealDataList.add(sealData);
//                                System.out.println(sealData);
                            }
                        }
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
            return null;
        }


        return sealDataList;
    }


}
