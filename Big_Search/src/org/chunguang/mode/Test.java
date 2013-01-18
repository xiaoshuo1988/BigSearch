package org.chunguang.mode;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Contain c=new Contain();
		Producer p=new Producer(c);
		Consume con=new Consume(c);
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(con).start();
	}

}
