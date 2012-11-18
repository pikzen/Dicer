package net.pikzen.rtd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.*;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class RollTheDiceActivity extends SherlockFragmentActivity implements TabListener{
	private ActionBar mBar;
	private TextView mDiceRoll;
	private int mMax;
	private Random rng;
	private List<Dice> dicePage;
	AlertDialog alert;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        dicePage = new ArrayList<Dice>();
        setupPages();
        setupDialog();
        
        // setting up the Action Bar (tabs)
        mBar = getSupportActionBar();
        mBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);     
        
        setupActionBar();
        
        mDiceRoll = (TextView)findViewById(R.id.dice);
        rng = new Random();
        mMax = 6;
        setRandomNumber(null);
        
    }
    
    private void addNewDice(int n, int max)
    {
    	if (n > 0)
    	{
    		dicePage.add(new Dice(n, max));
    	}
    }
    
    private void setupDialog()
    {
    	LayoutInflater li = LayoutInflater.from(this);
    	final View alertDialog = li.inflate(R.layout.diceadd, null);
    	
    	
    	AlertDialog.Builder build = new AlertDialog.Builder(this);
    	build.setView(alertDialog);
    	build.setTitle("Add a new dice");
    	build.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				TextView tv = (TextView)alertDialog.findViewById(R.id.diceAddNumRolls);
				int num = Integer.parseInt(tv.getText().toString());
				
				tv = (TextView)alertDialog.findViewById(R.id.diceAddMaxRoll);
				int max = Integer.parseInt(tv.getText().toString());
				
				addNewDice(num, max);
				setupActionBar();
			}
		});
    	build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});
    	alert = build.create();
    }
    public void setupPages()
    {
    	dicePage.add(new Dice(1, 6));
    	dicePage.add(new Dice(1, 10));
    	dicePage.add(new Dice(1, 20));
    	
    
    }
    
    
    
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	menu.add("Add")
    		.setIcon(android.R.drawable.ic_menu_add)
    		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    	menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				alert.show();
				return true;
			}
		});
    	
    	menu.add("Delete")
    		.setIcon(android.R.drawable.ic_menu_close_clear_cancel)
    		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    	menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if (dicePage.size() > 1)
				{
					dicePage.remove(mBar.getSelectedNavigationIndex());
					setupActionBar();
				}
				return false;
			}
		});
    	
    	return true;
    }
    
    public void setupActionBar()
    {
    	mBar.removeAllTabs();
    	for (Dice dice : dicePage){
    		mBar.addTab(mBar.newTab().setText(dice.getNumRolls() + "d" + dice.getMaxRoll()).setTabListener(this));
    	}
    }
    
    public void setRandomNumber(View v)
    {
    	mDiceRoll.setAlpha(0.5f);
    	new CountDownTimer(500, 5)
    	{
    		public void onTick(long millis) 
    		{ 
    			mDiceRoll.setText(Integer.toString(rng.nextInt(mMax) + 1));
    		}
    		public void onFinish()
    		{
    			mDiceRoll.setAlpha(1.0f);
    			mDiceRoll.setText(Integer.toString(rng.nextInt(mMax) + 1) + "!");
    		}
    	}.start();
    }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (mDiceRoll != null)
		{
			mMax = Integer.parseInt((String)tab.getText().subSequence(2, tab.getText().length()));
			setRandomNumber(null);
		}
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}