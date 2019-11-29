package com.restfulservice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restfulservice.model.Mailer;
import com.restfulservice.model.UrlClicks;
import com.restfulservice.model.UserToken;
import com.restfulservice.repository.UrlViewRepository;
import com.restfulservice.service.EmailService;
import com.restfulservice.service.UpdateLdapPassword;
import com.restfulservice.service.UpdateRavePassword;
import com.restfulservice.service.UserService;

@Controller
//@RequestMapping("/password")
public class PasswordResetController {
	@Autowired
	private UserService userService;
//UrlViewRepository
	@Autowired
	private EmailService emailService;
	@Autowired
	private UpdateLdapPassword updateLdapPassword;

	@Autowired
	private UpdateRavePassword updateRavePassword;
	
	@Autowired
	private UrlViewRepository urlViewRepository; 
	
	@Value("${app.centrallogin}")
	private String url;
	
	@Value("${app.redirectURL}")
	private String projredirurl;
	@Value("${app.mailtemplatekey}")
	private String keyUrl;
//http://bluealgo.com/portal/centralconsole.jsp app.centrallogin
 @RequestMapping("/helloworld")
 public ModelAndView hello(ModelAndView modelAndView) {
 
  String helloWorldMessage = "Hello world from java2blog!";
  modelAndView.addObject("resetToken", "Mohit");
  modelAndView.addObject("message", helloWorldMessage);
  modelAndView.setViewName("hello");
  return modelAndView;
 }
 @RequestMapping(value = "/appunsubscribeurl", method = RequestMethod.GET)//utm_source
	public ModelAndView displayurlUnsubscribe(ModelAndView modelAndView, @RequestParam("EMAIL") String email,@RequestParam("utm_source") String templatename) {
	 try {
		 userService.unsubscribeList(templatename, email,keyUrl);
		 modelAndView.addObject("message", email);
	} catch (Exception e) {
		// TODO Auto-generated catch block
	}

		modelAndView.setViewName("unsubscribe");
		return modelAndView;
	}
 
 @RequestMapping(value = "/appredirecturl", method = RequestMethod.GET)//utm_source
	public ModelAndView displayurlClickPage(ModelAndView modelAndView, @RequestParam("uri") String uri,@RequestParam("utm_source") String templatename) {
		
	 StringBuilder email = null;
 	
 	
 	 StringBuilder urlClick = null;
 	 
	 try {
		 urlClick = new StringBuilder();
		 email = new StringBuilder();
		 urlClick.append(uri.split("\\?", -1)[0]);
		 email.append((uri.split("\\?", -1)[1]).split("\\=", -1)[1]);
		 Optional<UrlClicks> user = urlViewRepository.findByEmailAndTemplatenameAndUrl(email.toString(), templatename, urlClick.toString());

			if (user.isPresent()) { // Token found in DB
			
				UrlClicks resetUserClick = user.get(); 
				Integer updatecount = resetUserClick.getClickCount() + 1;
				resetUserClick.setClickCount(updatecount);
				urlViewRepository.save(resetUserClick);
			} else { // Token not found in DB
				
				UrlClicks newUser = new UrlClicks();
				newUser.setClickCount(1);
				newUser.setEmail(email.toString());
				newUser.setUrl(urlClick.toString());
				newUser.setTemplatename(templatename);
				Date d=new Date();  
		        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd"); // the day of the week spelled out completely
		       
		        StringBuilder selday=new StringBuilder(simpleDateformat.format(d));
		        
		        Date date= null;
				try {
					date = (Date)simpleDateformat.parse(selday.toString());
					newUser.setCreateDate(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
				}
		        
				urlViewRepository.save(newUser);
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
	}

		modelAndView.setViewName("redirect:"+uri+"&utm_source="+templatename);
		return modelAndView;
	}
 
//Display forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage(ModelAndView modelAndView,@RequestParam("projectname") String projectname) {
		modelAndView.addObject("projectname", projectname);
		modelAndView.setViewName("forgotPassword");
		return modelAndView;
		//return new ModelAndView("forgotPassword");
  }
  
  // Process form submission from forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail,@RequestParam("projectname") String projectname, HttpServletRequest request) {
		Map<String, Object> model = null;
		// Lookup user in database by e-mail
		Optional<UserToken> optional = null;

		try {
			optional = userService.findUserByEmail(userEmail);
			if (!optional.isPresent()) {
				modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
			} else {
				
				// Generate random 36-character string token for reset password 
				UserToken user = optional.get();
				user.setResetToken(UUID.randomUUID().toString());

				// Save token to database
				userService.save(user);

				String appUrl = request.getScheme() + "://" + request.getServerName()+":8088/apirest/reset?token="+user.getResetToken()+"&projectname="+projectname;
				Mailer mailer = new Mailer();
				//mailer.setTo(user.getEmail());
				model = new HashMap<String, Object>();
				//model.put("username", user.getEmail());
				model.put("link", appUrl);
				mailer.setModel(model);
				mailer.setMailSubject("Forgot Password for UserId"+user.getEmail());
				mailer.setMailFrom("sales@doctiger.com");
				
				mailer.setMailTo(user.getEmail());
				emailService.sendForgotPasswordEmail(mailer);
				/*Mail mail = new Mail();
				mail.setSubject("Forgot Password for UserId"+user.getEmail());
				mail.setFrom("bizlem.demo@gmail.com");
				mail.setContent("To reset your password, click the link below:\n" + appUrl
						+ ":8087/apirest/reset?token=" + user.getResetToken());
				mail.setTo(user.getEmail());
				
				
				//sendForgotPasswordEmail
				emailService.sendSimpleMessage(mail);
				*/
				
			/*	
			 *  message.setSubject(mail.getSubject());
			message.setText(mail.getContent());
			message.setTo(mail.getTo());
			message.setFrom(mail.getFrom());

			 * passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
						+ "/reset?token=" + user.getResetToken());
			*/	
				

				// Add success message to view
				modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
			}

			modelAndView.setViewName("forgotPassword");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			modelAndView.addObject("errorMessage", "Eroor Occured due to::"+e.getMessage());
		}
		return modelAndView;

	}

	// Display form to reset password
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token,@RequestParam("projectname") String projectname) {
		
		Optional<UserToken> user = userService.findUserByResetToken(token);
//https://bluealgo.com/portal/resetredirect.jsp?projectname=doctiger
		if (user.isPresent()) { // Token found in DB
			modelAndView.addObject("resetToken", token);
		} else { // Token not found in DB
			modelAndView.addObject("error", "Oops!  This is an invalid password reset link.");
		}
		modelAndView.addObject("projectname", projectname);
		modelAndView.setViewName("resetPassword");
		return modelAndView;
	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

		// Find the user associated with the reset token
		Optional<UserToken> user = userService.findUserByResetToken(requestParams.get("token"));
		String projectName=requestParams.get("projectname");

		// This should always be non-null but we check just in case
		if (user.isPresent()) {
			
			UserToken resetUser = user.get(); 
			
			// Set new password    
        //  resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
          
			// Set the reset token to null so it cannot be used again
			resetUser.setResetToken(null);

			// Save user
			userService.save(resetUser);
			updateLdapPassword.updateLdap(resetUser.getEmail(), requestParams.get("password"));
			updateRavePassword.updateRecordSet(requestParams.get("password"), resetUser.getEmail());
			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			redir.addFlashAttribute("successMessage", "You have successfully reset your password.");
			
			modelAndView.setViewName("redirect:"+projredirurl+projectName);
			return modelAndView;
			
		} else {
			modelAndView.addObject("error", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetPassword");	
		}
		
		return modelAndView;
 }
 
  // Going to reset page without a token redirects to login page
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:"+url);
	}
 
}
