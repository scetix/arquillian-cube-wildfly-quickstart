package com.quickstart;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
@Stateless
public class HelloService {
	@PersistenceContext
	EntityManager entityManager;

	@Inject
	Hello hello;

	@GET
	public String get() {
		HelloEntity test = new HelloEntity();
		test.setValue(hello.yell());
		entityManager.persist(test);
		entityManager.flush();
		return entityManager.find(HelloEntity.class, test.getId())
				.getValue();
	}
}
