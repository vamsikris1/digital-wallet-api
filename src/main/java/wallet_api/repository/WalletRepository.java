package wallet_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wallet_api.entity.User;
import wallet_api.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUser(User user);

}