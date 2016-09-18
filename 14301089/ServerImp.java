package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;

public class ServerImp {  
    
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();  
    public static void main(String[] args){  
        try {  
            ServerSocket ss = new ServerSocket(3333);  
            while(true){  
                System.out.println("�ȴ�����...");  
                Socket s = ss.accept();  
                // ÿ�յ�һ��socket���� ��������һ��SeverThread�߳�  
                System.out.println("���ӳɹ����ȴ��ͻ���������Ϣ��");  
                socketList.add(s);  
                new Thread(new SeverThread(s)).start();  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        };  
    }  
}  
class SeverThread implements Runnable{
	
	    Socket s = null;  
	    BufferedReader br = null;  
	    PrintStream ps = null;  
	    StringBuffer sb;
	    public SeverThread(Socket s) {  
	        this.s = s;  
	        try {  
	            // ��ʼ����socket��Ӧ��������    �õ��ͻ��˵���Ϣ  
	            br = new BufferedReader(new InputStreamReader(s.getInputStream()));  
	            // ��ȡ��socket��Ӧ�������  
	            ps = new PrintStream(s.getOutputStream());  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    @Override  
	    public void run() {  
	        // TODO Auto-generated method stub  
	        String content = null;  
	        String adContent=null;
	        try {  
	              
	            while((content = br.readLine()) != null){  
	                sb = new StringBuffer(content);
	                
	                //��ȡ��ת�ַ���
	                adContent=sb.reverse().toString();
	                System.out.println("content : " + content);   
	                // ��ͻ��˷���������Ϣ  
	                ps.println("Receive" + adContent);  
	                 //�ж��Ƿ����  
	                if(content.equals("End"))  
	                {  
	                    System.out.println("�Ի�������");  
	                    s.shutdownOutput();  
	                   
	                    break;  
	                }  
	            }  
	              
	              
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	              
	        }  
	        finally{  
	            try {  
	                br.close();  
	                ps.close();  
	                s.close();
	                ServerImp.socketList.remove(s);  
	            } catch (IOException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	      
}
