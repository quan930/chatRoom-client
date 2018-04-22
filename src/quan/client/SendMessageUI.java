package quan.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SendMessageUI {
	private JFrame wind;
	public SendMessageUI(PrintWriter out,ChatMessage alMessage) {
		JFrame choose= new JFrame();
		choose.setTitle("请选择");
		choose.setSize(200, 100);
		choose.setLocationRelativeTo(null);//剧中
		choose.setResizable(false);//大小不可改变
        JPanel pan=new JPanel();
		pan.setLayout(new GridLayout(1, 2));
		JButton all=new JButton("群发");
		JButton one=new JButton("单发");
		pan.add(all);
		pan.add(one);
		choose.add(pan);
		choose.setVisible(true);
		all.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//群发
				allMess(out,alMessage);
   				choose.dispose();// 关闭并销毁
   				wind.setVisible(true);
			}
		});
		one.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//单发
				fendOne(out,alMessage);
				choose.dispose();// 关闭并销毁
   				wind.setVisible(true);
			}
		});
	}
	private void allMess(PrintWriter out,ChatMessage alMessage) {
		wind = new JFrame();
		wind.setTitle("群发");
		wind.setSize(200, 300);
		wind.setLocationRelativeTo(null);//剧中
		wind.setResizable(false);//大小不可改变
		JTextArea all_message = new JTextArea(".....", 20, 43);
		all_message.setLineWrap(true);
		JButton affirm = new JButton("确认");//确认键
		all_message.setText("......");
	    wind.add(affirm,BorderLayout.SOUTH);
	    wind.add(all_message,BorderLayout.CENTER);
	    affirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				out.println(new ClientProtocol().allSend(all_message.getText()));
				wind.dispose();
			}
		});
	}
	private void fendOne(PrintWriter out,ChatMessage alMessage) {
		wind = new JFrame();
		wind.setTitle("单发");
		wind.setSize(200, 300);
		wind.setLocationRelativeTo(null);//剧中
		wind.setResizable(false);//大小不可改变
		TextField tf_name=new TextField(15);//文本大小
		JButton affirm = new JButton("确认");//确认键
	    tf_name.setText("输入发送目标");
	    JTextArea oneMessage = new JTextArea(".....", 20, 43);
	    oneMessage.setLineWrap(true);
	    wind.add(tf_name,BorderLayout.NORTH);
	    wind.add(affirm,BorderLayout.SOUTH);
	    wind.add(oneMessage,BorderLayout.CENTER);
	    affirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				out.println(new ClientProtocol().sendNameMessage(tf_name.getText(), oneMessage.getText()));
				while(true) {
	   				if(alMessage.isGetYorN()) {
	   					break;
	   				}else {
	    				continue;
	    			}
	    		}
				System.out.println(alMessage.getOneMessage());
				if(alMessage.getOneMessage().equals("succsee")) {
					alMessage.setGetYorN(false);
   	    			wind.dispose();
   	   			}else {
   	   				alMessage.setGetYorN(false);
  					tf_name.setText("error!");
   	   			}
			}
		});
	}
}
