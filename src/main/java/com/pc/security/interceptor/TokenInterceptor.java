package com.pc.security.interceptor;

import com.pc.security.ResponseMgr;
import com.pc.security.TokenMgr;
import com.pc.security.config.Constant;
import com.pc.security.model.CheckResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String tokenStr = request.getParameter("token");
        if (tokenStr == null || tokenStr.equals("")) {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(ResponseMgr.err());
            printWriter.flush();
            printWriter.close();
            return false;
        }
        //
        CheckResult checkResult = TokenMgr.validateJWT(tokenStr);
        if (checkResult.isSuccess()) {
//			Claims claims = checkResult.getClaims();
//			SubjectModel model = GsonUtil.jsonStrToObject(claims.getSubject(), SubjectModel.class);
//			httpServletRequest.setAttribute("tokensub", model);
            return true;
        } else {
            switch (checkResult.getErrCode()) {
                //
                case Constant.JWT_ERRCODE_EXPIRE:
                    PrintWriter printWriter = response.getWriter();
                    printWriter.print(ResponseMgr.loginExpire());
                    printWriter.flush();
                    printWriter.close();
                    break;
                //
                case Constant.JWT_ERRCODE_FAIL:
                    PrintWriter printWriter2 = response.getWriter();
                    printWriter2.print(ResponseMgr.noAuth());
                    printWriter2.flush();
                    printWriter2.close();
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
