package com.example.entity;

import com.example.mapper.InterFaceMapper;
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
