package com.bs.farm.novelfarmweb.web.interceptor;

import com.bs.farm.novelfarmweb.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

//用户登录拦截器
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 处理用户登录Token
        String uri = request.getRequestURI();
        String token = request.getHeader("token");
        if(StringUtil.isEmpty( token )){
            token = request.getParameter("token");
        }

//        int res =  userService.verify( token );
//        if( userService.LOGIN_ACCESS == res ){
//            //验证 登录成功
//            if( managePlatAuthService.canAccess( token,uri ) ){
//                return true;
//            }else {
//                JSONObject result = new JSONObject();
//                result.put("code", ResHelper.ERROR);
//                result.put("msg","无权限");
//                writeJsonStr( result.toString(),response );
//                return false;
//            }
//        }else {
//            //验证 登录失败  获取原因消息
//            String reasonMsg = UserService.getDenyMsg( res );
//            reasonMsg = URLEncoder.encode(reasonMsg,"utf-8");
//            response.setHeader("reason-code",res + "");
//            response.setHeader( "reason-msg",reasonMsg );
//            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
//            return false;
//        }
        return false;
    }

    public void writeJsonStr( String json, HttpServletResponse response ){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
