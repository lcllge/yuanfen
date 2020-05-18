package com.lanzhou.yuanfen.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Administrator
 * TODO 提取出来, 只留openID校验即可, 重写好多代码
 */
public class QQAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * token
     */
    private String accessToken;

    /**
     * 有效期
     */
    private int expiresIn;

    /**
     * 刷新时用的 token
     */
    private String refreshToken;

    /**
     * openId
     */
    private String openId;

    /**
     * 未认证时候的构造器
     */
    public QQAuthenticationToken(String accessToken, String openId) {
        super(null);
        this.accessToken = accessToken;
        this.openId = openId;
        super.setAuthenticated(false);
    }


    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public QQAuthenticationToken(String accessToken, String openId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.accessToken = accessToken;
        this.openId = openId;
        super.setAuthenticated(true);
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
