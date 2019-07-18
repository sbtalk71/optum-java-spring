package com.demo.spring;

import org.springframework.stereotype.Repository;

@Repository
public class EmpDaoJPAImpl implements EmpDao{

	@Override
	public String saveEmp(Emp e) {
		
		return "JPA: Emp Saved with ID : "+e.getEmpId();
	}

}
