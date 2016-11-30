package com.utn.tesis.mail;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
@Slf4j
public class MailSender  {
    private final ExecutorService executor = Executors.newFixedThreadPool(1);
    private static final boolean ACTIVE = true;

    public void execute(Runnable command) {
        if (ACTIVE) {
            executor.submit(command);
        } else {
            log.info("Envio de emails desactivado.");
        }
    }
}
