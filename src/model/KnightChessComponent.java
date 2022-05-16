package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnightChessComponent extends ChessComponent {

    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;

    private Image knightImage;

    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("./images/棋子二/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/棋子二/knight-black.png"));
        }
    }

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateRookImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        ChessColor color = getChessColor();
        if (destination.getX() - source.getX() == 2 && destination.getY() - source.getY() == 1 && chessComponents[destination.getX()][destination.getY()].getChessColor() != color) {
            return true;
        }
        if (destination.getX() - source.getX() == 1 && destination.getY() - source.getY() == 2 && chessComponents[destination.getX()][destination.getY()].getChessColor() != color) {
            return true;
        }
        if (destination.getX() - source.getX() == 2 && destination.getY() - source.getY() == -1 && chessComponents[destination.getX()][destination.getY()].getChessColor() != color) {
            return true;
        }
        if (destination.getX() - source.getX() == -2 && destination.getY() - source.getY() == 1 && chessComponents[destination.getX()][destination.getY()].getChessColor() != color) {
            return true;
        }
        if (destination.getX() - source.getX() == 1 && destination.getY() - source.getY() == -2 && chessComponents[destination.getX()][destination.getY()].getChessColor() != color) {
            return true;
        }
        if (destination.getX() - source.getX() == -1 && destination.getY() - source.getY() == 2 && chessComponents[destination.getX()][destination.getY()].getChessColor() != color) {
            return true;
        }
        if (destination.getX() - source.getX() == -2 && destination.getY() - source.getY() == -1 && chessComponents[destination.getX()][destination.getY()].getChessColor() != color) {
            return true;
        }
        if (destination.getX() - source.getX() == -1 && destination.getY() - source.getY() == -2 && chessComponents[destination.getX()][destination.getY()].getChessColor() != color) {
            return true;
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(knightImage, getWidth() / 10, getHeight() / 5, getWidth() * 4 / 5, getHeight() * 3 / 5, this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }

    public List<ChessboardPoint> canMoveToList(ChessComponent[][] chessComponents) {
        return new ArrayList<>();
    }
}
