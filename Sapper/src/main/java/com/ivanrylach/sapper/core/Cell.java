package com.ivanrylach.sapper.core;

public class Cell {
	private boolean haveMine;
	private boolean haveFlag;
	private int counter;
	private boolean isCovered;

	public Cell() {
		setDefaultValues();
	}

    public void setHaveFlag(boolean haveFlag){
        this.haveFlag = haveFlag;
    }

    public int getCounter(){
        return counter;
    }

    public void setCovered(boolean isCovered){
        this.isCovered = isCovered;
    }



	public void setDefaultValues(){
		haveMine = false;
		haveFlag = false;
		counter = 0;
		isCovered = true;
	}
	
	
	public void incCounter() {
		if (!this.haveMine && counter >= 0)
			counter++;
	}

	public void installMine() {
		haveMine = true;
		counter = -1;
	}

	public boolean haveMine() {
		return haveMine;
	}

	public boolean haveFlag() {
		return haveFlag;
	}

	public boolean isCovered() {
		return isCovered;
	}
}
