package com.test.intproj.services.ref;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class FilterRulesService {

    public List<FilterRule> getRulesForType(String messageType){
        List<FilterRule> output = new ArrayList<>();
        Set<String> removeRules = Generator.removeList.get(messageType);

        for(String s : removeRules){
            output.add(new FilterRule(s, FilterRule.FilterAction.REMOVE));
        }

        Set<String> maskRules = Generator.maskList.get(messageType);

        for(String s : maskRules){
            output.add(new FilterRule(s, FilterRule.FilterAction.MASK));
        }

        return output;
    }



}
