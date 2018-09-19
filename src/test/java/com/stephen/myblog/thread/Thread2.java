package com.stephen.myblog.thread;

public class Thread2 implements Runnable{
 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		SynchronizedTest s = SynchronizedTest.getInstance();
//		SynchronizedTest s2 = new SynchronizedTest();
//		s2.method1();
		s.staticMethod1();
//		SynchronizedTest.staticMethod1();
//		SynchronizedTest.staticMethod2();
//		SynchronizedTest.staticIn.method2();
//		SynchronizedTest.staticIn.staticMethod1();
	}
}