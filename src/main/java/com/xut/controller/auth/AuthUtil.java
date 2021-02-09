package com.xut.controller.auth;

import com.xut.filter.Identity;
import com.xut.model.Constant;
import com.xut.model.Result;
import com.xut.util.JWTUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthUtil {

    public static Identity getIdentity(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (Constant.IDENTITY.equals(name)) {
                Object object = JWTUtils.getObject(cookie.getValue(), Identity.class);
                return (Identity) object;
            }
        }
        return null;
    }

    public static void clearIdentity(HttpServletResponse response) {
        Cookie cookie = new Cookie(Constant.IDENTITY, "");
        response.addCookie(cookie);
        return;
    }
}
