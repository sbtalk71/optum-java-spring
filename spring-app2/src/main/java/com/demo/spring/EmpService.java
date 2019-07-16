package com.demo.spring;

public class EmpService {

	EmpDao dao;

	public void setDao(EmpDao dao) {
		this.dao = dao;
	}

	public String registerEmp(int id, String name, String city, double salary) {
		String resp = dao.saveEmp(new Emp(id, name, city, salary));
		return resp;
	}
}
