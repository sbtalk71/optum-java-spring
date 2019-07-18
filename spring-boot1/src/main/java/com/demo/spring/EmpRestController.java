package com.demo.spring;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Emp;

@RestController
public class EmpRestController {

	@Autowired
	private EmpRepository repo;

	//@RequestMapping(path = "/emp/find/{id}", method =
	// RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(path = "/emp/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findEmp(@PathVariable("id") int id) {
		Optional<Emp> o = repo.findById(id);
		if (o.isPresent()) {
			return ResponseEntity.ok(o.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping(path = "/emp/save", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveEmp(@RequestBody Emp e) {
		if (repo.existsById(e.getEmpId())) {
			return ResponseEntity.ok("Emp already exists");
		} else {
			repo.save(e);
			return ResponseEntity.ok("Emp Saved Successfully..");
		}
	}

	@DeleteMapping(path = "/emp/delete", produces = MediaType.TEXT_PLAIN_VALUE)

	public ResponseEntity<String> deleteEmp(@RequestParam("id") int id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
			return ResponseEntity.ok("Emp Deleted");
		} else {

			return ResponseEntity.ok("Emp not found with the given id..");
		}
	}

	@PutMapping(path = "/emp/update", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateEmp(@RequestBody Emp e) {

		repo.save(e);
		return ResponseEntity.ok("Emp Saved Successfully..");
	}

	@GetMapping(path = "/emp", produces = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<List<Emp>> listAllEmps() {
		return ResponseEntity.ok(repo.findAll());
	}
}
