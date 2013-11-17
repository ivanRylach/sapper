package com.ivanrylach.sapper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ivanrylach.sapper.R;
import com.ivanrylach.sapper.adapters.ImageAdapter;
import com.ivanrylach.sapper.cell.Cell;
import com.ivanrylach.sapper.installer.Installer;
import com.ivanrylach.sapper.settings.SapperConstants;

import java.util.HashSet;
import java.util.Set;
/**
 * 
 * @author RIV
 *
 */
public class GameField extends BaseActivity {

	GridView gridView;
	ImageAdapter adapter;
	Toast toast;
	TextView flagCounterTextView;
	TextView gameStatusTextView;
	Installer installer;
	Cell[] mineField;
	int[] minePositions;
	Set<Integer> flaggedCells;
	
	private static final int NEW_GAME = 0x00000001;
	private static final int SHARE_WITH = 0x00000002;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, NEW_GAME, 0, R.string.text_action_bar_new_game)
			.setIcon(R.drawable.ic_new_game)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		menu.add(0, SHARE_WITH, 0, R.string.text_action_bar_share_with)
			.setIcon(R.drawable.share)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case NEW_GAME: {
				createGameField();
				break;
			}
			case SHARE_WITH: {
				Intent sendIntent = createShareIntent();
				startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.text_action_bar_share_with)));
				break;
			}
			default:
				createGameField();
				
		}
				
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_field);

		getSupportActionBar().setDisplayShowTitleEnabled(false);
		createGameField();
		initListeners();
		
	}

	private void initListeners() {
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				int counter;
				if (mineField[position].isCovered()	&& !mineField[position].haveFlag()) {
					
					counter = mineField[position].getCounter();
					
					if (counter > 0) {
						adapter.setThumbId(position, getImageForValue(counter));
						mineField[position].setCovered(false);
					}

					if (counter == -1) {
						showMinePositions(position);
						
					}
					
					if (counter == 0) {
						discoverEmptyCells(position);
					}
					gridView.invalidateViews();
				}
				
				checkIfVictory();
				
			}

			
		});

		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
				
				if (mineField[position].isCovered())
					if (!mineField[position].haveFlag()) {
						adapter.setThumbId(position, R.drawable.flagged_cell);
						flaggedCells.add(position);
						flagCounterTextView.setText(getFlaggedCellsNumber());
						mineField[position].setHaveFlag(true);
					} else {
						adapter.setThumbId(position, R.drawable.covered_cell);
						flaggedCells.remove(position);
						flagCounterTextView.setText(getFlaggedCellsNumber());
						mineField[position].setHaveFlag(false);
					}

				gridView.invalidateViews();
				checkIfVictory();
				return true;
			}
		});		
	}
	
	private void checkIfVictory () {
		int numberOfCoveredCells = 0;
		
		for(Cell eachMine: mineField) {
			if (eachMine.isCovered()) {
				numberOfCoveredCells++;
			}
		}
		if (numberOfCoveredCells == SapperConstants.MINE_NUMBER) {
			gridView.setEnabled(false);
			gameStatusTextView.setText(getResources().getText(R.string.word_victory));
		}
	}
	
	private void showMinePositions(int position) {
		
		for (int eachMinePosition : minePositions)
			if (!mineField[eachMinePosition].haveFlag()) {
				adapter.setThumbId(eachMinePosition,R.drawable.cell_with_mine);
			}
		Cell flaggedCell;
		for (int item: flaggedCells) {
			flaggedCell = mineField[item];
			if (flaggedCell.haveFlag() && !flaggedCell.haveMine()) {
				adapter.setThumbId(item, R.drawable.flagged_wrong_cell);
			}
		}
		adapter.setThumbId(position, R.drawable.discovered_cell_with_mine);
		gridView.setEnabled(false);
		gameStatusTextView.setText(getResources().getText(R.string.text_game_over));
	}

	private void createGameField() {

		if (mineField == null) {
			installer = new Installer();
			adapter = new ImageAdapter(this);
			flaggedCells = new HashSet<Integer>();
			gridView = (GridView) findViewById(R.id.gridView);
			flagCounterTextView = (TextView) findViewById(R.id.textFlagCounter);
			gameStatusTextView = (TextView) findViewById(R.id.textGameStatus);
			prepareGameField();
		
		} else {
		
			for(Cell eachCell: mineField)
				eachCell.setDefaultValues();
			
			installer.locateMines();				
			adapter.setDefaultValues();
			flaggedCells.clear();
			prepareGameField();
		}
	}

	private void prepareGameField(){
		gridView.setAdapter(adapter);
		
		if (!gridView.isEnabled()) {
			gridView.setEnabled(true);
		}
		
		flagCounterTextView.setText(getFlaggedCellsNumber());
		gameStatusTextView.setText(getResources().getText(R.string.empty));
		
		mineField = installer.getMineField();
		minePositions = installer.getMinePositions();

	}
	
	private void discoverEmptyCells(int currentPosition) {
		int position;

		if (mineField[currentPosition].isCovered()) {
			adapter.setThumbId(currentPosition, getImageForValue(mineField[currentPosition].getCounter()));
			mineField[currentPosition].setCovered(false);

			if (((currentPosition + 1) % SapperConstants.FIELD_DIMENSION) != 0) {
				position = currentPosition + 1;
				checkCell(position);
			}

			if ((currentPosition % SapperConstants.FIELD_DIMENSION) != 0) {
				position = currentPosition - 1;
				checkCell(position);
			}

			if ((currentPosition - SapperConstants.FIELD_DIMENSION) >= 0) {
				position = currentPosition - SapperConstants.FIELD_DIMENSION;
				checkCell(position);

				if ((currentPosition % SapperConstants.FIELD_DIMENSION) != 0) {
					position = currentPosition - SapperConstants.FIELD_DIMENSION - 1;
					checkCell(position);
				}

				if (((currentPosition + 1) % SapperConstants.FIELD_DIMENSION) != 0) {
					position = currentPosition - SapperConstants.FIELD_DIMENSION + 1;
					checkCell(position);
				}

			}

			if ((currentPosition + SapperConstants.FIELD_DIMENSION) < SapperConstants.CELL_NUMBER) {
				position = currentPosition + SapperConstants.FIELD_DIMENSION;
				checkCell(position);

				if ((currentPosition % SapperConstants.FIELD_DIMENSION) != 0) {
					position = currentPosition + SapperConstants.FIELD_DIMENSION - 1;
					checkCell(position);
				}

				if (((currentPosition + 1) % SapperConstants.FIELD_DIMENSION) != 0) {
					position = currentPosition + SapperConstants.FIELD_DIMENSION + 1;
					checkCell(position);
				}
			}
		}
	}

	private void checkCell(int position) {
		Cell cell = mineField[position];
		
		if (!cell.haveFlag()) {
		
			if (cell.getCounter() > 0) {
				adapter.setThumbId(position, getImageForValue(cell.getCounter()));
				cell.setCovered(false);
			}

			if (cell.getCounter() == 0) {
				adapter.setThumbId(position, getImageForValue(mineField[position].getCounter()));
				discoverEmptyCells(position);
			}
		}
	}
	
	private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getText(R.string.text_share_action_content));
        return shareIntent;
    }
	
	private String getFlaggedCellsNumber() {
		return Integer.toString(flaggedCells.size());
	}
	
	private int getImageForValue(int value) {
		switch (value) {
		case -1:
			return R.drawable.cell_with_mine;
		case 1:
			return R.drawable.number_1;
		case 2:
			return R.drawable.number_2;
		case 3:
			return R.drawable.number_3;
		case 4:
			return R.drawable.number_4;
		case 5:
			return R.drawable.number_5;
		case 6:
			return R.drawable.number_6;
		case 7:
			return R.drawable.number_7;
		case 8:
			return R.drawable.number_8;
		default:
			return R.drawable.cell;
		}
	}
}
