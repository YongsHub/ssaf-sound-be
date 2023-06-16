package com.ssafy.ssafsound.domain.auth.service.oauth;

import com.ssafy.ssafsound.domain.auth.exception.AuthException;
import com.ssafy.ssafsound.global.common.exception.GlobalErrorInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoOauthProvider implements OauthProvider {
    private final RestTemplate restTemplate;
    @Value("${oauth2.kakao.url}")
    private String KAKAO_URL;
    @Value("${oauth2.kakao.token-url}")
    private String KAKAO_TOKEN_URL;
    @Value("${oauth2.kakao.client-id}")
    private String KAKAO_CLIENT_ID;
    @Value("${oauth2.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URI;
    @Value("${oauth2.kakao.scope}")
    private String SCOPE;
    @Value("${oauth2.kakao.response_type}")
    private String RESPONSE_TYPE;
    @Value("${oauth2.kakao.grant_type}")
    private String GRANT_TYPE;
    @Override
    public String getOauthUrl() {
        Map<String, Object> params = new HashMap<>();

        params.put("scope", SCOPE);
        params.put("client_id", KAKAO_CLIENT_ID);
        params.put("redirect_uri", KAKAO_REDIRECT_URI);
        params.put("response_type", RESPONSE_TYPE);


        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return KAKAO_URL + "?" + parameterString;
    }

    @Override
    public String getOauthAccessToken(String code) {
        return null;
    }

    @Override
    public String getUserOauthIdentifier(String accessToken) {
        return null;
    }
}
