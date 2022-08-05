package com.minimakerlabrobo.valueObject;

/**
 * Created by saba on 01/03/17.
 */

public class MessageVO {

    private static MessageVO messageVO= null;

    private String message;

    private MessageVO(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static MessageVO getInstancia(){
        if (MessageVO.messageVO==null){
            MessageVO.messageVO = new MessageVO();

        }

            return messageVO;

    }
}
