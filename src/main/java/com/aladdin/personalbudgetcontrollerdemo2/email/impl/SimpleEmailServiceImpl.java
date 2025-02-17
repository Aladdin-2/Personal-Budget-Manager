package com.aladdin.personalbudgetcontrollerdemo2.email.impl;

import com.aladdin.personalbudgetcontrollerdemo2.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleEmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${email.fromEmail}")
    private String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String text, FileSystemResource imageFile) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addInline("image", imageFile);
            String linkText = buildEmailContent(text);
            helper.setText(linkText, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildEmailContent(String text) {
        return """
                <html>
                    <body style="font-family: Arial, sans-serif; color: #333;">
                        <h3 style="color: #007bff;">Bildiriş!</h3>
                        <p>%s</p> <!-- Burada sənin göndərdiyin text olacaq -->
                        <hr>
                        <p>Mənim portfoliyomu görmək üçün aşağıdakı linkə keçid edə bilərsiniz!:</p>
                        <p><a href="https://github.com/" style="color: #28a745; text-decoration: none;">➡ My portfolio click here ⬅</a></p>
                    </body>
                </html>
                """.formatted(text);
    }

}

