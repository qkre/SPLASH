package com.splash.splash_server.domain.score;

import com.splash.splash_server.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "score")
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

    @Builder
    public Score(String date, int score, User user) {
        this.date = date;
        this.score = score;
        this.user = user;
    }
}
