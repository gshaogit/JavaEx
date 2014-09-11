package com.test.drools.liveCycle;

public class HelloWorld {

	private String message = "";
	
	public HelloWorld(){
		this.message = "HelloWorld";
	}
	
	public HelloWorld(String message){
		this.message = message;
	}
	
	public String returnMessage() {
		return this.message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
// test
}
