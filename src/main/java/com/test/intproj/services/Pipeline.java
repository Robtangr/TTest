package com.test.intproj.services;
import com.test.intproj.CountryDecoder;
import com.test.intproj.MessageEncoder;
import com.test.intproj.TargetDecoder;
import com.test.intproj.TextDecoder;
import com.test.intproj.dto.OutputMessage;
import com.test.intproj.services.ref.CountryCodeService;
import com.test.intproj.services.ref.Sender;
import com.test.intproj.dto.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class Pipeline {
    private final Logger LOG = Logger.getLogger(Pipeline.class.getName());

    @Autowired
    private Sender sender;

    @Autowired
    @Qualifier("ReferenceTargetDecoder")
    private TargetDecoder targetDecoder;

    @Autowired
    @Qualifier("ReferenceCountryDecoder")
    private CountryDecoder countryDecoder;

    @Autowired
    @Qualifier("ReferenceTextDecoder")
    private TextDecoder textDecoder;

    @Autowired
    @Qualifier("ReferenceMessageEncoder")
    private MessageEncoder encoder;

    @Autowired
    @Qualifier("countryCodeService")
    private CountryCodeService countryCodeService;

    public Pipeline(){
    }

    public void executePipeline(String message)  {
        LOG.info("Processing message : " + message);
        try {
//          Example Message
//          +21312099182;VHJhbnNhY3Rpb24gQmxvY2tlZA==

            String target = targetDecoder.decode(message);

            String decodedMessage = textDecoder.decode(message);

            CountryCode countryCode = countryDecoder.decode(message);

            OutputMessage messageToSend = new OutputMessage(countryCode.getCountryCode(), countryCode.getCountryName(), decodedMessage,target);

            String toSend = encoder.encode(messageToSend);


           /*
            {
                "countryCode":"",
                "countryName":"",
                "message": "",
                "target": ""
            }
           */

            sender.send(toSend);
        } catch (Exception e) {
            LOG.severe("Failed to send : " + e.getMessage());
        }
    }
}
