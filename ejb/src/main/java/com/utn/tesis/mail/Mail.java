/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utn.tesis.mail;

/**
 *
 * @author Enzo
 */
public abstract class Mail {

    private String mail;
    private StringBuilder builder;
    private String title;
    private String content;

    public Mail() {
        this.builder = new StringBuilder();
    }

    /**
     * @return the mail
     */
    public String getMail() {
        mail = getBuilder().toString();
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    public void buildMail() {
        mail = getContent();
    }


    protected abstract void setVariableContent();

    /**
     * @return the builder
     */
    public StringBuilder getBuilder() {
        return builder;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
        mail = content;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
