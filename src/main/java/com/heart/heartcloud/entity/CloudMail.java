package com.heart.heartcloud.entity;

import java.io.Serializable;

/**
 * @ClassName: CloudMail
 * @Description: 写完发现没啥用，暂时留着吧
 * @Author: jayhe
 * @Date: 2019/9/27 9:20
 * @Version: v1.0
 */
public class CloudMail implements Serializable {

    private static final long serialVersionUID = 7796451382824186566L;

    private String sender;

    private String recipient;

    private String subject;

    private String content;

    public CloudMail() {
    }

    public CloudMail(String sender, String recipient, String subject, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public CloudMail setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public String getRecipient() {
        return recipient;
    }

    public CloudMail setRecipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public CloudMail setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CloudMail setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return "CloudMail{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
