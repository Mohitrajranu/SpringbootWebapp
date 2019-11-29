package com.restfulservice.util;

import java.io.IOException;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.ASM;
import com.sendgrid.helpers.mail.objects.BccSettings;
import com.sendgrid.helpers.mail.objects.ClickTrackingSetting;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.FooterSetting;
import com.sendgrid.helpers.mail.objects.GoogleAnalyticsSetting;
import com.sendgrid.helpers.mail.objects.MailSettings;
import com.sendgrid.helpers.mail.objects.OpenTrackingSetting;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.sendgrid.helpers.mail.objects.Setting;
import com.sendgrid.helpers.mail.objects.SpamCheckSetting;
import com.sendgrid.helpers.mail.objects.SubscriptionTrackingSetting;
import com.sendgrid.helpers.mail.objects.TrackingSettings;

public class SendgridTest {


	  // Fully populated Mail object
	  public static Mail buildKitchenSink() throws IOException {
	    Mail mail = new Mail();

	    Email fromEmail = new Email();
	    fromEmail.setName("Mohit Raj");
	    fromEmail.setEmail("mohit.raj@bizlem.com");
	    mail.setFrom(fromEmail);

	    mail.setSubject("Hello World from the Twilio SendGrid Java Library");

	    Personalization personalization = new Personalization();
	    Email to = new Email();
	    to.setName("Ranu");
	    to.setEmail("mohitraj.ranu@gmail.com");
	    personalization.addTo(to);
	    to.setName("Siva");
	    to.setEmail("siva@bizlem.com");
	    personalization.addTo(to);
	   /* Email cc = new Email();
	    cc.setName("Vivek");
	    cc.setEmail("test3@example.com");
	    personalization.addCc(cc);
	    cc.setName("Example User");
	    cc.setEmail("test4@example.com");
	    personalization.addCc(cc);
	    Email bcc = new Email();
	    bcc.setName("Example User");
	    bcc.setEmail("test5@example.com");
	    personalization.addBcc(bcc);
	    bcc.setName("Example User");
	    bcc.setEmail("test6@example.com");
	    personalization.addBcc(bcc);*/
	    personalization.setSubject("Hello World from the Personalized Twilio SendGrid Java Library");
	    personalization.addHeader("X-Test", "test");
	    personalization.addHeader("X-Mock", "true");
	    personalization.addSubstitution("%name%", "Mohit Raj");
	    personalization.addSubstitution("%city%", "Mahape");
	    personalization.addCustomArg("user_id", "343");
	    personalization.addCustomArg("type", "marketing");
	    personalization.setSendAt(1443636843);
	    mail.addPersonalization(personalization);

	    /*Personalization personalization2 = new Personalization();
	    Email to2 = new Email();
	    to2.setName("Example User");
	    to2.setEmail("test1@example.com");
	    personalization2.addTo(to2);
	    to2.setName("Example User");
	    to2.setEmail("test2@example.com");
	    personalization2.addTo(to2);
	    Email cc2 = new Email();
	    cc2.setName("Example User");
	    cc2.setEmail("test3@example.com");
	    personalization2.addCc(cc2);
	    cc2.setName("Example User");
	    cc2.setEmail("test4@example.com");
	    personalization2.addCc(cc2);
	    Email bcc2 = new Email();
	    bcc2.setName("Example User");
	    bcc2.setEmail("test5@example.com");
	    personalization2.addBcc(bcc2);
	    bcc2.setName("Example User");
	    bcc2.setEmail("test6@example.com");
	    personalization2.addBcc(bcc2);
	    personalization2.setSubject("Hello World from the Personalized Twilio SendGrid Java Library");
	    personalization2.addHeader("X-Test", "test");
	    personalization2.addHeader("X-Mock", "true");
	    personalization2.addSubstitution("%name%", "Example User");
	    personalization2.addSubstitution("%city%", "Denver");
	    personalization2.addCustomArg("user_id", "343");
	    personalization2.addCustomArg("type", "marketing");
	    personalization2.setSendAt(1443636843);
	    mail.addPersonalization(personalization2);
	*/
	    Content content = new Content();
	    content.setType("text/plain");
	    content.setValue("some text here");
	    mail.addContent(content);
	    content.setType("text/html");
	    content.setValue("<html><body>some text here</body></html>");
	    mail.addContent(content);

	   /* Attachments attachments = new Attachments();
	    attachments.setContent("TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4gQ3JhcyBwdW12");
	    attachments.setType("application/pdf");
	    attachments.setFilename("balance_001.pdf");
	    attachments.setDisposition("attachment");
	    attachments.setContentId("Balance Sheet");
	    mail.addAttachments(attachments);
	*/
	   /* Attachments attachments2 = new Attachments();
	    attachments2.setContent("BwdW");
	    attachments2.setType("image/png");
	    attachments2.setFilename("banner.png");
	    attachments2.setDisposition("inline");
	    attachments2.setContentId("Banner");
	    mail.addAttachments(attachments2);
	*/
	    mail.setTemplateId("d-d73bbd21f1f5470e8068507b5d124e5f");

	    mail.addSection("%section1%", "Substitution Text for Section 1");
	   mail.addSection("%section2%", "Substitution Text for Section 2");

	    mail.addHeader("X-Test1", "1");
	    mail.addHeader("X-Test2", "2");

	    mail.addCategory("May");
	    mail.addCategory("2016");

	    mail.addCustomArg("campaign", "welcome");
	    mail.addCustomArg("weekday", "morning");

	    mail.setSendAt(1443636842);

	    ASM asm = new ASM();
	    asm.setGroupId(99);
	    asm.setGroupsToDisplay(new int[] {4,5,6,7,8});
	    mail.setASM(asm);

	    // This must be a valid [batch ID](https://sendgrid.com/docs/API_Reference/SMTP_API/scheduling_parameters.html) to work
	    // mail.setBatchId("sendgrid_batch_id");

	    mail.setIpPoolId("23");

	    MailSettings mailSettings = new MailSettings();
	    BccSettings bccSettings = new BccSettings();
	    bccSettings.setEnable(true);
	    bccSettings.setEmail("viki@gmail.com");
	    mailSettings.setBccSettings(bccSettings);
	    Setting sandBoxMode = new Setting();
	    sandBoxMode.setEnable(true);
	    mailSettings.setSandboxMode(sandBoxMode);
	    Setting bypassListManagement = new Setting();
	    bypassListManagement.setEnable(true);
	    mailSettings.setBypassListManagement(bypassListManagement);
	    FooterSetting footerSetting = new FooterSetting();
	    footerSetting.setEnable(true);
	    footerSetting.setText("Footer Text");
	    footerSetting.setHtml("<html><body>Footer Text</body></html>");
	    mailSettings.setFooterSetting(footerSetting);
	    SpamCheckSetting spamCheckSetting = new SpamCheckSetting();
	    spamCheckSetting.setEnable(true);
	    spamCheckSetting.setSpamThreshold(1);
	    spamCheckSetting.setPostToUrl("https://spamcatcher.sendgrid.com");
	    mailSettings.setSpamCheckSetting(spamCheckSetting);
	    mail.setMailSettings(mailSettings);

	    TrackingSettings trackingSettings = new TrackingSettings();
	    ClickTrackingSetting clickTrackingSetting = new ClickTrackingSetting();
	    clickTrackingSetting.setEnable(true);
	    clickTrackingSetting.setEnableText(true);
	    trackingSettings.setClickTrackingSetting(clickTrackingSetting);
	    OpenTrackingSetting openTrackingSetting = new OpenTrackingSetting();
	    openTrackingSetting.setEnable(true);
	    openTrackingSetting.setSubstitutionTag("Optional tag to replace with the open image in the body of the message");
	    trackingSettings.setOpenTrackingSetting(openTrackingSetting);
	    SubscriptionTrackingSetting subscriptionTrackingSetting = new SubscriptionTrackingSetting();
	    subscriptionTrackingSetting.setEnable(true);
	    subscriptionTrackingSetting.setText("text to insert into the text/plain portion of the message");
	    subscriptionTrackingSetting.setHtml("<html><body>html to insert into the text/html portion of the message</body></html>");
	    subscriptionTrackingSetting.setSubstitutionTag("Optional tag to replace with the open image in the body of the message");
	    trackingSettings.setSubscriptionTrackingSetting(subscriptionTrackingSetting);
	    GoogleAnalyticsSetting googleAnalyticsSetting = new GoogleAnalyticsSetting();
	    googleAnalyticsSetting.setEnable(true);
	    googleAnalyticsSetting.setCampaignSource("some source");
	    googleAnalyticsSetting.setCampaignTerm("some term");
	    googleAnalyticsSetting.setCampaignContent("some content");
	    googleAnalyticsSetting.setCampaignName("some name");
	    googleAnalyticsSetting.setCampaignMedium("some medium");
	    trackingSettings.setGoogleAnalyticsSetting(googleAnalyticsSetting);
	    mail.setTrackingSettings(trackingSettings);

	    Email replyTo = new Email();
	    replyTo.setName("Mohit Raj");
	    replyTo.setEmail("mohit.raj@bizlem.com");
	    mail.setReplyTo(replyTo);

	    return mail;
	  }

	  // API V3 Dynamic Template implementation
	  public static Mail buildDynamicTemplate() throws IOException {
	    Mail mail = new Mail();

	    Email fromEmail = new Email();
	    fromEmail.setName("Mohit Raj");
	    fromEmail.setEmail("mohit.raj@bizlem.com");
	    mail.setFrom(fromEmail);
	   // d-d73bbd21f1f5470e8068507b5d124e5f
	    mail.setTemplateId("d-d73bbd21f1f5470e8068507b5d124e5f");

	    Personalization personalization = new Personalization();
	    personalization.addDynamicTemplateData("name", "Mohit");
	    personalization.addDynamicTemplateData("city", "Mahape");
	    personalization.addTo(new Email("mohitraj.ranu@outlook.com"));
	    mail.addPersonalization(personalization);
	    
	    return mail;
	  }

	  // Minimum required to send an email test-7fxiy@mail-tester.com
	  public static Mail buildHelloEmail() throws IOException {
		  Email fromEmail = new Email();
		    fromEmail.setName("Geetanjali");
		    fromEmail.setEmail("welcome@leadaconvert.com");
		  //  mail.setFrom(fromEmail);
	    //Email from = new Email("welcome@leadaconvert.com");
	    String subject = "Doctiger  Dispaly name change MailTest";
	    Email to = new Email("mohitraj.ranu@gmail.com");
	   // Content content = new Content("text/plain", "This mail is being sent from sendgrid account");
	    Content content = new Content();
	    content.setType("text/html");
	    content.setValue("<html><body>Sample Testmail to send through Sendgrid account</body></html>");
	    Mail mail = new Mail(fromEmail, subject, to, content);
	   // mail.addContent(content);
	    // Note that when you use this constructor an initial personalization object
	    // is created for you. It can be accessed via
	    // mail.personalization.get(0) as it is a List object
	   // Mail mail = new Mail(fromEmail, subject, to, content);
	    mail.setFrom(fromEmail);
	   /* Email email = new Email("sales@doctiger.com");
	    mail.personalization.get(0).addTo(email);
	    Email email1 = new Email("innovaters@gmail.com");
	    mail.personalization.get(0).addTo(email1);*/
	    Email email1 = new Email("mohit.raj@bizlem.io");
	    mail.personalization.get(0).addTo(email1);
	    /*Email email2 = new Email("chetan.agarwal@bizlem.com");
	    mail.personalization.get(0).addTo(email2);*/
	    return mail;
	  }

	  public static void baselineExample() throws IOException {
		  //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
	    SendGrid sg = new SendGrid("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");
	   //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
	  
	    sg.addRequestHeader("X-Mock", "true");

	    Request request = new Request();
	    Mail helloWorld = buildHelloEmail();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(helloWorld.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      throw ex;
	    }
	  }

	  public static void kitchenSinkExample() throws IOException {
	    SendGrid sg = new SendGrid("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");
	    sg.addRequestHeader("X-Mock", "true");

	    Request request = new Request();
	    Mail kitchenSink = buildKitchenSink();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(kitchenSink.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      throw ex;
	    }
	  }

	  public static void dynamicTemplateExample() throws IOException {
	    SendGrid sg = new SendGrid("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");
	    sg.addRequestHeader("X-Mock", "true");

	    Request request = new Request();
	    Mail dynamicTemplate = buildDynamicTemplate();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(dynamicTemplate.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      throw ex;
	    }
	  }
	  public static void main(String[] args) throws IOException {
	    baselineExample();
	    // kitchenSinkExample();
	    //dynamicTemplateExample();
	  }
}
