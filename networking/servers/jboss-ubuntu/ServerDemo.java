

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ServerDemo {

	JSch jsch = new JSch();
	Session session ;
	InputStream in;
	OutputStream out;
	String host = "127.0.0.1";
	String user = ""; //"your-username";
	String pswd = ""; //"your-password" ;
	
	public ServerDemo() {
	}
	public static void main(String ...strings ) throws Exception {
	
		new ServerDemo().tryCmd();
	}
	
	public void tryCmd() throws Exception {
		System.err.print("\nConnecting to Server ..." + host + "\n");
		
		Session session = getSession(host, user, pswd );
		ChannelShell channelShell = getShell(session);
		
		ReadingThread reader = new ReadingThread();
		reader.setIn(in);
		reader.setOut(System.out);
		reader.start();
		
		waitForcmdLine(reader,false);
		
		runSudo(pswd, reader, out);
		
		waitForcmdLine(reader,true);	
		tryRestart(reader, out);
		
		/*while(channelShell.isConnected()) 
			try { Thread.sleep(1000); }catch(Exception e){e.printStackTrace();}
		*/
		channelShell.disconnect();
		session.disconnect();
		System.err.print("\n\nJboss at "+host+" Restarted sucessfully");	
		System.exit(0);
	}
	
	public void runSudo(String pswd , ReadingThread reader,OutputStream out) {
		
		try {
			out.write("sudo -i\n".getBytes());
			out.flush();
			
			waitForSudoPswdAsk(reader);
			
			out.write( (pswd+"\n").getBytes());
			out.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tryRestart(ReadingThread reader,OutputStream out) {
		
		try {
			//System.err.print("\nTrying Restart ...");
			
			flushCmd("cd /usr/share/jboss/jboss-as-7.1.1.Final/bin", out);
			waitForcmdLine(reader, true);
			flushCmd("ps -ef |grep java| awk '/jboss-as-7.1/ {print $2}' | xargs kill",out);
			waitForcmdLine(reader, true);
			flushCmd("rm nohup.out",out);
			waitForcmdLine(reader, true);
			flushCmd("nohup ./standalone.sh -Djboss.bind.address=10.0.2.15 &",out);
			waitInLastLine("", reader);
			flushCmd("", out);
			flushCmd("exit", out);
			waitForcmdLine(reader, false);
			flushCmd("exit", out);
			
			//System.err.print("\nRestarted Successfully ... ");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void flushCmd( String cmd , OutputStream out) throws IOException {
		out.write((cmd+"\n").getBytes());
		out.flush();
	}
	
	public void waitForcmdLine(ReadingThread reader,boolean isRoot) throws Exception {

		WaitingPredicate cmdCheckPred = isRoot ? new WaitingPredicate() {
			@Override
			public boolean test(String t) {
				String ll = t.substring(t.lastIndexOf("\n")+1,t.length());
				if(ll.startsWith("root@")&&ll.endsWith("# "))
					matched(true);
				return matched();
			}
		} : new WaitingPredicate() {
			@Override
			public boolean test(String t) {
				if(t.endsWith(":~$ "))
					matched(true);
				return matched();
			}
		};
		
		waitOnReader(reader, cmdCheckPred);
	}

	public void waitOnReader(ReadingThread reader,WaitingPredicate pred) throws Exception {
		reader.addWait(pred);
		pred.waitForMatch(300, 6*1000);
		reader.removeWait(pred);
	}
	
	public void waitForSudoPswdAsk(ReadingThread reader) throws Exception {

		WaitingPredicate checkPred = new WaitingPredicate() {
			@Override
			public boolean test(String str) {
				String ll = str.substring(str.lastIndexOf("\n")+1,str.length());
				if(ll.contains("[sudo] password for")&&ll.endsWith(": ")) 
					this.matched(true); 
				return matched();
			}
		};
		
		waitOnReader(reader, checkPred);	
	}

	public void waitInLastLine(String str , ReadingThread reader) throws Exception {

		WaitingPredicate checkPred = new WaitingPredicate() {
			@Override
			public boolean test(String history) {
				String ll = history.substring(history.lastIndexOf("\n")+1,history.length());
				if(ll.endsWith(str)) 
					this.matched(true); 
				return matched();
			}
		};
		
		waitOnReader(reader, checkPred);	
	}
	
	public Session getSession(String host,String user,String pswd) throws JSchException {
		
		Session session = jsch.getSession(user, host, 22);
        session.setPassword(pswd);

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        return session;
		
	}
	
	public ChannelShell getShell(Session session) throws IOException, JSchException {
		
		ChannelShell channel = (ChannelShell) session.openChannel("shell");
		channel.setInputStream(null);
		channel.setOutputStream(null);
		channel.setExtOutputStream(System.err,true);
		
		in = channel.getInputStream();
		out = channel.getOutputStream();
		
		channel.connect(3*1000);
		return channel;
		
	}
	
	public class ReadingThread extends Thread {
		
		InputStream in ;
		OutputStream out;
		String historyBuffer="";
		
		private List<Predicate<String>> waitPredList = new ArrayList<Predicate<String>>();
		private Predicate<String> newWaitPred = null;
		
		@Override
		public void run() {
			while(true) {
				
				try { Thread.sleep(200); } catch (InterruptedException e1) {e1.printStackTrace();}
				
				try {
					String output = read(in);
					if(!output.isEmpty()) { 
						historyBuffer+=output;
						for(Predicate<String> pred : waitPredList) 
							pred.test(historyBuffer);
					}else if(newWaitPred!=null) {
						newWaitPred.test(historyBuffer);
						newWaitPred = null;
					}
						out.write(output.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public void addWait(Predicate<String> cond) {
			waitPredList.add(cond);
			newWaitPred = cond;
		}
		
		public void removeWait(Predicate<String> cond) {
			waitPredList.remove(cond);
		}
	
		public InputStream getIn() {
			return in;
		}

		public void setIn(InputStream in) {
			this.in = in;
		}

		public OutputStream getOut() {
			return out;
		}

		public void setOut(OutputStream out) {
			this.out = out;
		}

		public String read(InputStream in) {
			
			StringBuilder outputBuffer = new StringBuilder(); 
			try {	
				byte[] tmp = new byte[1024];
				while (in.available() > 0) {
				        int i = in.read(tmp, 0, 1024);
				        if (i < 0) break;
				        outputBuffer.append(new String(tmp, 0, i));
				    }
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return outputBuffer.toString();
		}
		
	}
	
	public class WaitingPredicate implements Predicate<String> {

		private boolean matched = false;
		
		@Override
		public boolean test(String t) {
			return true;
		}
		
		public void matched(boolean matched ) {
			this.matched = matched;
		}
		
		public boolean matched() {
			return this.matched;
		}
		
		public void waitForMatch(long gap , long timeout) throws Exception{
			long waitingTime = 0;
			while(!this.matched()) {
					waitingTime+=gap;
					if(waitingTime>timeout)
						throw new Exception("Exception : waiting predicate timed out of "+timeout);
					Thread.sleep(300);
			}
		}
	}
}
