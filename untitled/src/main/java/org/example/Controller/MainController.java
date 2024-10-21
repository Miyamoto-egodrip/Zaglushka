package org.example.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Model.RequestDTo;
import org.example.Model.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.RoundingMode;
import java.util.Random;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Random;

@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTo requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String rqUID = requestDTO.getRqUID();
            String country="Ru";
            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000);
                country="US";
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000);
                country="EU";
            } else {
                maxLimit = new BigDecimal(10000);
            }
            Random random = new Random();
            BigDecimal Balance = new BigDecimal(random.nextDouble()).multiply(maxLimit);
            Balance = Balance.setScale(2, RoundingMode.HALF_UP);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(country);
            responseDTO.setBalance(Balance);
            responseDTO.setMaxlimit(maxLimit);

            log.info("*** RequestUID***" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.info("*** ResponseUID***" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
