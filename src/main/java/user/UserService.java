package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void add(User user){
        if (userRepository.findByUserIp(user.getUserIp()).isPresent() && userRepository.findByUserIp(user.getUserIp()).get().getPassword() == null){
            user.setId(userRepository.findByUserIp(user.getUserIp()).get().getId());
            userRepository.save(user);
        }else if (userRepository.findByUserIp(user.getUserIp()).isPresent() == false){
            userRepository.save(user);
        }else if (userRepository.findByUserIp(user.getUserIp()).isPresent() && userRepository.findByUserIp(user.getUserIp()).get().getPassword() != null){
            System.out.println("user exist");
        }
    }

    public User findByEmail(String email){
        return userRepository.findUserByEmail(email).get();
    }

    public User getById(Long id){
        return userRepository.getOne(id);
    }

    public User findByIp(String ip){
        return userRepository.findByUserIp(ip).get();
    }

    public void confirmAccount(String ip){
       User user = userRepository.findByUserIp(ip).get();
       user.setConfirmated(true);
       userRepository.save(user);
    }
}
