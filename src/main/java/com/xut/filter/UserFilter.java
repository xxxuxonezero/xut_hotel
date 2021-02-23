package com.xut.filter;

import com.xut.controller.auth.AuthUtil;
import com.xut.model.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "UserFilter")
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        Identity user = AuthUtil.getIdentity(request);
        if (user == null) {
            return;
        }
        String path = request.getServletPath();
        if (path.startsWith("/user") || path.startsWith("account")) {
            if (user.getType() == UserType.COMMON_USER.id()) {
                chain.doFilter(req, resp);
            }
        } else if (path.startsWith("/admin")) {
            if (user.getType() == UserType.MANAGER.id()) {
                chain.doFilter(req, resp);
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
