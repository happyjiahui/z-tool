package com.github.happyjiahui.z.easyrule.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EasyRuleMessage {
    
    private String id;
    
    private String type;
    
    private Set<String> from;
    
    private Map<String, Object> paramData;
    
    private Map<String, Map<String, Object>> resultData = new HashMap<String, Map<String,Object>>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getFrom() {
        return from;
    }

    public void setFrom(Set<String> from) {
        this.from = from;
    }

    public Map<String, Object> getParamData() {
        return paramData;
    }

    public void setParamData(Map<String, Object> paramData) {
        this.paramData = paramData;
    }

    public Map<String, Map<String, Object>> getResultData() {
        return resultData;
    }

    public void setResultData(Map<String, Map<String, Object>> resultData) {
        this.resultData = resultData;
    }
}
