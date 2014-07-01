package toaster2.talk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	public static void main(String args[]) {

		try{

			ServerSocket server=null;

			try{

				server=new ServerSocket(4700);

				//创建一个ServerSocket在端口4700监听客户请求

			}catch(Exception e) {

				System.out.println("can not listen to:"+e);

				//出错，打印出错信息

			}

			Socket socket=null;

			try{

				socket=server.accept();

				//使用accept()阻塞等待客户请求，有客户

				//请求到来则产生一个Socket对象，并继续执行

			}catch(Exception e) {

				System.out.println("Error."+e);

				//出错，打印出错信息

			}

			String line;

			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));

			//由Socket对象得到输入流，并构造相应的BufferedReader对象

			PrintWriter os=new PrintWriter(socket.getOutputStream());

			//由Socket对象得到输出流，并构造PrintWriter对象

			BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));

			//由系统标准输入设备构造BufferedReader对象

			System.out.println("Client:"+is.readLine());

			//在标准输出上打印从客户端读入的字符串

			os.close(); //关闭Socket输出流

			is.close(); //关闭Socket输入流

			socket.close(); //关闭Socket

			server.close(); //关闭ServerSocket

		}catch(Exception e){

			System.out.println("Error:"+e);

			//出错，打印出错信息

		}

	}
}
