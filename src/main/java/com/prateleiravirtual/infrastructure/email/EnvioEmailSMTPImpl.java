package com.prateleiravirtual.infrastructure.email;

import com.prateleiravirtual.core.properties.EmailProperties;
import com.prateleiravirtual.domain.service.EmailService;
import com.prateleiravirtual.infrastructure.exception.EnvioEmailException;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import jakarta.mail.MessagingException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 * Classe que implementa a interface de envio de e-mails.
 *
 * @author Jhansen Barreto
 */
public class EnvioEmailSMTPImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemarker;

    /**
     * Método responsável por fazer o envio de e-mails.
     *
     * @param email (Objeto com requisitos necessários para um envio)
     */
    @Override
    public void enviar(Email email) {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(email.getDestinatarios().toArray(String[]::new));
            helper.setSubject(email.getAssunto());
            helper.setText(getBodyMailTemplate(email.getCorpo(), email.getVariaveis()), true);

            mailSender.send(message);

        } catch (MessagingException | MailException ex) {
            throw new EnvioEmailException("Não foi possível enviar e-mail.", ex);
        }
    }

    /**
     * Método responsável por fazer a mescla entre as informações fornecidas
     * pelo sistema com o template HTML específico de cada tipo de e-mail.
     *
     * @param fileName (Nome do arquivo HTML)
     * @param fields (Objeto com informações a serem inseridas no template)
     * @return -> Corpo do e-mail
     */
    protected String getBodyMailTemplate(String fileName, Object fields) {
        try {
            var template = freemarker.getTemplate(fileName);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, fields);

        } catch (TemplateException | IOException ex) {
            throw new EnvioEmailException("Não foi possível processar template do e-mail.", ex);
        }
    }
}
