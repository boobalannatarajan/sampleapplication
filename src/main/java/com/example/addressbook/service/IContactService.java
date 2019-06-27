package com.example.addressbook.service;

import java.util.List;

import com.example.addressbook.model.Contact;

public interface IContactService {
	Contact saveContact(Contact contact);
	Contact getContact(String mobile);
	boolean deleteContact(String mobile);
	
	List<Contact> getAllContacts();
	List<Contact> getAllContactsByName(String cname);
	List<Contact> getAllContactsByMail(String mailId);
}

