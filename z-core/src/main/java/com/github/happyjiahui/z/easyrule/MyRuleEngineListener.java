package com.github.happyjiahui.z.easyrule;

import java.util.HashSet;
import java.util.Set;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineListener;

public class MyRuleEngineListener implements RulesEngineListener {
    
    private SimpleEasyRuleListener simpleEasyRuleListener;
    
    public MyRuleEngineListener() {
        
    }
    
    public MyRuleEngineListener(SimpleEasyRuleListener listener) {
        this.simpleEasyRuleListener = listener;
    }

    @Override
    public void afterExecute(Rules rules, Facts facts) {
        SimpleEasyRule simpleEasyRule = facts.get("this");
        if (simpleEasyRule == null) {
            if (this.simpleEasyRuleListener != null) {
                this.simpleEasyRuleListener.complete();
            }
            return;
        }
        if (!simpleEasyRule.getHasError().isEmpty()) {
            System.err.println("有錯誤");
            return;
        }
        Set<String> firedRuleSet = new HashSet<>();
        firedRuleSet.addAll(simpleEasyRule.getFiredRuleList());
        if (firedRuleSet == null || firedRuleSet.isEmpty()) {
            return;
        }
        simpleEasyRule.getFiredRuleList().clear();
        simpleEasyRule.fire(firedRuleSet, this.simpleEasyRuleListener);
        RulesEngineListener.super.afterExecute(rules, facts);
    }

}
