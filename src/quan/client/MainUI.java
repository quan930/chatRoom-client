package quan.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MainUI {
	private JFrame f;
	private String sendName;
	private JList list;
	private String name;
	private JLabel oneself;
	public MainUI(PrintWriter out,ChatMessage alMessage) {
		f =new JFrame();
		f.setTitle("chatroom");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口默认关闭方式
	    f.setSize(500,570);//窗体大小
	    f.setLocationRelativeTo(null);//剧中
	    f.setResizable(false);//大小不可改变
	    JPanel pan = new JPanel(null);
	    JLabel message=new JLabel("消息列表");//消息
	    JButton send=new JButton("发送");//发送键
	    JButton newB=new JButton("刷新");//发送键
	    oneself=new JLabel("", SwingConstants.RIGHT);
	    JPanel namelist = new JPanel(null);
	    JScrollPane nameJS = new JScrollPane();
	    JTextArea oneMessage = new JTextArea(".....", 10, 15);//输入消息
	    message.setBounds(0, 0, 300, 350);
	    oneMessage.setBounds(0,350, 300,150 );
	    send.setBounds(0, 500, 300, 50);
	    namelist.setBounds(300, 0, 200, 570);
//	    namelist.setBackground(new Color(255, 255, 0));
	    pan.add(send);
	    pan.add(oneMessage);
	    pan.add(message);
	    pan.add(namelist);
	    f.add(pan,BorderLayout.CENTER);
		list = new JList();
		//list.setBounds(0, 30, 200, 470);
		list.setFont(new Font("宋体",Font.BOLD,30));//void setFont(Font font)\
		oneself.setBounds(0, 500, 200, 40);
		newB.setBounds(0, 0, 200, 30);
		nameJS.setBounds(0, 30, 200, 470);
		namelist.add(nameJS);
		//namelist.add(list);
		namelist.add(newB);
		namelist.add(oneself);
		nameJS.setViewportView(list);
		
//		什么时候你想更新数据，只需要调用JList.setModel(ListModel model)方法设置新的数据即可。
		//list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//设置只允许选择一个项目
		list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    JList myList = (JList) e.getSource();
                    int index = myList.getSelectedIndex(); //已选项的下标
                    Object obj = myList.getModel().getElementAt(index);
                    sendName = obj.toString();
                }
            }
        });
		send.addActionListener(new ActionListener() {
			//发送键
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String message = oneMessage.getText();
				out.println(new ClientProtocol().sendNameMessage(sendName, message));
				while(true) {
	   				if(alMessage.isGetYorN()) {
	   					break;
	   				}else {
	    				continue;
	    			}
	    		}
				alMessage.setGetYorN(false);
				oneMessage.setText("");
			}
		});
		newB.addActionListener(new ActionListener() {
			//刷新键
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				out.println(new ClientProtocol().onlineName());
				while(true) {
					if(alMessage.isGetYorN()) {
						break;
					}else {
						continue;
					}
				}
				DefaultListModel listModel = new DefaultListModel();
				for(int i = 0;i < alMessage.getNames().size();i++) {
					listModel.addElement(alMessage.getNames().get(i));
//					System.out.println(alMessage.getNames().get(i));
				}
				alMessage.listNames(new ArrayList<String>());//清空列表
				alMessage.setGetYorN(false);
				list.setModel(listModel);
				String messageList = "<html><body>";
				for(int i = 0; i < alMessage.getMessages().size(); i++) {
					messageList += ("第"+(i+1)+"条\t"+alMessage.getMessages().get(i)+"<br>");
					
				}
				messageList += "<body></html>";
				message.setText(messageList);
			}
		});
	}
	public void showShow(String name) {
		this.name = name;
		f.setVisible(true);
		oneself.setText(name);
	}
}