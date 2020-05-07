package com.sarielx.study.xr.service;
import com.sarielx.study.xr.entity.User;
import java.util.Date;

public interface TokenService {

    public  String getToken(User user, Date date);
}