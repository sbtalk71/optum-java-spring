package com.demo.spring;

import org.springframework.stereotype.Repository;

@Repository
public class EmpDaoMockImpl implements EmpDao{

	@Override
	public String saveEmp(Emp e) {
		
		return "Emp Saved with ID : "+e.getEmpId();
	}

}
