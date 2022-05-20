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

    private ArrayList<List<String>> recordChessBoard = new ArrayList<>();

    public void setRecordChessBoard(ArrayList<List<String>> recordChessBoard) {
        this.recordChessBoard = recordChessBoard;
    }

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
        ChessComponent[][] chessComponentsTest = new ChessComponent[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(chessComponents[i], 0, chessComponentsTest[i], 0, 8);
        }
        if (getChessColor() == ChessColor.BLACK) {
            if (((Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 1) || (Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 0) || (Math.abs(destination.getX() - source.getX()) == 0 && Math.abs(destination.getY() - source.getY()) == 1)) && chessComponents[destination.getX()][destination.getY()].getChessColor() != ChessColor.BLACK) {
                boolean test = true;
                out:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessboardPoint chessboardPoint = chessComponentsTest[destination.getX()][destination.getY()].getChessboardPoint();
                        Point point = chessComponentsTest[destination.getX()][destination.getY()].getLocation();
                        chessComponentsTest[destination.getX()][destination.getY()] = new KingChessComponent(chessboardPoint, point, getChessColor(), getClickController(), 0);
                        chessComponentsTest[source.getX()][source.getY()] = new EmptySlotComponent(getChessboardPoint(), getLocation(), getClickController(), 0);
                        if (chessComponentsTest[i][j].getChessColor() == ChessColor.WHITE && chessComponentsTest[i][j].canMoveTo(chessComponentsTest, destination)) {
                            test = false;
                            break out;
                        }
                    }
                }
                return test;
            } else if (destination.getX() == 0 && destination.getY() == 2) {
                boolean test = true;
                out:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, destination)) {
                            test = false;
                            break out;
                        }
                    }
                }
                if (test) {
                    boolean test1 = true;
                    out:
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            ChessboardPoint chessboardPoint = chessComponentsTest[0][2].getChessboardPoint();
                            Point point = chessComponentsTest[0][2].getLocation();
                            chessComponentsTest[0][2] = new KingChessComponent(chessboardPoint, point, getChessColor(), getClickController(), 0);
                            chessComponentsTest[source.getX()][source.getY()] = new EmptySlotComponent(getChessboardPoint(), getLocation(), getClickController(), 0);
                            if (chessComponentsTest[i][j].getChessColor() == ChessColor.WHITE && chessComponentsTest[i][j].canMoveTo(chessComponentsTest, destination)) {
                                test1 = false;
                                break out;
                            }
                        }
                    }
                    if (test1) {
                        if (recordChessBoard.get(recordChessBoard.size() - 1).get(0).charAt(1) == '_' && recordChessBoard.get(recordChessBoard.size() - 1).get(0).charAt(2) == '_' && recordChessBoard.get(recordChessBoard.size() - 1).get(0).charAt(3) == '_') {
                            boolean test2 = true;
                            for (int i = recordChessBoard.size() - 1; i >= 0; i--) {
                                if (recordChessBoard.get(i).get(0).charAt(4) == 'K' || recordChessBoard.get(i).get(0).charAt(0) == 'R') {
                                    test2 = false;
                                    break;
                                }
                            }
                            return test2;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (destination.getX() == 0 && destination.getY() == 6) {
                boolean test = true;
                out:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, destination)) {
                            test = false;
                            break out;
                        }
                    }
                }
                if (test) {
                    boolean test1 = true;
                    out:
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            ChessboardPoint chessboardPoint = chessComponentsTest[0][6].getChessboardPoint();
                            Point point = chessComponentsTest[0][6].getLocation();
                            chessComponentsTest[0][6] = new KingChessComponent(chessboardPoint, point, getChessColor(), getClickController(), 0);
                            chessComponentsTest[source.getX()][source.getY()] = new EmptySlotComponent(getChessboardPoint(), getLocation(), getClickController(), 0);
                            if (chessComponentsTest[i][j].getChessColor() == ChessColor.WHITE && chessComponentsTest[i][j].canMoveTo(chessComponentsTest, destination)) {
                                test1 = false;
                                break out;
                            }
                        }
                    }
                    if (test1) {
                        if (recordChessBoard.get(recordChessBoard.size() - 1).get(0).charAt(5) == '_' && recordChessBoard.get(recordChessBoard.size() - 1).get(0).charAt(6) == '_') {
                            boolean test2 = true;
                            for (int i = recordChessBoard.size() - 1; i >= 0; i--) {
                                if (recordChessBoard.get(i).get(0).charAt(4) == 'K' || recordChessBoard.get(i).get(0).charAt(7) == 'R') {
                                    test2 = false;
                                    break;
                                }
                            }
                            return test2;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            if (((Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 1) || (Math.abs(destination.getX() - source.getX()) == 1 && Math.abs(destination.getY() - source.getY()) == 0) || (Math.abs(destination.getX() - source.getX()) == 0 && Math.abs(destination.getY() - source.getY()) == 1)) && chessComponents[destination.getX()][destination.getY()].getChessColor() != ChessColor.WHITE) {
                boolean test = true;
                out:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessboardPoint chessboardPoint = chessComponentsTest[destination.getX()][destination.getY()].getChessboardPoint();
                        Point point = chessComponentsTest[destination.getX()][destination.getY()].getLocation();
                        chessComponentsTest[destination.getX()][destination.getY()] = new KingChessComponent(chessboardPoint, point, getChessColor(), getClickController(), 0);
                        chessComponentsTest[source.getX()][source.getY()] = new EmptySlotComponent(getChessboardPoint(), getLocation(), getClickController(), 0);
                        if (chessComponentsTest[i][j].getChessColor() == ChessColor.BLACK && chessComponentsTest[i][j].canMoveTo(chessComponentsTest, destination)) {
                            test = false;
                            break out;
                        }
                    }
                }
                return test;
            } else if (destination.getX() == 7 && destination.getY() == 2) {
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
                if (test) {
                    boolean test1 = true;
                    out:
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            ChessboardPoint chessboardPoint = chessComponentsTest[7][2].getChessboardPoint();
                            Point point = chessComponentsTest[7][2].getLocation();
                            chessComponentsTest[7][2] = new KingChessComponent(chessboardPoint, point, getChessColor(), getClickController(), 0);
                            chessComponentsTest[source.getX()][source.getY()] = new EmptySlotComponent(getChessboardPoint(), getLocation(), getClickController(), 0);
                            if (chessComponentsTest[i][j].getChessColor() == ChessColor.BLACK && chessComponentsTest[i][j].canMoveTo(chessComponentsTest, destination)) {
                                test1 = false;
                                break out;
                            }
                        }
                    }
                    if (test1) {
                        if (recordChessBoard.get(recordChessBoard.size() - 1).get(7).charAt(1) == '_' && recordChessBoard.get(recordChessBoard.size() - 1).get(7).charAt(2) == '_' && recordChessBoard.get(recordChessBoard.size() - 1).get(7).charAt(3) == '_') {
                            boolean test2 = true;
                            for (int i = recordChessBoard.size() - 1; i >= 0; i--) {
                                if (recordChessBoard.get(i).get(7).charAt(4) == 'k' || recordChessBoard.get(i).get(7).charAt(0) == 'r') {
                                    test2 = false;
                                    break;
                                }
                            }
                            return test2;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (destination.getX() == 7 && destination.getY() == 6) {
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
                if (test) {
                    boolean test1 = true;
                    out:
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            ChessboardPoint chessboardPoint = chessComponentsTest[7][6].getChessboardPoint();
                            Point point = chessComponentsTest[7][6].getLocation();
                            chessComponentsTest[7][6] = new KingChessComponent(chessboardPoint, point, getChessColor(), getClickController(), 0);
                            chessComponentsTest[source.getX()][source.getY()] = new EmptySlotComponent(getChessboardPoint(), getLocation(), getClickController(), 0);
                            if (chessComponentsTest[i][j].getChessColor() == ChessColor.BLACK && chessComponentsTest[i][j].canMoveTo(chessComponentsTest, destination)) {
                                test1 = false;
                                break out;
                            }
                        }
                    }
                    if (test1) {
                        if (recordChessBoard.get(recordChessBoard.size() - 1).get(7).charAt(5) == '_' && recordChessBoard.get(recordChessBoard.size() - 1).get(7).charAt(6) == '_') {
                            boolean test2 = true;
                            for (int i = recordChessBoard.size() - 1; i >= 0; i--) {
                                if (recordChessBoard.get(i).get(7).charAt(4) == 'k' || recordChessBoard.get(i).get(7).charAt(7) == 'r') {
                                    test2 = false;
                                    break;
                                }
                            }
                            return test2;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(kingImage, getWidth() / 10, getHeight() / 5, getWidth() * 4 / 5, getHeight() * 3 / 5, this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            Image image = null;
            if (chessColor == ChessColor.BLACK) {
                try {
                    image = ImageIO.read(new File("./images/虚线框2.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (chessColor == ChessColor.WHITE) {
                try {
                    image = ImageIO.read(new File("./images/虚线框.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            g.drawImage(image, getWidth() / 10, getHeight() / 10, getWidth() * 4 / 5, getHeight() * 4 / 5, this);
        }
    }
}
