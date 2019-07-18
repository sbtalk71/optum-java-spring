package com.demo.spring.batch;

import org.springframework.batch.item.ItemProcessor;

import com.demo.spring.batch.entity.Emp;

public class EmpProcessor implements ItemProcessor<Emp, Emp> {
public Emp process(Emp item) throws Exception {
	System.out.println("Inserting Emp to database ...."+item.getEmpId());
	return item;
}
}
