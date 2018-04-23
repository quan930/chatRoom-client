package quan.client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient {
	private Scanner read;
	public ChatClient(String ip,int prot) throws UnknownHostException, IOException {
		Socket client = new Socket(ip,prot);
		ChatMessage alMessage = new ChatMessage(client);
		Thread t = new Thread(alMessage);
		t.start();//开启接收消息的线程
		PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8"),true);
		System.out.print("已连接服务器"+"请注册名字:");
		read = new Scanner(System.in);
		String name = read.nextLine();
		out.println(new ClientProtocol().giveName(name));
		//注册名字
		while(true) {
			System.out.println("名字注册中......");
			while(true) {
				if(alMessage.isGetYorN()) {
					break;
				}else {
					continue;
				}
			}
			alMessage.setGetYorN(false);
			if(alMessage.getMakeNameMessage().equals("创建成功")) {
				System.out.println("创建成功");
				break;
			}else {
				System.out.println("名字重复!!");
				System.out.print("重新注册!");
				name = read.nextLine();
				out.println(new ClientProtocol().giveName(name));
				continue;
			}
		}
		alMessage.setGetYorN(false);	
		//注册完毕执行功能
		while(true) {
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
				while(true) {
					if(alMessage.isGetYorN()) {
						break;
					}else {
						continue;
					}
				}
				System.out.println(alMessage.getOneMessage());
				alMessage.setGetYorN(false);//收到消息
				break;
			case 3:
				//文件
				break;
			case 4://显示在线人数
				out.println(new ClientProtocol().onlineNumber());
				System.out.println("查询中......");
				while(true) {
					if(alMessage.isGetYorN()) {
						break;
					}else {
						continue;
					}
				}
				System.out.println(alMessage.getPersonNum()+"人在线");
				alMessage.setGetYorN(false);
				break;
			case 5://显示当前上线的name
				out.println(new ClientProtocol().onlineName());
				System.out.println("查询中......");
				while(true) {
					if(alMessage.isGetYorN()) {
						break;
					}else {
						continue;
					}
				}
				System.out.println("上线名单");
				for(int i = 0;i < alMessage.getNames().size();i++) {
					System.out.println(alMessage.getNames().get(i));
				}
				alMessage.listNames(new ArrayList<String>());//清空列表
				alMessage.setGetYorN(false);
				break;
			case 6://退出
				out.println(new ClientProtocol().signOut());
				while(true) {
					if(alMessage.isGetYorN()) {
						break;
					}else {
						continue;
					}
				}
				out.close();
				client.close();
				System.out.println("下线");
				return;
			case 7:
				for(int i = 0; i < alMessage.getMessages().size(); i++) {
					System.out.println("第"+i+"条"+alMessage.getMessages().get(i));
				}
				break;
			default:
				break;
			}
		}
	}
}