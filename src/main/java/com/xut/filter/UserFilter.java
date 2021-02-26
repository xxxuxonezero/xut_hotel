package com.xut.filter;

import com.xut.controller.auth.AuthUtil;
import com.xut.model.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "UserFilter")
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Identity user = AuthUtil.getIdentity(request);

        String path = request.getServletPath();
        if (path.contains("/user/") || path.contains("/account/")) {
            if (user != null) {
                chain.doFilter(req, resp);
                return;
            }
        } else if (path.contains("/admin/")) {
            if (user != null && user.getType() == UserType.MANAGER.id()) {
                chain.doFilter(req, resp);
                return;
            }
        } else {
            chain.doFilter(req, resp);
            return;
        }
        String basePath = request.getScheme()+ "://"+request.getServerName()+ ":"+request.getServerPort()+ request.getContextPath();
        response.sendRedirect(basePath);
        return;
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
