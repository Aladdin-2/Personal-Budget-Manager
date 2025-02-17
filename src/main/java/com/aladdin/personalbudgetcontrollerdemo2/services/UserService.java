package com.aladdin.personalbudgetcontrollerdemo2.services;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.User;
import com.aladdin.personalbudgetcontrollerdemo2.dao.repository.UserRepository;
import com.aladdin.personalbudgetcontrollerdemo2.email.impl.ConfirmationEmailServiceImpl;
import com.aladdin.personalbudgetcontrollerdemo2.exception.UserNotFoundException;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseUserDto;
import com.aladdin.personalbudgetcontrollerdemo2.security.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final ConfirmationEmailServiceImpl confirmationEmailService;


    public void loginUser(ResponseUserDto userDto) throws Exception {
        String encryptPassword = encryptionService.encrypt(userDto.getPassword());
        User user = userRepository
                .findByEmailAndPassword(userDto.getEmail(), encryptPassword)
                .orElseThrow(() -> new UserNotFoundException("Not found user this email:" + userDto.getEmail()));
        log.info("You have successfully logged in: {}", user.getUsername());
    }


    public User checkingPassword(String encryptPassword) throws Exception {
        User user = userRepository
                .findByPassword(encryptPassword)
                .orElseThrow(() -> new UserNotFoundException("Incorrect verification!"));

        user.setPassword(encryptionService.decrypt(user.getPassword()));
        return user;
    }
}

