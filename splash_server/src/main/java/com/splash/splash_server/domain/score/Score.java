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
@Table(name = "score")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Score {
    @Id
    @Column(name = "scoreKey")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreKey;

    @Column(name = "firstScore")
    private int firstScore;

    @Column(name = "secondScore")
    private int secondScore;

    @Column(name = "thirdScore")
    private int thirdScore;

    @Column(name = "date")
    private String date;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "userKey")
    private User user;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Score(int firstScore, int secondScore, int thirdScore, String date, User user) {
        this.firstScore = firstScore;
        this.secondScore = secondScore;
        this.thirdScore = thirdScore;
        this.date = date;
        this.user = user;
    }
}
