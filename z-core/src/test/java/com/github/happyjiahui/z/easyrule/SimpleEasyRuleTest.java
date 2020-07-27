package com.github.happyjiahui.z.easyrule;

import com.github.happyjiahui.z.easyrule.model.EasyRuleInfo;
import com.github.happyjiahui.z.easyrule.model.EasyRuleMessage;
import com.github.happyjiahui.z.util.JsonUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SimpleEasyRuleTest {

    @Test
    public void fire() {

        String jsonStr =
                "[{\"id\":\"1\",\"label\":\"输入源1\",\"from\":[],\"to\":[\"31\"]},{\"id\":\"2\",\"label\":\"输入源2\",\"from\":[],\"to\":[\"3\"]},{\"id\":\"3\",\"label\":\"算法模型\",\"from\":[\"2\"],\"to\":[\"4\",\"5\"]},{\"id\":\"31\",\"label\":\"算法模型1\",\"from\":[\"2\"],\"to\":[\"6\",\"7\"]},{\"id\":\"4\",\"label\":\"输出源1\",\"from\":[\"3\"],\"to\":[]},{\"id\":\"5\",\"label\":\"输出源2\",\"from\":[\"3\"],\"to\":[]},{\"id\":\"6\",\"label\":\"输出源3\",\"from\":[\"31\"],\"to\":[]},{\"id\":\"7\",\"label\":\"输出源4\",\"from\":[\"31\"],\"to\":[\"8\"]},{\"id\":\"8\",\"label\":\"输出源8\",\"from\":[\"7\"],\"to\":[]}]";
        List<EasyRuleInfo> easyRuleInfos = JsonUtils.parseList(jsonStr, EasyRuleInfo.class);

        SimpleEasyRule simpleEasyRule = new SimpleEasyRule(easyRuleInfos, new IEasyRuleHandle() {

            @Override
            public Map<String, Object> call(EasyRuleMessage easyRuleMessage) {
                System.err.println("---------------");
                System.err.println(easyRuleMessage.getFrom());
                System.err.println(easyRuleMessage.getResultData());
                String id = easyRuleMessage.getId();
                Map<String, Object> result = new HashMap<>();
                if ("1".equals(id)) {
                    result.put("a1", "hello world");
                } else if ("2".equals(id)) {
                    result.put("b", "dfsdf");
                } else if ("3".equals(id)) {
//                    throw new RuntimeException("未知错误");
                }

                return result;
            }
        });

        Long executeId = simpleEasyRule.fire(new HashSet<>(), new SimpleEasyRuleListener() {

            @Override
            public void complete() {
                System.err.println("结束");

            }
        });

        System.err.println(simpleEasyRule.getResultData());
    }
}