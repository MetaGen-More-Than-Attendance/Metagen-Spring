package com.hst.metagen.service.concretes;

import com.hst.metagen.service.abstracts.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

	private final JavaMailSender mailSender;


	public void sendInfoEmail(String recipientEmail, String info, String lectureName) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("layermarkhakan@gmail.com", "Survey App");
		helper.setTo(recipientEmail);

		String subject = String.format("Attendance Information for %s", lectureName);

		String content = "<p>Hello,</p>"
				+ "<br>"
				+ "<p>" + info + "</p>"
				+ "<br>"
				+ "<p>Thanks</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);

	}
}
