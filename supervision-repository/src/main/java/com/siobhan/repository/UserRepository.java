package com.siobhan.repository;

import com.siobhan.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by siobhan.zheng on 2019/3/25.
 */
@Repository
public interface UserRepository extends JpaRepository<UserDO,Long>{

    @Override
    List<UserDO> findAll();

    /**
     * 登陆时用于校验账号
     * @param userCode 登陆码
     * @param password 密码
     * @param status 有效状态
     * @return
     */
    UserDO findByUserCodeAndPasswordAndStatus(String userCode, String password, String status);
}
