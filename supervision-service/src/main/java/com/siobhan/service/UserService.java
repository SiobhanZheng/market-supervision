package com.siobhan.service;
import com.siobhan.entity.UserDO;
import com.siobhan.proxy.dto.UserDTO;

import java.util.List;

/**
 * Created by siobhan.zheng on 2019/3/25.
 */
public interface UserService {
    List<UserDO> findAllUsers();
    boolean checkUser(UserDTO userDTO);

}
