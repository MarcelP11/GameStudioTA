package sk.tacademy.gamestudio.stones.core;

import java.io.*;

public class Field {
    private int rowCount;
    private int columnCount;
    private final Box[][] boxes;
    private GameState state = GameState.PLAYING;
    private long startMillis;

    private boolean justFinished=false;

    public boolean isJustFinished() {
        return justFinished;
    }

    public void setJustFinished(boolean justFinished) {
        this.justFinished = justFinished;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Box[][] getBoxes() {   //pozor!!
        return boxes;
    }

    public Box getBoxes(int row, int column) {
        Box box = boxes[row][column];
        return box;
    }

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.boxes = new Box[rowCount][columnCount];
        generateField();  //pri vytvoreni objektu sa rovno vygeneruju cisla
    }

    public void saveField() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "field.txt"));
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("nepodaril sa zapis do suboru");
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public Field loadField() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "field.txt"));
            return (Field) ois.readObject();
        } catch (IOException e) {
            System.out.println("nepodarilo sa otvorit subor so stavom hry");
        } catch (ClassNotFoundException e) {
            System.out.println("nepodarilo sa precitat subor so stavom hry");
        }
        return new Field(this.rowCount, this.columnCount);  //ak nenajde vrati novy prazdny objekt
    }


    public void generateField() {
        startMillis = System.currentTimeMillis();

        //pole na skusku
        int value=0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                boxes[i][j]=new Box(value);
                value++;
            }
            boxes[0][0].setValue(1);
            boxes[0][1].setValue(0);
        }

        //vygenerovane pole
        /*
        Random randomNumber = new Random();
        for (int i = 0; i < (rowCount * columnCount); ) {
            int rRandom = randomNumber.nextInt(getRowCount());
            int cRandom = randomNumber.nextInt(getColumnCount());
            if (boxes[cRandom][rRandom] == null) {
                boxes[cRandom][rRandom] = new Box(i);
                i++;
            }
        }
        */
    }

    private int findRowOfEmptyBox() {  //najdenie cisla riadku prazdneho kamena
        int row = 0;
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                if (boxes[r][c].getValue() == 0) {
                    row = r;
                }
            }
        }
        return row;
    }

    private int findColumnOfEmptyBox() {  //najdenie cisla stlpca prazdneho kamena
        int column = 0;
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                if (boxes[r][c].getValue() == 0) {
                    column = c;
                }
            }
        }
        return column;
    }

    public void moveBox(String input) {
        int column = findColumnOfEmptyBox();
        int row = findRowOfEmptyBox();
        if (input.equals("w")) {
            if (row - 1 < rowCount && row - 1 >= 0) {
                int temporaryValue = boxes[row - 1][column].getValue();
                boxes[row][column].setValue(temporaryValue);
                boxes[row - 1][column].setValue(0);
            }
        }
        if (input.equals("s")) {
            if (row + 1 >= 0 && row + 1 < rowCount){
                int temporaryValue = boxes[row + 1][column].getValue();
                boxes[row][column].setValue(temporaryValue);
                boxes[row + 1][column].setValue(0);
            }
        }
        if (input.equals("d")) {
            if (column + 1 > 0 && column + 1 < columnCount) {
                int temporaryValue = boxes[row][column + 1].getValue();
                boxes[row][column].setValue(temporaryValue);
                boxes[row][column + 1].setValue(0);
            }
        }
        if (input.equals("a")) {
            if ((column - 1) >= 0 && (column - 1) < columnCount) {
                int temporaryValue = boxes[row][column - 1].getValue();
                boxes[row][column].setValue(temporaryValue);
                boxes[row][column - 1].setValue(0);
            }
        }
        if(isSolved()){
            this.state = GameState.SOLVED;
            this.justFinished=true;
        }
    }

    public boolean isSolved(){
        int valueComparator = 0;  //premenna pre porovnavanie hodnoty boxu
        int countComparator = 0;  //premenna na porovnavanie poctu spravne ulozenych boxov
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                if (boxes[r][c].getValue() == valueComparator) {  //ak je hodnota boxu zhodna s hodnotou value comparatora tak je box na spravnom
                    //mieste a teda sa countComparator zvysi o 1
                    countComparator++;
                }
                if (countComparator == (rowCount * columnCount)) {  //ak je countComparator zhodny s poctom boxov celeho hracieho pola tak je hra vyriesena
                    return true;  //vrati true
                }
                valueComparator++;
            }
        }
        return false;
    }

    public int getPlayTimeInSeconds() {
        return ((int) (System.currentTimeMillis() - startMillis) / 1000);
    }

    public int getScore() {
        return rowCount * columnCount * 50 - getPlayTimeInSeconds();
    }


}




