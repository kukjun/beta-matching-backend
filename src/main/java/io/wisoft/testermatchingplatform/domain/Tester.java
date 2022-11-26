package io.wisoft.testermatchingplatform.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String email;
    private String password;

    @Column(unique = true)
    private String nickname;

    private String phone;
    private String introduce;
    private long point;
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
            throw new RuntimeException("해당 id, password를 가지는 사용자가 없습니다.");
        }
    }

    public void changeAccount(String account) {
        this.account = account;
    }

    public void mockDepositPoint(long point) {
        checkPoint(point);
        if (this.point < point) {
            throw new RuntimeException("잔여 포인트가 부족합니다.");
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
            throw new RuntimeException("음수와 0은 들어갈 수 없습니다.");
        }
    }

}
