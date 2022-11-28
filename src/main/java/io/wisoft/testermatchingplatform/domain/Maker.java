package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.CashException;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Maker {

    @Id
    @GeneratedValue
    @Column(name = "maker_id")
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

    @Column(unique = true)
    @NotNull
    private String phone;
    @NotNull
    private String company;
    @NotNull
    private long point;
    @NotNull
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
            throw new LoginException();
        }
    }
    public void changeAccount(String account) {
        this.account = account;
    }

    public void withdrawCash(long cash) {
        checkCash(cash);
        mockWithdrawCash(cash);
    }

    private void mockWithdrawCash(long cash) {
        if(true) {
            System.out.println("현금 " + cash +"원을 Account 에서 빼갑니다!!");
            this.point += cash;
        } else {
            System.out.println("계좌 잔액이 부족합니다!!");
        }
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

    public void usePoint(long point) {
        checkPoint(point);
        if (this.point < point) {
            throw new PointException();
        }
        this.point -= point;
    }

    public void refundPoint(long point) {
        checkPoint(point);
        this.point += point;
    }

    private void checkPoint(long point){
        if (point <= 0) {
            throw new PointException("음수와 0은 들어갈 수 없습니다.");
        }
    }

    private void checkCash(long cash) {
        if (cash <= 0) {
            throw new CashException("음수와 0은 들어갈 수 없습니다.");
        }
    }




}
