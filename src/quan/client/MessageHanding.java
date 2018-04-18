package quan.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//处理消息
public class MessageHanding {
	private Set<String>names = new HashSet<String>();//避免名字重负
	private int personNum = 0;//上线总人数
	private List<String>newMessage = new ArrayList<String>();//处理后消息
	private boolean personNumYorN = false;//是否有人数消息
	private boolean personNameYorN = false;//是否接受消息
	
	public Set<String> getNames() {
		return names;
	}
	public int getPersonNum(Object client) {
		while(true) {//接收指令
			if(personNumYorN) {
				client.notify();
				break;
			}
		}
		return personNum;
	}
	public List<String> getNewMessage() {
		return newMessage;
	}
	//构造器
	public MessageHanding(List<String>messages) {
		//System.out.println(messages.size()+"个");
		for(int i = 0;i<messages.size();i++) {
			String s = messages.get(i).substring(0,4);
			if(s.equals("*sp*")) {//人数指令
				personNum = Integer.parseInt(messages.get(i).substring(4));
				personNumYorN = true;
				continue;
			}else {
				if(s.equals("Name")) {//人名指令
					names.add(messages.get(i).substring(4));
					continue;
				}else {
					if(s.equals("*ou*")) {//退出
						names.remove(messages.get(i).substring(4));
						continue;
					}else {
						newMessage.add(messages.get(i));
					}
				}
			}
		}
	}
//	public MessageHanding(ChatClient client) {
//		if() {
//			client.notify();
//		}
//	}
}
