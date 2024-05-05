package com.project.EzyWallet.TransactionService.controller;

import com.project.EzyWallet.TransactionService.constants.TransactionStatus;
import com.project.EzyWallet.TransactionService.dto.InitiateTransactionRequestModel;
import com.project.EzyWallet.TransactionService.exception.TransactionFailureException;
import com.project.EzyWallet.TransactionService.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TransactionController {
    @Autowired
    TransactionService service;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiate(@RequestBody InitiateTransactionRequestModel request) throws Exception {
        TransactionStatus status = service.initiateTransaction(request);
            Map<String, Object> map= new HashMap<>();
            map.put("status", 1);
            map.put("message", "Successfully sent ₹"+request.getAmount()+" to "+request.getReceiver());
            return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/getTransactions")
    ResponseEntity<?> getTrnasactionPage(@RequestParam(name="offset") int pageNumber, @RequestParam(name="size") int pageSize){
        return new ResponseEntity<>(service.findAllUserTransactions(pageNumber, pageSize), HttpStatus.OK);
    }


    @ExceptionHandler(TransactionFailureException.class)
    public ResponseEntity<?>  transactionFailureException(TransactionFailureException e){

        Map<String, Object> map= new HashMap<>();
        map.put("status", 0);
        map.put("message", "Transaction failed: "+e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?>  internalServerError(Exception e){
        System.out.println(e.toString());
            Map<String, Object> map= new HashMap<>();
            map.put("status", 0);
            map.put("message", "Internal server error.");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
