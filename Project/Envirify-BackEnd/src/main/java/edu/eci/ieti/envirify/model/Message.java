package edu.eci.ieti.envirify.model;


import edu.eci.ieti.envirify.controllers.dtos.MessageDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String message;
    private String sender;
    private String receiver;
    private String channelId;

    public Message() {
    }

    public Message(MessageDTO messageDTO) {
        this.message = messageDTO.getMessageDTO();
        this.sender = messageDTO.getSenderDTO();
        this.receiver = messageDTO.getReceiverDTO();
        this.channelId = messageDTO.getChannelIdDTO();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}

