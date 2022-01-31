package com.test.intproj.services.ref;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MessageForwarder {

    @Autowired
    private FilterRulesService filterRules;

    public void forward(String messageType, String message) throws Exception{

        List<FilterRule> rulesForType = filterRules.getRulesForType(messageType);
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> stringObjectMap = mapper.readValue(message, new TypeReference<Map<String, Object>>() {
        });

        StringBuilder sb = new StringBuilder();
        for(FilterRule rule : rulesForType) {
            String fieldPath = rule.getFieldPath();
            String [] pathSplit = fieldPath.split("/");
            Object node = stringObjectMap;

            String targetField = pathSplit[pathSplit.length-1];
            for(int i=1;i<pathSplit.length-1;i++){
                if(node instanceof Map){
                    Map mapNode = (Map) node;
                    node = mapNode.get(pathSplit[i]);
                }
            }
            Map targetNode = (Map) node;
            if(rule.getFilterRule() == FilterRule.FilterAction.REMOVE){
                if(targetNode.containsKey(targetNode)){
                    sb.append("Failed filter rule [" + rule.getFilterRule()+ "] [" + rule.getFieldPath() +"], ");
                }
            } else {
                String actualField = (String)targetNode.get(targetField);
                if(actualField.matches("[0-9]")){
                    sb.append("Failed filter rule  [" + rule.getFilterRule()+ "] [" + rule.getFieldPath() +"], ");
                }
            }
        }
        String errors = sb.toString();
        if(errors.length() > 0){
            throw new Exception("ERROR : " + errors);
        }

    }
}
