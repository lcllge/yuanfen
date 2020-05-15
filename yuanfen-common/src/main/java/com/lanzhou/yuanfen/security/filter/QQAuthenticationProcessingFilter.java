package com.lanzhou.yuanfen.security.filter;

import com.alibaba.fastjson.JSON;
import com.lanzhou.yuanfen.security.token.EmailAuthenticationToken;
import com.lanzhou.yuanfen.security.token.QQAuthenticationToken;
import com.lanzhou.yuanfen.security.token.QQAuthenticationToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * AbstractAuthenticationProcessingFilter这个过滤器在前面一节介绍过，
 * 是UsernamePasswordAuthenticationFilter的父类，我们的IpAuthenticationProcessingFilter也继承了它
 * 构造器中传入了/emailVerify作为IP登录的端点
 * attemptAuthentication()方法中加载请求的IP地址，之后交给内部的AuthenticationManager去认证
 *
 * @author Administrator
 */
public class QQAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final static String CODE = "code";

    /**
     * 获取 Token 的 API
     */
    private final static String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    /**
     * grant_type 由腾讯提供
     */
    private final static String GRANT_TYPE = "authorization_code";

    /**
     * client_id 由腾讯提供
     */
    private static final String CLIENT_ID = "101848364";

    /**
     * client_secret 由腾讯提供
     */
    private final static String CLIENT_SECRET = "eb2b63999cfcc6831c99ab362970d6a3";

    /**
     * redirect_uri 腾讯回调地址
     */
    private final static String REDIRECT_URI = "http://yuangfeng.top/client";

    /**
     * 获取 OpenID 的 API 地址
     */
    private final static String OPENID_URI = "https://graph.qq.com/oauth2.0/me?access_token=";

    /**
     * 获取 token 的地址拼接
     */
    private final static String TOKEN_ACCESS_API = "%s?grant_type=%s&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    public QQAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "GET"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String code = request.getParameter(CODE);
        System.out.println("Code : " + code);
        String tokenAccessApi = String.format(TOKEN_ACCESS_API, ACCESS_TOKEN_URL,
                GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, code, REDIRECT_URI);
        QQAuthenticationToken qqToken = this.getToken(tokenAccessApi);
        System.out.println(JSON.toJSONString(qqToken));
        if (qqToken != null) {
            String openId = getOpenId(qqToken.getAccessToken());
            qqToken.setOpenId(openId);
            System.out.println("openId=============> :" + openId);
            if (openId != null) {
                // 返回验证结果
                System.out.println("当前认证管理器为: " + this.getAuthenticationManager().getClass().getName());
                return this.getAuthenticationManager().authenticate(qqToken);
            }
        }
        return null;
    }

    private QQAuthenticationToken getToken(String tokenAccessApi) throws IOException {
        Document document = Jsoup.connect(tokenAccessApi).get();
        String tokenResult = document.text();
        String[] results = tokenResult.split("&");
        if (results.length == 3) {
            QQAuthenticationToken qqToken = new QQAuthenticationToken();
            String accessToken = results[0].replace("access_token=", "");
            int expiresIn = Integer.valueOf(results[1].replace("expires_in=", ""));
            String refreshToken = results[2].replace("refresh_token=", "");
            qqToken.setAccessToken(accessToken);
            qqToken.setExpiresIn(expiresIn);
            qqToken.setRefreshToken(refreshToken);
            return qqToken;
        }
        return null;
    }

    private String getOpenId(String accessToken) throws IOException {
        String url = OPENID_URI + accessToken;
        Document document = Jsoup.connect(url).get();
        String resultText = document.text();
        Matcher matcher = compile("\"openid\":\"(.*?)\"").matcher(resultText);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
