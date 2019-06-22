package com.example.demo.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

public class QfpayUtil {

    /**
     * 加签
     * @param map
     * @return
     */
    public static String mapACSIIrank(Map<String, Object> map, String signKey){
        if(map==null){
            return null;
        }
        List<String> keyList = new ArrayList<>(map.keySet());
        Collections.sort(keyList);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<keyList.size();i++){
            String key = keyList.get(i);
            Object value = map.get(key);
            if (!StringUtils.isEmpty(""+map.get(key))){
                sb.append(key+"="+value+"&");
            }
        }
        String signStr = sb+"key="+signKey;
        System.out.println("before sign: "+signStr);
//        String md5Str = DigestUtils.md5Hex(signStr);
//        System.out.println("after sign: "+md5Str);
        return signStr;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("abc", "123");
        map.put("cba", "111");
        map.put("bca", "222");
        System.out.println(QfpayUtil.mapACSIIrank(map, "111111"));
        /**
         * output
         * 	before sign: abc=123&bca=222&cba=111111111
         after sign: c1f8a7c16bdb1f998f361d4dfafef4ee
         */

    }

}