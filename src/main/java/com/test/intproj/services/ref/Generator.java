package com.test.intproj.services.ref;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.intproj.services.Filter;
import com.test.intproj.services.Pipeline;
import com.test.intproj.dto.CountryCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class Generator {
    private static final Random r = new Random(System.currentTimeMillis());

    public static final List<String> messages = new ArrayList<>();

    public static final List<String> word1 = new ArrayList<>();
    public static final List<String> word2 = new ArrayList<>();
    public static final List<String> word3 = new ArrayList<>();

    public static final List<String> word4 = new ArrayList<>();

    public static final List<String> fieldNames = new ArrayList<>();

    static {
        messages.add("Amount Credited");
        messages.add("Amount Debited");
        messages.add("Limit Exceeded");
        messages.add("Transaction Blocked");
        messages.add("Transaction delayed");

        fieldNames.add("cost");
        fieldNames.add("name");
        fieldNames.add("balance");
        fieldNames.add("destinationAccount");
        fieldNames.add("routingNumber");
        fieldNames.add("iban");
        fieldNames.add("sourceAccount");
        fieldNames.add("documentName");
        fieldNames.add("documentId");
        fieldNames.add("checksum");
        fieldNames.add("value");
        fieldNames.add("relationshipManager");
        fieldNames.add("branchCode");
        fieldNames.add("id");
        fieldNames.add("valor");
        fieldNames.add("iban");


        word1.add("Client");
        word1.add("Partner");
        word1.add("Employee");
        word1.add("Manager");
        word1.add("Money");

        word2.add("Routing");
        word2.add("Transfer");
        word2.add("Credit");
        word2.add("Debit");
        word2.add("Source");
        word2.add("Destination");
        word2.add("Decision");
        word2.add("Settlement");

        word3.add("Account");
        word3.add("Instrument");
        word3.add("Group");
        word3.add("Container");
        word3.add("Pool");

        word4.add("GHN");
        word4.add("RGB");
        word4.add("ZZH");
        word4.add("BGD");
        word4.add("RTZ");
        word4.add("SDV");
        word4.add("ERF");
        word4.add("SER");

    }

    public static Map<String,Object> objectTypes = new HashMap<>();
    public static Map<String,Set<String>> removeList = new HashMap<>();
    public static Map<String,Set<String>> maskList = new HashMap<>();


    public static Map<String,Object> createObjectOfType(String type){
        Map<String,Object> output = new HashMap<>();
        Map<String,Object>  o = (Map<String, Object>) objectTypes.get(type);

        output = buildObjectWithDef(output,o);



        return  output;
    }

    public static Map<String,Object> buildObjectWithDef(Map<String,Object> object, Map<String,Object>  def){

        for(Map.Entry<String, Object> defEntry : def.entrySet()){
            if(defEntry.getValue() instanceof String){
                object.put(defEntry.getKey(),randPathString());

            } else {
                Map<String,Object> node = buildObjectWithDef(new HashMap<>(), (Map<String, Object>) defEntry.getValue());
                object.put(defEntry.getKey(),node);

            }
        }
        return object;
    }

    public static void populateNode(Map<String,Object> node, int depth, String typeName, String path){
        depth++;
        int baseFields = r.nextInt(4)  + 2;
        for(int i=0;i<baseFields;i++){
            String fieldName = randField();
            if(r.nextInt(100) < 5) {
                node.put(fieldName, "remove");
                removeList.get(typeName).add(path + "/" + fieldName);
            } else if(r.nextInt(100)< 15){
                node.put(fieldName,"mask");
                maskList.get(typeName).add(path + "/" + fieldName);
            } else {
                node.put(fieldName,"field");
            }
        }

        for(int i=0;i<baseFields-1;i++){
            if(doCreateSubNode(depth)){
                String nodeName = randWord();
                System.out.println("Creating subnode at depth [" + depth +"] @ ["+ path +"/" + nodeName +"]");
                Map<String,Object> newNode = new HashMap<>();
                populateNode(newNode,depth++,typeName,path +"/" + nodeName);
                node.put(nodeName,newNode);
            }
        }

    }

    public static boolean doCreateSubNode(int depth){
        if(depth >= 4){
            return false;
        }
        return (r.nextInt(100)> (35 / depth));
    }

    public static String randField(){
        return fieldNames.get(r.nextInt(fieldNames.size()));
    }

    public static String randPathString() {
        return randNumber() + word4.get(r.nextInt(word4.size())) + randNumber();
    }

    public static String randWord(){
        String one = word1.get(r.nextInt(word1.size()));
        String two = word2.get(r.nextInt(word2.size()));
        String three = word3.get(r.nextInt(word3.size()));
        return one+two+three;
    }

    public static String randNumber(){
        return String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
    }

    public Generator(@Qualifier("TestExecutor") ThreadPoolTaskExecutor executor, @Qualifier("TestScheduler") ThreadPoolTaskScheduler scheduler, Pipeline pipeline, Filter filter) {
        // Create 5 possible types
        for(int x=0; x<5;x++){
            String typeName = randWord();
            removeList.put(typeName,new HashSet<>());
            maskList.put(typeName,new HashSet<>());

            Map<String,Object> root = new HashMap<>();
            System.out.println("Creating type ["  + x +"] - ["+ typeName +"]");
            populateNode(root,0, typeName,"");
            objectTypes.put(typeName,root);

        }

        for(String objectTypeName : objectTypes.keySet()){
            Map<String, Object> objectOfType = createObjectOfType(objectTypeName);
        }

        CronTrigger trigger = new CronTrigger("*/3 * * * * *");

//        scheduler.schedule(new Runnable() {
//            @Override
//            public void run() {
//                CountryCode country = CountryCodes.getRandom();
//                String number =  String.valueOf((int) ((Math.random() * (99999999 - 10000000)) + 10000000));
//                String target = CountryCodes.addStyle(country.getCountryCode()) + country.getCountryCode() + number;
//                String message = messages.get(r.nextInt(messages.size()));
//                message = Base64.getEncoder().encodeToString(message.getBytes(StandardCharsets.UTF_8));
//                pipeline.executePipeline(target + ";" + message);
//            }
//        },trigger);

        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                String target = String.valueOf(objectTypes.keySet().toArray()[r.nextInt(objectTypes.keySet().size())]);
                Map<String, Object> objectOfType = createObjectOfType(target);
                ObjectMapper mapper = new ObjectMapper();

                try {
                    filter.executePipeline(target,mapper.writeValueAsString(objectOfType));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        },trigger);


    }





}
