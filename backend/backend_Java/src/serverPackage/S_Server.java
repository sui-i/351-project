package serverPackage;
import java.util.LinkedList;
import java.util.HashMap;
public class S_Server {
	private static void print(String s) {System.out.println("[SERVER]: " + s);}
	HashMap<Integer,S_Client> activeClientTable;
	//Will be used to track active sessions.
	
	LinkedList<LinkedList<Integer>> clientsTimeoutQueue; 
	//Will be used to keep timeout clients inactive for N minutes
	/*
	 * Linked list of users who entered at Minute X.
	 * if a client contacts the server for the first time, a new Client
	 * object is created for him. and is placed in the bucket at the head of the
	 * linked list. Every minute, the tail of the linked list becomes a new node.
	 * and if the size of the linked list exceeds N, timeout everyone at head
	 * and set the new head at head.next .
	 * Every time a client updates himself, he is deleted from his old position
	 * and placed in head again.
	 */
	
	S_Server() {
		S_Server.print("Starting...");
		clientsTimeoutQueue = new LinkedList<LinkedList<Integer>>();
		activeClientTable = new HashMap<Integer,S_Client>();
		
		
		
	}
	
	
}
