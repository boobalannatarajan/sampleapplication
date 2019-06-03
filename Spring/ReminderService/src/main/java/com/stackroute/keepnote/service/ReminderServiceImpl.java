package com.stackroute.keepnote.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.ReminderRepository;

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
public class ReminderServiceImpl implements ReminderService {
	/*
	 * Autowiring should be implemented for the ReminderRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	ReminderRepository reminderRepository;

	@Autowired
	public ReminderServiceImpl(ReminderRepository reminderRepository) {
		this.reminderRepository = reminderRepository;
	}

	/*
	 * This method should be used to save a new reminder.Call the corresponding
	 * method of Respository interface.
	 */
	public Reminder createReminder(Reminder reminder) throws ReminderNotCreatedException {
		Reminder resObj = new Reminder();
		if (reminder != null) {
			resObj = reminderRepository.insert(reminder);// Insertion
		}
		if (resObj == null || resObj.equals(null)) {
			throw new ReminderNotCreatedException("Reminder Not Created");// If -ve case throw exception
		}
		return resObj;
	}

	/*
	 * This method should be used to delete an existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteReminder(String reminderId) throws ReminderNotFoundException {
		boolean isStatsu = false;
		Reminder fetchedReminder = new Reminder();
		fetchedReminder = reminderRepository.findById(reminderId).get();// Find id and then delete action
		if (fetchedReminder != null) {
			reminderRepository.delete(fetchedReminder);
			isStatsu = true;
		}
		if (fetchedReminder == null || fetchedReminder.equals(null)) {
			throw new ReminderNotFoundException("Reminder Not Found");
		}
		return isStatsu;
	}

	/*
	 * This method should be used to update a existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder updateReminder(Reminder reminder, String reminderId) throws ReminderNotFoundException {
		Reminder output = new Reminder();
		Reminder existingReminder = null;
		try {
			existingReminder = reminderRepository.findById(reminderId).get();// Find id and then update action
		} catch (NoSuchElementException e) {
			existingReminder = null;
		}
		if (existingReminder != null) {
			reminder.setReminderCreationDate(existingReminder.getReminderCreationDate());
			reminder.setReminderCreatedBy(existingReminder.getReminderCreatedBy());
			reminderRepository.save(reminder);
			output = reminder;
		}
		return output;
	}

	/*
	 * This method should be used to get a reminder by reminderId.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder getReminderById(String reminderId) throws ReminderNotFoundException {
		Reminder fetchedReminder = new Reminder();
		if (!reminderId.isEmpty()) {
			try {
				fetchedReminder = reminderRepository.findById(reminderId).get();
				/* fetchedCategory = categoryRepository.findByCategoryId(categoryId); */
			} catch (NoSuchElementException e) {
				System.out.println("NoSuchElementException Occured");
				fetchedReminder = null;
			}
		}
		if (fetchedReminder == null || fetchedReminder.equals(null)) {
			throw new ReminderNotFoundException("Reminder Not Found");
		}
		return fetchedReminder;
	}

	/*
	 * This method should be used to get all reminders. Call the corresponding
	 * method of Respository interface.
	 */
	public List<Reminder> getAllReminders() {
		return reminderRepository.findAll();
	}
}