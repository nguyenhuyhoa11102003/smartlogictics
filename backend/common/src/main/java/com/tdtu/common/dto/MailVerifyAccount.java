package com.tdtu.common.dto;

public class MailVerifyAccount {

    //Customer's name created in user profile
    private String subject;

    //Customer's email created in user profile
    private String to;

    //The content of the email
    private String contents;

    public MailVerifyAccount() {
    }

    public MailVerifyAccount(String subject, String to, String contents) {
        this.subject = subject;
        this.to = to;
        this.contents = contents;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public String getContents() {
        return contents;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "MailVerifyAccount{" +
                "subject='" + subject + '\'' +
                ", to='" + to + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
