package com.hst.metagen.service.abstracts;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailSenderService {

	void sendInfoEmail(String recipientEmail, String info, String lectureName) throws MessagingException, UnsupportedEncodingException;
}
