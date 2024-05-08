package com.project.EzyWallet.TransactionService.service;

import com.project.EzyWallet.TransactionService.configuration.MailSenderService;
import com.project.EzyWallet.TransactionService.constants.Constants;
import com.project.EzyWallet.TransactionService.constants.TransactionStatus;
import com.project.EzyWallet.TransactionService.dao.TransactionDao;
import com.project.EzyWallet.TransactionService.dto.InitiateTransactionRequestModel;
import com.project.EzyWallet.TransactionService.dto.WalletBalanceUpdateReauestModel;
import com.project.EzyWallet.TransactionService.entity.Transaction;
import com.project.EzyWallet.TransactionService.entity.User;
import com.project.EzyWallet.TransactionService.exception.TransactionFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    WalletServiceFeign walletService;

    @Autowired
    UserServiceFeign userService;

    @Autowired
    TransactionDao transactionDao;

    @Autowired
    MailSenderService mailSender;


    public TransactionStatus initiateTransaction(InitiateTransactionRequestModel request) throws Exception {
        String sender = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPhone();
        String receiver = request.getReceiver();
        double amount = request.getAmount();
        String purpose = request.getPurpose();
        if(sender.equals(receiver)){
            Transaction transaction = Transaction.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .amount(amount)
                    .purpose(purpose)
                    .status(TransactionStatus.FAILED)
                    .build();
            transactionDao.save(transaction);
            throw new TransactionFailureException("Can't send money to self.");
        }


        ResponseEntity<Map<String, Object>> response = walletService.updateBalance(WalletBalanceUpdateReauestModel.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .amount(amount)
                    .build());

            Map<String, Object> map = response.getBody();
            Transaction transaction = Transaction.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .amount(amount)
                    .purpose(purpose)
                    .status((map.get("status") == (Integer) 0) ? TransactionStatus.FAILED : TransactionStatus.SUCCESS)
                    .build();

            if (map.get("status") == (Integer) 0) {
                transactionDao.save(transaction);
                throw new TransactionFailureException((String) map.get("error"));
            } else {
                String senderEmail = userService.getEmailFromPhone(sender);
                Map<String, Object> mailToSender = new HashMap<>();
                mailToSender.put("to", senderEmail);
                mailToSender.put("subject", "EzyWallet: Transaction successful.");
                mailToSender.put("body", "Successfully sent ₹" + amount + " to " + receiver + "'s account.");
                mailSender.sendMail(Constants.MAIL_SENDER_TOPIC, mailToSender);


                String receiverEmail = userService.getEmailFromPhone(receiver);
                Map<String, Object> mailToReceiver = new HashMap<>();
                mailToReceiver.put("to", receiverEmail);
                mailToReceiver.put("subject", "EzyWallet: Amount received.");
                mailToReceiver.put("body", "You have successfully received ₹" + amount + " from " + sender + "'s account.");
                mailSender.sendMail(Constants.MAIL_SENDER_TOPIC, mailToReceiver);

                transactionDao.save(transaction);
                return TransactionStatus.SUCCESS;
            }
    }

    public List<Transaction> findAllUserTransactions(int offset, int size){
        List<Transaction> list = transactionDao.findTransactionsInvolvingUser(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPhone(), PageRequest.of(offset, size).withSort(Sort.Direction.DESC, "createdOn"));
        return list;
    }
}
