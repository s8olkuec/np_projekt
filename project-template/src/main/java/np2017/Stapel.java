package np2017;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Stapel {
	LinkedList<Exam> nehmenlist;
	LinkedList<Exam> legenlist;	
	Lock ln; //Lock für nehmenlist
	Lock ll; //lock für legenlist
	
	
	public Stapel(Collection<Exam> ex, int anz)  //nimmt die ersten anz Exams aus ex und fügt sie zur nehenlist
	{  this.nehmenlist=new LinkedList<Exam>();
	   this.legenlist=new LinkedList<Exam>();
	   this.ln=new ReentrantLock();
	   this.ll=new ReentrantLock();
	   
	   Iterator<Exam> it= ex.iterator();
	   
	   for (int i=0;i<anz;i++) {
		   nehmenlist.add(it.next());
		   it.remove();
	   }
	   
		;
	}

	
	public void legen(Exam e)
	{this.ll.lock();
	try {this.legenlist.add(e);}
	 finally {this.ll.unlock();}
		}

	public Exam nehmen()
	{this.ln.lock();
	try {if(this.nehmenlist.isEmpty()) { 
		this.ll.lock();
		try {int s=this.legenlist.size();
			if(s==0)return null;   // das muss noch geändert werden 
		      else {; for(int i=0;i<s;) {this.nehmenlist.add(this.legenlist.remove());}} }
		finally {this.ln.unlock();}
	     }
	    return this.nehmenlist.remove();    
	}
	 finally {this.ln.unlock();}
		}
	
	

}
