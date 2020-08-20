/*
 * package com.example.demo.email;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.mail.javamail.JavaMailSender; import
 * org.springframework.mail.javamail.MimeMessageHelper; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import javax.mail.internet.MimeMessage;
 * 
 * @RestController public class EmailControllerHtml {
 * 
 * @Autowired private JavaMailSender mailSender;
 * 
 * @RequestMapping(path = "/email-send", method = RequestMethod.GET) public
 * String sendMailHtml() { try { MimeMessage mail =
 * mailSender.createMimeMessage();
 * 
 * MimeMessageHelper helper = new MimeMessageHelper( mail );
 * helper.setFrom("sergiofeitosa@gmail.com"); helper.setTo(
 * "sergiofeitosa@live.com" ); helper.setSubject( "Teste Envio de e-mail" );
 * helper.setText("<p>Hello from Spring Boot Application</p>", true);
 * mailSender.send(mail);
 * 
 * return "OK"; } catch (Exception e) { e.printStackTrace(); return
 * "Erro ao enviar e-mail"; } } }
 */