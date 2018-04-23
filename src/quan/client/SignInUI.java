package quan.client;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//启动页面
public class SignInUI {
	public SignInUI(PrintWriter out,ChatMessage alMessage,MainUI fNew) {
		JFrame f=new JFrame();
	    f.setTitle("登录界面");
	    f.setSize(250,320);//窗体大小
	    f.setLocationRelativeTo(null);//剧中
	    //f.setDefau ltCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setResizable(false);//大小不可改变
	    Container con=f.getContentPane();
	    con.setLayout(new GridLayout(3,1));
	    JPanel pan1=new JPanel();
	    JLabel textView=new JLabel("注册用户名");
	    //用户名向下偏移
	    textView.setFont(new Font("宋体",Font.BOLD, 20));
	    pan1.add(textView);
	    con.add(pan1);
	    JPanel pan2=new JPanel();//生成一个新的版面
	    JLabel textName=new JLabel("用户名");
	    pan2.add(textName);
	    TextField tf_name=new TextField(15);//文本大小
	    tf_name.setText("****");
	    pan2.add(tf_name);
	    con.add(pan2);
	    //用户名及其文本框放置在第二个版面上
	    JPanel pan3 = new JPanel();
	    JButton up=new JButton("登陆");
	    pan3.add(up);
	    con.add(pan3);
     	f.setVisible(true);//设置窗口为可见
     	up.addActionListener(new ActionListener() {
   	   	 //监听
   	    	@Override
   	    	public void actionPerformed(ActionEvent e) {
   	    	 // TODO Auto-generated method stub
   	    		String name = tf_name.getText();
   	    		out.println(new ClientProtocol().giveName(name));
   	    		while(true) {
   	   				while(true) {
   	   					if(alMessage.isGetYorN()) {
   	   						break;
       					}else {
   	    					continue;
   	    				}
   	    			}
   	    			alMessage.setGetYorN(false);
   	    			if(alMessage.getMakeNameMessage().equals("创建成功")) {
   	   				 //启动一个新的窗口
   	    				alMessage.setGetYorN(false);
   	    				fNew.showShow(name);
   	    				f.dispose();// 关闭并销毁
   	    				fNew.showShow(name);
   	   					break;
   	   				}else {
   	   					tf_name.setText("名字重复,重新注册");
       					break;
   	   				}
   	   			}
   	    	}
   	    });
	}
}
