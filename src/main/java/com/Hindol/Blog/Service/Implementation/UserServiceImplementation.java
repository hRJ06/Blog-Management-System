package com.Hindol.Blog.Service.Implementation;

import com.Hindol.Blog.Entity.User;
import com.Hindol.Blog.Exception.ResourceNotFoundException;
import com.Hindol.Blog.Payload.LoginDTO;
import com.Hindol.Blog.Payload.LoginRequestDTO;
import com.Hindol.Blog.Payload.UserDTO;
import com.Hindol.Blog.Repository.UserRepository;
import com.Hindol.Blog.Service.UserService;
import com.Hindol.Blog.Util.JWTToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JWTToken jwtToken;
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String hashedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(hashedPassword);
        User user = this.DTOToUser(userDTO);
        User savedUser = this.userRepository.save(user);
        return this.modelMapper.map(savedUser,UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","ID",userId));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User save = this.userRepository.save(user);
        UserDTO newUserDTO = UserToDTO(save);
        return newUserDTO;
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","ID",userId));
        return this.UserToDTO(user);
    }

    @Override
    public LoginDTO loginUser(LoginRequestDTO loginRequestDTO) {
        User user = this.userRepository.findByEmail(loginRequestDTO.getEmail());
        if(user != null) {
            if(bCryptPasswordEncoder.matches(loginRequestDTO.getPassword(),user.getPassword())) {
                String token = jwtToken.generateToken(user);
                LoginDTO loginDTO = new LoginDTO();
                loginDTO.setToken(token);
                return loginDTO;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public List<UserDTO> getAllusers() {
        List<User> users = this.userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(user -> this.UserToDTO(user)).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","ID",userId));
        this.userRepository.delete(user);
    }

    private User DTOToUser(UserDTO userDTO) {
       User user = this.modelMapper.map(userDTO, User.class);
       return user;
    }
    private UserDTO UserToDTO(User user) {
        UserDTO userDTO = this.modelMapper.map(user,UserDTO.class);
        return userDTO;
    }

}