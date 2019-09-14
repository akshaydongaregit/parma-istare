import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NetWorkingDemo {

	
	public static void main(String ...strings ) throws Exception {
		Thread serverThread = new Thread() {
			@Override 
			public void run() {
				try {
					new NetWorkingDemo().startServerAt(8080);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread clientThread = new Thread() {
		 @Override
		 public void run() {
			 try {
				new NetWorkingDemo().tryToConnectServer("127.0.0.1", 8080);
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		};
		
		serverThread.start();
		clientThread.start();
		clientThread.join();
		
	}
	
	public void startServerAt(int port) throws Exception {
		ServerSocket server = new ServerSocket(port);
		
		System.out.println("Starting server...");
		while(true) {
			Socket soc = server.accept();
			System.out.println("Connection accepted");
			OutputStream out = soc.getOutputStream();
			Path path = Paths.get( getClass().getClassLoader().getResource("home.html").toURI());
			Stream<String> lines = Files.lines(path);
			String page = lines.collect(Collectors.joining("\n"));
			out.write(page.getBytes());
			out.flush();
			soc.close();
			System.out.println("connection closed");
		}
	}
	
	public void tryToConnectServer(String host,int port) throws Exception {
		Socket sock = new Socket(host,port);
		InputStream in = sock.getInputStream();
		Scanner ins = new Scanner(in);
		while(ins.hasNext()) 
			System.out.println(ins.next());
	}
	
	
}
