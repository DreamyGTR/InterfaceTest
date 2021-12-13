package com.example.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InterfaceCaseBean {
    private Integer id;
    private Integer caseId;
    private Integer interfaceId;
    private String expectResult;

}
