package quan.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ClientDemo {

	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException, IOException {
		// TODO Auto-generated method stub
		new ChatClient().startClient("127.0.0.1", 6666,new ClientDemo());
	}
}
