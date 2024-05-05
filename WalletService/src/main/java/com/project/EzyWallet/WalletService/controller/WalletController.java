package com.project.EzyWallet.WalletService.controller;

import com.project.EzyWallet.WalletService.Entity.User;
import com.project.EzyWallet.WalletService.dto.WalletBalanceUpdateReauestModel;
import com.project.EzyWallet.WalletService.exception.InsufficiantBalanceException;
import com.project.EzyWallet.WalletService.exception.ReceiverWalletDoesNotExistException;
import com.project.EzyWallet.WalletService.exception.SenderWalletDoesNotExistException;
import com.project.EzyWallet.WalletService.exception.WalletAlreadyExistsException;
import com.project.EzyWallet.WalletService.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WalletController {
    @Autowired
    WalletService walletService;

    //Methods to be used by internal microservices only
    @PostMapping("/updateBalance")
    public ResponseEntity<?> updateBalance(@RequestBody WalletBalanceUpdateReauestModel requestModel) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("message", "Wallet updated successfully.");
            walletService.updateWallet(requestModel);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (SenderWalletDoesNotExistException | ReceiverWalletDoesNotExistException |
                 InsufficiantBalanceException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create() throws WalletAlreadyExistsException {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("message", "Wallet created successfully.");

            Map<String, Object> requestMap = new HashMap<>();
            walletService.create(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPhone());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (WalletAlreadyExistsException e){
            Map<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getWallet")
    public ResponseEntity<?> getBalnce(){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Success");
        map.put("data", walletService.getWallet(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPhone()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/recharge/{amount}")
    public ResponseEntity<?> recharge(@PathVariable int amount){
        boolean isSuccess = walletService.recharge(amount);
        Map<String, Object> map = new HashMap<>();
        if(isSuccess){
            map.put("status", 1);
            map.put("message", "Recharge successful.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else {
            map.put("status", 0);
            map.put("error", "Recharge Failed.");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerError(Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("error", "Internal server error.");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
