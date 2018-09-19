package com.stephen.myblog.controller;


import com.stephen.myblog.common.AjaxResult;
import com.stephen.myblog.entity.User;
import com.stephen.myblog.mapper.UserMapper;
import com.stephen.myblog.util.ImageCode;
import com.stephen.myblog.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/loginPage")
    public String login() {
        logger.info("===========跳转登陆页面");
        return "login";
    }

    @RequestMapping("/verifyCode")
    public String verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream os = response.getOutputStream();
        Map<String, Object> map = ImageCode.getImageCode(60, 20, os);
        String simpleCaptcha = "simpleCaptcha";
        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime", new Date().getTime());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
        } catch (IOException e) {
            return "";
        }
        return null;
    }

    public AjaxResult checkcode(HttpServletRequest request, HttpSession session)
            throws Exception {
        logger.info("=================校验验证码");
        String checkCode = request.getParameter("checkCode");
        Object cko = session.getAttribute("simpleCaptcha"); //验证码对象
        if (cko == null) {
            request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
            return new AjaxResult(false,"验证码已失效，请重新输入！","");
        }

        String captcha = cko.toString();
        Date now = new Date();
        Long codeTime = Long.valueOf(session.getAttribute("codeTime") + "");
        if (StringUtils.isEmpty(checkCode) || captcha == null || !(checkCode.equalsIgnoreCase(captcha))) {
            request.setAttribute("errorMsg", "验证码错误！");
            return new AjaxResult(false,"验证码错误！","");
        } else if ((now.getTime() - codeTime) / 1000 / 60 > 5) {//验证码有效时长为5分钟
            request.setAttribute("errorMsg", "验证码已失效，请重新输入！");
            return new AjaxResult(false,"验证码已失效，请重新输入！","");
        } else {
            session.removeAttribute("simpleCaptcha");
            return new AjaxResult(true,"验证码正确","");
        }
    }

    @RequestMapping(value = "/checkUserLogin",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult checkUserLogin(HttpServletRequest req,HttpSession session) throws Exception {
        AjaxResult ajaxResult=checkcode(req,session);
        if (!ajaxResult.isSuccess()){
            return ajaxResult;
        }
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            String encryPassword = MD5Util.encrypt(password);
            User user = userMapper.getOne(username);
            if (!user.getPassword().equals(encryPassword)) {
                return new AjaxResult(false, "密码错误", "");
            }
            logger.info("|---------------用户信息验证通过");
            session.setAttribute("username",username);
            return new AjaxResult(true, "用户信息验证通过", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new AjaxResult(false, "登陆异常", "");
    }


}
