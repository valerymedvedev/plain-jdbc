package com.valeriimedvedev.demo.spring4.jdbc;

import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PlainJdbcSample {
    private static ContactDao contactDao = new PlainContactDao();

    public static void main(String[] args) {
        System.out.println("Listing initial contact data:");

        listAllContacts();

        System.out.println();
        System.out.println("Insert a new contact");

        Contact contact = new Contact();
        contact.setFirstName("Jacky");
        contact.setLastName("Chan");
        contact.setBirthDate(new Date((new GregorianCalendar(2001, 10, 1)).getTime().getTime()));
        contactDao.insert(contact);

        System.out.println("Listing contact data after new contact created:");

        listAllContacts();

        System.out.println();
        System.out.println("Deleting the previous created contact");

        contactDao.delete(contact.getId());

        System.out.println("Listing contact data after new contact deleted:");

        listAllContacts();


        // jdbc template
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/spring/app-context.xml");
        ctx.refresh();

        SlimContactDao contactDao = ctx.getBean("slimContactDao", SlimContactDao.class);

        System.out.println("First name for contact id 1 is: " +
                contactDao.findFirstNameById(1l));
    }

    private static void listAllContacts() {
        List<Contact> contacts = contactDao.findAll();

        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
}
