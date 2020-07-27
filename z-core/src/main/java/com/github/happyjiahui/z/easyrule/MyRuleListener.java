package com.github.happyjiahui.z.easyrule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

public class MyRuleListener implements RuleListener {

    @Override
    public void onSuccess(Rule rule, Facts facts) {
        SimpleEasyRule simpleEasyRule = facts.get("this");
        if (simpleEasyRule == null) {
            return;
        }
        simpleEasyRule.getFiredRuleList().add(rule.getName());
        RuleListener.super.onSuccess(rule, facts);
    }

}
