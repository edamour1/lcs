package com.warner.lcs;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class LcsApplication  {
	public static void main(String[] args) {
		Application.launch(JavaFx.class,args);
	}

}