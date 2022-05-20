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
    private int CHESS_SIZE;

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

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
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
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
        initKingOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 4, ChessColor.WHITE);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        for (int i = 0; i < 4; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK);
            initPawnOnBoard(1, CHESSBOARD_SIZE - 1 - i, ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, CHESSBOARD_SIZE - 1 - i, ChessColor.WHITE);
        }
        recordChessBoard.add(getChessStringList());
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


        if (chessComponents[row][col] instanceof PawnChessComponent) {
            ((PawnChessComponent) chessComponents[row][col]).setRecordChessBoard(recordChessBoard);
        }
        if (chessComponents[row][col] instanceof KingChessComponent) {
            ((KingChessComponent) chessComponents[row][col]).setRecordChessBoard(recordChessBoard);
        }
    }

    public void setCount(float count) {
        this.count = count;
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
                        case 'R' -> initRookOnBoard(i, j, ChessColor.BLACK);
                        case 'N' -> initKnightOnBoard(i, j, ChessColor.BLACK);
                        case 'B' -> initBishopOnBoard(i, j, ChessColor.BLACK);
                        case 'Q' -> initQueenOnBoard(i, j, ChessColor.BLACK);
                        case 'K' -> initKingOnBoard(i, j, ChessColor.BLACK);
                        case 'P' -> initPawnOnBoard(i, j, ChessColor.BLACK);
                    }
                } else if ((int) chessData.get(i).charAt(j) >= 97 && (int) chessData.get(i).charAt(j) <= 122) {
                    switch (chessData.get(i).charAt(j)) {
                        case 'r' -> initRookOnBoard(i, j, ChessColor.WHITE);
                        case 'n' -> initKnightOnBoard(i, j, ChessColor.WHITE);
                        case 'b' -> initBishopOnBoard(i, j, ChessColor.WHITE);
                        case 'q' -> initQueenOnBoard(i, j, ChessColor.WHITE);
                        case 'k' -> initKingOnBoard(i, j, ChessColor.WHITE);
                        case 'p' -> initPawnOnBoard(i, j, ChessColor.WHITE);
                    }
                }
                chessComponents[i][j].repaint();
            }
        }
        recordChessBoard.add(getChessStringList());
        if (chessData.get(8).equals("w")) {
            currentColor = ChessColor.WHITE;
        } else {
            currentColor = ChessColor.BLACK;
        }
        count = 1;
        JLabel jLabel = (JLabel) this.getParent().getComponent(1);
        jLabel.setText("Round  1————" + "It's " + this.getCurrentColor().getName() + "'s turn.");

        JLabel blackCapturedChessLabel = (JLabel) this.getParent().getComponent(7);
        JLabel whiteCapturedChessLabel = (JLabel) this.getParent().getComponent(8);
        blackCapturedChessLabel.setText(this.getCapturedChess(ChessColor.BLACK));
        whiteCapturedChessLabel.setText(this.getCapturedChess(ChessColor.WHITE));
    }

    public void backChess2() {
        if (recordChessBoard.size() != 1) {
            loadGame2(recordChessBoard.get(recordChessBoard.size() - 2));
            recordChessBoard.remove(recordChessBoard.size() - 1);
        } else {
            JOptionPane.showMessageDialog(null, "It's the first step of the chess now!!", "悔棋失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<String> getChessStringList() {
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
            chessStringList.add(sb + "\n");
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
                        case 'R' -> initRookOnBoard(i, j, ChessColor.BLACK);
                        case 'N' -> initKnightOnBoard(i, j, ChessColor.BLACK);
                        case 'B' -> initBishopOnBoard(i, j, ChessColor.BLACK);
                        case 'Q' -> initQueenOnBoard(i, j, ChessColor.BLACK);
                        case 'K' -> initKingOnBoard(i, j, ChessColor.BLACK);
                        case 'P' -> initPawnOnBoard(i, j, ChessColor.BLACK);
                    }
                } else if ((int) chessData.get(i).charAt(j) >= 97 && (int) chessData.get(i).charAt(j) <= 122) {
                    switch (chessData.get(i).charAt(j)) {
                        case 'r' -> initRookOnBoard(i, j, ChessColor.WHITE);
                        case 'n' -> initKnightOnBoard(i, j, ChessColor.WHITE);
                        case 'b' -> initBishopOnBoard(i, j, ChessColor.WHITE);
                        case 'q' -> initQueenOnBoard(i, j, ChessColor.WHITE);
                        case 'k' -> initKingOnBoard(i, j, ChessColor.WHITE);
                        case 'p' -> initPawnOnBoard(i, j, ChessColor.WHITE);
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
        jLabel.setText("Round  " + (int) count + "————" + "It's " + currentColor.getName() + "'s turn.");
    }

    public void resetChessboardSize(int CHESS_SIZE) {
        this.CHESS_SIZE = CHESS_SIZE;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].setBounds(j * CHESS_SIZE, i * CHESS_SIZE, CHESS_SIZE, CHESS_SIZE);
            }
        }
    }

    public boolean tellIfKingIsAttacked(ChessColor color) {
        boolean test = false;
        String beAttackedColor;
        if (color == ChessColor.BLACK) {
            beAttackedColor = "White";
        } else {
            beAttackedColor = "Black";
        }
        int kingX = 0;
        int kingY = 0;
        out:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() != color) {
                    kingX = i;
                    kingY = j;
                    break out;
                }
            }
        }
        out:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == color && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(kingX, kingY))) {
                    JOptionPane.showMessageDialog(null, beAttackedColor + "'s king is being attacked", "王被攻击报警提示", JOptionPane.ERROR_MESSAGE);
                    test = true;
                    break out;
                }
            }
        }
        return test;
    }

    public boolean tellIfKingIsAttacked2(ChessComponent[][] chessComponents, ChessColor color) {
        boolean test = false;
        int kingX = 0;
        int kingY = 0;
        out:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() != color) {
                    kingX = i;
                    kingY = j;
                    break out;
                }
            }
        }
        out:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == color && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(kingX, kingY))) {
                    test = true;
                    break out;
                }
            }
        }
        return test;
    }

    public void moveTo(ChessComponent[][] chessComponents, ChessComponent chessComponent1, ChessComponent chessComponent2) {
        if (chessComponent2 instanceof EmptySlotComponent) {
            chessComponents[chessComponent1.getChessboardPoint().getX()][chessComponent1.getChessboardPoint().getY()] = chessComponent2;
            chessComponents[chessComponent2.getChessboardPoint().getX()][chessComponent2.getChessboardPoint().getY()] = chessComponent1;
        } else {
            chessComponents[chessComponent2.getChessboardPoint().getX()][chessComponent2.getChessboardPoint().getY()] = chessComponent1;
            chessComponents[chessComponent1.getChessboardPoint().getX()][chessComponent1.getChessboardPoint().getY()] = new EmptySlotComponent(chessComponent2.getChessboardPoint(), chessComponent2.getLocation(), clickController, CHESS_SIZE);
        }
    }

    public void moveBack(ChessComponent[][] chessComponents, ChessComponent chessComponent1, ChessComponent chessComponent2) {
        chessComponents[chessComponent1.getChessboardPoint().getX()][chessComponent1.getChessboardPoint().getY()] = chessComponent1;
        chessComponents[chessComponent2.getChessboardPoint().getX()][chessComponent2.getChessboardPoint().getY()] = chessComponent2;
    }

    public boolean tellWinOrDefeat(ChessColor color) {
        ChessComponent[][] chessComponentsTest = new ChessComponent[8][8];
        boolean test = true;
        for (int i = 0; i < 8; i++) {
            System.arraycopy(chessComponents[i], 0, chessComponentsTest[i], 0, 8);
        }
        out:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(chessComponentsTest[i][j] instanceof EmptySlotComponent) && chessComponentsTest[i][j].getChessColor() != color && chessComponentsTest[i][j].canMoveToList(chessComponentsTest).size() != 0) {
                    for (int k = 0; k < chessComponentsTest[i][j].canMoveToList(chessComponentsTest).size(); k++) {

                        ChessComponent chessComponent1 = chessComponentsTest[i][j];
                        ChessComponent chessComponent2 = chessComponentsTest[i][j].canMoveToList(chessComponentsTest).get(k);
                        moveTo(chessComponentsTest, chessComponent1, chessComponent2);
                        if (!tellIfKingIsAttacked2(chessComponentsTest, color)) {
                            test = false;
                            break out;
                        }
                        moveBack(chessComponentsTest, chessComponent1, chessComponent2);
                    }

                }
            }
        }
        return test;
    }


    public String getCapturedChess(ChessColor player) {
        if (player == ChessColor.BLACK) {
            int num1 = 0;
            int num2 = 0;
            int num3 = 0;
            int num4 = 0;
            int num5 = 0;
            int num6 = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                        num1++;
                    } else if (chessComponents[i][j] instanceof QueenChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                        num2++;
                    } else if (chessComponents[i][j] instanceof RookChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                        num3++;
                    } else if (chessComponents[i][j] instanceof BishopChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                        num4++;
                    } else if (chessComponents[i][j] instanceof KnightChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                        num5++;
                    } else if (chessComponents[i][j] instanceof PawnChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                        num6++;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            int capturedNum1 = 1 - num1;
            if (capturedNum1 != 0) {
                sb.append("K");
                sb.append(" ");
                sb.append(capturedNum1);
                sb.append("  ");
            }
            int capturedNum2 = 1 - num2;
            if (capturedNum2 != 0) {
                sb.append("Q");
                sb.append(" ");
                sb.append(capturedNum2);
                sb.append("  ");
            }
            int capturedNum3 = 2 - num3;
            if (capturedNum3 != 0) {
                sb.append("R");
                sb.append(" ");
                sb.append(capturedNum3);
                sb.append("  ");
            }
            int capturedNum4 = 2 - num4;
            if (capturedNum4 != 0) {
                sb.append("B");
                sb.append(" ");
                sb.append(capturedNum4);
                sb.append("  ");
            }
            int capturedNum5 = 2 - num5;
            if (capturedNum5 != 0) {
                sb.append("N");
                sb.append(" ");
                sb.append(capturedNum5);
                sb.append("  ");
            }
            int capturedNum6 = 8 - num6;
            if (capturedNum6 != 0) {
                sb.append("P");
                sb.append(" ");
                sb.append(capturedNum6);
                sb.append("  ");
            }
            return sb.toString();
        } else {
            int num1 = 0;
            int num2 = 0;
            int num3 = 0;
            int num4 = 0;
            int num5 = 0;
            int num6 = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        num1++;
                    } else if (chessComponents[i][j] instanceof QueenChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        num2++;
                    } else if (chessComponents[i][j] instanceof RookChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        num3++;
                    } else if (chessComponents[i][j] instanceof BishopChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        num4++;
                    } else if (chessComponents[i][j] instanceof KnightChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        num5++;
                    } else if (chessComponents[i][j] instanceof PawnChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        num6++;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            int capturedNum1 = 1 - num1;
            if (capturedNum1 != 0) {
                sb.append("k");
                sb.append(" ");
                sb.append(capturedNum1);
                sb.append("  ");
            }
            int capturedNum2 = 1 - num2;
            if (capturedNum2 != 0) {
                sb.append("q");
                sb.append(" ");
                sb.append(capturedNum2);
                sb.append("  ");
            }
            int capturedNum3 = 2 - num3;
            if (capturedNum3 != 0) {
                sb.append("r");
                sb.append(" ");
                sb.append(capturedNum3);
                sb.append("  ");
            }
            int capturedNum4 = 2 - num4;
            if (capturedNum4 != 0) {
                sb.append("b");
                sb.append(" ");
                sb.append(capturedNum4);
                sb.append("  ");
            }
            int capturedNum5 = 2 - num5;
            if (capturedNum5 != 0) {
                sb.append("n");
                sb.append(" ");
                sb.append(capturedNum5);
                sb.append("  ");
            }
            int capturedNum6 = 8 - num6;
            if (capturedNum6 != 0) {
                sb.append("p");
                sb.append(" ");
                sb.append(capturedNum6);
                sb.append("  ");
            }
            return sb.toString();
        }
    }

    public boolean tellIfHasNotChessToMove(ChessColor color) {
        boolean test = true;
        out:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == color && chessComponents[i][j].canMoveToList(chessComponents).size() != 0) {
                    test = false;
                    break out;
                }
            }
        }
        return test;
    }

    public boolean tellIfMoreThanThreeTimes() {
        if (recordChessBoard.size() >= 13) {
            boolean test = true;
            for (int i = 0; i < 8; i++) {
                String str13 = recordChessBoard.get(recordChessBoard.size() - 1).get(i);
                String str9 = recordChessBoard.get(recordChessBoard.size() - 5).get(i);
                String str5 = recordChessBoard.get(recordChessBoard.size() - 9).get(i);
                String str1 = recordChessBoard.get(recordChessBoard.size() - 13).get(i);
                String str12 = recordChessBoard.get(recordChessBoard.size() - 2).get(i);
                String str8 = recordChessBoard.get(recordChessBoard.size() - 6).get(i);
                String str4 = recordChessBoard.get(recordChessBoard.size() - 10).get(i);
                String str11 = recordChessBoard.get(recordChessBoard.size() - 3).get(i);
                String str7 = recordChessBoard.get(recordChessBoard.size() - 7).get(i);
                String str3 = recordChessBoard.get(recordChessBoard.size() - 11).get(i);
                String str10 = recordChessBoard.get(recordChessBoard.size() - 4).get(i);
                String str6 = recordChessBoard.get(recordChessBoard.size() - 8).get(i);
                String str2 = recordChessBoard.get(recordChessBoard.size() - 12).get(i);
                if (!(str1.equals(str5) && str5.equals(str9) && str9.equals(str13))) {
                    test = false;
                    break;
                }
                if (!(str2.equals(str6) && str6.equals(str10))) {
                    test = false;
                    break;
                }
                if (!(str3.equals(str7) && str7.equals(str11))) {
                    test = false;
                    break;
                }
                if (!(str4.equals(str8) && str8.equals(str12))) {
                    test = false;
                    break;
                }
            }
            return test;
        } else {
            return false;
        }
    }
//    public boolean tellWinOrDefeat(ChessColor color) {
//        if (color == ChessColor.BLACK) {
//            int kingI = 0;
//            int kingJ = 0;
//            int killerI = 0;
//            int killerJ = 0;
//            out:
//            for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                    if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
//                        kingI = i;
//                        kingJ = j;
//                        break out;
//                    }
//                }
//            }
//            boolean test = false;
//            out:
//            for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                    if (chessComponents[i][j].getChessColor() == color && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(kingI, kingJ))) {
//                        test = true;
//                        killerI = i;
//                        killerJ = j;
//                        break out;
//                    }
//                }
//            }
//            if (test) {
//                if (chessComponents[killerI][killerJ] instanceof PawnChessComponent || chessComponents[killerI][killerJ] instanceof KnightChessComponent) {
//                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
//                        boolean test1 = true;
//                        out:
//                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
//                                    test1 = false;
//                                    break out;
//                                }
//                            }
//                        }
//                        return test1;
//                    } else {
//                        return false;
//                    }
//                } else if (chessComponents[killerI][killerJ] instanceof RookChessComponent) {
//                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
//                        boolean test1 = true;
//                        out:
//                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
//                                    test1 = false;
//                                    break out;
//                                }
//                            }
//                        }
//                        if (!test1) {
//                            return false;
//                        } else {
//                            boolean test2 = true;
//                            if (killerI == kingI && killerJ - kingJ != 0) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else if (killerJ == kingJ && killerI - kingI != 0) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerI - kingI); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, killerJ))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            return test;
//                        }
//                    } else {
//                        return false;
//                    }
//                } else if (chessComponents[killerI][killerJ] instanceof BishopChessComponent) {
//                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
//                        boolean test1 = true;
//                        out:
//                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
//                                    test1 = false;
//                                    break out;
//                                }
//                            }
//                        }
//                        if (!test1) {
//                            return false;
//                        } else {
//                            boolean test2 = true;
//                            if (killerI - kingI == killerJ - kingJ) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.max(killerI, kingI) - i, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            return test;
//                        }
//                    } else {
//                        return false;
//                    }
//                } else {
//                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
//                        boolean test1 = true;
//                        out:
//                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
//                                    test1 = false;
//                                    break out;
//                                }
//                            }
//                        }
//                        if (!test1) {
//                            return false;
//                        } else {
//                            boolean test2 = true;
//                            if (killerI == kingI && killerJ - kingJ != 0) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else if (killerJ == kingJ && killerI - kingI != 0) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerI - kingI); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, killerJ))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else if (killerI - kingI == killerJ - kingJ) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.max(killerI, kingI) - i, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            return test;
//                        }
//                    } else {
//                        return false;
//                    }
//                }
//            } else {
//                return false;
//            }
//        } else {
//            int kingI = 0;
//            int kingJ = 0;
//            int killerI = 0;
//            int killerJ = 0;
//            out:
//            for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                    if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
//                        kingI = i;
//                        kingJ = j;
//                        break out;
//                    }
//                }
//            }
//            boolean test = false;
//            out:
//            for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                    if (chessComponents[i][j].getChessColor() == color && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(kingI, kingJ))) {
//                        test = true;
//                        killerI = i;
//                        killerJ = j;
//                        break out;
//                    }
//                }
//            }
//            if (test) {
//                if (chessComponents[killerI][killerJ] instanceof PawnChessComponent || chessComponents[killerI][killerJ] instanceof KnightChessComponent) {
//                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
//                        boolean test1 = true;
//                        out:
//                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
//                                    test1 = false;
//                                    break out;
//                                }
//                            }
//                        }
//                        return test1;
//                    } else {
//                        return false;
//                    }
//                } else if (chessComponents[killerI][killerJ] instanceof RookChessComponent) {
//                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
//                        boolean test1 = true;
//                        out:
//                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
//                                    test1 = false;
//                                    break out;
//                                }
//                            }
//                        }
//                        if (!test1) {
//                            return false;
//                        } else {
//                            boolean test2 = true;
//                            if (killerI == kingI && killerJ - kingJ != 0) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else if (killerJ == kingJ && killerI - kingI != 0) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerI - kingI); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, killerJ))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            return test;
//                        }
//                    } else {
//                        return false;
//                    }
//                } else if (chessComponents[killerI][killerJ] instanceof BishopChessComponent) {
//                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
//                        boolean test1 = true;
//                        out:
//                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
//                                    test1 = false;
//                                    break out;
//                                }
//                            }
//                        }
//                        if (!test1) {
//                            return false;
//                        } else {
//                            boolean test2 = true;
//                            if (killerI - kingI == killerJ - kingJ) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.max(killerI, kingI) - i, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            return test;
//                        }
//                    } else {
//                        return false;
//                    }
//                } else {
//                    if (chessComponents[kingI][kingJ].canMoveToList(chessComponents).isEmpty()) {
//                        boolean test1 = true;
//                        out:
//                        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                            for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, killerJ))) {
//                                    test1 = false;
//                                    break out;
//                                }
//                            }
//                        }
//                        if (!test1) {
//                            return false;
//                        } else {
//                            boolean test2 = true;
//                            if (killerI == kingI && killerJ - kingJ != 0) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(killerI, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else if (killerJ == kingJ && killerI - kingI != 0) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerI - kingI); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, killerJ))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else if (killerI - kingI == killerJ - kingJ) {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.min(killerI, kingI) + i, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else {
//                                out:
//                                for (int i = 0; i < CHESSBOARD_SIZE; i++) {
//                                    for (int j = 0; j < CHESSBOARD_SIZE; j++) {
//                                        for (int k = 1; k < Math.abs(killerJ - kingJ); k++) {
//                                            if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(Math.max(killerI, kingI) - i, Math.min(killerJ, kingJ) + i))) {
//                                                test2 = false;
//                                                break out;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            return test;
//                        }
//                    } else {
//                        return false;
//                    }
//                }
//            } else {
//                return false;
//            }
//        }
//
//    }
}
