package com.lanzhou.yuanfen.security.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanzhou.yuanfen.security.token.QQAuthenticationToken;
import com.lanzhou.yuanfen.sys.entity.SocialUser;
import com.lanzhou.yuanfen.sys.mapper.RolePermissionMapper;
import com.lanzhou.yuanfen.sys.mapper.SocialUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * QQ认证的处理者
 *
 * @author Administrator
 */
@Slf4j
public class QQAuthenticationProvider implements AuthenticationProvider {

    /**
     * 获取 QQ 登录信息的 API 地址
     */
    private final static String USER_INFO_URI = "https://graph.qq.com/user/get_user_info";

    /**
     * 获取 QQ 用户信息的地址拼接
     */
    private final static String USER_INFO_API = "%s?access_token=%s&oauth_consumer_key=%s&openid=%s";

    private static final String CLIENT_ID = "101848364";

    /**
     * 默认授权用户角色KEY
     */
    // private static final Long DEFAULT_ROLE_KEY = 2L;

    /**
     * 默认授权用户权限: code
     */
    private static final Set<SimpleGrantedAuthority> DEFAULT_GRANTED_AUTHORITY = new HashSet<SimpleGrantedAuthority>() {{
        // 可以添加默认权限
        add(new SimpleGrantedAuthority("authority"));
    }};

    @Resource
    private SocialUserMapper socialUserMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        QQAuthenticationToken qqAuth;
        if (authentication instanceof QQAuthenticationToken) {
            qqAuth = (QQAuthenticationToken) authentication;
        } else {
            return null;
        }
        System.out.println("假装方法调用到了哈哈 : " + JSONObject.toJSONString(qqAuth));
        if (qqAuth.getAccessToken() != null && qqAuth.getOpenId() != null) {
            System.out.println("登入信息用户为: " + JSONObject.toJSONString(qqAuth));
            String openId = qqAuth.getOpenId();
            SocialUser user = socialUserMapper.selectOne(new QueryWrapper<SocialUser>().eq("open_id", openId));
            // 通过OpenId寻找数据库, 没有的话直接保存当前用户, 有的话用当前用户登入
            if (user != null) {
                // 数据库用户有关联Key就给对应的权限, 没有就默认吧
                if (!StringUtils.isEmpty(user.getUserKey())) {
                    List<String> perms = rolePermissionMapper.getPermByUserKey(user.getUserKey());
                    for (String perm : perms) {
                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(perm);
                        DEFAULT_GRANTED_AUTHORITY.add(authority);
                    }
                }
                System.out.println("当前登入数据库用户啦...........");
            } else {
                user = getUserInfo(qqAuth.getAccessToken(), openId);
                // 添加用户到数据库
                saveSocialUser(user, openId);
                System.out.println("当前QQ用户已经登入啦, 哈哈哈哈, 骚等哦!!");
                System.out.println(JSONObject.toJSONString(user));
                // 默认给个授权用户权限, 即可不用密码啦哈哈哈
                System.out.println("返回有默认权限: {} 的用户" + JSONObject.toJSONString(DEFAULT_GRANTED_AUTHORITY));
            }
            return new QQAuthenticationToken(qqAuth.getAccessToken(), openId, DEFAULT_GRANTED_AUTHORITY);
        }
        System.out.println("好吧, 校验失败了, 腾讯不鸟你我了...........");
        throw new BadCredentialsException("Bad Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return QQAuthenticationToken.class.isAssignableFrom(authentication);
    }


    /**
     * 添加社交用户到数据库
     *
     * @param openId
     */
    private void saveSocialUser(SocialUser user, String openId) {
        // 添加新用户
        user.setOpenId(openId);
        socialUserMapper.insert(user);
    }

    private SocialUser getUserInfo(String accessToken, String openId) {
        String url = String.format(USER_INFO_API, USER_INFO_URI, accessToken, CLIENT_ID, openId);
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BadCredentialsException("Bad Credentials!");
        }
        String resultText = document.text();
        JSONObject json = JSON.parseObject(resultText);
        System.out.println("QQ登入的远程账号信息为: " + json.toString());
        SocialUser user = new SocialUser();
        user.setNickname(json.getString("nickname"));
        user.setGender(json.getString("gender"));
        user.setProvince(json.getString("province"));
        user.setCity(json.getString("city"));
        user.setYear(json.getString("year"));
        user.setLevel(json.getInteger("level"));
        user.setVip(json.getString("vip").equals("1"));
        user.setAvatar(json.getString("figureurl_qq_2"));
        System.out.println("QQ登入的账号信息为: " + JSONObject.toJSONString(user));
        return user;
    }
}
