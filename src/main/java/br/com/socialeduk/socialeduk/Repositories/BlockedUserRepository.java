package br.com.socialeduk.socialeduk.Repositories;

import br.com.socialeduk.socialeduk.Entities.BlockedUser;
import br.com.socialeduk.socialeduk.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {
    BlockedUser findByUserIdAndBlockedUserId(Long user_id, Long blocked_id);

//    @Query("select f.friend from Friend f where f.user.id = :userId")
    @Query("select b.blockedUser from BlockedUser b where b.user.id = :userId")
    List<User> findBlockedUsersByUserId(@Param("userId") Long userId);


    @Query("select b.user from BlockedUser b where b.blockedUser.id = :userId")
    List<User> findUsersByBlockedUserId(@Param("userId") Long userId);


}
