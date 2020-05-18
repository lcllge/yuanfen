package com.lanzhou.yuanfen.security.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanzhou.yuanfen.security.token.EmailAuthenticationToken;
import com.lanzhou.yuanfen.security.token.QQAuthenticationToken;
import com.lanzhou.yuanfen.sys.entity.User;
import com.lanzhou.yuanfen.sys.entity.UserRole;
import com.lanzhou.yuanfen.sys.mapper.RolePermissionMapper;
import com.lanzhou.yuanfen.sys.mapper.UserMapper;
import com.lanzhou.yuanfen.sys.mapper.UserRoleMapper;
import com.lanzhou.yuanfen.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    private static final Long DEFAULT_ROLE_KEY = 2L;

    /**
     * 默认授权用户权限: code
     */
    private static final Set<SimpleGrantedAuthority> DEFAULT_GRANTED_AUTHORITY = new HashSet<SimpleGrantedAuthority>() {{
        // 可以添加默认权限
        add(new SimpleGrantedAuthority("authority"));
    }};

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
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
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("open_id", openId));
            // 通过OpenId寻找数据库, 没有的话直接保存当前用户, 有的话用当前用户登入
            if (user != null) {
                System.out.println("数据库查询用户为: " + JSONObject.toJSONString(user));
                List<String> perms = rolePermissionMapper.getPermByUserKey(user.getUserKey());
                for (String perm : perms) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(perm);
                    DEFAULT_GRANTED_AUTHORITY.add(authority);
                }
                System.out.println("当前登入数据库用户啦...........");
            } else {
                user = getUserInfo(qqAuth.getName(), openId);
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
     * @param user
     * @param openId
     */
    private void saveSocialUser(User user, String openId) {
        // 添加新用户
        user.setOpenId(openId);
        userMapper.insert(user);
        // 添加默认角色
        UserRole userRole = new UserRole();
        userRole.setUserKey(user.getUserKey());
        userRole.setRoleKey(DEFAULT_ROLE_KEY);
        userRoleMapper.insert(userRole);
    }

    private User getUserInfo(String accessToken, String openId) {
        String url = String.format(USER_INFO_API, USER_INFO_URI, accessToken, CLIENT_ID, openId);
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BadCredentialsException("Bad Credentials!");
        }
        String resultText = document.text();
        JSONObject json = JSON.parseObject(resultText);

        User user = new User();
        user.setUsername(json.getString("nickname"));
        user.setGender(json.getString("gender"));
        user.setProvince(json.getString("province"));
        user.setYear(json.getString("year"));
        user.setAvatar(json.getString("figureurl_qq_2"));
        System.out.println("QQ登入的账号信息为: " + JSONObject.toJSONString(user));
        return user;
    }
}
