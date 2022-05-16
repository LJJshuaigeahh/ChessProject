package view;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示棋盘上的位置，如(0, 0), (0, 7)等等
 * 其中，左上角是(0, 0)，左下角是(7, 0)，右上角是(0, 7)，右下角是(7, 7)
 */
public class ChessboardPoint extends JComponent {
    private int x, y;

    public ChessboardPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ") " + "on the chessboard is clicked!";
    }

    //    判断走棋越不越界
    public ChessboardPoint offset(int dx, int dy) {
        int x_ = x + dx;
        int y_ = y + dy;
        if (x_ >= 0 && x_ <= 7 && y_ >= 0 && y_ <= 7) {
            return new ChessboardPoint(x_, y_);
        } else {
            return null;
        }
    }
}
