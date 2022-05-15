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

    public ArrayList<List<String>> getRecordChessBoard() {
        return recordChessBoard;
    }

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }

    private float count = 1;
    private ArrayList<List<String>> recordChessBoard = new ArrayList<>();

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
        recordChessBoard.add(getChessStringList2());
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


//        if (tellDefeat(chess1.getChessColor())) {
//            JOptionPane.showMessageDialog(this, chess1.getChessColor().getName() + "  wins!!");
//        }

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
        recordChessBoard = new ArrayList<>();
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((int) chessData.get(i).charAt(j) >= 65 && (int) chessData.get(i).charAt(j) <= 90) {
                    switch (chessData.get(i).charAt(j)) {
                        case 'R':
                            initRookOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'N':
                            initKnightOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'B':
                            initBishopOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'Q':
                            initQueenOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'K':
                            initKingOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'P':
                            initPawnOnBoard(i, j, ChessColor.BLACK);
                            break;
                    }
                } else if ((int) chessData.get(i).charAt(j) >= 97 && (int) chessData.get(i).charAt(j) <= 122) {
                    switch (chessData.get(i).charAt(j)) {
                        case 'r':
                            initRookOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'n':
                            initKnightOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'b':
                            initBishopOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'q':
                            initQueenOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'k':
                            initKingOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'p':
                            initPawnOnBoard(i, j, ChessColor.WHITE);
                            break;
                    }
                }
                chessComponents[i][j].repaint();
            }
        }
        recordChessBoard.add(getChessStringList2());
        if (chessData.get(8).equals("w")) {
            currentColor = ChessColor.WHITE;
        } else {
            currentColor = ChessColor.BLACK;
        }
        count = 1;
        JLabel jLabel = (JLabel) this.getParent().getComponent(1);
        jLabel.setText("第1回合  " + "It's " + this.getCurrentColor().getName() + "'s turn.");
//        chessData.forEach(System.out::println);
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

    public void backChess2() {
        if (recordChessBoard.size() != 1) {
            loadGame2(recordChessBoard.get(recordChessBoard.size() - 2));
            recordChessBoard.remove(recordChessBoard.size() - 1);
        }else {
            JOptionPane.showMessageDialog(null, "It's the first step of the chess now!!", "悔棋失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<String> getChessStringList2() {
        List<String> chessStringList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                    if (chessComponents[i][j] instanceof RookChessComponent) {
                        sb.append('R');
                    } else if (chessComponents[i][j] instanceof KnightChessComponent) {
                        sb.append('N');
                    } else if (chessComponents[i][j] instanceof BishopChessComponent) {
                        sb.append('B');
                    } else if (chessComponents[i][j] instanceof QueenChessComponent) {
                        sb.append('Q');
                    } else if (chessComponents[i][j] instanceof KingChessComponent) {
                        sb.append('K');
                    } else if (chessComponents[i][j] instanceof PawnChessComponent) {
                        sb.append('P');
                    }
                } else if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                    if (chessComponents[i][j] instanceof RookChessComponent) {
                        sb.append('r');
                    } else if (chessComponents[i][j] instanceof KnightChessComponent) {
                        sb.append('n');
                    } else if (chessComponents[i][j] instanceof BishopChessComponent) {
                        sb.append('b');
                    } else if (chessComponents[i][j] instanceof QueenChessComponent) {
                        sb.append('q');
                    } else if (chessComponents[i][j] instanceof KingChessComponent) {
                        sb.append('k');
                    } else if (chessComponents[i][j] instanceof PawnChessComponent) {
                        sb.append('p');
                    }
                } else {
                    sb.append('_');
                }
            }
            chessStringList.add(sb.toString() + "\n");
        }
        if (currentColor == ChessColor.BLACK) {
            chessStringList.add("b\n");
        } else {
            chessStringList.add("w\n");
        }
        return chessStringList;
    }

    public void loadGame2(List<String> chessData) {
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((int) chessData.get(i).charAt(j) >= 65 && (int) chessData.get(i).charAt(j) <= 90) {
                    switch (chessData.get(i).charAt(j)) {
                        case 'R':
                            initRookOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'N':
                            initKnightOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'B':
                            initBishopOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'Q':
                            initQueenOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'K':
                            initKingOnBoard(i, j, ChessColor.BLACK);
                            break;
                        case 'P':
                            initPawnOnBoard(i, j, ChessColor.BLACK);
                            break;
                    }
                } else if ((int) chessData.get(i).charAt(j) >= 97 && (int) chessData.get(i).charAt(j) <= 122) {
                    switch (chessData.get(i).charAt(j)) {
                        case 'r':
                            initRookOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'n':
                            initKnightOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'b':
                            initBishopOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'q':
                            initQueenOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'k':
                            initKingOnBoard(i, j, ChessColor.WHITE);
                            break;
                        case 'p':
                            initPawnOnBoard(i, j, ChessColor.WHITE);
                            break;
                    }
                }
                chessComponents[i][j].repaint();
            }
        }
        if (chessData.get(8).equals("w\n")) {
            currentColor = ChessColor.WHITE;
        } else {
            currentColor = ChessColor.BLACK;
        }
        count -= 0.5;
        JLabel jLabel = (JLabel) this.getParent().getComponent(1);
        jLabel.setText("第" + (int) count + "回合  " + "It's " + currentColor.getName() + "'s turn.");
//        chessData.forEach(System.out::println);
    }


}
