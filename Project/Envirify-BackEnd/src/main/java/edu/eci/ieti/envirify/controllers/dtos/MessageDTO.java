package edu.eci.ieti.envirify.controllers.dtos;

import java.io.Serializable;

public class MessageDTO implements Serializable {

    private String messageDTO;
    private String senderDTO;
    private String receiverDTO;
    private String channelIdDTO;

    public MessageDTO(String messageDTO, String senderDTO, String receiverDTO, String channelIdDTO) {
        this.messageDTO = messageDTO;
        this.senderDTO = senderDTO;
        this.receiverDTO = receiverDTO;
        this.channelIdDTO = channelIdDTO;
    }

    public MessageDTO() {
    }

    public String getMessageDTO() {
        return messageDTO;
    }

    public void setMessageDTO(String messageDTO) {
        this.messageDTO = messageDTO;
    }

    public String getSenderDTO() {
        return senderDTO;
    }

    public void setSenderDTO(String senderDTO) {
        this.senderDTO = senderDTO;
    }

    public String getReceiverDTO() {
        return receiverDTO;
    }

    public void setReceiverDTO(String receiverDTO) {
        this.receiverDTO = receiverDTO;
    }

    public String getChannelIdDTO() {
        return channelIdDTO;
    }

    public void setChannelIdDTO(String channelIdDTO) {
        this.channelIdDTO = channelIdDTO;
    }
}
