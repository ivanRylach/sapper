package com.ivanrylach.sapper.core;

import com.ivanrylach.sapper.settings.SapperConstants;

import java.util.Random;

/**
 * 
 * @author RIV
 *
 * 
 */

public class Installer {
	Cell[] mineField;
	int minePositions[] = new int[SapperConstants.MINE_NUMBER];

	public Installer() {

		mineField = new Cell[SapperConstants.CELL_NUMBER];

		for(int i = 0; i < SapperConstants.CELL_NUMBER; i++)
			mineField[i] = new Cell();
		
		locateMines();
		
			}

	public void locateMines() {
		Random generator = new Random();
		int tempCoordinate;
		
		for (int i = 0; i < SapperConstants.MINE_NUMBER; i++)
			while (true) {
				tempCoordinate = generator.nextInt(SapperConstants.CELL_NUMBER);
				if (!mineField[tempCoordinate].haveMine()) {
					mineField[tempCoordinate].installMine();
					minePositions[i] = tempCoordinate;
					break;
				}
			}

		setHelpers();

	}

    public Cell[] getMineField(){
        return mineField;
    }

    public int[] getMinePositions(){
        return minePositions;
    }

    private void setHelpers() {
		for (int i = 0; i < SapperConstants.CELL_NUMBER; i++)
			if (mineField[i].haveMine())
				incNeighborCounter(i);

	}
	
	private void incNeighborCounter(int currentPosition) {
		if (((currentPosition + 1) % SapperConstants.FIELD_DIMENSION) != 0)
			mineField[currentPosition + 1].incCounter();

		if ((currentPosition % SapperConstants.FIELD_DIMENSION) != 0)
			mineField[currentPosition - 1].incCounter();

		if ((currentPosition - SapperConstants.FIELD_DIMENSION) >= 0) {
			mineField[currentPosition - SapperConstants.FIELD_DIMENSION]
					.incCounter();
			if ((currentPosition % SapperConstants.FIELD_DIMENSION) != 0)
				mineField[currentPosition - SapperConstants.FIELD_DIMENSION
						- 1].incCounter();
			if (((currentPosition + 1) % SapperConstants.FIELD_DIMENSION) != 0)
				mineField[currentPosition - SapperConstants.FIELD_DIMENSION
						+ 1].incCounter();
		}

		if ((currentPosition + SapperConstants.FIELD_DIMENSION) < SapperConstants.CELL_NUMBER) {
			mineField[currentPosition + SapperConstants.FIELD_DIMENSION]
					.incCounter();
			if ((currentPosition % SapperConstants.FIELD_DIMENSION) != 0)
				mineField[currentPosition + SapperConstants.FIELD_DIMENSION
						- 1].incCounter();
			if (((currentPosition + 1) % SapperConstants.FIELD_DIMENSION) != 0)
				mineField[currentPosition + SapperConstants.FIELD_DIMENSION
						+ 1].incCounter();
		}
	}
	
	
}
