package br.com.socialeduk.socialeduk.Services;

import br.com.socialeduk.socialeduk.Dto.LoginRequest;
import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(User user){
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new RuntimeException("Name already exists!");
        }

        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("E-mail already exists!");
        }

        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User authenticate(LoginRequest loginRequest) {

        User user = findByEmail(loginRequest.getEmail());
        if(user == null || !user.getPassword().equals(loginRequest.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        User user = userRepository.getUserById(id);

        if(user == null){
            throw new RuntimeException("User not found");
        }
        return user;

    }
}
