package com.github.happyjiahui.z.easyrule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.happyjiahui.z.easyrule.model.EasyRuleInfo;
import com.github.happyjiahui.z.easyrule.model.EasyRuleMessage;

/**
 * @author zhaojinbing
 */
public class SimpleEasyRule {
    
    private static final Logger LOG = LoggerFactory.getLogger(SimpleEasyRule.class);

    private Long executeId;
    
    private List<Boolean> hasError = new ArrayList<>();

    private Set<String> firedRuleList = new HashSet<>();

    private List<Rule> ruleList;

    private IEasyRuleHandle easyRuleHandle;

    private Map<String, Map<String, Object>> resultData = new HashMap<>();

    private SimpleEasyRuleListener simpleEasyRuleListener;

    public SimpleEasyRule(List<EasyRuleInfo> easyRuleInfos, IEasyRuleHandle easyRuleHandle) {
        this.easyRuleHandle = easyRuleHandle;
        init(easyRuleInfos);
    }

    private void init(List<EasyRuleInfo> easyRuleInfos) {
        executeId = System.currentTimeMillis();
        this.ruleList = easyRuleInfos.stream().map(easyRuleInfo -> {
            return new RuleBuilder().name(easyRuleInfo.getId()).description(easyRuleInfo.getLabel()).when(facts -> {
                Set<String> firedRuleSet = facts.get("firedRules");
                if (firedRuleSet.size() == 0 && easyRuleInfo.getFrom().size() == 0) {
                    return true;
                } else if (easyRuleInfo.getFrom().size() != 0) {
                    return firedRuleSet.containsAll(easyRuleInfo.getFrom());
                } else {
                    return false;
                }
            }).then(facts -> {
                facts.put("this", this);
                String id = easyRuleInfo.getId();
                EasyRuleMessage easyRuleMessage = new EasyRuleMessage();
                easyRuleMessage.setId(id);
                easyRuleMessage.setType(easyRuleInfo.getType());
                easyRuleMessage.setParamData(easyRuleInfo.getConfig());
                easyRuleMessage.setFrom(easyRuleInfo.getFrom());
                easyRuleMessage.setResultData(this.resultData);
                try {
                    Map<String, Object> result = this.easyRuleHandle.call(easyRuleMessage);
                    this.resultData.put(id, result);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                    this.hasError.add(false);
                }

            }).build();
        }).collect(Collectors.toList());
    }

    public Long fire(Set<String> firedRules, SimpleEasyRuleListener listener) {
        Facts facts = new Facts();
        facts.put("firedRules", firedRules);

        Rules rules = new Rules();
        this.ruleList.forEach(rule -> {
            rules.register(rule);
        });

        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerRuleListener(new MyRuleListener());
        rulesEngine.registerRulesEngineListener(new MyRuleEngineListener(listener));
        rulesEngine.fire(rules, facts);
        return this.executeId;
    }

    public List<Boolean> getHasError() {
        return hasError;
    }

    public Set<String> getFiredRuleList() {
        return firedRuleList;
    }

    public void setFiredRuleList(Set<String> firedRuleList) {
        this.firedRuleList = firedRuleList;
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    public IEasyRuleHandle getEasyRuleHandle() {
        return easyRuleHandle;
    }

    public void setEasyRuleHandle(IEasyRuleHandle easyRuleHandle) {
        this.easyRuleHandle = easyRuleHandle;
    }

    public Map<String, Map<String, Object>> getResultData() {
        return resultData;
    }

    public void setResultData(Map<String, Map<String, Object>> resultData) {
        this.resultData = resultData;
    }

    public SimpleEasyRuleListener getSimpleEasyRuleListener() {
        return simpleEasyRuleListener;
    }

}
