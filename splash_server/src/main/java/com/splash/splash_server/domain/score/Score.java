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

    @Column
    private int dayTotalScore;

    @Column
    private double dayAverage;


    @Column
    private String semester;

    @Column
    private int week;

    @Column
    private int ranks;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userKey")
    private User user;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Score(int firstScore, int secondScore, int thirdScore, int dayTotalScore, String semester, int week, User user) {
        this.firstScore = firstScore;
        this.secondScore = secondScore;
        this.thirdScore = thirdScore;
        this.dayTotalScore = dayTotalScore;
        this.semester = semester;
        this.week = week;
        this.user = user;
    }
}
