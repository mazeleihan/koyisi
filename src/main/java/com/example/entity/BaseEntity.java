package com.example.entity;

import lombok.Data;

import java.util.Date;
@Data
public class BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date created;
    private Date modified;
}
