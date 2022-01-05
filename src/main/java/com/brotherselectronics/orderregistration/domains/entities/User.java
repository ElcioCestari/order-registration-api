package com.brotherselectronics.orderregistration.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Users")
@EqualsAndHashCode(callSuper=true)
public class User extends BaseEntity {

    private String login;
    private String password;
    private String name;

}