package com.valorant.dataService.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String message;
}
