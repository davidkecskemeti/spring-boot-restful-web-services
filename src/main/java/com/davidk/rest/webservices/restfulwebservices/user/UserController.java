package com.davidk.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {



    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);

        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        Resource<User> userResource=new Resource<>(user);
        ControllerLinkBuilder linkBuilder= ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(this.getClass())
                        .retrieveAllUsers());
        userResource.add(linkBuilder.withRel("all-users"));
        return userResource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);
        URI savedUserLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(savedUserLocation).build();
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable int id) {
        User deletedUser = userDaoService.deleteById(id);

        if (deletedUser == null) {
            throw new UserNotFoundException("id-" + id);
        }

    }
}
