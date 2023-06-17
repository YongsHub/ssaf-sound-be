package com.ssafy.ssafsound.domain.auth.service;

import com.ssafy.ssafsound.domain.auth.dto.AuthenticatedUser;
import com.ssafy.ssafsound.domain.auth.dto.CreateMemberReqDto;
import com.ssafy.ssafsound.domain.auth.dto.CreateMemberTokensResDto;
import com.ssafy.ssafsound.domain.auth.exception.AuthException;
import com.ssafy.ssafsound.domain.auth.exception.MemberErrorInfo;
import com.ssafy.ssafsound.domain.auth.service.oauth.OauthProvider;
import com.ssafy.ssafsound.domain.auth.service.oauth.OauthProviderFactory;
import com.ssafy.ssafsound.domain.auth.service.token.JwtTokenProvider;
import com.ssafy.ssafsound.domain.member.dto.PostMemberReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class AuthService {

    private final OauthProviderFactory oauthProviderFactory;
    private OauthProvider oauthProvider;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(OauthProviderFactory oauthProviderFactory, JwtTokenProvider jwtTokenProvider) {
        this.oauthProviderFactory = oauthProviderFactory;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void sendRedirectURL(String oauthName, HttpServletResponse response) {
        oauthProvider = oauthProviderFactory.from(oauthName);
        try {
            String redirectURL = oauthProvider.getOauthUrl();
            response.sendRedirect(redirectURL);
        } catch (Exception e) {
            throw new AuthException(MemberErrorInfo.AUTH_SERVER_ERROR);
        }
    }

    public PostMemberReqDto login(CreateMemberReqDto createMemberReqDto) {
        oauthProvider = oauthProviderFactory.from(createMemberReqDto.getOauthName());
        String accessToken = oauthProvider.getOauthAccessToken(createMemberReqDto.getCode());;
        return oauthProvider.getUserOauthIdentifier(accessToken, createMemberReqDto.getOauthName());
    }

    public CreateMemberTokensResDto createToken(AuthenticatedUser authenticatedUser) {
        return CreateMemberTokensResDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(authenticatedUser))
                .refreshToken(jwtTokenProvider.createRefreshToken())
                .build();
    }
}
