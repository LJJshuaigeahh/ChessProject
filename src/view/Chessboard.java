package view;


import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private float count = 1;

    public float getCount() {
        return count;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initKingOnBoard(0, CHESSBOARD_SIZE - 4, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 4, ChessColor.WHITE);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        for (int i = 0; i < 4; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK);
            initPawnOnBoard(1, CHESSBOARD_SIZE - 1 - i, ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, CHESSBOARD_SIZE - 1 - i, ChessColor.WHITE);
        }
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }


    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();

        count += 0.5;

//        if (chess1.getChessColor() == ChessColor.BLACK) {
//            if (tellDefeat(ChessColor.WHITE)) {
//                JOptionPane.showMessageDialog(this, ChessColor.WHITE.getName() + "  wins!!");
//            }
//            if (tellDefeat(ChessColor.BLACK)) {
//                JOptionPane.showMessageDialog(this, ChessColor.BLACK.getName() + "  wins!!");
//            }
//        } else {
//            if (tellDefeat(ChessColor.BLACK)) {
//                JOptionPane.showMessageDialog(this, ChessColor.BLACK.getName() + "  wins!!");
//            }
//            if (tellDefeat(ChessColor.WHITE)) {
//                JOptionPane.showMessageDialog(this, ChessColor.WHITE.getName() + "  wins!!");
//            }
//        }
        if (tellDefeat(chess1.getChessColor())) {
            JOptionPane.showMessageDialog(this, chess1.getChessColor().getName() + "  wins!!");
        }

    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }

    public boolean tellDefeat(ChessColor color) {
        if (color == ChessColor.BLACK) {
            int kingI = 0;
            int kingJ = 0;
            int killerI = 0;
            int killerJ = 0;
            out:
            for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                    if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        kingI = i;
                        kingJ = j;
                        break out;
                    }
                }
            }
            boolean test = false;
            out:
            for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                    if (chessComponents[i][j].getChessColor() == color && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(kingI, kingJ))) {
                        test = true;
                        killerI = i;
                        killerJ = j;
                        break out;
                    }
                }
            }
            if (test) {
                if (chessComponents[killerI][killerJ] instanceof PawnChessComponent || chessComponents[killerI][killerJ] instanceof KnightChessComponent) {
                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
                        boolean test1 = true;
                        out:
                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
                                    test1 = false;
                                    break out;
                                }
                            }
                        }
                        return test1;
                    } else {
                        return false;
                    }
                } else if (chessComponents[killerI][killerJ] instanceof RookChessComponent) {
                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
                        boolean test1 = true;
                        out:
                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
                                    test1 = false;
                                    break out;
                                }
                            }
                        }
                        if (!test1) {
                            return false;
                        } else {
                            boolean test2 = true;
                            if (killerI == kingI && killerJ - kingJ != 0) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            } else if (killerJ == kingJ && killerI - kingI != 0) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerI - kingI); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, killerJ))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            }
                            return test;
                        }
                    } else {
                        return false;
                    }
                } else if (chessComponents[killerI][killerJ] instanceof BishopChessComponent) {
                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
                        boolean test1 = true;
                        out:
                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
                                    test1 = false;
                                    break out;
                                }
                            }
                        }
                        if (!test1) {
                            return false;
                        } else {
                            boolean test2 = true;
                            if (killerI - kingI == killerJ - kingJ) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            } else {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.max(killerI, kingI) - i, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            }
                            return test;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
                        boolean test1 = true;
                        out:
                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
                                    test1 = false;
                                    break out;
                                }
                            }
                        }
                        if (!test1) {
                            return false;
                        } else {
                            boolean test2 = true;
                            if (killerI == kingI && killerJ - kingJ != 0) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            } else if (killerJ == kingJ && killerI - kingI != 0) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerI - kingI); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, killerJ))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            } else if (killerI - kingI == killerJ - kingJ) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            } else {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.max(killerI, kingI) - i, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            }
                            return test;
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            int kingI = 0;
            int kingJ = 0;
            int killerI = 0;
            int killerJ = 0;
            out:
            for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                    if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                        kingI = i;
                        kingJ = j;
                        break out;
                    }
                }
            }
            boolean test = false;
            out:
            for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                    if (chessComponents[i][j].getChessColor() == color && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(kingI, kingJ))) {
                        test = true;
                        killerI = i;
                        killerJ = j;
                        break out;
                    }
                }
            }
            if (test) {
                if (chessComponents[killerI][killerJ] instanceof PawnChessComponent || chessComponents[killerI][killerJ] instanceof KnightChessComponent) {
                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
                        boolean test1 = true;
                        out:
                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
                                    test1 = false;
                                    break out;
                                }
                            }
                        }
                        return test1;
                    } else {
                        return false;
                    }
                } else if (chessComponents[killerI][killerJ] instanceof RookChessComponent) {
                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
                        boolean test1 = true;
                        out:
                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
                                    test1 = false;
                                    break out;
                                }
                            }
                        }
                        if (!test1) {
                            return false;
                        } else {
                            boolean test2 = true;
                            if (killerI == kingI && killerJ - kingJ != 0) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            } else if (killerJ == kingJ && killerI - kingI != 0) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerI - kingI); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, killerJ))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            }
                            return test;
                        }
                    } else {
                        return false;
                    }
                } else if (chessComponents[killerI][killerJ] instanceof BishopChessComponent) {
                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
                        boolean test1 = true;
                        out:
                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
                                    test1 = false;
                                    break out;
                                }
                            }
                        }
                        if (!test1) {
                            return false;
                        } else {
                            boolean test2 = true;
                            if (killerI - kingI == killerJ - kingJ) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            } else {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.max(killerI, kingI) - i, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            }
                            return test;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
                        boolean test1 = true;
                        out:
                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
                                    test1 = false;
                                    break out;
                                }
                            }
                        }
                        if (!test1) {
                            return false;
                        } else {
                            boolean test2 = true;
                            if (killerI == kingI && killerJ - kingJ != 0) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            } else if (killerJ == kingJ && killerI - kingI != 0) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerI - kingI); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, killerJ))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            } else if (killerI - kingI == killerJ - kingJ) {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            } else {
                                out:
                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.max(killerI, kingI) - i, Math.min(killerJ, kingJ) + i))) {
                                                test2 = false;
                                                break out;
                                            }
                                        }
                                    }
                                }
                            }
                            return test;
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }

    }
}
