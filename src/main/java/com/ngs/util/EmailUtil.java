package com.ngs.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.Part;
import jakarta.mail.internet.MimeMessage;
@Component
public class EmailUtil {

	@Autowired
	  private JavaMailSender javaMailSender;
	

	  public void sendOtpEmail(String email,String otp) throws MessagingException {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	    mimeMessageHelper.setTo(email);
	    mimeMessageHelper.setSubject("Verify OTP");
	    String emailContent = "<html><body>";
        emailContent += "<h2>Verification code</h2>";
        emailContent += "<p>Please use the verification code below to sign in.</p>";
        emailContent += "<p><strong>" + otp + "</strong></p>";
        emailContent += "<p>If you didn’t request this, you can ignore this email.</p>";
        emailContent += "<p>Thanks,<br>The Mailmeteor team</p>";
        emailContent += "</body></html>";
        mimeMessage.setContent(emailContent, "text/html; charset=utf-8");

	    javaMailSender.send(mimeMessage);
	  }

}
