package org.example.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    private String getLoginFromSession(HttpServletRequest request)
    {
        // получаем сессию
        HttpSession session = request.getSession();
        // получаем объект login
        String login = (String) session.getAttribute("login");
        return login;
    }

    @GetMapping("/chat")
    public String authorization(HttpServletRequest request)
    {
        String login = getLoginFromSession(request);
        //проверяем что пользователь уже авторизовался
        if(login==null)
        {
            LOGGER.info("Redirection to the authorization form.");
            return "/authorization-form";
        }
        else {
            LOGGER.info("Displaying chat messages - {}",login);
            return "/chat-messages";
        }
    }

    @PostMapping("/auth")
    public String heandlerPostRequest(@RequestParam("login") String login, HttpServletRequest request) {
        LOGGER.info("POST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if(login!=null) {
            // получаем сессию
            HttpSession session = request.getSession();
            //устанавливаем в сессию пользователя логин
            session.setAttribute("login", login);
            LOGGER.info("POST from {}.", login);
            //тут мы должны загрузить все сообщения из DAO и перейти на отображение всех сообщений
            return "/chat-messages";
        }
        else
            return "redirect:/authorization-form";

    }
}
