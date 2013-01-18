package org.chunguang.mode;

public class Producer implements Runnable {
	private int producNum = 20;//
	Contain con = null;

	public Producer(Contain con) {
		super();
		this.con = con;
	}

	public int getProducNum() {
		return producNum;
	}

	public void setProducNum(int producNum) {
		this.producNum = producNum;
	}

	int b = 0;

	public void run() {
		b++;
		for (int i = 0; i < producNum; i++) {
			List_obj obj = new List_obj(i);
			con.push(obj);
			System.out.println("��ǰ�߳�" + b + "����ˣ�" + obj);
		}
	}

}
