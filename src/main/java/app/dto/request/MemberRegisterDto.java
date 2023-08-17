package app.dto.request;

import app.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberRegisterDto {
    private String email;
    private String password;
    private String name;

    private Member toEntity(){
        return Member.builder()
            .email(email)
            .password(password)
            .name(name)
            .build();
    }
}
