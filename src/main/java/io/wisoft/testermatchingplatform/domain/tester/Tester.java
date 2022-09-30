package io.wisoft.testermatchingplatform.domain.tester;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "tester")
@NoArgsConstructor
public class Tester extends BaseTime {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy = "uuid2")
    @Column(columnDefinition = "BYTEA(16)")
    @NotNull
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
    private String introMessage;

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
