package com.demo.spring.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class MyWordPrinter implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> items) throws Exception {
		for(String s: items) {
			System.out.println(s);
		}

	}

}
