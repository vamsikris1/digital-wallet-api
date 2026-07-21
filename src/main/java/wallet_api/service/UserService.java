package wallet_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import wallet_api.dto.LoginDTO;
import wallet_api.dto.LoginResponseDTO;
import wallet_api.dto.UserRegisterDTO;
import wallet_api.entity.User;
import wallet_api.entity.Wallet;
import wallet_api.repository.UserRepository;
import wallet_api.repository.WalletRepository;
import wallet_api.util.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;

    public String register(UserRegisterDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return "Email already exists";
        }

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER");

        user = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setBalance(0);
        wallet.setUser(user);

        walletRepository.save(wallet);

        return "User Registered Successfully";
    }
    
    public LoginResponseDTO login(LoginDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail()).orElse(null);

        if (user == null) {
            return null;
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return null;
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponseDTO(token);
    }

}