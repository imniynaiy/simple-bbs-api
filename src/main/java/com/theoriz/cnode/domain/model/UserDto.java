package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String loginname;
    private String password;
}
