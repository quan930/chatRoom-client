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
	private Set<String> names = new HashSet<String>();//名字列表
	private int personNum = 0;//上线总人数
	private boolean personNameYorN = false;//是否有名字消息
	private boolean personNumYorN = false;//是否有人数消息
	private boolean isOnline;//改变线程
	private boolean makeName = false;//是否注册名字
	private String makeNameMessage;//注册名字的信息
	//返回是否接到人数消息
	public boolean isPersonNumYorN() {
		return personNumYorN;
	}
	//返回是否接到名字消息
	public boolean isPersonNameYorN() {
		return personNameYorN;
	}
	//看完人数为假
	public void setPersonNumYorN(boolean personNumYorN) {
		this.personNumYorN = personNumYorN;
	}
	//看完名字为假
	public void setPersonNameYorN(boolean personNameYorN) {
		this.personNameYorN = personNameYorN;
	}
	//返回名字列表
	public Set<String> getNames() {
		while(true) {//接收指令
			if(personNameYorN) {
				break;
			}else {
				continue;
			}
		}
		return names;
	}
	//返回人数
	public int getPersonNum() {//查看人数看完就关
		while(true) {//接收指令
			if(personNumYorN) {
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
	//结束线程
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	//清空名字列表
	public void setNames(Set<String> names) {
		this.names = names;
	}
	//返回是否注册名字消息
	public boolean isMakeName() {
		return makeName;
	}
	//注册完以后返回假
	public void setMakeName(boolean makeName) {
		this.makeName = makeName;
	}
	//返回注册名字信息
	public String getMakeNameMessage() {
		return makeNameMessage;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Scanner in = new Scanner(client.getInputStream());
			isOnline= true;//上下线
			while(isOnline){
				String message = in.nextLine();//读入数据
				String s = message.substring(0,4);
				if(s.equals("*na*")) {//名字指令
					makeNameMessage = message.substring(4);
					makeName = true;
					continue;
				}else {
					if(s.equals("*sp*")) {//人数指令
						personNum = Integer.parseInt(message.substring(4));
						personNumYorN = true;//收到人数指令
						continue;
					}else {
						if(s.equals("Name")) {//人名指令
							String[] sarray = message.substring(4).split("Name"); 
					        for(String ss:sarray){
					            names.add(ss);
					        }
					        personNameYorN = true;//收到人名指令
							continue;
						}else {
							messages.add(message);
						}
					}
				}				
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
