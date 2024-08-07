package com.fsoft.lecture5.lecture5.repository;

import com.fsoft.lecture5.lecture5.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository  extends JpaRepository<Contact, String> {
}
