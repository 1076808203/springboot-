package cn.tgc.demo.web.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {
    //向登录页面跳转，同时封装原始页面地址
    @GetMapping(value = "/login")
    public String login(HttpServletRequest request, Map map){
        //分别获取请求头和参数url中的原始非拦截的访问路径
        String referer = request.getHeader("Referer");
        String url = request.getParameter("url");
        if (url!=null&& !url.equals("")){
            map.put("url",url);
        }else  if (referer!=null && referer.contains("/login")){
            map.put("url","");
        }else {
            map.put("url",referer);
        }
        return "comm/login";
    }
    //对Security拦截的无权限访问异常处理的路径映射
    @GetMapping(value = "/errorPage/{page}/{code}")
    public String AccessExceptionHandler(@PathVariable("page") String page,@PathVariable("code") String code){
        return page+"/"+code;
    }
}
