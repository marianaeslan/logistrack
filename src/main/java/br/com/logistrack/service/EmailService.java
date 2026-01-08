package br.com.logistrack.service;

import br.com.logistrack.dto.encomenda.EncomendaInputDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final Configuration fmConfiguration;


    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(String subject, Map<String, Object> dados, EncomendaInputDTO encomendaDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(encomendaDTO.getEmail());
            mimeMessageHelper.setSubject(subject);


            String geeneratedHtml = getTemplateHtml(dados, "email-template.ftl");


            mimeMessageHelper.setText(geeneratedHtml, true);


            emailSender.send(mimeMessage);

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();

            throw new RuntimeException("Erro ao enviar email para: " + encomendaDTO.getEmail());
        }
    }

    // Método auxiliar para misturar o HTML com o Map de dados
    private String getTemplateHtml(Map<String, Object> dados, String templateName) throws IOException, TemplateException {
        Template template = fmConfiguration.getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }
}