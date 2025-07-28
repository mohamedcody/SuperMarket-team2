package com.team2.supermarket.entity;
import com.team2.supermarket.enums.UserRoles;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "CustomUser")
@Builder
public class CustomUser {

    @Id
    private String id;
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank (message = "password is mandatory")
    private String password;
    @NotBlank(message = "name is user name ")
    private String username;

    @Builder.Default
    private UserRoles role =UserRoles.CUSTOMER;



}
