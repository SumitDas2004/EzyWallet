package com.project.EzyWallet.WalletService.service;

import com.project.EzyWallet.WalletService.Entity.User;
import com.project.EzyWallet.WalletService.Entity.Wallet;
import com.project.EzyWallet.WalletService.constants.KafkaTopicNames;
import com.project.EzyWallet.WalletService.dto.WalletBalanceUpdateReauestModel;
import com.project.EzyWallet.WalletService.exception.InsufficiantBalanceException;
import com.project.EzyWallet.WalletService.exception.ReceiverWalletDoesNotExistException;
import com.project.EzyWallet.WalletService.exception.SenderWalletDoesNotExistException;
import com.project.EzyWallet.WalletService.exception.WalletAlreadyExistsException;
import com.project.EzyWallet.WalletService.repository.WalletDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    WalletDao walletDao;

    @Value("${wallet.initial.balance}")
    int initialBalance;

    @KafkaListener(topics = KafkaTopicNames.USER_WALLET_TOPIC, groupId = "create_wallet_request_consumer_group")
    public void create(String phone) throws WalletAlreadyExistsException {
        if(walletDao.findByPhone(phone)==null) {
            walletDao.save(Wallet.builder().phone(phone).balance(initialBalance).build());
            System.out.println("Wallet creation successful.");
        }else throw new WalletAlreadyExistsException();
    }

    @Transactional
    public void updateWallet(WalletBalanceUpdateReauestModel request) throws InsufficiantBalanceException, ReceiverWalletDoesNotExistException, SenderWalletDoesNotExistException {
        String sender = request.getSender();
        String receiver = request.getReceiver();
        double amount = request.getAmount();
        Wallet senderWallet = walletDao.findByPhone(sender);
        if(senderWallet==null)throw new SenderWalletDoesNotExistException();

        if(senderWallet.getBalance()<request.getAmount())throw new InsufficiantBalanceException();

        Wallet receiverWallet = walletDao.findByPhone(receiver);
        if(receiverWallet==null)throw new ReceiverWalletDoesNotExistException();

        senderWallet.setBalance(senderWallet.getBalance()-amount);
        receiverWallet.setBalance(receiverWallet.getBalance()+amount);

        walletDao.save(senderWallet);
        walletDao.save(receiverWallet);
    }
    public boolean recharge(int amount){
        try {
            String user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPhone();
            walletDao.recharge(user, amount);
            return true;
        }catch(Exception e){
            System.out.println(e.toString());
            return false;
        }
    }

    public Wallet getWallet(String phone) {
        return walletDao.findByPhone(phone);
    }
}
