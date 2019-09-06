package com.restfulservice.service;

import javax.mail.internet.MimeMessage;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.QueueSender;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.restfulservice.model.Mail;
import com.restfulservice.model.Mailer;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class EmailService {

	String BROKER_URL = "tcp://34.74.243.55:61616";
	String BROKER_USERNAME = "admin"; 
	String BROKER_PASSWORD = "admin";
    @Autowired
    private JavaMailSender emailSender;
    
    @Autowired
    private Configuration freemarkerConfig;

    public String GetProducer(String queueName ,String correlationId,String jsonString) {
		Connection connection = null;
		ActiveMQSession session = null;
		Destination destination = null;
		MessageProducer producer = null;
		try {
	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_USERNAME, BROKER_PASSWORD, BROKER_URL);
     connection = connectionFactory.createConnection();
    session = (ActiveMQSession) connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
          queueName = queueName.trim();
            destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
          connection.start();
          TextMessage textMessage = session.createTextMessage();
          textMessage.setText(jsonString);
          textMessage.setJMSCorrelationID(correlationId);
			textMessage.setJMSRedelivered(true);
			producer.send(textMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
			connection.close();
			connection=null;
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
  		//System.out.println("correlationId Producer :: "+correlationId);

		return correlationId;
	}
    
    public void sendEmail(Mailer mail) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
      
        // Using a subfolder such as /templates here
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        
        Template t = freemarkerConfig.getTemplate("email-template.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());

       // helper.setFrom(mail.getMailFrom());
        helper.setTo(mail.getMailTo());
        helper.setText(text, true);
        helper.setSubject(mail.getMailSubject());

        emailSender.send(message);
    }

    public void sendForgotPasswordEmail(Mailer mail) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
      
        // Using a subfolder such as /templates here
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        
        Template t = freemarkerConfig.getTemplate("forgotpasswd-email-template.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());

       // helper.setFrom(mail.getMailFrom());
        helper.setTo(mail.getMailTo());
        helper.setText(text, true);
        helper.setSubject(mail.getMailSubject());

        emailSender.send(message);
    }

    public void sendFreeTrialEmail(Mailer mail) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
      
        // Using a subfolder such as /templates here
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        
        Template t = freemarkerConfig.getTemplate("freeTrial-email-template.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());

       // helper.setFrom(mail.getMailFrom());
        helper.setTo(mail.getMailTo());
        helper.setText(text, true);
        helper.setSubject(mail.getMailSubject());

        emailSender.send(message);
    }
    public void sendSimpleMessage(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        message.setTo(mail.getTo());
        message.setFrom(mail.getFrom());

        emailSender.send(message);
    }

}