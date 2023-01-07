package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.domain.EmptyAccountException;
import io.wisoft.testermatchingplatform.handler.exception.domain.MissMatchPasswordException;
import io.wisoft.testermatchingplatform.handler.exception.domain.NotNaturalNumberException;
import io.wisoft.testermatchingplatform.handler.exception.domain.InsufficientPointException;
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
    private List<Mission> createdMissions = new ArrayList<>();

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
    public void verifyPassword(String password) {
        isValidPassword(password);
    }

    public String changeAccount(String account) {
        this.account = account;
        return account;
    }

    public long cashToPoint(long cash) {
        isCashNaturalNumber(cash);
        this.point += cash;
        return cash;
    }

    public long pointToCash(long point) {
        isAccountEmpty();
        isPointNaturalNumber(point);
        isEnoughPoint(point);
        long cash = point * 19 / 20;
        this.point -= point;
        return cash;
    }

    public void usePoint(long point) {
        isPointNaturalNumber(point);
        isEnoughPoint(point);
        this.point -= point;
    }

    public void refundPoint(long point) {
        isPointNaturalNumber(point);
        this.point += point;
    }



    public void updatePoint(long point) {
        isEnoughPoint(point);
        this.point -= point;
    }

    /**
     * 예외 처리 로직
     */
    private void isAccountEmpty() {
        if (account == null) {
            throw new EmptyAccountException();
        }
    }

    private void isPointNaturalNumber(long point) {
        if (point <= 0) {
            throw new NotNaturalNumberException(String.valueOf(point));
        }
    }

    private void isEnoughPoint(long point) {
        if (this.point < point) {
            throw new InsufficientPointException(String.valueOf(point - this.point));
        }
    }

    private void isCashNaturalNumber(long cash) {
        if (cash <= 0) {
            throw new NotNaturalNumberException(String.valueOf(cash));
        }
    }

    private void isValidPassword(String password) {
        if (!this.password.equals(password)) {
            throw new MissMatchPasswordException(password);
        }
    }


}
