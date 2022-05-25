import view.ChessGameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            建立一个新窗口
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 760);
//            窗口可见
            mainFrame.setVisible(true);
        });
    }
}
