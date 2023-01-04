package io.wisoft.testermatchingplatform.domain;

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
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Tester extends BaseEntity{
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

    @OneToMany(mappedBy = "tester")
    private List<ApplyInformation> applyInformationList = new ArrayList<>();

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
        tester.createEntity();
        return tester;
    }

    /**
     * 비지니스 로직
     */

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new LoginException();
        }
    }

    public String changeAccount(String account) {
        this.account = account;
        return account;
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
        System.out.println("계좌로 현금을 " + cash + "만큼 충전합니다.!!");
        this.point -= point;
        return cash;
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
