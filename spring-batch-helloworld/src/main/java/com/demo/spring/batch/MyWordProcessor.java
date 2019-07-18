package com.demo.spring.batch;

import org.springframework.batch.item.ItemProcessor;

public class MyWordProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		System.out.println("Item Processed : "+item);
		return item;
	}

}
