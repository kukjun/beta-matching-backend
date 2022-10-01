package io.wisoft.testermatchingplatform.domain.maker;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@Table(name = "maker")
@NoArgsConstructor
public class Maker extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String phoneNumber;

    private String company;

    private Long point;

    private String accountNumber;

    private String refreshToken;


    public void checkPassword(final String confirmPassword) {
        if (!this.password.equals(confirmPassword)) {
            throw new IllegalArgumentException("confirmPassword and password not equal");
        }
    }
}
