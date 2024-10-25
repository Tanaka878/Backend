package com.musungare.BackendForReact;

import com.musungare.BackendForReact.Email.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BackendForReactApplication {

	@Autowired
	private MailSenderService mailSenderService;

	public static void main(String[] args) {
		SpringApplication.run(BackendForReactApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sendMail() {
		String to = "maxwelli20021@gmail.com"; // recipient email
		String subject = "Welcome";
		String text = "Welcome to our banking application";

		mailSenderService.sendSimpleMail(to, subject, text);
	}
}
