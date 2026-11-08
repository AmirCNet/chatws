package org.example.chatws.controller;

import org.example.chatws.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public static final String CHAT_ADD_USER = "/chat.addUser";
    public static final String PATH = "/topic/public";

    @MessageMapping("/chat.sendMessage")
    @SendTo(PATH)
    public ChatMessage enviarMensaje(ChatMessage mensaje) {
        System.out.println("Mensaje recibido en servidor: " + mensaje);
        return mensaje;
    }

    @MessageMapping(CHAT_ADD_USER)
    @SendTo(PATH)
    public ChatMessage agregarUsuario(ChatMessage mensaje) {
        return mensaje;
    }

    @MessageMapping("/chat.sendPrivate")
    public void enviarMensajePrivado(ChatMessage mensaje) {
        System.out.println("Mensaje privado recibido en servidor: " + mensaje);
        messagingTemplate.convertAndSend("/queue/" + mensaje.destinatario(), mensaje);
    }
}
