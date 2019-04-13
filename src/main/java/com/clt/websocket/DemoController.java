package com.clt.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("") // url:/模块/资源/{id}/细分 /seckill/list
public class DemoController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;


    /**
     * 每次拦截到请求会先访问此函数，
     * 获取request,session,response等实例
     * @param request http请求
     * @param response http回应
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    private ModelAndView index(String token, String userId)  {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/websocket_java.html", method = RequestMethod.GET)
    private ModelAndView websocket_java()  {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("websocket_java");
        return modelAndView;
    }
    @RequestMapping(value = "/websocket_spring.html", method = RequestMethod.GET)
    private ModelAndView websocket_spring()  {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("websocket_spring");

        return modelAndView;
    }


}
