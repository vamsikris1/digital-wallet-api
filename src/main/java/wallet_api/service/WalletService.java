package wallet_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wallet_api.entity.Transaction;
import wallet_api.entity.User;
import wallet_api.entity.Wallet;
import wallet_api.repository.TransactionRepository;
import wallet_api.repository.UserRepository;
import wallet_api.repository.WalletRepository;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Double getBalance(String email) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return null;
        }

        Wallet wallet = walletRepository.findByUser(user).orElse(null);

        if (wallet == null) {
            return null;
        }

        return wallet.getBalance();
    }

    public String deposit(String email, Double amount) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return "User not found";
        }

        Wallet wallet = walletRepository.findByUser(user).orElse(null);

        if (wallet == null) {
            return "Wallet not found";
        }

        wallet.setBalance(wallet.getBalance() + amount);

        walletRepository.save(wallet);

        return "Amount deposited successfully";
    }

    public String withdraw(String email, Double amount) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return "User not found";
        }

        Wallet wallet = walletRepository.findByUser(user).orElse(null);

        if (wallet == null) {
            return "Wallet not found";
        }

        if (wallet.getBalance() < amount) {
            return "Insufficient balance";
        }

        wallet.setBalance(wallet.getBalance() - amount);

        walletRepository.save(wallet);

        return "Amount withdrawn successfully";
    }

    @Transactional
    public String transferMoney(String senderEmail, String receiverEmail, Double amount) {

        User sender = userRepository.findByEmail(senderEmail).orElse(null);

        if (sender == null) {
            return "Sender not found";
        }

        User receiver = userRepository.findByEmail(receiverEmail).orElse(null);

        if (receiver == null) {
            return "Receiver not found";
        }

        Wallet senderWallet = walletRepository.findByUser(sender).orElse(null);
        Wallet receiverWallet = walletRepository.findByUser(receiver).orElse(null);

        if (senderWallet == null || receiverWallet == null) {
            return "Wallet not found";
        }

        if (senderWallet.getBalance() < amount) {
            return "Insufficient balance";
        }

        senderWallet.setBalance(senderWallet.getBalance() - amount);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transaction transaction = new Transaction();
        transaction.setSender(sender.getEmail());
        transaction.setReceiver(receiver.getEmail());
        transaction.setAmount(amount);
        transaction.setType("TRANSFER");
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return "Money transferred successfully";
    }

    public List<Transaction> getTransactionHistory(String email) {

        return transactionRepository.findBySenderOrReceiver(email, email);
    }
}