package com.example.addressbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.addressbook.dao.ContactRepository;
import com.example.addressbook.model.Contact;

@Service
public class ContactServiceImpl implements IContactService{

	@Autowired
	private ContactRepository contactRepo;

	@Override
	public Contact saveContact(Contact contact) {
		// TODO Auto-generated method stub
		if(contact!=null) {
			contact=contactRepo.save(contact);
		}
		return contact;
	}

	@Override
	public Contact getContact(String mobile) {
		// TODO Auto-generated method stub
		Contact contact=null;
		if(contactRepo.existsById(mobile)) {
			contact=contactRepo.findById(mobile).get();
		}
		return contact;
	}

	@Override
	public boolean deleteContact(String mobile) {
		// TODO Auto-generated method stub
		boolean isDeleted=false;
		if(null!=mobile && contactRepo.existsById(mobile)) {
			contactRepo.deleteById(mobile);
			isDeleted=true;
		}
		return isDeleted;
	}

	@Override
	public List<Contact> getAllContacts() {
		// TODO Auto-generated method stub
		return contactRepo.findAll();
	}

	@Override
	public List<Contact> getAllContactsByName(String cname) {
		// TODO Auto-generated method stub
		return contactRepo.findAllByCname(cname);
	}

	@Override
	public List<Contact> getAllContactsByMail(String mailId) {
		// TODO Auto-generated method stub
		return contactRepo.findAllByMailId(mailId);
	}
	
}
