

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.io.UnsupportedEncodingException;
	import java.net.InetAddress;
	import java.net.InetSocketAddress;
	import java.net.Socket;

import javax.swing.JOptionPane;


public class itpCom extends Thread {
		/**	�|�[�g�ԍ�	*/
		// �{�Ԋ�
		private static int port_no = 8081;// 8080
		private static String innerhostName = "devcontrol.cloud-garden.com";//

		//�J����
		private static int dev_port_no = 8081;
		private static String dev_innerhostName = "devcontrol.cloud-garden.com";//175.41.238.34

		private static InetSocketAddress 	socketAddress=null;
		private static InputStream			is1=null;
		private static InputStreamReader	reader=null;
		private static PrintWriter 			writer=null;
		private static Socket 				socket = null;

		private static String buffer="";
		//�ڑ���̏�������InetAddress�^��inadr��p�ӂ���B
		private static InetAddress 		inadr;
		
		public static String hostname=innerhostName;
		public static int portnum=port_no;
		
		//
		public itpCom()
		{
//			System.out.println("SetupStream");
			SetupStream();
		}
		
		//server�Ƀ��b�Z�[�W�𑗂�
		public void say(String message)
		{
//			System.out.println("say: "+message);
			itp_Logger.logger.info(message);
			
			if(writer==null) return;
			try{
			writer.print(message);
			writer.flush();
			}catch(Exception e){
			//itp_Logger.logger.info("say(): write error");
				// try again
				try{
				writer.print(message);
				writer.flush();
				}catch(Exception e2){
					return;
				}
			}
			System.out.println("say:------"+message);
			//itp_Logger.logger.info("------");
		}
		
		
		private static void clearBuffer()
		{
			buffer="";
		}
		
		private static String readBuffer()
		{
			return buffer;
		}
		
		// server����̃��b�Z�[�W���󂯂�
		public String listen()
		{
			String cline="";
			for(int i=0;i<100;i++){// time out
				if(readBuffer() != ""){
					cline=readBuffer();
					clearBuffer();// clear buffer
					break;
				}
				try {
					sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}// while end
			System.out.println("listen:------"+cline);
			return cline;
		}
		
		// server�Ƃ̒ʐM�|�[�g���m������B
		public void SetupStream()
		{
//			JOptionPane.showMessageDialog(null, "SetupStream start");
				//�A�h���X����ێ�����socketAddress���쐬�B

			// debug file������΃f�o�b�O���[�h�ŋN������B
			String fname=System.getProperty("user.home")+"/itpManager"+Version.getFolderName()+"/debug.txt";
			System.out.println("fname="+fname);
			
			File f = new File(fname);
			try 
			{	
				FileInputStream fi = new FileInputStream(f);
				fi.close();
				// there are debug.txt
				socketAddress = 
					new InetSocketAddress(dev_innerhostName, dev_port_no);// �{�Ԃ́A port_no �ɂ��邱��
				hostname=dev_innerhostName;
				portnum=dev_port_no;
				System.out.println("server : "+hostname);
			}
			catch(Exception e)
			{
				// �Ȃ���΁A�ʏ탂�[�h�ŋN������B
				socketAddress = 
					new InetSocketAddress(innerhostName, port_no);// �{�Ԃ́A port_no �ɂ��邱��
				hostname=innerhostName;
				portnum=port_no;
				System.out.println("server : "+hostname);
			}
				
				
				//socketAddress�̒l�Ɋ�Â��ĒʐM�Ɏg�p����\�P�b�g���쐬����B
					
			socket= new Socket();// new socket
			
			//�^�C���A�E�g��10�b(10000msec)
			try {
				socket.connect(socketAddress, 10000);
			} catch (IOException e) {
				System.out.println("can not open soccet");
				e.printStackTrace();
				System.out.println("Terminated");
				System.exit(0);
			}

		//socket����̃f�[�^��InputStreamReader�ɑ���A�����
		//BufferedReader�ɂ���ăo�b�t�@�����O����B
		    
			try {
				is1 = socket.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    try {
				reader = new InputStreamReader(is1,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//PrintWriter�^��writer�ɁA�\�P�b�g�̏o�̓X�g���[����n���B
			try {
				writer = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			//inadr�Ƀ\�P�b�g�̐ڑ���A�h���X�����Anull�ł���ꍇ�ɂ�
			//�ڑ����s�Ɣ��f����B
			//null�łȂ���΁A�ڑ��m�����Ă���B
				if ((inadr = socket.getInetAddress()) != null) {
					System.out.println("Connect to " + inadr);
					//itp_Logger.logger.info("Connect to " + inadr);
				}else {
					System.out.println("Connection failed.");
					//itp_Logger.logger.info("Connection failed.");
				}
//				JOptionPane.showMessageDialog(null, "SetupStream end");

				return;

		}
		
		public void close() throws IOException
		{
			writer.close();
			reader.close();
			is1.close();
			socket.close();
		}

		boolean runflag=true;
		boolean runEndflag=false;
		
		public void stop_thread()
		{
			runflag=false;
		}
		
		public boolean get_RunEndFlag()
		{
			return runEndflag;
		}
		
	@Override
	public void run()
	{
		char[]	cline=null;
		boolean flag=true;
		
		System.out.println("run");
		// loop
		while(runflag){
			if(buffer=="") flag=true;// buffer is empty
	
			// check server
			if(is1==null){
				System.out.println("Server not response");
				JOptionPane.showMessageDialog(null, "�N���E�h�K�[�f����������Ȃ��Ȃ�܂����B");
				return;
			}
		
			// wait for server response
			while(runflag){
				try {
					if(is1.available() !=0 ) break;//
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					sleep(30);//�@CPU���J����
				} catch (InterruptedException e0) {
					e0.printStackTrace();
				}//
			}//
			
			if(flag){// read server when buffer is empty 
				// find data
				try {
						cline = new char[is1.available()];
						try {
							if(is1.available() != 0 ){
							reader.read(cline);
							
							//System.out.println(cline);
							//System.out.println("cline1:|"+String.valueOf(cline)+"|");
							buffer += String.valueOf(cline);
				
							// check for terminator�@���s��������
							//System.out.println("bf="+buffer);
							int st=buffer.indexOf("\r\n\r\n");
							//System.out.println("st="+st);
							if(st<0) continue;
							if(	buffer.substring(st, st+4).matches("\r\n\r\n")) 
												flag=false;// find terminator�@���s����������
								}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				} catch (IOException e0) {
					e0.printStackTrace();
				}
			}
		}
		
		runEndflag=true;// stop thread run flag
	}// run end
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String s="";
		
		System.out.println("Constructor");
		itpCom itpCom1=new itpCom();
		
		System.out.println("Start");
		itpCom1.start();
		
		System.out.println("HELO");
		itpCom1.say("HELO\r\n\r\n");
		
		System.out.println("listen");
		s = itpCom1.listen();
		System.out.println("listen:"+s);
		
		System.out.println("CLS");
		itpCom1.say("CLS\r\n\r\n");

		System.out.println("listen");
		s = itpCom1.listen();
		System.out.println("listen:"+s);
		
/*
		while(true){
			//wait(100);
		}
*/
		itpCom1.stop_thread();
		while(itpCom1.get_RunEndFlag()==false);
		
		System.out.println("close");
		try {
			itpCom1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("end");
	}

}
