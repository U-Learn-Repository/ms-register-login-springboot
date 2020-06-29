package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.pojo.LoginUserPOJO;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;
import co.edu.unal.software_engineering.labs.service.RoleService;
import co.edu.unal.software_engineering.labs.service.UserLdapService;
import co.edu.unal.software_engineering.labs.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

	private UserService userService;

	private RoleService roleService;

	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserLdapService ldapService;

	public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping(value = { "/registro/nuevo-usuario/rol/{roleId}" })
	public ResponseEntity registerNewUser(@PathVariable Integer roleId, @RequestBody RegisterUserPOJO userPOJO) {
		Role role = roleService.findById(roleId);
		User existingUser = userService.findByUsername(userPOJO.getUsername());
		if (role == null || existingUser != null || !userService.isRightUser(userPOJO)) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		User newUser = new User();
		newUser.setNames(userPOJO.getNames().toUpperCase());
		newUser.setSurnames(userPOJO.getSurnames().toUpperCase());
		newUser.setUsername(userPOJO.getUsername().toLowerCase());
		newUser.setIdDocumment(userPOJO.getId_documment());
		newUser.setPassword(passwordEncoder.encode(userPOJO.getPassword()));
		newUser.setRoles(Collections.singletonList(role));
		userService.save(newUser);
		ldapService.create(userPOJO);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PostMapping(value = { "/registro/usuario/rol/{roleId}" })
	public ResponseEntity registerRoleToUser(@PathVariable Integer roleId, @RequestBody LoginUserPOJO userPOJO) {
		Role role = roleService.findById(roleId);
		User existingUser = userService.findByUsername(userPOJO.getUsername());
		if (role == null || existingUser == null || existingUser.hasRole(role)) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		} else if (!passwordEncoder.matches(userPOJO.getPassword(), existingUser.getPassword())) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		existingUser.addRole(role);
		userService.save(existingUser);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PostMapping(value = { "/login" })
	public ResponseEntity loginUser(@RequestBody LoginUserPOJO userPOJO) {
		User existingUser = userService.findByUsername(userPOJO.getUsername());
		if (passwordEncoder.matches(userPOJO.getPassword(), existingUser.getPassword())
				&& ldapService.exist(userPOJO)) {
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping(value = { "/usuarios" })
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	@GetMapping(value = { "/buscarUsuario/{Id}" })
	public Object getUserById(@PathVariable Integer Id) {
		User user = userService.findById(Id);
		if (user != null) {
			return user;
		} else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = { "/rolPorId/{Id}" })
	public Object getUserRoles(@PathVariable Integer Id) {
		User user = userService.findById(Id);
		if (user != null) {
			return user.getRoles();
		} else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = { "/usuarioPorUsername/{Username}" })
	public Object getUserByUsername(@PathVariable String Username) {
		System.out.println(Username);
		User user = userService.findByUsername(Username);
		if (user != null) {
			return user;
		} else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = { "/contarUsuarios" })
	public User contarUsuarios() {
		List<User> users = userService.getAll();
		User user = users.get(users.size() - 1);
		return user;
	}

	@GetMapping(value = { "/usuario/auth" })
	public boolean validarToken() {
		return true;
	}

}
