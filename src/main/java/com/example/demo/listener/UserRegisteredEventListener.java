package com.example.demo.listener;

import com.example.demo.event.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventListener {

    private static final Logger log = LoggerFactory.getLogger(UserRegisteredEventListener.class);

    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event) {
        log.info("User registered event received: username={}, email={}, role={}",
                event.username(),
                event.email(),
                event.role());
    }
}
