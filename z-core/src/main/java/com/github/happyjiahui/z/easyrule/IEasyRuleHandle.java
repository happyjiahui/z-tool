package com.github.happyjiahui.z.easyrule;

import java.util.Map;

import com.github.happyjiahui.z.easyrule.model.EasyRuleMessage;

public interface IEasyRuleHandle {
    
    Map<String, Object> call(EasyRuleMessage easyRuleMessage);
    
}
