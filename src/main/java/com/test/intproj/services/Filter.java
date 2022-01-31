package com.test.intproj.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.intproj.services.ref.FilterRule;
import com.test.intproj.services.ref.FilterRulesService;
import com.test.intproj.services.ref.MessageForwarder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class Filter {

    private final Logger LOG = Logger.getLogger(Filter.class.getName());

    @Autowired
    private FilterRulesService filterRules;

    @Autowired
    private MessageForwarder forwarder;

    public void executePipeline(String messageType, String message) throws JsonProcessingException {
        LOG.info("Processing message ["+  messageType+"] [" + message +"]");
        List<FilterRule> rulesForType = filterRules.getRulesForType(messageType);

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> stringObjectMap = mapper.readValue(message, new TypeReference<Map<String, Object>>() {
        });

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
                targetNode.remove(targetField);
            } else {
                String currentValue = (String)targetNode.get(targetField);
                String maskedValue = currentValue.replaceAll("[0-9]","*");
//                targetNode.put(targetField,maskedValue);
            }
        }
        String filteredMessage = mapper.writeValueAsString(stringObjectMap);

        try {
            forwarder.forward(messageType, filteredMessage);
            LOG.info("Message Processed");
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

}
