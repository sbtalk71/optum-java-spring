package com.demo.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmpService {

	@Autowired
	@Qualifier("empDaoJPAImpl")
	EmpDao dao;

	/*public void setDao(EmpDao dao) {
		this.dao = dao;
	}*/

	public String registerEmp(int id, String name, String city, double salary) {
		String resp = dao.saveEmp(new Emp(id, name, city, salary));
		return resp;
	}
}
