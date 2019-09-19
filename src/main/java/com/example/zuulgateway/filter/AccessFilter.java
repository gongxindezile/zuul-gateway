package com.example.zuulgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全验证做在网关层面
 *
 * @author wangxl
 * @date 2019-09-17
 */
public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    /**
     *
     * 定义filter的类型，有pre、route、post、error四种, 前置过滤器，路由过滤器，错误过滤器，简单过滤器
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0; //过滤器执行顺序, 当请求在一个阶段中有多个过滤器时,需要根据该方法返回的值来依次执行, 数字越小越先执行
    }

    @Override
    public boolean shouldFilter() {
        return true; //表示是否需要执行该filter，true表示执行，false表示不执行(默认false)
    }

    /**
     * 定义了一个简单的zuul过滤器, 它实现了在请求被路由前检查HttpServletRequest中是否有token参数,
     * 若有就进行路由, 若没有就拒绝访问, 返回401 Unauthorized错误
     *
     * @return
     */
    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("send {} request to to {}", request.getMethod(),
                request.getRequestURL().toString());
        String accessToken = request.getHeader("token"); //在请求头header中添加 token=token
        if (accessToken == null) {
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }else {
            log.info("access token ok");
            ctx.setSendZuulResponse(true);  //对该请求进行路由
            ctx.setResponseStatusCode(HttpStatus.OK.value());
        }
        return null;
    }
}
