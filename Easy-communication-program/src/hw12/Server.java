package hw12;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class Server{

    public static void main(String agrs[]){
        ServerSocket server=null;//�˿�
        Socket socketAtServer=null;//�׽���
        String localAddress=null;//���ص�ַ
        while(true){
            try{
                server=new ServerSocket(1000);
            }catch (IOException e){}

            try{
                socketAtServer=server.accept();
                localAddress=socketAtServer.getLocalAddress().toString();
            }catch (IOException e1){}

            if(socketAtServer!=null){//��ÿ���û��ṩһ�������߳�               
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
        															//������ַĩβʶ���û���AΪ1001��BΪ1002
        user=1000+Integer.parseInt(Character.toString(localAddress.charAt(localAddress.length()-1)));
    }
    @Override
    public void run(){
        while(true){
            try {
                byte[] message=new byte[1024];                
                in.read(message);//��ȡ�ͻ��˵������
                InetAddress address = InetAddress.getByName("127.0.0.1");//����B����������򽫶�ȡ��Ϣ���͸�A�����򽫶�ȡ��Ϣ���͸�B                
                DatagramPacket data = new DatagramPacket(message, message.length,address,user);
                DatagramSocket mail = new DatagramSocket();
                mail.send(data);
            }catch (IOException leave){
                break;
            }
        }
    }
}
