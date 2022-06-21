package uz.mh.talkoncopy.dto.user.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionDto {
    private Long expiryForAccessToken;

    private String accessToken;

    private Long expiryForRefreshToken;

    private String refreshToken;

    private Long issuedAt;
}
