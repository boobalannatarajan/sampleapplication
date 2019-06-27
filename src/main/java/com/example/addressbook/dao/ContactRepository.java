package com.example.addressbook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.addressbook.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>{
	List<Contact> findAllByCname(String cnmae);
	List<Contact> findAllByMailId(String mailId);

}
