package br.com.socialeduk.socialeduk.Services;

import br.com.socialeduk.socialeduk.Dto.LoginRequestDto;
import br.com.socialeduk.socialeduk.Entities.FriendRequest;
import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Repositories.FriendRequestRepository;
import br.com.socialeduk.socialeduk.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    public UserService(UserRepository userRepository, FriendRequestRepository friendRequestRepository){
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
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

    public User authenticate(LoginRequestDto loginRequest) {

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

    public boolean sendFriendRequest(Long id, Long idFriend){
        User user = getUserById(id);
        User friend = getUserById(idFriend);

        if(friendRequestRepository.findBySenderAndReceiver(user, friend) != null){
            throw new RuntimeException("Friend request already sent!");
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(user);
        friendRequest.setReceiver(friend);

        friendRequestRepository.save(friendRequest);
        return true;
    }

    public List<FriendRequest> getSendFriendsRequest(Long id){
        User user = getUserById(id);
        return friendRequestRepository.findBySenderId(user.getId());
    }

    public List<FriendRequest> getReceivedFriendsRequest(Long id){
        User user = getUserById(id);
        return friendRequestRepository.findByReceiverId(user.getId());
    }
}
