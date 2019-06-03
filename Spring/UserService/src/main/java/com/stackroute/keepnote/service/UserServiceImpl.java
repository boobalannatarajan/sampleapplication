package com.stackroute.keepnote.service;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class UserServiceImpl implements UserService {
	/*
	 * Autowiring should be implemented for the UserRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/*
	 * This method should be used to save a new user.Call the corresponding method
	 * of Respository interface.
	 */
	public User registerUser(User user) throws UserAlreadyExistsException {
		User usrObj = null;
		if (user != null) {
			try {
				usrObj = userRepository.insert(user);
			} catch (Exception e) {
			}
		}
		if (usrObj == null || usrObj.equals(null)) {
			throw new UserAlreadyExistsException("User Already Exists"); // For -ve case,if null then
		}
		return usrObj;
	}

	/*
	 * This method should be used to update a existing user.Call the corresponding
	 * method of Respository interface.
	 */
	public User updateUser(String userId, User user) throws UserNotFoundException {
		boolean isUpdated = false;
		User usrObj = new User();
		User User = userRepository.findById(user.getUserId()).get();// find id and then update action
		if (User == null) {
			throw new UserNotFoundException("User Not Found");
		}
		user.setUserAddedDate(User.getUserAddedDate());
		userRepository.save(user);
		isUpdated = true;
		if (isUpdated) {
			usrObj = user;
		}
		return usrObj;
	}

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */
	public boolean deleteUser(String userId) throws UserNotFoundException {
		boolean isStatus = false;
		User fetchedUser = new User();
		fetchedUser = userRepository.findById(userId).get();// find id and then deleteUser action
		if (fetchedUser != null) {
			userRepository.delete(fetchedUser);
			isStatus = true;
		}
		if (fetchedUser == null || fetchedUser.equals(null)) {
			throw new UserNotFoundException("User Not Found");
		}
		return isStatus;
	}

	/*
	 * This method should be used to get a user by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public User getUserById(String userId) throws UserNotFoundException {
		User usrObj = null;
		if (userId != null) {
			try {
				usrObj = userRepository.findById(userId).get();
			} catch (NoSuchElementException e) {
				usrObj = null;
			}
		}
		if (usrObj == null || usrObj.equals(null)) {
			throw new UserNotFoundException("User Not Found");
		}
		return usrObj;
	}
}