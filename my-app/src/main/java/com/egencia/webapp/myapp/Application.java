package com.egencia.webapp.myapp;

import org.springframework.boot.SpringApplication;
import com.egencia.library.boot.autoconfigure.EgenciaApplication;

@EgenciaApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}