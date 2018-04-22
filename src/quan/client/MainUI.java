package quan.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainUI {
	private JFrame f;
	public MainUI(PrintWriter out,ChatMessage alMessage) {
		f=new JFrame();
        f.setTitle("chatroom");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口默认关闭方式
		f.setSize(250,500);//窗体大小
        f.setResizable(false);//大小不可改变
        JLabel message=new JLabel("消息列表");
        JButton refresh=new JButton("刷新");
        JLabel showNumber=new JLabel("0人在线",JLabel.CENTER);
        JButton send=new JButton("发送消息");
        JButton showMessage=new JButton("查看消息");
        showNumber.setBounds(0, 0, 125, 50);
        refresh.setBounds(125, 0, 125, 50);
        send.setBounds(0, 430, 125, 50);
        showMessage.setBounds(125, 430, 125, 50);
        f.add(send);
        f.add(showMessage);
        f.add(showNumber);
        f.add(refresh);
        f.add(message,BorderLayout.CENTER);
//        f.setVisible(true);
        //刷新键
        refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				out.println(new ClientProtocol().onlineNumber());
				while(true) {
					if(alMessage.isGetYorN()) {
						break;
					}else {
						continue;
					}
				}
				showNumber.setText(alMessage.getPersonNum()+"人在线");
				alMessage.setGetYorN(false);
				out.println(new ClientProtocol().onlineName());
				while(true) {
					if(alMessage.isGetYorN()) {
						break;
					}else {
						continue;
					}
				}
				String nameList = "<html><body>";
				for(int i = 0;i < alMessage.getNames().size();i++) {
					nameList += (alMessage.getNames().get(i)+"<br>");
				}
				nameList += "<body></html>";
				alMessage.listNames(new ArrayList<String>());//清空列表
				message.setText(nameList);
				alMessage.setGetYorN(false);
			}
		});
        //查看消息
        showMessage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String messageList = "<html><body>";
				
				// TODO Auto-generated method stub
				for(int i = 0; i < alMessage.getMessages().size(); i++) {
					messageList += ("第"+(i+1)+"条"+alMessage.getMessages().get(i)+"<br>");
					
				}
				messageList += "<body></html>";
				message.setText(messageList);
			}
		});
        //发送消息
        send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//发收消息页面
				new SendMessageUI(out, alMessage);
			}
		});
	}
	public void showShow() {
		f.setVisible(true);
	}
}
