package com.aladdin.personalbudgetcontrollerdemo2.security;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.User;
import com.aladdin.personalbudgetcontrollerdemo2.dao.repository.UserRepository;
import com.aladdin.personalbudgetcontrollerdemo2.email.impl.ConfirmationEmailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final ConfirmationEmailServiceImpl confirmationEmailServiceImpl;

    public void registerUser(User user) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new AlreadyBoundException("User already exists with this email: " + user.getEmail());
        }
        String encryptPassword = encryptionService.encrypt(user.getPassword());
        user.setPassword(encryptPassword);
        userRepository.save(user);
        confirmationEmailServiceImpl.sendEmail(
                user.getEmail(),
                "Thank you for registering with the Aladdin budget management program!\n 🎉✨🎉✨🎉✨🎊 ",
                encryptPassword);
    }
}
