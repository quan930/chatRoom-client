package quan.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

//全部消息
//处理消息类
public class ChatMessage implements Runnable{
	private Socket client;
	private List<String>messages = new ArrayList<String>();//处理后消息
	private List<String> names = new ArrayList<String>();//名字列表
	private int personNum = 0;//上线总人数
	private String makeNameMessage;//注册名字的信息
	private boolean getYorN;//得到消息
	private String oneMessage;//单发成功消息
	public boolean isGetYorN() {
		return getYorN;
	}
	public void setGetYorN(boolean getYorN) {
		this.getYorN = getYorN;
	}
	//返回名字列表
	public List<String> getNames() {
		while(true) {//接收指令
			if(getYorN) {
				break;
			}else {
				continue;
			}
		}
		return names;
	}
	//单发消息
	public String getOneMessage() {
		while(true) {//接收指令
			if(getYorN) {
				break;
			}else {
				continue;
			}
		}
		return oneMessage;
	}
	//返回人数
	public int getPersonNum() {//查看人数看完就关
		while(true) {//接收指令
			if(getYorN) {
				break;
			}else {
				continue;
			}
		}
//		personNumYorN = false;
		return personNum;
	}
	//消息列表
	public List<String> getMessages() {
		return messages;
	}
	//返回注册名字信息
	public String getMakeNameMessage() {
		return makeNameMessage;
	}
	//清空名字列表
	public void listNames(List<String> names) {
		this.names = names;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Scanner in = new Scanner(client.getInputStream());
			//isOnline= true;//上下线
			while(true){//
				String message = in.nextLine();//读入数据
				String s = message.substring(0,4);
				if(s.equals("*na*")) {//名字指令
					makeNameMessage = message.substring(4);
					getYorN = true;
					continue;
				}else {
					if(s.equals("*sp*")) {//人数指令
						personNum = Integer.parseInt(message.substring(4));
						getYorN = true;
						continue;
					}else {
						if(s.equals("Name")) {//人名指令
							String[] sarray = message.substring(4).split("Name"); 
					        for(String ss:sarray){
					            names.add(ss);
					        }
					        getYorN = true;
							continue;
						}else {
							if(s.equals("*ou*")) {//退出指令
								getYorN = true;
								break;
							}else {
								if(s.equals("*11*")) {
									oneMessage = message.substring(4);
									
									getYorN = true;
									continue;
								}else {
									messages.add(message);
									continue;
								}
							}
						}
					}
				}		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//将socket client 传入
	public ChatMessage(Socket client) {
		this.client = client;
	}
}
