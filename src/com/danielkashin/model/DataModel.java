package com.danielkashin.model;

import javafx.util.Pair;

/**
 * Created by Кашин on 05.02.2017.
 */
public interface DataModel {


    /**
     * Refreshes DataModel
     * AFTER: step count == 0, pyatnashkas indexes are mixed randomly
     */
    void onGameRestarted();


    /**
     * Returns whether move of the Pyathashka with current index can be performed
     *
     * @param   currentClickedIndex index of element on which click event was called
     * @return  indexes of moved Pyatnashkas, if moving can be performed
     *          otherwise, null
     */
    Pair<Integer, Integer> onSwapRequested(int currentClickedIndex);


    /**
     * Returns whether game is solved
     *
     * @return  true, if all the pyatnashkas are on their real places
     *          otherwise, false
     */
    boolean isSolved();


    /**
     * Returns current step count
     *
     * @return stepCount
     */
    int getmStepCount();


    /**
     * Returns Pyatnashka with given current index
     *
     * @param  index index on which Pyatnashka is placed currently
     * @return Pyatnashka which currentIndex equals given, if such exists,
     *         otherwise, null
     */
    Pyatnashka findWithCurrentIndex(int index);


    /**
     * Returns Pyatnashka with given current index
     *
     * @param  index real index of Pyatnashka
     * @return Pyatnashka with given real index, if such exists,
     *         otherwise, throws IndexOutOfRangeException
     */
    Pyatnashka findWithRealIndex(int index);

}
