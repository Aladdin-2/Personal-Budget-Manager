package com.aladdin.personalbudgetcontrollerdemo2.email.impl;

import com.aladdin.personalbudgetcontrollerdemo2.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class ConfirmationEmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${email.fromEmail}")
    private String fromEmail;

    @Override
    public void sendEmail(String email, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject(subject);
            String encodedPassword = URLEncoder.encode(text, "UTF-8");
            String linkText = buildEmailContentForVerify(encodedPassword);
            helper.setText(linkText, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Email göndərmə zamanı xəta baş verdi!", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String buildEmailContentForVerify(String encodedPassword) {
        String verificationLink = "http://localhost:8080/aladdin.com/user/checkPassword?password=" + encodedPassword;
        return """
                <html>
                    <body style="font-family: Arial, sans-serif; color: #333;">
                        <h3 style="color: #007bff;">Qeydiyyat bildirişi!</h3>
                        <p></p> <!-- Burada sənin göndərdiyin text olacaq -->
                        <hr>
                        <p>Qeydiyyatınızı təsdiqləmək üçün aşağıdakı linkə keçid edə bilərsiniz:</p>
                        <p><a href="%s" style="color: #28a745; text-decoration: none;">Təsdiqləmə linkinə keç</a></p>
                    </body>
                </html>
                """.formatted(verificationLink);
    }
}
