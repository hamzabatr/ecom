package com.ms.user.services.user;

import com.ms.user.dto.UserDTO;

public interface IUserService {
    UserDTO getUserById(String userId);
}
