package br.com.socialeduk.socialeduk.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name =  "blocked_users")
public class BlockedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "blocked_id")
    private User blockedUser;

    @Column(name = "created_at")
    private String created_at;
}
