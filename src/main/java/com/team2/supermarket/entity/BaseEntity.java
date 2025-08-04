package com.team2.supermarket.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public abstract class BaseEntity {

    @Id
    private String id;

    @NotBlank(message = "CreatedBy is required")
    private String createBy;

    @NotBlank(message = "UpdatedBy is required")
    private String updateBy;

    @PastOrPresent(message = "createAt must be in the past or now")
    private Date createAt;

    @PastOrPresent(message = "updateAt must be in the past or now")
    private Date updateAt;



}
