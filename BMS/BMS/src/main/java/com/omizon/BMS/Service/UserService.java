package com.omizon.BMS.Service;

import com.omizon.BMS.dto.LoginRequest;
import com.omizon.BMS.dto.UserRequest;
import com.omizon.BMS.entity.User;
import com.omizon.BMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

private final UserRepository userRepository;


// register

public User register(UserRequest request) {

    if (userRepository.existsByEmail(request.getEmail())) {

        throw new RuntimeException("Email already exists : " + request.getEmail());

    }

  User user = User.builder()
          .name(request.getName())
          .email(request.getEmail())
          .password(request.getPassword())
          .phone(request.getPhone())
          .build();


      return userRepository.save(user);
}


// login
    public  User login(LoginRequest request)
    {
       User user = userRepository.findByEmail(request.getEmail())
               .orElseThrow(()->new RuntimeException("User not found with email : "+ request.getEmail()));

       if (!user.getPassword().equals(request.getPassword()))
       {

           throw new RuntimeException("Invalid password");
       }


       return user;

    }



    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }

    public User getUserById(Long id )
    {
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with email : "+id));


    }




}
