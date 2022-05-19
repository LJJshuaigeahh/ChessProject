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
//            窗口可见
            mainFrame.setVisible(true);
        });
    }
}
