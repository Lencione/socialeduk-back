package br.com.socialeduk.socialeduk.Repositories;

import br.com.socialeduk.socialeduk.Entities.Friend;
import br.com.socialeduk.socialeduk.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Friend findByUserIdAndFriendId(Long userId, Long friendId);
    List<Friend> findByUserId(Long userId);
    List<Friend> findByFriendId(Long friendId);
    Friend getFriendById(Long id);

    @Query("select f.friend from Friend f where f.user.id = :userId")
    List<User> findFriendsByUserId(@Param("userId") Long userId);

}

