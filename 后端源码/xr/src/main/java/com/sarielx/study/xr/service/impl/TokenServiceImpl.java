package com.sarielx.study.xr.service.impl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sarielx.study.xr.entity.User;
import com.sarielx.study.xr.service.TokenService;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String getToken(User user, Date date) {
        String token="";

        token= JWT.create()
                .withAudience(String.valueOf(user.getId()))
                .withExpiresAt(date) //过期时间配置
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
