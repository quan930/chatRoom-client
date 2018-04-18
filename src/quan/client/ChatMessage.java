package quan.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//全部消息
public class ChatMessage implements Runnable{
//	private int personNum = 0;
	private Socket client;
	private List<String>messages = new ArrayList<String>();
//	private List<String>names = new ArrayList<String>();
	
	public List<String> getMessages() {
		return messages;
	}//消息列表
	
	//接收消息，处理消息
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Scanner in = new Scanner(client.getInputStream());
			while(true){
				String message = in.nextLine();
				messages.add(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ChatMessage(Socket client) {
		this.client = client;
	}
}
