package mainpackage.socMedApp.controller;

import mainpackage.socMedApp.model.user.*;
import mainpackage.socMedApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
	public ResponseEntity<SignUpResponse> signup(@RequestBody User user) {
		SignUpResponse signUpResponse = userService.register(user);
		return new ResponseEntity<>(signUpResponse, signUpResponse.isStatus() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/signin", produces = "application/json")
	public ResponseEntity<SignInResponse> signin(@RequestHeader("cred") String cred, @RequestHeader("password") String password) {
		SignInRequest signInRequest=new SignInRequest();
		signInRequest.setCred(cred);
		signInRequest.setPassword(password);
		SignInResponse signInResponse = userService.authenticate(signInRequest);
		return new ResponseEntity<>(signInResponse, signInResponse.isStatus() ? HttpStatus.ACCEPTED : HttpStatus.UNAUTHORIZED);
	}

	@GetMapping(value = "/user/{userId}", produces = "application/json")
	public ResponseEntity<ProfileResponse> getUserInfo(@PathVariable("userId") String userId) {
		ProfileResponse userResponse = userService.getUser(userId);
		return new ResponseEntity<>(userResponse, userResponse.isStatus() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/userhead/{userId}", produces = "application/json")
	public ResponseEntity<ProfileHeadResponse> getUserHeader(@PathVariable("userId") String userid) {
		ProfileHeadResponse profileHeadResponse = userService.getUserHead(userid);
		return new ResponseEntity<>(profileHeadResponse, profileHeadResponse.isStatus() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
}
