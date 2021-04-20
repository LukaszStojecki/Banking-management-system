package pl.stojecki.bankingmanagementsystem.user.service;

import pl.stojecki.bankingmanagementsystem.exception.EmailException;
import pl.stojecki.bankingmanagementsystem.user.dto.NotificationEmail;

public interface MailService {

    void sendRegisterEmail(NotificationEmail notificationEmail) throws EmailException;
}
