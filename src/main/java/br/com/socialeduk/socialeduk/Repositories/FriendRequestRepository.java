package br.com.socialeduk.socialeduk.Repositories;

import br.com.socialeduk.socialeduk.Entities.FriendRequest;
import br.com.socialeduk.socialeduk.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {

    FriendRequest findBySenderAndReceiver(User sender, User receiver);
    List<FriendRequest> findBySenderId(Long sender_id);
    List<FriendRequest> findByReceiverId(Long receiver_id);
    FriendRequest getFriendRequestById(Long id);

}
