package com.github.happyjiahui.z.easyrule.model;

import java.util.Map;
import java.util.Set;

public class EasyRuleInfo {

    private String id;
    
    private String label;
    
    private String type;
    
    private Set<String> from;
    
    private Set<String> to;
    
    private Map<String, Object> config;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public Set<String> getTo() {
        return to;
    }

    public void setTo(Set<String> to) {
        this.to = to;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }
    
}
