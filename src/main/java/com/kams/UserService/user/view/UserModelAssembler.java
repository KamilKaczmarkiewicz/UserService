package com.kams.UserService.user.view;

import com.kams.UserService.user.controller.UserController;
import com.kams.UserService.user.entity.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {



    @Override
    public UserModel toModel(User entity) {
        UserModel user = UserModel.builder()
                .userName(entity.getUserName())
                .age(entity.getAge())
                .createdTime(entity.getCreatedTime())
                .build();
        Link link = linkTo(methodOn(UserController.class).getUser(entity.getId())).withSelfRel();
        user.add(link);
        return user;
    }


    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserModel> directorRepresentations = RepresentationModelAssembler.super.toCollectionModel(entities);


        return directorRepresentations;
    }
}
