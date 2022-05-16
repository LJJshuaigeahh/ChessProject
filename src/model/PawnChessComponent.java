package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PawnChessComponent extends ChessComponent {

    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;

    private Image pawnImage;

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/棋子二/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/棋子二/pawn-black.png"));
        }
    }

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateRookImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (getChessColor() == ChessColor.BLACK) {
            if (source.getX() == 1) {
                if (destination.getX() == source.getX() + 1 && destination.getY() == source.getY() && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return true;
                } else if (destination.getX() == source.getX() + 2 && destination.getY() == source.getY() && chessComponents[destination.getX() - 1][destination.getY()] instanceof EmptySlotComponent && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return true;
                }
            } else {
                if (destination.getX() == source.getX() + 1 && destination.getY() == source.getY() && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return true;
                }
            }
            if (chessComponents[destination.getX()][destination.getY()].getChessColor() == ChessColor.WHITE) {
                if (source.getX() + 1 == destination.getX() && source.getY() + 1 == destination.getY()) {
                    return true;
                } else if (source.getX() + 1 == destination.getX() && source.getY() - 1 == destination.getY()) {
                    return true;
                }
            }
            if (source.getY() != 0 && source.getY() != 7) {
                if (source.getX() == 4 && ((chessComponents[source.getX()][source.getY() + 1] instanceof PawnChessComponent && chessComponents[source.getX()][source.getY() + 1].getChessColor() == ChessColor.WHITE) || (chessComponents[source.getX()][source.getY() - 1] instanceof PawnChessComponent && chessComponents[source.getX()][source.getY() - 1].getChessColor() == ChessColor.WHITE))) {
                    if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                        if (source.getX() + 1 == destination.getX() && source.getY() + 1 == destination.getY()) {
                            return true;
                        } else if (source.getX() + 1 == destination.getX() && source.getY() - 1 == destination.getY()) {
                            return true;
                        }
                    }
                }
            }
        } else {
            if (source.getX() == 6) {
                if (destination.getX() == source.getX() - 1 && destination.getY() == source.getY() && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return true;

                } else if (destination.getX() == source.getX() - 2 && destination.getY() == source.getY() && chessComponents[destination.getX() + 1][destination.getY()] instanceof EmptySlotComponent && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return true;
                }
            } else {
                if (destination.getX() == source.getX() - 1 && destination.getY() == source.getY() && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return true;
                }
            }
            if (chessComponents[destination.getX()][destination.getY()].getChessColor() == ChessColor.BLACK) {
                if (source.getX() - 1 == destination.getX() && source.getY() - 1 == destination.getY()) {
                    return true;
                } else if (source.getX() - 1 == destination.getX() && source.getY() + 1 == destination.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, getWidth() / 10, getHeight() / 5, getWidth() * 4 / 5, getHeight() * 3 / 5, this);
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
