package org.chunguang.mode;

import java.util.Collection;

/**
 * ����������������������Ķ�����ͬʱҲ�����������ѡ�
 * @author lenovo
 *
 */
public class Contain {
	private int number=200;//����ָ���������list���ڴ��С��Ĭ����200.
	Collection[] con=new Collection[number];
	int count=0;
	
	
	
	public Contain() {
		super();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	/**
	 * 
	 * @param list������������Ķ���
	 */
	public synchronized void push(Collection list){
		if(count == con.length){
			try {
				this.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.notifyAll();
		con[count]=list;
		count ++;
	}
	/**
	 * 
	 * @return ����ֵ��Collection���������ѵĶ�����
	 */
	public synchronized Collection pop(){
	//	System.out.println("count value is"+count);
		if(count == 0){
			try {
				this.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.notifyAll();
		count --;
		return con[count];
	}
}
