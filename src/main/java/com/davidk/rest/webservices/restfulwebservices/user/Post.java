package com.davidk.rest.webservices.restfulwebservices.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
