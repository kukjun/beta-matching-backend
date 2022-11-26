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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Maker {

    @Id
    @GeneratedValue
    @Column(name = "maker_id")
    private UUID id;

    @Column(unique = true)
    private String email;
    private String password;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String phone;
    private String company;
    private long point;
    private String account;

    /**
     * 정적 생성자 메서드
     */
    public static Maker newInstance(
            String email,
            String password,
            String nickname,
            String phone,
            String company
    ) {
        Maker maker = new Maker();
        maker.email = email;
        maker.password = password;
        maker.nickname = nickname;
        maker.phone = phone;
        maker.company = company;
        maker.point = 0L;
        maker.account = "";
        return maker;
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

    public void chargePoint(long point) {
        checkPoint(point);
        mockWithdrawCash(point);
        this.point += point;
    }

    private void mockWithdrawCash(long point) {
        if(true) {
            System.out.println("현금 " + point +"원을 Account 에서 빼갑니다!!");
        } else {
            System.out.println("계좌 잔액이 부족합니다!!");
        }
    }

    public void mockDepositPoint(long point) {
        checkPoint(point);
        if (this.point < point) {
            throw new RuntimeException("잔여 포인트가 부족합니다.");
        }
        System.out.println("계좌로 포인트 " + point*19/20 + "만큼 충전합니다.!!");
        this.point -= point;
    }

    public void refundPoint(long point) {
        checkPoint(point);
        this.point += point;
    }

    private void checkPoint(long point){
        if (point <= 0) {
            throw new RuntimeException("음수와 0은 들어갈 수 없습니다.");
        }
    }



}
