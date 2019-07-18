package com.demo.spring.batch;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.demo.spring.batch.entity.Emp;

public class RecordFieldSetMapper implements FieldSetMapper<Emp> {

    public Emp mapFieldSet(FieldSet fieldSet) throws BindException {

       
        Emp emp = new Emp();
        emp.setEmpId(fieldSet.readInt("empId"));
        emp.setName(fieldSet.readString("name"));
        emp.setCity(fieldSet.readString("city"));
        emp.setSalary(fieldSet.readDouble("salary"));

        return emp;

    }

}