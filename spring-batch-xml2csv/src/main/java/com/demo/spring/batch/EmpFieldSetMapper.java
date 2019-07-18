package com.demo.spring.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EmpFieldSetMapper implements FieldSetMapper<Emp> {

	@Override
	public Emp mapFieldSet(FieldSet fieldSet) throws BindException {
		Emp e = new Emp();
		e.setEmpId(fieldSet.readInt("empId"));
		e.setName(fieldSet.readString("name"));
		e.setCity(fieldSet.readString("city"));
		e.setSalary(fieldSet.readDouble("salary"));
		return e;
	}

}
