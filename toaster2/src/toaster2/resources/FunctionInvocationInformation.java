package toaster2.resources;

import java.util.ArrayList;

public class FunctionInvocationInformation {

	String name;
	
	ArrayList<ThreadRecord> threads;
	
	public FunctionInvocationInformation(String string) {
		name = string;
		threads = new ArrayList<ThreadRecord>();
	}

	public void addThread(ThreadRecord thread){
		threads.add(thread);
	}
	
	public ArrayList<ThreadRecord> getThreads(){
		return threads;
	}
}
