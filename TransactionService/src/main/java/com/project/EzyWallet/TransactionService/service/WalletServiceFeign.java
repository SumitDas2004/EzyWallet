package com.project.EzyWallet.TransactionService.service;

import com.project.EzyWallet.TransactionService.dto.WalletBalanceUpdateReauestModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("WALLET")
public interface WalletServiceFeign {
    @PostMapping("/updateBalance")
    ResponseEntity<Map<String, Object>> updateBalance(@RequestBody WalletBalanceUpdateReauestModel requestModel);
}
