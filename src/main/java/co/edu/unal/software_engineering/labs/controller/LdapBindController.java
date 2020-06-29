package co.edu.unal.software_engineering.labs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unal.software_engineering.labs.pojo.LoginUserPOJO;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;
import co.edu.unal.software_engineering.labs.service.UserLdapService;

@RestController
@RequestMapping("/secret/api/ldap")
public class LdapBindController {

	@Autowired
	private UserLdapService ldapRepo;

	@PostMapping()
	public ResponseEntity<String> createUserLdap(@RequestBody RegisterUserPOJO person) {
		String result = ldapRepo.create(person);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<String> existUserLdap(@RequestBody LoginUserPOJO person) {
		Boolean result = ldapRepo.exist(person);
		return new ResponseEntity<>(result.toString(), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<List<RegisterUserPOJO>> retrieve() {
		return new ResponseEntity<List<RegisterUserPOJO>>(ldapRepo.list(), HttpStatus.OK);
	}
}