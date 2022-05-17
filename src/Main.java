import view.ChessGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            建立一个新窗口
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);

//            适应窗口大小
//            mainFrame.addComponentListener(new ComponentListener() {
//                //当窗口大小改变时
//                public void componentResized(ComponentEvent e) {
//                    //获取窗口的大小
//                    Dimension d = mainFrame.getSize();
//
//                    //设置组件的大小
////                    开始游戏界面
//                    mainFrame.getPanel0().setBounds(0, 0, d.width, d.height);
//                    mainFrame.getPanel0().repaint();
////                    开始游戏按钮
//                    mainFrame.getJButton1().setBounds(d.width * 2 / 5, d.height * 2 / 3, d.width / 5, d.height / 10);
//                    mainFrame.getJButton1().repaint();
//                }
//
//                //当窗口位置改变时
//                public void componentMoved(ComponentEvent e) {
//                    // TODO Auto-generated method stub
//
//                }
//
//                //当窗口可见时
//                public void componentShown(ComponentEvent e) {
//                    // TODO Auto-generated method stub
//
//                }
//
//                //当窗口不可见时
//                public void componentHidden(ComponentEvent e) {
//                    // TODO Auto-generated method stub
//
//                }
//            });

//            窗口可见
            mainFrame.setVisible(true);
        });
    }

}
