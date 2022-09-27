package io.wisoft.testermatchingplatform.domain.maker;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "maker")
@NoArgsConstructor
public class Maker extends BaseTime {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy = "uuid")
    @Column(columnDefinition = "BINARY(16)")
    @NotNull
    private String id;

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
