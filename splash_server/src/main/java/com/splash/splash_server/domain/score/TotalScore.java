package com.splash.splash_server.domain.score;

import com.splash.splash_server.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "totalScore")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class TotalScore {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long totalScoreKey;

    @Column
    private int totalScore;

    @Column
    private String semester;

    @Column
    private int played;

    @Column
    private double average;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userKey")
    private User user;

    @CreatedDate
    @Column
    private LocalDateTime createdAt;

    @Builder
    public TotalScore(User user) {
        this.user = user;
    }
}
