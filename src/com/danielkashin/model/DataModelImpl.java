package com.danielkashin.model;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.util.Pair;
import com.danielkashin.hepler.PyatnashkaHelper;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by Кашин on 05.02.2017.
 */
public class DataModelImpl implements DataModel {

    private final int GRID_WIDTH;
    private final int GRID_HEIGHT;

    private int mStepCount;
    Pyatnashka[] pyatnashkas = new Pyatnashka[16];



    public DataModelImpl(File file, final int gridSize) throws MalformedURLException {
        GRID_WIDTH = gridSize;
        GRID_HEIGHT = gridSize;

        Image image = new Image(file.toURI().toURL().toString(), GRID_WIDTH, GRID_HEIGHT, false, true);
        initializeImage(image);
        onGameRestarted();
    }



    @Override
    public Pyatnashka findWithCurrentIndex(int k) {
        for (int i = 0; i < 16; ++i) {
            if (pyatnashkas[i].getCurrentIndex() == k) {
                return pyatnashkas[i];
            }
        }

        return null;
    }

    @Override
    public Pyatnashka findWithRealIndex(int k) {
        return pyatnashkas[k];
    }

    @Override
    public void onGameRestarted() {
        mStepCount = 0;
        int[] randomNumbers = PyatnashkaHelper.generateRandomValidNumbers(16);
        for (int i = 0; i < 16; ++i) {
            pyatnashkas[i].setCurrentIndex(randomNumbers[i]);
        }
    }

    @Override
    public Pair<Integer, Integer> onSwapRequested(int currentClickedIndex) {
        Pyatnashka clicked = findWithCurrentIndex(currentClickedIndex);
        Pyatnashka empty = pyatnashkas[15];

        int currentEmptyIndex = empty.getCurrentIndex();

        if ((currentClickedIndex / 4 == currentEmptyIndex / 4)               // pyatnashkas are on the same line
                && Math.abs(currentClickedIndex - currentEmptyIndex) == 1    // and border
                || Math.abs(currentClickedIndex - currentEmptyIndex) == 4) { // they are on the different lines and border

            clicked.setCurrentIndex(currentEmptyIndex);
            empty.setCurrentIndex(currentClickedIndex);

            mStepCount++;
            return new Pair<>(currentClickedIndex, currentEmptyIndex);
        } else {
            return null;
        }
    }

    @Override
    public boolean isSolved() {
        for (int i = 0; i < 16; ++i) {
            if (pyatnashkas[i].getCurrentIndex() != i) {
                return false;
            }
        }
        return true;
    }

    public int getmStepCount() {
        return mStepCount;
    }



    private void initializeImage(Image image) {
        double quaterWidth = GRID_WIDTH / 4.0;
        double quaterHeight = GRID_HEIGHT / 4.0;

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                WritableImage newImage = (col == 3 && row == 3) ? null
                        : new WritableImage(image.getPixelReader(),
                        (int) (quaterWidth * col), (int) (quaterHeight * row),
                        (int) quaterWidth - 1, (int) quaterHeight - 1);

                pyatnashkas[row * 4 + col] = new Pyatnashka(newImage);
            }
        }
    }
}
