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

    private ArrayList<List<String>> recordChessBoard = new ArrayList<>();

    public void setRecordChessBoard(ArrayList<List<String>> recordChessBoard) {
        this.recordChessBoard = recordChessBoard;
    }

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
        highLightChess(this);
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
            if (source.getX() == 4) {
                if (source.getY() != 0 && source.getY() != 7) {
                    if (recordChessBoard.get(recordChessBoard.size() - 1).get(source.getX()).charAt(source.getY() + 1) == 'p' && recordChessBoard.get(recordChessBoard.size() - 2).get(source.getX() + 2).charAt(source.getY() + 1) == 'p') {
                        if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                            if (source.getX() + 1 == destination.getX() && source.getY() + 1 == destination.getY()) {
                                return true;
                            }
                        }
                    }
                    if (recordChessBoard.get(recordChessBoard.size() - 1).get(source.getX()).charAt(source.getY() - 1) == 'p' && recordChessBoard.get(recordChessBoard.size() - 2).get(source.getX() + 2).charAt(source.getY() - 1) == 'p') {
                        if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                            return source.getX() + 1 == destination.getX() && source.getY() - 1 == destination.getY();
                        }
                    }
                } else if (source.getY() == 0) {
                    if (recordChessBoard.get(recordChessBoard.size() - 1).get(source.getX()).charAt(source.getY() + 1) == 'p' && recordChessBoard.get(recordChessBoard.size() - 2).get(source.getX() + 2).charAt(source.getY() + 1) == 'p') {
                        if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                            return source.getX() + 1 == destination.getX() && source.getY() + 1 == destination.getY();
                        }
                    }
                } else if (source.getY() == 7) {
                    if (recordChessBoard.get(recordChessBoard.size() - 1).get(source.getX()).charAt(source.getY() - 1) == 'p' && recordChessBoard.get(recordChessBoard.size() - 2).get(source.getX() + 2).charAt(source.getY() - 1) == 'p') {
                        if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                            return source.getX() + 1 == destination.getX() && source.getY() - 1 == destination.getY();
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
            if (source.getX() == 3) {
                if (source.getY() != 0 && source.getY() != 7) {
                    if (recordChessBoard.get(recordChessBoard.size() - 1).get(source.getX()).charAt(source.getY() + 1) == 'P' && recordChessBoard.get(recordChessBoard.size() - 2).get(source.getX() - 2).charAt(source.getY() + 1) == 'P') {
                        if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                            if (source.getX() - 1 == destination.getX() && source.getY() + 1 == destination.getY()) {
                                return true;
                            }
                        }
                    }
                    if (recordChessBoard.get(recordChessBoard.size() - 1).get(source.getX()).charAt(source.getY() - 1) == 'P' && recordChessBoard.get(recordChessBoard.size() - 2).get(source.getX() - 2).charAt(source.getY() - 1) == 'P') {
                        if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                            return source.getX() - 1 == destination.getX() && source.getY() - 1 == destination.getY();
                        }
                    }
                } else if (source.getY() == 0) {
                    if (recordChessBoard.get(recordChessBoard.size() - 1).get(source.getX()).charAt(source.getY() + 1) == 'P' && recordChessBoard.get(recordChessBoard.size() - 2).get(source.getX() - 2).charAt(source.getY() + 1) == 'P') {
                        if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                            return source.getX() - 1 == destination.getX() && source.getY() + 1 == destination.getY();
                        }
                    }
                } else if (source.getY() == 7) {
                    if (recordChessBoard.get(recordChessBoard.size() - 1).get(source.getX()).charAt(source.getY() - 1) == 'P' && recordChessBoard.get(recordChessBoard.size() - 2).get(source.getX() - 2).charAt(source.getY() - 1) == 'P') {
                        if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                            return source.getX() - 1 == destination.getX() && source.getY() - 1 == destination.getY();
                        }
                    }
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
