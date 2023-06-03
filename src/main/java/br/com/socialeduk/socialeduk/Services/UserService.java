package br.com.socialeduk.socialeduk.Services;

import br.com.socialeduk.socialeduk.Dto.AcceptAndRefuseFriendRequestDto;
import br.com.socialeduk.socialeduk.Dto.LoginRequestDto;
import br.com.socialeduk.socialeduk.Entities.BlockedUser;
import br.com.socialeduk.socialeduk.Entities.Friend;
import br.com.socialeduk.socialeduk.Entities.FriendRequest;
import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Repositories.BlockedUserRepository;
import br.com.socialeduk.socialeduk.Repositories.FriendRepository;
import br.com.socialeduk.socialeduk.Repositories.FriendRequestRepository;
import br.com.socialeduk.socialeduk.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository  friendRepository;

    private final BlockedUserRepository  blockedUserRepository;
    public UserService(UserRepository userRepository, FriendRequestRepository friendRequestRepository, FriendRepository friendRepository, BlockedUserRepository blockedUserRepository){
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.friendRepository = friendRepository;
        this.blockedUserRepository = blockedUserRepository;
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

        if(user.getId().equals(friend.getId())){
            throw new RuntimeException("You can't send a friend request to yourself!");
        }

        if(friendRepository.findByUserIdAndFriendId(user.getId(), friend.getId()) != null){
            throw new RuntimeException("You are already friends!");
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

    public boolean blockUser(Long user_id, Long blocked_user_id){
        User user = getUserById(user_id);
        User blockedUser = getUserById(blocked_user_id);

        if(blockedUserRepository.findByUserIdAndBlockedUserId(user.getId(), blockedUser.getId()) != null){
            throw new RuntimeException("User already blocked");
        }

        if(blockedUser.getId().equals(user.getId())){
            throw new RuntimeException("You can't block yourself");
        }

        Friend friendship = friendRepository.findByUserIdAndFriendId(user.getId(), blockedUser.getId());
        if( friendship != null){
            friendRepository.delete(friendship);
        }
        friendship = friendRepository.findByUserIdAndFriendId(blockedUser.getId(), user.getId());
        if( friendship != null){
            friendRepository.delete(friendship);
        }

        BlockedUser blockedUserEntity = new BlockedUser();
        blockedUserEntity.setUser(user);
        blockedUserEntity.setBlockedUser(blockedUser);
        blockedUserRepository.save(blockedUserEntity);

        return true;
    }

    public boolean unblockUser(Long user_id, Long blocked_user_id){
        User user = getUserById(user_id);
        User blockedUser = getUserById(blocked_user_id);

        BlockedUser blockedUserEntity = blockedUserRepository.findByUserIdAndBlockedUserId(user.getId(), blockedUser.getId());
        if(blockedUserEntity == null){
            throw new RuntimeException("User not blocked");
        }
        blockedUserRepository.delete(blockedUserEntity);

        return true;
    }

    public List<User> getBlockedUsers(Long id){
        User user = getUserById(id);
        return blockedUserRepository.findBlockedUsersByUserId(user.getId());
    }

    public List<User> getNotBlockedUsers(Long id, String name){
        User user = getUserById(id);

        List<User> blockedUsers = blockedUserRepository.findBlockedUsersByUserId(user.getId());
        List<User> userBlockedMe = blockedUserRepository.findUsersByBlockedUserId(user.getId());
        List<User> users = userRepository.findAll();

        if (name == null || name.equals("")){
            return users.stream().filter(u ->
                    !blockedUsers.contains(u) && !userBlockedMe.contains(u) && !u.getId().equals(user.getId())
            ).collect(Collectors.toList());
        }

        return users.stream().filter(u ->
           !blockedUsers.contains(u) && !userBlockedMe.contains(u) && !u.getId().equals(user.getId())  && u.getUsername().toLowerCase().contains(name.toLowerCase())
       ).collect(Collectors.toList());

    }
}
