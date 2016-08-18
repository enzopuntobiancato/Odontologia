package com.utn.tesis.mail;

import javax.ejb.Stateless;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Stateless
public class MailSender  {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public void execute(Runnable command) {
        executor.submit(command);
    }
}
