package com.quickstart;

import javax.enterprise.context.Dependent;

@Dependent
public class Hello {
	public String yell() {
		return "Hello";
	}
}
