package com.demo.spring;

public class EmpDaoMockImpl implements EmpDao{

	@Override
	public String saveEmp(Emp e) {
		
		return "Emp Saved with ID : "+e.getEmpId();
	}

}
