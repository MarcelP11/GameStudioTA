package sk.tacademy.gamestudio.minesweeper;

import sk.tacademy.gamestudio.minesweeper.core.Field;

public interface UserInterface {
    void newGameStarted(Field field);

    void update();

    void play();
}
