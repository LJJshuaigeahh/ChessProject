package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class KingChessComponent extends ChessComponent {

    private static Image KING_WHITE;
    private static Image KING_BLACK;

    private Image kingImage;

    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
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
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (getChessColor() == ChessColor.BLACK) {
            if ((Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 1) || (Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 0) || (Math.abs(destination.getX() - source.getX()) == 0 && Math.abs(destination.getY() - source.getY()) == 1)) {
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
            if ((Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 1) || (Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 0) || (Math.abs(destination.getX() - source.getX()) == 0 && Math.abs(destination.getY() - source.getY()) == 1)) {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
