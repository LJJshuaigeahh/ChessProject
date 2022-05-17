package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KingChessComponent extends ChessComponent {

    private static Image KING_WHITE;
    private static Image KING_BLACK;


    private Image kingImage;

    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/棋子二/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/棋子二/king-black.png"));
        }
    }

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateRookImage(color);
        highLightChess(this);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (getChessColor() == ChessColor.BLACK) {
            if (((Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 1) || (Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 0) || (Math.abs(destination.getX() - source.getX()) == 0 && Math.abs(destination.getY() - source.getY()) == 1)) && chessComponents[destination.getX()][destination.getY()].getChessColor() != ChessColor.BLACK) {
                boolean test = true;
                out:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessComponent[][] chessComponents1 = chessComponents;
                        chessComponents1[destination.getX()][destination.getY()] = new KingChessComponent(getChessboardPoint(), getLocation(), getChessColor(), getClickController(), 0);
                        if (chessComponents1[i][j].getChessColor() == ChessColor.WHITE && chessComponents1[i][j].canMoveTo(chessComponents, destination)) {
                            test = false;
                            break out;
                        }
                    }
                }
                return test;
            } else {
                return false;
            }
        } else {
            if (((Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 1) || (Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 0) || (Math.abs(destination.getX() - source.getX()) == 0 && Math.abs(destination.getY() - source.getY()) == 1)) && chessComponents[destination.getX()][destination.getY()].getChessColor() != ChessColor.WHITE) {
                boolean test = true;
                out:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, destination)) {
                            test = false;
                            break out;
                        }
                    }
                }
                return test;
            } else {
                return false;
            }
        }
    }

    //    public List<ChessboardPoint> canMoveToList(ChessComponent[][] chessComponents) {
//        ArrayList<ChessboardPoint> canMoveTo = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                ChessboardPoint chessboardPoint = new ChessboardPoint(i, j);
//                if (canMoveTo(chessComponents, chessboardPoint)) {
//                    canMoveTo.add(chessboardPoint);
//                }
//            }
//        }
//        return canMoveTo;
////        if (getChessColor() == ChessColor.BLACK) {
////            if (getChessboardPoint().offset(1, 1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() + 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() + 1));
////                }
////            }
////            if (getChessboardPoint().offset(1, -1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() - 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() - 1));
////                }
////            }
////            if (getChessboardPoint().offset(-1, 1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() + 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() + 1));
////                }
////            }
////            if (getChessboardPoint().offset(-1, -1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() - 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() - 1));
////                }
////            }
////            if (getChessboardPoint().offset(1, 0) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY()))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY()));
////                }
////            }
////            if (getChessboardPoint().offset(-1, 0) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY()))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY()));
////                }
////            }
////            if (getChessboardPoint().offset(0, 1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX(), getChessboardPoint().getY() + 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX(), getChessboardPoint().getY() + 1));
////                }
////            }
////            if (getChessboardPoint().offset(0, -1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX(), getChessboardPoint().getY() - 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX(), getChessboardPoint().getY() - 1));
////                }
////            }
////        } else if (getChessColor() == ChessColor.WHITE) {
////            if (getChessboardPoint().offset(1, 1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() + 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() + 1));
////                }
////            }
////            if (getChessboardPoint().offset(1, -1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() - 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY() - 1));
////                }
////            }
////            if (getChessboardPoint().offset(-1, 1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() + 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() + 1));
////                }
////            }
////            if (getChessboardPoint().offset(-1, -1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() - 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY() - 1));
////                }
////            }
////            if (getChessboardPoint().offset(1, 0) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY()))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() + 1, getChessboardPoint().getY()));
////                }
////            }
////            if (getChessboardPoint().offset(-1, 0) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY()))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX() - 1, getChessboardPoint().getY()));
////                }
////            }
////            if (getChessboardPoint().offset(0, 1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX(), getChessboardPoint().getY() + 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX(), getChessboardPoint().getY() + 1));
////                }
////            }
////            if (getChessboardPoint().offset(0, -1) != null) {
////                if (chessComponents[getChessboardPoint().getX()][getChessboardPoint().getY()].canMoveTo(chessComponents, new ChessboardPoint(getChessboardPoint().getX(), getChessboardPoint().getY() - 1))) {
////                    canMoveTo.add(new ChessboardPoint(getChessboardPoint().getX(), getChessboardPoint().getY() - 1));
////                }
////            }
////        }
//    }
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, getWidth() / 10, getHeight() / 5, getWidth() * 4 / 5, getHeight() * 3 / 5, this);
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
}
