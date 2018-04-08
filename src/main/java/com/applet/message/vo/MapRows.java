package com.applet.message.vo;

import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class MapRows {
    Map<String, Rows> map;

    public MapRows(int size) {
        map = new HashMap<String, Rows>();
        for (int i = 0; i < size; i++) {
            map.put("keyword" + (i + 1), Rows.getDefaultRows());
        }
    }
    public Rows get(int i){
        return map.get("keyword" + i);
    }

    public String toJSON(){
        return JSONArray.toJSON(map).toString();
    }
}
