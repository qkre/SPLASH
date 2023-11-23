package com.splash.splash_server.domain.score;

import com.splash.splash_server.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "totalScore")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class TotalScore {
    @Id
    @Column(name = "totalScoreKey")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long totalScoreKey;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "userKey")
    private User user;

    @Column
    private int totalScore;

    @Column
    private int played;

    @Column
    private double average;

    @CreatedDate
    @Column
    private LocalDateTime createdAt;

    @Builder
    public TotalScore(User user, int totalScore, int played, double average) {
        this.user = user;
        this.totalScore = totalScore;
        this.played = played;
        this.average = average;
    }
}
