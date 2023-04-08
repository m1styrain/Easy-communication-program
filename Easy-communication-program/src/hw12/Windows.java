package hw12;
import javax.swing.*;
import java.awt.*;

public class Windows extends JFrame {     
     public JTextArea Dialog;//对话框     
     public JTextField Message;//待发送的信息框
     public JButton send;//发送按钮
     public JScrollPane scroll;//滚动条
     Windows(String name){ //窗口
         super(name);        
         Message=new JTextField(20);
         JPanel jp=new JPanel();
         send=new JButton("发送");
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
