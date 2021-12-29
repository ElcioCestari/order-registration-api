package com.brotherselectronics.orderregistration.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
a@Document("Users")
public class User extends BaseEntity {

    private String login;
    private String password;
    private String name;

}