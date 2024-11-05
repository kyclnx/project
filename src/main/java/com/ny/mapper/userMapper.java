package com.ny.mapper;

import com.ny.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author njx
 * @date 2024/11/4
 * @Description
 */
@Mapper
public interface userMapper {
    User userLogin(@Param("username") String username);
}