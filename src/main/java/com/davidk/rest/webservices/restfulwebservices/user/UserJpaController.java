package com.davidk.rest.webservices.restfulwebservices.user;

import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/jpa")
public class UserJpaController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        Resource<User> userResource = new Resource<>(user.get());
        ControllerLinkBuilder linkBuilder = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(this.getClass())
                        .retrieveAllUsers());
        userResource.add(linkBuilder.withRel("all-users"));
        return userResource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI savedUserLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(savedUserLocation).build();
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) throw new UserNotFoundException("id-" + id);

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {

        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) throw new UserNotFoundException("id-" + id);

        User user = userOptional.get();

        post.setUser(user);

        Post savedPost = postRepository.save(post);

        URI savedUserLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(savedUserLocation).build();
    }
}
