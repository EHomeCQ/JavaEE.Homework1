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
                System.out.println("等待连接...");  
                Socket s = ss.accept();  
                // 每收到一个socket连接 ，则启动一条SeverThread线程  
                System.out.println("连接成功，等待客户端输入信息！");  
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
	            // 初始化该socket对应的输入流    得到客户端的信息  
	            br = new BufferedReader(new InputStreamReader(s.getInputStream()));  
	            // 获取该socket对应的输出流  
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
	                
	                //获取反转字符串
	                adContent=sb.reverse().toString();
	                System.out.println("content : " + content);   
	                // 向客户端发出反馈消息  
	                ps.println("Receive" + adContent);  
	                 //判断是否结束  
	                if(content.equals("End"))  
	                {  
	                    System.out.println("对话结束！");  
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
