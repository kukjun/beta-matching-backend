package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.CashException;
import io.wisoft.testermatchingplatform.handler.exception.EmptyAccountException;
import io.wisoft.testermatchingplatform.handler.exception.LoginException;
import io.wisoft.testermatchingplatform.handler.exception.PointException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Maker extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "maker_id")
    private UUID id;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "maker")
    private List<Tests> createdTests = new ArrayList<>();

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
        maker.createEntity();
        return maker;
    }


    /**
     * 비지니스 로직
     */
    // 넘겨받은 Email, Password 가 일치하는가?
    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new LoginException();
        }
    }

    public void changeAccount(String account) {
        this.account = account;
    }

    public long cashToPoint(long cash) {
        checkCash(cash);
        long point = mockCashToPoint(cash);
        return point;
    }

    private long mockCashToPoint(long cash) {
        System.out.println("현금 " + cash + "원을 Account 에서 빼갑니다!!");
        this.point += cash;
        return cash;
    }

    public long pointToCash(long point) {
        checkAccount();
        checkPoint(point);
        long cash = mockPointToCash(point);
        return cash;
    }

    private void checkAccount() {
        if (account == null) {
            throw new EmptyAccountException();
        }
    }

    private long mockPointToCash(long point) {
        long cash = point * 19 / 20;
        if (this.point < point) {
            throw new PointException();
        }
        System.out.println("계좌로 포인트 " + cash + "만큼 충전합니다.!!");
        this.point -= point;
        return cash;
    }

    public void usePoint(long point) {
        checkPoint(point);
        checkInsufficientPoint(point);
        this.point -= point;
    }

    public void refundPoint(long point) {
        checkPoint(point);
        this.point += point;
    }

    private void checkPoint(long point) {
        if (point <= 0) {
            throw new PointException("음수와 0은 들어갈 수 없습니다.");
        }
    }

    private void checkInsufficientPoint(long point) {
        if (this.point - point < 0) {
            throw new PointException();
        }
    }

    private void checkCash(long cash) {
        if (cash <= 0) {
            throw new CashException("음수와 0은 들어갈 수 없습니다.");
        }
    }


    public void updatePoint(long point) {
        checkInsufficientPoint(point);
        this.point -= point;
    }
}
