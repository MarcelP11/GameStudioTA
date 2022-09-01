package sk.tacademy.gamestudio.stones.core;

public class Box {
    private int value;  //ciselna hodnota kamena
    private int posX;  //ciselna poloha v osi x
    private int posY;  //ciselna poloha v osi y


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Box(int value) {
        this.value = value;
    }

    }
