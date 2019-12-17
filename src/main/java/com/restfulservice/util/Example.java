package com.restfulservice.util;

// TODO: Auto-generated Javadoc
/**
 * The Class Example.
 */
public class Example {

	/** The value. */
	int value = 1;
	  
  	/**
  	 * The main method.
  	 *
  	 * @param args the arguments
  	 */
  	public static void main(String args[]) {
	   Example obj = new Example();
	   obj.method(10);
	   obj.method(12);
	  }
	  
  	/**
  	 * Method.
  	 *
  	 * @param value the value
  	 */
  	void method(int value) {
	   //this.value = value;
	   System.out.println("Instance: " + this.value);
	   System.out.println("Local: " + value);
	  }
}
