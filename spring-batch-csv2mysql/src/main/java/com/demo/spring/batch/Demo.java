package com.demo.spring.batch;

public class Demo {

	{ //this is a block and gets executed every time it is instantiated
		test();
		test2();
	}

	public void test() {
		System.out.println("hello");
	}

	public void test2() {
		System.out.println("hello2");
	}

	public static void main(String[] args) {

		new Demo() {
		};

	}

}
