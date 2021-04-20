package pl.stojecki.bankingmanagementsystem.user.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import pl.stojecki.bankingmanagementsystem.exception.EmailException;
import pl.stojecki.bankingmanagementsystem.user.dto.NotificationEmail;
import pl.stojecki.bankingmanagementsystem.user.service.MailService;

@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

        private JavaMailSender javaMailSender;


    @Override
     public void sendRegisterEmail(NotificationEmail notificationEmail) throws EmailException {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(notificationEmail.getBody());
        };
        try {
            javaMailSender.send(mimeMessagePreparator);
            log.info("Activation email sent");
        } catch (MailException e) {
            throw new EmailException("Exception occurred when sending email to " + notificationEmail.getRecipient());
        }
    }
}
