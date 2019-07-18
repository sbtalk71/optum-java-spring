package com.demo.spring.batch;

import org.springframework.batch.item.ItemProcessor;

public class EmpItemProcessor implements ItemProcessor<Emp, Emp> {

	@Override
	public Emp process(Emp item) throws Exception {
		System.out.println("precessing : "+item);
		return item;
	}

}
