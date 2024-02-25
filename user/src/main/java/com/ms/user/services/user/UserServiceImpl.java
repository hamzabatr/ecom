package com.ms.user.services.user;

import com.ms.user.dto.UserDTO;
import com.ms.user.mappers.IUserDTOMapper;
import com.ms.user.repositories.IUserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{
    private final IUserRepository userRepository;
    private final IUserDTOMapper userDTOMapper;

    @Override
    public UserDTO getUserById(String userId) {
        if (!StringUtils.hasText(userId)) {
            throw new IllegalArgumentException("");
        }

        return userDTOMapper.userToUserDTO(
                userRepository.findUserById(userId)
                        .orElseThrow(() -> new NotFoundException("User not found."))
        );
    }
}
