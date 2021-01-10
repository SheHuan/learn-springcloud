package com.sn.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义请求过滤器
 */
@Component
public class PermissionFilter extends ZuulFilter {

    /**
     * 过滤器类型，权限判断一般是pre
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器优先级
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 处理核心的过滤逻辑
     *
     * @return 返回值无意义
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 获取当前请求
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        if (requestURI.contains("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (!"shehuan".equals(username) && !"123456".equals(password)) {
                currentContext.setSendZuulResponse(false);
                currentContext.setResponseStatusCode(401);
                currentContext.addZuulResponseHeader("content-type", "text/html;charset=utf-8");
                currentContext.setResponseBody("非法访问");
            }
        }
        return null;
    }
}
