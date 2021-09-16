package com.test.intproj.services.ref;

import com.test.intproj.services.Pipeline;
import com.test.intproj.dto.CountryCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Component
public class Receiver {
    private final Random r = new Random(System.currentTimeMillis());

    public static final List<String> messages = new ArrayList<>();
    static {
        messages.add("Amount Credited");
        messages.add("Amount Debited");
        messages.add("Limit Exceeded");
        messages.add("Transaction Blocked");
        messages.add("Transaction delayed");

    }

    public Receiver(@Qualifier("TestExecutor") ThreadPoolTaskExecutor executor, @Qualifier("TestScheduler") ThreadPoolTaskScheduler scheduler, Pipeline pipeline) {
        CronTrigger trigger = new CronTrigger("*/3 * * * * *");

        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                CountryCode country = CountryCodes.getRandom();
                String number =  String.valueOf((int) ((Math.random() * (99999999 - 10000000)) + 10000000));
                String target = CountryCodes.addStyle(country.getCountryCode()) + country.getCountryCode() + number;
                String message = messages.get(r.nextInt(messages.size()));
                message = Base64.getEncoder().encodeToString(message.getBytes(StandardCharsets.UTF_8));
                pipeline.executePipeline(target + ";" + message);
            }
        },trigger);

    }



}
