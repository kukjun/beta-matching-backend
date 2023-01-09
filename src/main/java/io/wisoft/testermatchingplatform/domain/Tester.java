package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.domain.EmptyAccountException;
import io.wisoft.testermatchingplatform.handler.exception.domain.MissMatchPasswordException;
import io.wisoft.testermatchingplatform.handler.exception.domain.NotNaturalNumberException;
import io.wisoft.testermatchingplatform.handler.exception.domain.InsufficientPointException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Tester extends BaseEntity{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "tester_id")
    private UUID id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String phoneNumber;

    private String intro_Message;
    private long point;
    private String accountNumber;

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
        tester.phoneNumber = phone;
        tester.intro_Message = introduce;
        tester.point = 0L;
        tester.accountNumber = "";
        tester.createEntity();
        return tester;
    }

    /**
     * 비지니스 로직
     */
    public void verifyPassword(String password) {
        isValidPassword(password);
    }

    public String changeAccount(String account) {
        this.accountNumber = account;
        return account;
    }

    public long pointToCash(long point) {
        isAccountEmpty();
        IsPointNaturalNumber(point);
        long cash = point * 19 / 20;
        isEnoughPoint(point);
        this.point -= point;
        return cash;
    }

    public void rewardPoint(long point) {
        IsPointNaturalNumber(point);
        this.point += point;
    }



    /**
     * 예외 처리 발생 로직
     */
    private void IsPointNaturalNumber(long point) {
        if (point <= 0) {
            throw new NotNaturalNumberException(String.valueOf(point));
        }
    }
    private void isEnoughPoint(long point) {
        if (this.point < point) {
            throw new InsufficientPointException(String.valueOf(point - this.point));
        }
    }
    private void isAccountEmpty() {
        if (accountNumber == null) {
            throw new EmptyAccountException();
        }
    }
    private void isValidPassword(String password) {
        if (!this.password.equals(password)) {
            throw new MissMatchPasswordException(password);
        }
    }
}
