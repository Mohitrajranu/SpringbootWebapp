package com.restfulservice.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.restfulservice.exception.ApiResponse;
import com.restfulservice.model.UrlClicks;
import com.restfulservice.model.User;
import com.restfulservice.model.UserToken;
import com.restfulservice.repository.UrlViewRepository;
import com.restfulservice.repository.UserRepository;
import com.restfulservice.service.UserService;
import com.restfulservice.util.CustomErrorType;
// TODO: Auto-generated Javadoc

/**
 * The Class RestApiController.
 * @author Mohit Raj
 */
@CrossOrigin( origins = "*" )
@RestController
@RequestMapping("/usrmgmt")
public class RestApiController {

	/** The Constant logger. */
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	/** The user repository. */
	@Autowired
	UserRepository userRepository;
	
	/** The url view repository. */
	@Autowired
	private UrlViewRepository urlViewRepository; 
	
	/** The user service. */
	@Autowired
	private UserService userService;
	// -------------------Retrieve All Users---------------------------------------------

	/**
	 * Gets the token user.
	 *
	 * @param email the email
	 * @return the token user
	 */
	@RequestMapping(value = "/usertokenview/", method = RequestMethod.GET)
	public ResponseEntity<?> getTokenUser(@RequestParam("email") String email) {
		Optional<UserToken> optional = userService.findUserByEmail(email.trim());
		if (!optional.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(false, "User Email Address doesnot exist!"), HttpStatus.BAD_REQUEST);
		}
		UserToken user = optional.get();
		return new ResponseEntity<UserToken>(user, HttpStatus.OK);
	}
	
	/**
	 * List all url views.
	 *
	 * @param email the email
	 * @param templatename the templatename
	 * @return the response entity
	 */
	@RequestMapping(value = "/urlviews/", method = RequestMethod.GET)
	public ResponseEntity<?> listAllUrlViews(@RequestParam String email,@RequestParam String templatename) {
		List<UrlClicks> urlviews = urlViewRepository.findByEmailAndTemplatename(email, templatename);
		if (urlviews.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(false, "Email Address or templatename doesnot exist!"), HttpStatus.BAD_REQUEST);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<UrlClicks>>(urlviews, HttpStatus.OK);
	}
	
		/**
		 * List all users.
		 *
		 * @return the response entity
		 */
		@RequestMapping(value = "/user/", method = RequestMethod.GET)
		public ResponseEntity<?> listAllUsers() {
			List<User> users = userRepository.findAll();
			if (users.isEmpty()) {
				return new ResponseEntity<>(new ApiResponse(false, "No data exist!"),HttpStatus.NO_CONTENT);
				// You many decide to return HttpStatus.NOT_FOUND
			}
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}

		// -------------------Retrieve Single User------------------------------------------

		/**
		 * Gets the user.
		 *
		 * @param id the id
		 * @return the user
		 */
		@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getUser(@PathVariable("id") String id) {
			logger.info("Fetching User with id {}", id);
			User user = userRepository.findById(Integer.parseInt(id));
			if (user == null) {
				logger.error("User with id {} not found.", id);
				return new ResponseEntity<>(new CustomErrorType("User with id " + id 
						+ " not found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		// -------------------Create a User-------------------------------------------
		
		/**
		 * Creates the user.
		 *
		 * @param user the user
		 * @param ucBuilder the uc builder
		 * @return the response entity
		 */
		@RequestMapping(value = "/user/", method = RequestMethod.POST)
		public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
			logger.info("Creating User : {}", user);

			if (userRepository.existsByUsernameAndRoleid(user.getUsername(),user.getRoleid())) {
				logger.error("Unable to create. A User with name {} already exist", user.getUsername());
				return new ResponseEntity<>(new CustomErrorType("Unable to create. A User with name " + 
				user.getUsername() + " and role "+user.getRoleid()+"already exist."),HttpStatus.CONFLICT);
			}
			userRepository.save(user);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/apirest/user/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		}

		// ------------------- Update a User ------------------------------------------------

		/**
		 * Update user.
		 *
		 * @param id the id
		 * @param user the user
		 * @return the response entity
		 */
		@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
		public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User user) {
			logger.info("Updating User with id {}", id);

			User currentUser = userRepository.findById(Integer.parseInt(id));

			if (currentUser == null) {
				logger.error("Unable to update. User with id {} not found.", id);
				return new ResponseEntity<>(new CustomErrorType("Unable to update. User with id " + id + " not found."),
						HttpStatus.NOT_FOUND);
			}
			currentUser.setUsername(user.getUsername());
			currentUser.setIsactive(user.getIsactive());
			currentUser.setRoleid(user.getRoleid());
			/*currentUser.setUser(user.getUser());
			currentUser.setProduct(user.getProduct());
			currentUser.setRole(user.getRole());
*/
			userRepository.save(currentUser);
			return new ResponseEntity<User>(currentUser, HttpStatus.OK);
		}

		// ------------------- Delete a User-----------------------------------------

		/**
		 * Delete user.
		 *
		 * @param id the id
		 * @return the response entity
		 */
		@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
			logger.info("Fetching & Deleting User with id {}", id);

			User user = userRepository.findById(Integer.parseInt(id));
			if (user == null) {
				logger.error("Unable to delete. User with id {} not found.", id);
				return new ResponseEntity<>(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
						HttpStatus.NOT_FOUND);
			}
			userRepository.deleteUserById(Integer.parseInt(id));
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}

		// ------------------- Delete All Users-----------------------------

		/**
		 * Delete all users.
		 *
		 * @return the response entity
		 */
		@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
		public ResponseEntity<User> deleteAllUsers() {
			logger.info("Deleting All Users");

			userRepository.deleteAll();
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}

}
