package hw12;
import javax.swing.*;
import java.awt.*;

public class Windows extends JFrame {     
     public JTextArea Dialog;//�Ի���     
     public JTextField Message;//�����͵���Ϣ��
     public JButton send;//���Ͱ�ť
     public JScrollPane scroll;//������
     Windows(String name){ //����
         super(name);        
         Message=new JTextField(20);
         JPanel jp=new JPanel();
         send=new JButton("����");
         jp.add(Message);
         jp.add(send);
         scroll=new JScrollPane(Dialog);
         add(scroll);
         Dialog=new JTextArea(10,20);
         add(new JScrollPane(Dialog), BorderLayout.NORTH);
         add(jp);

         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(320,260);
         setVisible(true);
     }
}
