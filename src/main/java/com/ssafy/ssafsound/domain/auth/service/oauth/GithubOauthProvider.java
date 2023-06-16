package com.ssafy.ssafsound.domain.auth.service.oauth;

import com.ssafy.ssafsound.domain.auth.exception.AuthException;
import com.ssafy.ssafsound.global.common.exception.GlobalErrorInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
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
public class GithubOauthProvider implements OauthProvider {

    private final RestTemplate restTemplate;
    @Value("${oauth2.github.url}")
    private String GITHUB_URL;
    @Value("${oauth2.github.token-url}")
    private String GITHUB_TOKEN_URL;
    @Value("${oauth2.github.client-id}")
    private String GITHUB_CLIENT_ID;
    @Value("${oauth2.github.client-secret}")
    private String GITHUB_CLIENT_SECRET;
    @Value("${oauth2.github.redirect-uri}")
    private String GITHUB_REDIRECT_URI;
    @Value("${oauth2.github.scope}")
    private String SCOPE;

    @Override
    public String getOauthUrl() {
        Map<String, Object> params = new HashMap<>();

        params.put("scope", SCOPE);
        params.put("client_id", GITHUB_CLIENT_ID);
        params.put("redirect_uri", GITHUB_REDIRECT_URI);


        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return GITHUB_URL + "?" + parameterString;
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
