package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RoleController{

    private RoleService roleService;


    public RoleController( RoleService roleService ){
        this.roleService = roleService;
    }


    @GetMapping( value = { "/roles" } )
    public List<Role> getAllRoles( ){
        return roleService.getAll( );
    }

}
