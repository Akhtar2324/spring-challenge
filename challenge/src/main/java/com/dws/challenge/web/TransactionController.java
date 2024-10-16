package com.dws.challenge.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dws.challenge.domain.Account;
import com.dws.challenge.request.dto.TransferRequest;
import com.dws.challenge.service.AccountsService;
import com.dws.challenge.service.EmailNotificationService;
import com.dws.challenge.service.TransactionService;

import jakarta.validation.Valid;

/**
 * Hanlde the transactions related to account
 */
@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final EmailNotificationService notificationService;
    private final AccountsService accountsService;

    public TransactionController(TransactionService transactionService, EmailNotificationService notificationService, 
                            AccountsService accountsService) {
        this.transactionService = transactionService;
        this.notificationService = notificationService;
        this.accountsService = accountsService;
    }

    @PostMapping("/transfer")
    public String transfer(@Valid @RequestBody TransferRequest request) {
        this.transactionService.transfer(request);
        
        Account fromAccount = accountsService.getAccount(request.getAccountFromId());
        Account toAccount = accountsService.getAccount(request.getAccountToId());

        // Send notifications
        this.notificationService.notifyAboutTransfer(fromAccount, "Transferred Money");
        this.notificationService.notifyAboutTransfer(toAccount, "Recieved Money");

         return "success";
    }

    /* 
      We can mask account id before pass it to notification service
      to hide accountIds
    private String maskAccountId(Optional<String> accountId) {
        if (accountId.isPresent()) {
            String accIdStr = accountId.get();
            String maskedPart = accIdStr.substring(4).replaceAll(".", "*");
            return accIdStr.substring(0, 4) + maskedPart;
        }
        return null;
    }*/ 
}
