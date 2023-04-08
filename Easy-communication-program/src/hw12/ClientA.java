package hw12;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.*;

public class ClientA implements ActionListener,Runnable {
    private Windows Win;//窗口
    private Socket socketAtClient;//套接字
    private DataOutputStream out;//输出流
    ClientA(){
        socketAtClient=new Socket();
        Win=new Windows("在线通讯A端");

        Win.send.addActionListener(this);
        try {            
            InetAddress address = InetAddress.getByName("127.0.0.1");//地址名，用于区别AB
            InetSocketAddress socketAddress=new InetSocketAddress(address,1000);//连接地址，服务器端口号
            socketAtClient.connect(socketAddress);
            out=new DataOutputStream(socketAtClient.getOutputStream());
        }catch (Exception e){}

        Thread thread=new Thread(this);
        thread.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    	String message = Win.Message.getText().trim();//读取要发送的信息
    	byte[] message_send = message.getBytes();       
        try {          
            out.write(message_send);//信息通过out传给服务器  
            StringBuffer sbuf = new StringBuffer(message.length());//在本客户端输出对话记录
            sbuf.append(String.valueOf(message));
            Win.Dialog.append("userA:"+sbuf+"\n");
            Win.Message.setText("");//发送之后把待发送的信息框清空
        } catch (Exception Ex) {}
    }
    @Override
    public void run() {//接受来自服务器的数据报，依靠port区别AB的数据报        
        DatagramPacket pack = null;
        DatagramSocket mail = null;
        byte[] message_receive = new byte[1024];
        try {
            pack = new DatagramPacket(message_receive, message_receive.length);//接受信息
            mail = new DatagramSocket(1002);//B端的端口号
        } catch (Exception Ex) {}

        //监测是否有数据报传入，有则接受翻译并显示在文本上
        while(true){
            try{
                mail.receive(pack);
                String message=new String(pack.getData(),0,pack.getLength());
                Win.Dialog.append("userB:"+message+"\n");//输出信息
            }catch (Exception Ex){
            }
        }
    }
    public static void main(String args[]){
        ClientA userA=new ClientA();
    }
}
