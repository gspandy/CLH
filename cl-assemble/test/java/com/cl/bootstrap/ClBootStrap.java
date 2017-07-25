package com.cl.bootstrap;


public class ClBootStrap {
	
	public static void main(String[] args) {
		new TomcatBootstrapHelper(8081, false, "dev").start();
	}
	
}
