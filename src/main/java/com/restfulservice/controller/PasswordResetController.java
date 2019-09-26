package com.restfulservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
import com.restfulservice.model.UserToken;
import com.restfulservice.service.EmailService;
import com.restfulservice.service.UpdateLdapPassword;
import com.restfulservice.service.UpdateRavePassword;
import com.restfulservice.service.UserService;

@Controller
//@RequestMapping("/password")
public class PasswordResetController {
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;
	@Autowired
	private UpdateLdapPassword updateLdapPassword;

	@Autowired
	private UpdateRavePassword updateRavePassword;
	
	@Value("${app.centrallogin}")
	private String url;
	
//http://bizlem.io/portal/centralconsole.jsp app.centrallogin
 @RequestMapping("/helloworld")
 public ModelAndView hello(ModelAndView modelAndView) {
 
  String helloWorldMessage = "Hello world from java2blog!";
  modelAndView.addObject("resetToken", "Mohit");
  modelAndView.addObject("message", helloWorldMessage);
  modelAndView.setViewName("hello");
  return modelAndView;
 }
 
//Display forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage() {
		return new ModelAndView("forgotPassword");
  }
  
  // Process form submission from forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {
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

				String appUrl = request.getScheme() + "://" + request.getServerName()+":8087/apirest/reset?token="+user.getResetToken();
				Mailer mailer = new Mailer();
				//mailer.setTo(user.getEmail());
				model = new HashMap<String, Object>();
				//model.put("username", user.getEmail());
				model.put("link", appUrl);
				mailer.setModel(model);
				mailer.setMailSubject("Forgot Password for UserId"+user.getEmail());
				mailer.setMailFrom("bizlem.demo@gmail.com");
				
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
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		
		Optional<UserToken> user = userService.findUserByResetToken(token);

		if (user.isPresent()) { // Token found in DB
			modelAndView.addObject("resetToken", token);
		} else { // Token not found in DB
			modelAndView.addObject("error", "Oops!  This is an invalid password reset link.");
		}

		modelAndView.setViewName("resetPassword");
		return modelAndView;
	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

		// Find the user associated with the reset token
		Optional<UserToken> user = userService.findUserByResetToken(requestParams.get("token"));

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

			modelAndView.setViewName("redirect:"+url);
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
