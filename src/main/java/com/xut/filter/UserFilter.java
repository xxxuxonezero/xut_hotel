package com.xut.filter;

import com.xut.bean.User;
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
        User user = (User) request.getSession().getAttribute("user");
        String path = request.getServletPath();
        if (path.startsWith("/user")) {
            if (user != null && user.getType() == UserType.COMMON_USER.id()) {
                chain.doFilter(req, resp);
            }
        } else if (path.startsWith("/admin")) {
            if (user != null && user.getType() == UserType.MANAGER.id()) {
                chain.doFilter(req, resp);
            }
        } else {
            chain.doFilter(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
