package hw12;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class Server{

    public static void main(String agrs[]){
        ServerSocket server=null;//端口
        Socket socketAtServer=null;//套接字
        String localAddress=null;//本地地址
        while(true){
            try{
                server=new ServerSocket(1000);
            }catch (IOException e){}

            try{
                socketAtServer=server.accept();
                localAddress=socketAtServer.getLocalAddress().toString();
            }catch (IOException e1){}

            if(socketAtServer!=null){//给每个用户提供一个服务线程               
                new ServerThread(socketAtServer,localAddress).start();
            }else{
                continue;
            }
        }
    }
}

class ServerThread extends Thread{
    Socket socket;
    DataInputStream in;
    int user;
    ServerThread(Socket t,String localAddress){
        socket=t;
        try{
            in=new DataInputStream(socket.getInputStream());
        }catch (IOException e){}
        															//依靠地址末尾识别用户，A为1001，B为1002
        user=1000+Integer.parseInt(Character.toString(localAddress.charAt(localAddress.length()-1)));
    }
    @Override
    public void run(){
        while(true){
            try {
                byte[] message=new byte[1024];                
                in.read(message);//读取客户端的输出流
                InetAddress address = InetAddress.getByName("127.0.0.1");//若是B的输出流，则将读取信息发送给A，否则将读取信息发送给B                
                DatagramPacket data = new DatagramPacket(message, message.length,address,user);
                DatagramSocket mail = new DatagramSocket();
                mail.send(data);
            }catch (IOException leave){
                break;
            }
        }
    }
}
