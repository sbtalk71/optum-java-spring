package com.demo.spring;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestService {
	
	@RequestMapping(path="/greet/{user}",method=RequestMethod.GET)
	public String greet(@PathVariable("user") String name) {
		return name+" Welcome to Spring REST";
	}
	
	@RequestMapping(path="/hi",method=RequestMethod.GET)
	//http://localhost:8080/hi?name=Shantanu
	public String sayHi(@RequestParam("name")String name) {
		return "hi,"+name;
	}

}
