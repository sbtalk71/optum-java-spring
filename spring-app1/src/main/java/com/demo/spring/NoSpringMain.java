package com.demo.spring;

public class NoSpringMain {

	public static void main(String[] args) {
		Message msg= new Message();
		msg.setHeader("Tea Break");
		msg.setBody("No Idea of brek timings");
		
		Mail mail= new Mail();
		mail.setToAddress("all participants");
		mail.setFromAddress("Trainer");
		mail.setMessage(msg);
		
		System.out.println(mail.getMessage().getBody());

	}

}
