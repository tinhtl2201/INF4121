package hioa.mappe2.s169986;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
    
    @Override
	protected void onResume() {
		super.onResume();
	}
    
    @Override
   	protected void onPause() {
   		super.onPause();
   	} 
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_start, menu);
		return true;
	}
	
	//actionBar
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mk_start:
			Intent tilMain = new Intent(this, MainActivity.class);
			startActivity(tilMain);
			super.finish();
			return true;  
	    case R.id.mk_avslutt:
	    	super.finish();
	        return true; 
	    }
		return false;
	}
	
	//Start spill knapp
	public void start(View v) {
		Intent tilMain = new Intent(this, MainActivity.class);
		startActivity(tilMain);  
		super.finish();
    }

	//Til omMarvel siden for forklaring av spillet
	public void tilMarvel(View v) {
		Intent tilAbout = new Intent(this, AboutActivity.class);
		startActivity(tilAbout);  
		super.finish();
    }
}
