package com.Hindol.Blog.Controller;

import com.Hindol.Blog.Payload.APIResponse;
import com.Hindol.Blog.Payload.LoginDTO;
import com.Hindol.Blog.Payload.LoginRequestDTO;
import com.Hindol.Blog.Payload.UserDTO;
import com.Hindol.Blog.Service.UserService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    // POST - Create User
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUserDTO = this.userService.createUser(userDTO);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginDTO loginDTO = this.userService.loginUser(loginRequestDTO);
        if(loginDTO != null) {
            return new ResponseEntity<LoginDTO>(loginDTO, HttpStatus.OK);
        }
        else {
            return null;
        }
    }
    // PUT - Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,@PathVariable Integer userId) {
        UserDTO updatedUser = this.userService.updateUser(userDTO,userId);
        return ResponseEntity.ok(updatedUser);
    }
    // DELETE  - Delete User
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<APIResponse>(new APIResponse("User deleted successfully",true),HttpStatus.OK);
    }
    // GET - Get All Users
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllusers());
    }
    // GET - Get Single Users
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}