package org.chunguang.mode;

public class Consume implements Runnable {
	private int producNum = 20;//
	Contain con = null;

	public Consume(Contain con) {
		super();
		this.con = con;
	}

	public int getProducNum() {
		return producNum;
	}

	public void setProducNum(int producNum) {
		this.producNum = producNum;
	}

	public void run() {
		for (int i = 0; i < producNum; i++) {
			List_obj obj = (List_obj) con.pop();
			System.out.println("����ˣ�" + obj);
		}
	}

}
