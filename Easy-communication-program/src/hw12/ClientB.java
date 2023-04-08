package hw12;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.*;

public class ClientB implements ActionListener,Runnable {
    private Windows Win;
    private Socket socketAtClient;
    private DataOutputStream out;
    ClientB(){
        socketAtClient=new Socket();
        Win=new Windows("在线通讯B端");

        Win.send.addActionListener(this);
        try {       	
            InetAddress address = InetAddress.getByName("127.0.0.2");
            InetSocketAddress socketAddress=new InetSocketAddress(address,1000);
            socketAtClient.connect(socketAddress);
            out=new DataOutputStream(socketAtClient.getOutputStream());
        }catch (Exception e){}

        Thread thread=new Thread(this);
        thread.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    	String message = Win.Message.getText().trim();
    	byte[] message_send = message.getBytes();
        try {         
            out.write(message_send);
            StringBuffer sbuf = new StringBuffer(message.length());
            sbuf.append(String.valueOf(message));
            Win.Dialog.append("userB:"+sbuf+"\n");
            Win.Message.setText("");
        } catch (Exception Ex) {}
    }
    @Override
    public void run() {
        DatagramPacket pack = null;
        DatagramSocket mail = null;
        byte[] message_receive = new byte[1024];
        try {
            pack = new DatagramPacket(message_receive, message_receive.length);
            mail = new DatagramSocket(1001);
        } catch (Exception Ex) {}

        while(true){
            try{
                mail.receive(pack);
                String message=new String(pack.getData(),0,pack.getLength());
                Win.Dialog.append("userA:"+message+"\n");
                Win.Dialog.setCaretPosition(Win.Dialog.getText().length());
            }catch (Exception Ex){
            }
        }
    }
    public static void main(String agrs[]){
        ClientB userA=new ClientB();
    }
}
