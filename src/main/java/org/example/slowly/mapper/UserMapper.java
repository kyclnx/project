package org.example.slowly.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.slowly.pojo.User;

/**
 * @author njx
 * @date 2024/11/11
 * @Description
 */

@Mapper
public interface UserMapper {
    User loginUsers(@Param("username") String username);
}
