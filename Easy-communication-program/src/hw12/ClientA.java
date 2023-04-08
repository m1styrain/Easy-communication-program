package hw12;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.*;

public class ClientA implements ActionListener,Runnable {
    private Windows Win;//����
    private Socket socketAtClient;//�׽���
    private DataOutputStream out;//�����
    ClientA(){
        socketAtClient=new Socket();
        Win=new Windows("����ͨѶA��");

        Win.send.addActionListener(this);
        try {            
            InetAddress address = InetAddress.getByName("127.0.0.1");//��ַ������������AB
            InetSocketAddress socketAddress=new InetSocketAddress(address,1000);//���ӵ�ַ���������˿ں�
            socketAtClient.connect(socketAddress);
            out=new DataOutputStream(socketAtClient.getOutputStream());
        }catch (Exception e){}

        Thread thread=new Thread(this);
        thread.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    	String message = Win.Message.getText().trim();//��ȡҪ���͵���Ϣ
    	byte[] message_send = message.getBytes();       
        try {          
            out.write(message_send);//��Ϣͨ��out����������  
            StringBuffer sbuf = new StringBuffer(message.length());//�ڱ��ͻ�������Ի���¼
            sbuf.append(String.valueOf(message));
            Win.Dialog.append("userA:"+sbuf+"\n");
            Win.Message.setText("");//����֮��Ѵ����͵���Ϣ�����
        } catch (Exception Ex) {}
    }
    @Override
    public void run() {//�������Է����������ݱ�������port����AB�����ݱ�        
        DatagramPacket pack = null;
        DatagramSocket mail = null;
        byte[] message_receive = new byte[1024];
        try {
            pack = new DatagramPacket(message_receive, message_receive.length);//������Ϣ
            mail = new DatagramSocket(1002);//B�˵Ķ˿ں�
        } catch (Exception Ex) {}

        //����Ƿ������ݱ����룬������ܷ��벢��ʾ���ı���
        while(true){
            try{
                mail.receive(pack);
                String message=new String(pack.getData(),0,pack.getLength());
                Win.Dialog.append("userB:"+message+"\n");//�����Ϣ
            }catch (Exception Ex){
            }
        }
    }
    public static void main(String args[]){
        ClientA userA=new ClientA();
    }
}
