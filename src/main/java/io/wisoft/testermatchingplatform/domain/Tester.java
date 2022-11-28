package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.LoginException;
import io.wisoft.testermatchingplatform.handler.exception.PointException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Tester {
    @Id
    @GeneratedValue
    @Column(name = "tester_id")
    private UUID id;

    @Column(unique = true)
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @Column(unique = true)
    @NotNull
    private String nickname;

    @NotNull
    private String phone;

    @NotNull
    private String introduce;
    @NotNull
    private long point;
    @NotNull
    private String account;

    /**
     * 정적 생성자 메서드
     */
    public static Tester newInstance(
            String email,
            String password,
            String nickname,
            String phone,
            String introduce
    ) {
        Tester tester = new Tester();
        tester.email = email;
        tester.password = password;
        tester.nickname = nickname;
        tester.phone = phone;
        tester.introduce = introduce;
        tester.point = 0L;
        tester.account = "";
        return tester;
    }

    /**
     * 비지니스 로직
     */

    public void login(String email, String password) {
        if (!this.email.equals(email) || !this.password.equals(password)) {
            throw new LoginException();
        }
    }

    public void changeAccount(String account) {
        this.account = account;
    }

    public void depositPoint(long point) {
        checkPoint(point);
        mockDepositPoint(point);
    }

    private void mockDepositPoint(long point) {
        if (this.point < point) {
            throw new PointException();
        }
        System.out.println("계좌로 포인트 " + point*19/20 + "만큼 충전합니다.!!");
        this.point -= point;
    }

    public void rewardPoint(long point) {
        checkPoint(point);
        this.point += point;
    }

    private void checkPoint(long point) {
        if (point <= 0) {
            throw new PointException("음수와 0은 들어갈 수 없습니다.");
        }
    }

}
