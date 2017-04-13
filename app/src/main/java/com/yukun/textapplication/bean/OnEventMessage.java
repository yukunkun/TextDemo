package com.yukun.textapplication.bean;

import com.avos.avoscloud.im.v2.AVIMMessage;

/**
 * Created by yukun on 17-4-12.
 */
public class OnEventMessage {
    public String Message;
    public AVIMMessage avimMessage;

    public OnEventMessage(String message, AVIMMessage avimMessage) {
        Message = message;
        this.avimMessage = avimMessage;
    }

    public OnEventMessage(String message) {
        Message = message;
    }
}
