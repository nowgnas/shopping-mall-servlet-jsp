package app.dto.response;

import app.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String email;
    private String name;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
            .id(member.getId())
            .email(member.getEmail())
            .name(member.getName())
            .build();
    }
}
