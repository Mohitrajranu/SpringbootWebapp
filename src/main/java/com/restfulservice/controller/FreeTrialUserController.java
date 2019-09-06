package com.restfulservice.controller;

import java.util.List;

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

import com.restfulservice.model.FreeTrialUser;
import com.restfulservice.model.User;
import com.restfulservice.repository.FreeTrialRepository;
import com.restfulservice.util.CustomErrorType;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/trialmgmt")
public class FreeTrialUserController {

	public static final Logger logger = LoggerFactory.getLogger(FreeTrialUserController.class);

	@Autowired
	FreeTrialRepository freeTrialRepository;
	
	
	@RequestMapping(value = "/trialuser/", method = RequestMethod.GET)
	public ResponseEntity<List<FreeTrialUser>> listAllUsers() {
		List<FreeTrialUser> users = freeTrialRepository.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<FreeTrialUser>>(users, HttpStatus.OK);
	}
	@RequestMapping(value = "/trialuser/productList", method = RequestMethod.GET)
	public ResponseEntity<List<FreeTrialUser>> listAllProducts(@RequestParam String username) {
		List<FreeTrialUser> users = freeTrialRepository.findByUsername(username);
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<FreeTrialUser>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/trialuser/{username}/{productType}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("username") String username,@PathVariable("productType") String productType) {
		logger.info("Fetching User {}", username);
		//boolean recExist = freeTrialRepository.existsByUsernameAndProductType(username,productType);
		FreeTrialUser user = freeTrialRepository.findByUsernameAndProductType(username,productType);
		if (user == null) {
			logger.error("User not found {} ", username);
			return new ResponseEntity(new CustomErrorType("User " + username 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<FreeTrialUser>(user, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/trialuser/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody FreeTrialUser user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);

		if (freeTrialRepository.existsByUsernameAndProductType(user.getUsername(),user.getProductType())) {
			logger.error("Unable to create. A User with name {} already exist", user.getUsername());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			user.getUsername() + "already exist for Free Trial."),HttpStatus.CONFLICT);
		}
		freeTrialRepository.save(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/apirest/trialuser/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User ------------------------------------------------

			@RequestMapping(value = "/trialuser/{username}", method = RequestMethod.PUT)
			public ResponseEntity<?> updateUser(@PathVariable("username") String username, @RequestBody FreeTrialUser user) {
				logger.info("Updating User with id {}", username);

				FreeTrialUser currentUser = freeTrialRepository.findByUsernameAndProductType(username,user.getProductType());

				if (currentUser == null) {
					logger.error("Unable to update. User  {} not found.", username);
					/*return new ResponseEntity(new CustomErrorType("Unable to update. User " + username + " not found."),
							HttpStatus.NOT_FOUND);*/
					currentUser = user;
					freeTrialRepository.save(user);
					
					
				}else {
				currentUser.setUsername(user.getUsername());
				currentUser.setExpireFlag(user.getExpireFlag());
				currentUser.setFreetrial(user.getFreetrial());
				/*currentUser.setUser(user.getUser());
				currentUser.setProduct(user.getProduct());
				currentUser.setRole(user.getRole());
	*/
				freeTrialRepository.save(currentUser);
				}
				//freeTrialRepository.save(currentUser);
				return new ResponseEntity<FreeTrialUser>(currentUser, HttpStatus.OK);
			}

			@RequestMapping(value = "/trialuser/{id}", method = RequestMethod.DELETE)
			public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
				logger.info("Fetching & Deleting User with id {}", id);

				FreeTrialUser user = freeTrialRepository.findById(Integer.parseInt(id));
				if (user == null) {
					logger.error("Unable to delete. User with id {} not found.", id);
					return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
							HttpStatus.NOT_FOUND);
				}
				freeTrialRepository.deleteFreeTrialUserById(Integer.parseInt(id));
				return new ResponseEntity<FreeTrialUser>(HttpStatus.NO_CONTENT);
			}
	
	/*@RequestMapping(value = "/trialuser/{username}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
		logger.info("Fetching & Deleting User with username {}", username);

		Long user = freeTrialRepository.countByUsername(username);
		if (user == null || user == 0) {
			logger.error("Unable to delete. User {} not found.", username);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User  " + username + " not found."),
					HttpStatus.NOT_FOUND);
		}
		freeTrialRepository.deleteByUsername(username);
		return new ResponseEntity<FreeTrialUser>(HttpStatus.NO_CONTENT);
	}
*/}
