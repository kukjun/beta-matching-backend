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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private UUID id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String nickname;

    @NotNull
    @Column(unique = true)
    private String phoneNumber;

    @NotNull
    private String company;

    @NotNull
    private Long point;

    private String accountNumber;

    private String refreshToken;


    public void checkPassword(final String confirmPassword) {
        if (!this.password.equals(confirmPassword)) {
            throw new IllegalArgumentException("confirmPassword and password not equal");
        }
    }
}
