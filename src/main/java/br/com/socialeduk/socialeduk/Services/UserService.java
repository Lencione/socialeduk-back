package br.com.socialeduk.socialeduk.Services;

import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(User user){
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new RuntimeException("Nome de usuário já existe!");
        }

        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("Email já cadastrado!");
        }

        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User findById(long id){
        return userRepository.getUserById(id);
    }
}
