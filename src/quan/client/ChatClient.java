package quan.client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ChatClient {
	private Scanner read;
	
	public void startClient(String ip,int prot,Object main) throws InterruptedException, UnsupportedEncodingException, IOException {
		//this.wait();
		Socket client = new Socket(ip,prot);
		ChatMessage alMessage = new ChatMessage(client);
		Thread t = new Thread(alMessage);
		t.start();//开启接收消息的线程
		PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8"),true);
		System.out.print("已连接服务器"+"请注册名字:");
		read = new Scanner(System.in);
		String name = read.nextLine();
		out.println(new ClientProtocol().giveName(name));//fgfdgdafadfadsgdsfgas
		while(true) {//刷新基本信息人名，人数
			out.println(new ClientProtocol().onlineNumber());//显示人名
			System.out.print("请选择功能:");
			int menu = read.nextInt();
			switch(menu) {
			case 1://群发
				System.out.println("群发内容:");
				read = new Scanner(System.in);
				String message = read.nextLine();
				out.println(new ClientProtocol().allSend(message));
				break;
			case 2://单发
				Scanner inNewTwo = new Scanner(System.in);
				System.out.println("单发对象:");
				name = inNewTwo.nextLine();
				System.out.println("单发内容:");
				message = inNewTwo.nextLine();
				out.println(new ClientProtocol().sendNameMessage(name, message));
				break;
			case 3:
				//文件
				break;
			case 4://显示在线人数
				out.println(new ClientProtocol().onlineNumber());
				System.out.println("查询中......");
				main.wait();
				System.out.println("查询成功");
				System.out.println(new MessageHanding(alMessage.getMessages()).getPersonNum(main));
				break;
			case 5://显示当前上线的name
//				out.println(new ClientProtocol().onlineName());
				MessageHanding messageHandOne = new MessageHanding(alMessage.getMessages());
				System.out.println(messageHandOne.getNames().size()+"人");
				for(String str : messageHandOne.getNames()) {
					System.out.println(str); 
				}
				break;
			case 6://退出
				out.println(new ClientProtocol().signOut());
				client.close();
				out.close();
				t.stop();//停止接收线程
				return;
			case 7:
				MessageHanding messageHandTwo = new MessageHanding(alMessage.getMessages());
				for(int i = 0; i < messageHandTwo.getNewMessage().size(); i++) {
					System.out.println("第"+i+"条"+messageHandTwo.getNewMessage().get(i));
				}
				break;
			default:
				break;
			}
		}
	}
}
