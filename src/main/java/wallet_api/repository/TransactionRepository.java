package wallet_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wallet_api.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findBySenderOrReceiver(String sender, String receiver);

}