package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueenChessComponent extends ChessComponent {

    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    private Image queenImage;

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/棋子二/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/棋子二/queen-black.png"));
        }
    }

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateRookImage(color);
        highLightChess(this);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (chessComponents[destination.getX()][destination.getY()].getChessColor() != chessColor) {
            if (source.getX() == destination.getX()) {
                int row = source.getX();
                for (int col = Math.min(source.getY(), destination.getY()) + 1;
                     col < Math.max(source.getY(), destination.getY()); col++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else if (source.getY() == destination.getY()) {
                int col = source.getY();
                for (int row = Math.min(source.getX(), destination.getX()) + 1;
                     row < Math.max(source.getX(), destination.getX()); row++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else if (Math.abs(destination.getX() - source.getX()) == Math.abs(destination.getY() - source.getY())) {
                for (int i = 1; i < Math.abs(destination.getX() - source.getX()); i++) {
                    int row = source.getX();
                    int col = source.getY();
                    if (destination.getX() - source.getX() > 0 && destination.getY() - source.getY() > 0 && !(chessComponents[row + i][col + i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                    if (destination.getX() - source.getX() > 0 && destination.getY() - source.getY() < 0 && !(chessComponents[row + i][col - i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                    if (destination.getX() - source.getX() < 0 && destination.getY() - source.getY() > 0 && !(chessComponents[row - i][col + i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                    if (destination.getX() - source.getX() < 0 && destination.getY() - source.getY() < 0 && !(chessComponents[row - i][col - i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else {
                return false;
            }

            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, getWidth() / 10, getHeight() / 5, getWidth() * 4 / 5, getHeight() * 3 / 5,  this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            Image image = null;
            if (chessColor==ChessColor.BLACK){
                try {
                    image = ImageIO.read(new File("./images/虚线框2.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (chessColor==ChessColor.WHITE){
                try {
                    image = ImageIO.read(new File("./images/虚线框.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            g.drawImage(image, getWidth() / 10, getHeight() / 10, getWidth() * 4 / 5, getHeight() * 4 / 5, this);
//            g.setColor(Color.RED);
//            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }


    public List<ChessComponent> canMoveToList(ChessComponent[][] chessComponents) {
        ArrayList<ChessComponent> canMoveTo = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessboardPoint chessboardPoint = new ChessboardPoint(i, j);
                if (canMoveTo(chessComponents, chessboardPoint)) {
                    canMoveTo.add(chessComponents[i][j]);
                }
            }
        }
        return canMoveTo;
    }
}
