package com.example.mapper;

import com.example.entity.User;

import java.util.List;

/**
 * User 表的数据访问接口。
 * MyBatis 通过 UserMapper.xml 的 namespace 绑定到此接口。
 */
public interface UserMapper {

    /** 查询所有用户 */
    List<User> findAll();

    /** 根据主键查询 */
    User findById(Integer id);

    /** 新增用户，返回影响行数 */
    int insert(User user);

    /** 更新用户，返回影响行数 */
    int update(User user);

    /** 根据主键删除 */
    int deleteById(Integer id);
}
