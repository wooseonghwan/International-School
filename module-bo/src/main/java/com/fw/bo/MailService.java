package com.fw.bo;

import com.fw.core.dto.bo.BoUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    /** public void sendRejectMail(BoUserDTO boUserDTO) {
        String to = boUserDTO.getEmail();
        String subject = "[파트너스] 승인 거부 안내";

        String content = buildHtmlContent(boUserDTO.getPartnersReason());

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // HTML 여부 true

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("메일 전송 실패", e);
        }
    }

    private String buildHtmlContent(String reason) {
        return "<html>" +
                "<body style='font-family:Arial, sans-serif;'>" +
                "<h2 style='color:#d9534f;'>[승인 거부 안내]</h2>" +
                "<p>안녕하세요, 파트너스 신청자님.</p>" +
                "<p>귀하의 신청이 아래 사유로 인해 승인되지 않았습니다:</p>" +
                "<p><strong>사유: </strong>" + reason + "</p>" +
                "<p>자세한 사항은 070-4115-0403 에 문의해 주세요.</p>" +
                "<br/>" +
                "<p>감사합니다.<br/>유엔엠</p>" +
                "</body>" +
                "</html>";
    }*/
}
