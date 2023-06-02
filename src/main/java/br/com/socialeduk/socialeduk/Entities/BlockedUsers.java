package br.com.socialeduk.socialeduk.Entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

@Entity
@Data
@Table(name =  "blocked_users")
public class BlockedUsers{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "blocked_id")
    private User friend;

    @Column(name = "created_at")
    private String created_at;
}
