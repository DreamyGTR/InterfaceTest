package com.example.entity;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class InterfaceBean {
    private Integer id;
    private String name;
    private String url;
    private String method;
}
