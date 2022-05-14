import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
//        String path = "resource/game5.txt";
//        System.out.println(path.substring(path.length() - 4));
//        ArrayList<Integer> recordChessComponents = new ArrayList<>();
//        recordChessComponents.add(1);
//        recordChessComponents.add(2);
//        recordChessComponents.add(3);
//        recordChessComponents.add(4);
//        ArrayList<Integer> recordChessComponents1 = new ArrayList<>();
//        recordChessComponents1.add(1);
//        recordChessComponents.remove(recordChessComponents1);
//        for (int i = 0; i < recordChessComponents.size(); i++) {
//            System.out.println(recordChessComponents.get(i));
//        }
        JFrame J = new JFrame("这是窗口");
        JPanel p = new JPanel();
        J.add(p);
//界面设置
        J.setBounds(1000, 200, 400, 500);
        p.setSize(J.getWidth(),J.getHeight());
        J.setVisible(true);
//        p.setSize(J.getWidth(), J.getHeight());
//        p.setLayout(null);
        J.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("这是按钮");
//        JTextArea area = new JTextArea("这是文本框");
        p.add(button);
//        p.add(area);
        button.setBounds(0, 0, p.getWidth() / 2, p.getHeight() / 2);
//        area.setBounds(0, J.getHeight() - 50, J.getWidth() / 2, J.getHeight() / 2);

        J.addComponentListener(new ComponentListener() {
            //当窗口大小改变时
            public void componentResized(ComponentEvent e) {
                //获取窗口的大小
                Dimension d = J.getSize();

                //设置组件的大小
                p.setBounds(0, 0, d.width, d.height);
//                area.setBounds(0, d.height - 50, d.width, d.height - 20);
            }

            //当窗口位置改变时
            public void componentMoved(ComponentEvent e) {
                // TODO Auto-generated method stub

            }

            //当窗口可见时
            public void componentShown(ComponentEvent e) {
                // TODO Auto-generated method stub

            }

            //当窗口不可见时
            public void componentHidden(ComponentEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }
}
//97  122  65  90