package com.davidk.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"field2"})//Static filtering
@JsonFilter("SomeBeanFilter")//Dinamyc filtering
public class SomeBean {

    private String field1;
    private String field2;

    //    @JsonIgnore//Static filtering
    private String field3;

}
