package org.bcia.javachain.ca.szca.publicweb.service;

import org.springframework.stereotype.Repository;

@Repository
public class HelloSshServiceImpl implements HelloSshService{

	public String hello(int id) {
 
		return "Hello";
	}
	
}
