package com.start.hyper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class HyperController {
    private static final String template = "hello,%s";

    @GetMapping("hyper")
    public HttpEntity<Hyper> hyperHttpEntity(
            @RequestParam(value = "name",required = false,
            defaultValue = "world") String name
    ){
        Hyper hyper = new Hyper(String.format(template, name));
        hyper.add(linkTo(methodOn(HyperController.class).
                hyperHttpEntity(name)).withSelfRel());
        return new ResponseEntity<>(hyper, HttpStatus.OK);
    }

}
