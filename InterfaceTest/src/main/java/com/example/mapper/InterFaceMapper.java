package com.example.mapper;

import com.example.entity.InterfaceBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterFaceMapper {
    public List<InterfaceBean> getInterfaceList();
}
