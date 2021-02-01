package org.example.controllers;

import org.example.dao.ChatDAO;
import org.example.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    private final ChatDAO chatDAO;

    @Autowired
    public ChatController(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }

    private String getLoginFromSession(HttpServletRequest request)
    {
        // получаем сессию
        HttpSession session = request.getSession();
        // получаем объект login
        String login = (String) session.getAttribute("login");
        return login;
    }

    @GetMapping("/")
    public String authorization(HttpServletRequest request, Model model)
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
            model.addAttribute("chatMessages",chatDAO.getChatMessages());
            model.addAttribute("login",login);
            return "/chat-messages";
        }
    }

    @PostMapping("/auth")
    public String heandlerPostRequest(@RequestParam("login") String login, HttpServletRequest request, Model model) {
        LOGGER.info("POST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if(login!=null) {
            // получаем сессию
            HttpSession session = request.getSession();
            //устанавливаем в сессию пользователя логин
            session.setAttribute("login", login);
            LOGGER.info("Displaying chat messages - {}",login);
            //тут мы должны загрузить все сообщения из DAO и перейти на отображение всех сообщений
            model.addAttribute("chatMessages",chatDAO.getChatMessages());
            model.addAttribute("login",login);
            return "/chat-messages";
        }
        else
            return "redirect:/authorization-form";
    }

    @PostMapping("/add-message")
    public String addMessageToChatDAO(@RequestParam("message") String msg, HttpServletRequest request, Model model) {
        LOGGER.info("Add message to chat");

        Message messageToChat = new Message(getLoginFromSession(request),msg,new Date());
        chatDAO.addMessage(messageToChat);

        //тут мы должны загрузить все сообщения из DAO и перейти на отображение всех сообщений
        model.addAttribute("chatMessages",chatDAO.getChatMessages());
        String login = getLoginFromSession(request);
        model.addAttribute("login",login);
        return "/chat-messages";

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        // получаем сессию
        HttpSession session = request.getSession();
        String removeLogin = getLoginFromSession(request);
        session.removeAttribute("login");
        LOGGER.info("Exit from chat - {}", removeLogin);
        //переходим на форму авторизации
        return "/authorization-form";

    }

}
