package com.siobhan.service.impl;

import com.siobhan.entity.UserDO;
import com.siobhan.proxy.dto.UserDTO;
import com.siobhan.repository.UserRepository;
import com.siobhan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by siobhan.zheng on 2019/3/25
 */
@Service
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<UserDO> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean checkUser(UserDTO userDTO) {
        boolean flag = false;
        if (userDTO.getUserCode() != null && userDTO.getUserPw() != null){
            UserDO userDO = userRepository.findByUserCodeAndPasswordAndStatus(userDTO.getUserCode(),
                    userDTO.getUserPw(), "1");
            if (userDO != null){
                flag = true;
            }
        }
        return flag;
    }
}
