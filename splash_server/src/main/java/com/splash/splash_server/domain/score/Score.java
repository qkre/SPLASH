package com.splash.splash_server.domain.score;

import com.splash.splash_server.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "score")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Score {
    @Id
    @Column(name = "scoreKey")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scoreKey;

    @Column(name = "score")
    private int score;

    @Column(name = "date")
    private String date;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "userKey")
    private User user;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Score(String date, int score, User user) {
        this.date = date;
        this.score = score;
        this.user = user;
    }
}
