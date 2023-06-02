package br.com.socialeduk.socialeduk.Services;

import br.com.socialeduk.socialeduk.Dto.AcceptAndRefuseFriendRequestDto;
import br.com.socialeduk.socialeduk.Dto.LoginRequestDto;
import br.com.socialeduk.socialeduk.Entities.Friend;
import br.com.socialeduk.socialeduk.Entities.FriendRequest;
import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Repositories.FriendRepository;
import br.com.socialeduk.socialeduk.Repositories.FriendRequestRepository;
import br.com.socialeduk.socialeduk.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository  friendRepository;
    public UserService(UserRepository userRepository, FriendRequestRepository friendRequestRepository, FriendRepository friendRepository){
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.friendRepository = friendRepository;
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

    public boolean refuseFriendRequest(AcceptAndRefuseFriendRequestDto request){

        FriendRequest friendRequest = friendRequestRepository.getFriendRequestById(request.getFriendRequestId());
        if(friendRequest == null){
            throw new RuntimeException("Friend request not found");
        }
        if(!friendRequest.getReceiver().getId().equals(request.getUserId())){
            throw new RuntimeException("Invalid user");
        }
        friendRequestRepository.delete(friendRequest);
        return true;
    }

    public boolean acceptFriendRequest(AcceptAndRefuseFriendRequestDto request){
        FriendRequest friendRequest = friendRequestRepository.getFriendRequestById(request.getFriendRequestId());
        if(friendRequest == null){
            throw new RuntimeException("Friend request not found");
        }
        if(!friendRequest.getReceiver().getId().equals(request.getUserId())){
            throw new RuntimeException("Invalid user");
        }
        Friend friendship = new Friend();
        friendship.setUser(friendRequest.getSender());
        friendship.setFriend(friendRequest.getReceiver());
        friendRepository.save(friendship);

        friendship = new Friend();
        friendship.setUser(friendRequest.getReceiver());
        friendship.setFriend(friendRequest.getSender());
        friendRepository.save(friendship);

        friendRequestRepository.delete(friendRequest);

        return true;
    }

    public List<User> getFriends(Long id){
        User user = getUserById(id);
        return friendRepository.findFriendsByUserId(user.getId());
    }
}
