package quan.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class ClientUI {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Socket client = new Socket("127.0.0.1",6666);
		ChatMessage alMessage = new ChatMessage(client);
		Thread t = new Thread(alMessage);
		t.start();//开启接收消息的线程
		PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8"),true);
		MainUI as = new MainUI(out, alMessage);
		new SignInUI(out,alMessage,as);		
	}
}
