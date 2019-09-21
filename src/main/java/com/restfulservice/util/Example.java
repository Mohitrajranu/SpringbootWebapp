package com.restfulservice.util;

public class Example {

	int value = 1;
	  public static void main(String args[]) {
	   Example obj = new Example();
	   obj.method(10);
	   obj.method(12);
	  }
	  void method(int value) {
	   //this.value = value;
	   System.out.println("Instance: " + this.value);
	   System.out.println("Local: " + value);
	  }
}
