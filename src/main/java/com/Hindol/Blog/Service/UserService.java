package com.Hindol.Blog.Service;
import com.Hindol.Blog.Payload.LoginDTO;
import com.Hindol.Blog.Payload.LoginRequestDTO;
import com.Hindol.Blog.Payload.UserDTO;

import java.util.List;
public interface UserService {
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(UserDTO user,Integer userId);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllusers();
    LoginDTO loginUser(LoginRequestDTO loginRequestDTO);
    void deleteUser(Integer userId);

}