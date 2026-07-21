package wallet_api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wallet_api.service.WalletService;
import wallet_api.dto.AmountDTO;
import wallet_api.dto.TransferDTO;
import wallet_api.entity.Transaction;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/balance")
    public Double getBalance(Principal principal) {

        return walletService.getBalance(principal.getName());
    }
    

    @PostMapping("/deposit")
    public String deposit(Principal principal,
                          @RequestBody AmountDTO dto) {

        return walletService.deposit(
                principal.getName(),
                dto.getAmount());
    }
    
    @PostMapping("/withdraw")
    public String withdraw(Principal principal,
                           @RequestBody AmountDTO dto) {

        return walletService.withdraw(
                principal.getName(),
                dto.getAmount());
    }
    
    @PostMapping("/transfer")
    public String transfer(Principal principal,
                           @RequestBody TransferDTO dto) {

        return walletService.transferMoney(
                principal.getName(),
                dto.getReceiverEmail(),
                dto.getAmount());
    }
    
    @GetMapping("/transactions")
    public List<Transaction> getTransactionHistory(Principal principal) {

        return walletService.getTransactionHistory(principal.getName());
    }
}