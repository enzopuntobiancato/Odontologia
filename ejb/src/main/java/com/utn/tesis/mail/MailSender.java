package com.utn.tesis.mail;

import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Stateless
@Slf4j
public class MailSender  {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private static final boolean ACTIVE = true;

    public void execute(Runnable command) {
        if (ACTIVE) {
            executor.submit(command);
        } else {
            log.info("Envio de emails desactivado.");
        }
    }
}
