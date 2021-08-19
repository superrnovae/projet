package com.example.demo.rest.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.jwt.response.JwtResponse;
import com.example.demo.rest.dto.LoginDto;
import com.example.demo.rest.dto.SignupDto;
import com.example.demo.rest.models.PasswordResetToken;
import com.example.demo.rest.models.User;
import com.example.demo.rest.models.VerificationToken;

public interface UserService {

	User registerUser(SignupDto signupDto);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String verificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    Optional<User> findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    List<String> getUsersFromSessionRegistry();

	User getUserData(String username);

	JwtResponse authenticateUser(LoginDto request);
	
	User getUserFromSession();

	void follow(String username);

	void unfollow(String username);
}