package hioa.mappe2.s169986;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {

	private int counter, teller,antallRiktig, antallFeil, antRiktig;
	private String[] sporsmal, rSvar, fSvar1, fSvar2, fSvar3, bHint;
	private TextView textView1,textView2, myCounter;
	private ImageButton hintKnapp, bhintKnapp;
	private Button btn1, btn2, btn3, btn4;
	private CountDownTimer mCountDown;
    private Question nextQuestion;
	private Drawable [] spmside;
	private ImageView bildeside;
	private SharedPreferences intFil;
	
	private ArrayList<String> allAnswers = new ArrayList<String>();
    private ArrayList<Question> qsts = new ArrayList<Question>();
    private List<Integer> generated = new ArrayList<Integer>();
    private Random rng = new Random();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Henter verdiene fra array.xml
        sporsmal = getResources().getStringArray(R.array.sporsmal);
        rSvar = getResources().getStringArray(R.array.rSvar);
        fSvar1 = getResources().getStringArray(R.array.fSvar1);
        fSvar2 = getResources().getStringArray(R.array.fSvar2);
        fSvar3 = getResources().getStringArray(R.array.fSvar3);
        bHint = getResources().getStringArray(R.array.bHint);
        
        //Og setter inn i nextQuestion
        for (int i = 0; i < sporsmal.length; i++) 
        {
			nextQuestion = new Question(sporsmal[i], rSvar[i], fSvar1[i], fSvar2[i], fSvar3[i], bHint[i]);
			qsts.add(nextQuestion);
		}
        
        counter = 0;
        antRiktig = 0;
        teller = 0;
        
        spmside = new Drawable[10];
        spmside[0] = getResources().getDrawable(R.drawable.spm1);
		spmside[1] = getResources().getDrawable(R.drawable.spm2);
		spmside[2] = getResources().getDrawable(R.drawable.spm3);
		spmside[3] = getResources().getDrawable(R.drawable.spm4);
		spmside[4] = getResources().getDrawable(R.drawable.spm5);
		spmside[5] = getResources().getDrawable(R.drawable.spm6);
		spmside[6] = getResources().getDrawable(R.drawable.spm7);
		spmside[7] = getResources().getDrawable(R.drawable.spm8);
		spmside[8] = getResources().getDrawable(R.drawable.spm9);
		spmside[9] = getResources().getDrawable(R.drawable.spm10);

		bildeside = (ImageView) findViewById(R.id.spmsider);
		bildeside.setImageDrawable(spmside[teller]);
		
    	textView1 = (TextView) findViewById(R.id.question);
    	textView2 = (TextView) findViewById(R.id.hint);
    	myCounter = (TextView) findViewById(R.id.countdown);

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
	    hintKnapp = (ImageButton) findViewById(R.id.button5);
	    bhintKnapp = (ImageButton) findViewById(R.id.button6);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        
        intFil = this.getSharedPreferences("statistikk", MODE_PRIVATE);
		antallRiktig = intFil.getInt("antallRiktig", 0);
		antallFeil = intFil.getInt("antallFeil", 0);

		//Setter på CountDownTimer for første spørsmål, 21 sekunder
        mCountDown = new CountDownTimer(21000, 1000) {
        	@Override
            public void onFinish() {
                timeUp();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                myCounter.setText(getResources().getString(R.string.tidigjen)+ String.valueOf(millisUntilFinished / 1000));
            }
        }.start();
    generateQuestion();
    }
    
    //Når tiden er ute dukker det opp en AlertDialog som forteller at du må trykke ok for å gå videre
    public void timeUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getResources().getString(R.string.tidute))
        		.setMessage(getResources().getString(R.string.trykkok))
                .setCancelable(false)
                .setNeutralButton(android.R.string.ok,
                		new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) 
                        {
                        	if(counter == qsts.size())
                            {
                        		SharedPreferences intFil = MainActivity.this.getSharedPreferences("statistikk", MODE_PRIVATE);
                        		antallFeil = intFil.getInt("antallFeil", 0);
                        		SharedPreferences.Editor edit = intFil.edit();
                        		edit.putInt("antallFeil", ++antallFeil);
                        		edit.commit();
                        		
            	            	mCountDown.cancel();
            	            	Intent tilEnd = new Intent(MainActivity.this, EndActivity.class);
            	            	tilEnd.putExtra("antRiktig", antRiktig);
            	        		startActivity(tilEnd);
            	        		finish();
                            }
                        	else
                        	{
	                        	++teller;
	                        	bildeside.setImageDrawable(spmside[teller]);
	                        	
	                        	SharedPreferences intFil = MainActivity.this.getSharedPreferences("statistikk", MODE_PRIVATE);
	                    		antallFeil = intFil.getInt("antallFeil", 0);
	                    		SharedPreferences.Editor edit = intFil.edit();
	                    		edit.putInt("antallFeil", ++antallFeil);
	                    		edit.commit();
	                    		
	                        	mCountDown.cancel();
	                            allAnswers.clear();
	                            generateQuestion();
	                            
	                            btn1.setEnabled(true);
	                			btn2.setEnabled(true);
	                			btn3.setEnabled(true);
	                			btn4.setEnabled(true);
	                			textView2.setVisibility(View.INVISIBLE);
                            
	                			mCountDown = new CountDownTimer(21000, 1000) {

                                @Override
                                public void onFinish() {
                                    timeUp();
                                }

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    myCounter.setText(getResources().getString(R.string.tidigjen) + String.valueOf(millisUntilFinished / 1000));
                                }
                            }.start(); 
                    	}
                    }
               	});
			AlertDialog alert = builder.create();
		alert.show();
    }
    
    //Genererer tilfeldig spørsmål
    public void generateQuestion()
    {
    	counter++;
        while(true) {
            int nxt = rng.nextInt(10);
            if (!generated.contains(nxt)){

                generated.add(nxt);

                nextQuestion = qsts.get(nxt);

                textView1.setText(nextQuestion.questionText);
                textView2.setText(nextQuestion.hint);
                allAnswers.add(nextQuestion.correctAnswerText);
                allAnswers.add(nextQuestion.wrongAnswer1);
                allAnswers.add(nextQuestion.wrongAnswer2);
                allAnswers.add(nextQuestion.wrongAnswer3);

                Collections.shuffle(allAnswers);

                btn1.setText(allAnswers.get(0));
                btn2.setText(allAnswers.get(1));
                btn3.setText(allAnswers.get(2));
                btn4.setText(allAnswers.get(3));
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        //Hvis det har gått 10 spørsmål, er man ferdig med quizen og blir sendt til EndActivity
        if(counter == qsts.size())
        {
        	if(buttonText.equals(nextQuestion.correctAnswerText))
            { 
        		++antRiktig;
        		
            	intFil = this.getSharedPreferences("statistikk", MODE_PRIVATE);
				antallRiktig = intFil.getInt("antallRiktig", 0);
				SharedPreferences.Editor edit = intFil.edit();
				edit.putInt("antallRiktig", ++antallRiktig);
				edit.commit();
				
            	mCountDown.cancel();
            	Intent tilEnd = new Intent(this, EndActivity.class);
            	tilEnd.putExtra("antRiktig", antRiktig);
        		startActivity(tilEnd);
        		super.finish();
        		return;
        	}
        	else
        	{
        		intFil = this.getSharedPreferences("statistikk", MODE_PRIVATE);
        		antallFeil = intFil.getInt("antallFeil", 0);
        		SharedPreferences.Editor edit = intFil.edit();
        		edit.putInt("antallFeil", ++antallFeil);
        		edit.commit();
        		
            	mCountDown.cancel();
            	Intent tilEnd = new Intent(this, EndActivity.class);
            	tilEnd.putExtra("antRiktig", antRiktig);
        		startActivity(tilEnd);
        		super.finish();
        		return;
        	}
        }
        if(buttonText.equals(nextQuestion.correctAnswerText))
        { 
        	++teller;
        	++antRiktig;
        	bildeside.setImageDrawable(spmside[teller]);
        	
        	//lagrer antallRiktig i filen statistikk for å bevare resultatene
        	intFil = this.getSharedPreferences("statistikk", MODE_PRIVATE);
			antallRiktig = intFil.getInt("antallRiktig", 0);
			SharedPreferences.Editor edit = intFil.edit();
			edit.putInt("antallRiktig", ++antallRiktig);
			edit.commit();
			
        	mCountDown.cancel();
            allAnswers.clear();
            generateQuestion();
            
            btn1.setEnabled(true);
			btn2.setEnabled(true);
			btn3.setEnabled(true);
			btn4.setEnabled(true);
			textView2.setVisibility(View.INVISIBLE);
			
            mCountDown = new CountDownTimer(21000, 1000) {
                @Override
                public void onFinish() {
                    timeUp();
                }

                @Override
                public void onTick(long millisUntilFinished) {
                    myCounter.setText(getResources().getString(R.string.tidigjen) + String.valueOf(millisUntilFinished / 1000));
                }
            }.start();  
            return;
        }
        
        else
        {
        	++teller;
        	bildeside.setImageDrawable(spmside[teller]);
        	
        	//lagrer antallFeil i filen statistikk
        	intFil = this.getSharedPreferences("statistikk", MODE_PRIVATE);
    		antallFeil = intFil.getInt("antallFeil", 0);    		
    		SharedPreferences.Editor edit = intFil.edit();
    		edit.putInt("antallFeil", ++antallFeil);
    		edit.commit();
    		
        	mCountDown.cancel();
            allAnswers.clear();
            generateQuestion();
            
            btn1.setEnabled(true);
			btn2.setEnabled(true);
			btn3.setEnabled(true);
			btn4.setEnabled(true);
			textView2.setVisibility(View.INVISIBLE);
			
            mCountDown = new CountDownTimer(21000, 1000) {
                @Override
                public void onFinish() {
                    timeUp();
                }

                @Override
                public void onTick(long millisUntilFinished) {
                    myCounter.setText(getResources().getString(R.string.tidigjen) + String.valueOf(millisUntilFinished / 1000));
                }
            }.start();  
            return;
        }
    }

    @Override
	protected void onResume() {
		super.onResume();
	}
    
    @Override
   	protected void onPause() {
   		super.onPause();
   	} 
    
    //50/50-hintknapp funksjonen
	public void hintKnapp(View v)
	{
		hintKnapp.setVisibility(View.INVISIBLE);
		if(btn1.getText().equals(nextQuestion.correctAnswerText))
		{
			btn1.setEnabled(true);
			btn2.setEnabled(true);
			btn3.setEnabled(false);
			btn4.setEnabled(false);
		}
		
		if(btn2.getText().equals(nextQuestion.correctAnswerText))
		{
			btn1.setEnabled(false);
			btn2.setEnabled(true);
			btn3.setEnabled(true);
			btn4.setEnabled(false);
		}
		
		if(btn3.getText().equals(nextQuestion.correctAnswerText))
		{
			btn1.setEnabled(false);
			btn2.setEnabled(false);
			btn3.setEnabled(true);
			btn4.setEnabled(true);
		}
		
		if(btn4.getText().equals(nextQuestion.correctAnswerText))
		{
			btn1.setEnabled(true);
			btn2.setEnabled(false);
			btn3.setEnabled(false);
			btn4.setEnabled(true);
		}
	}
	
	//Vanlig Hintknapp som gir deg hint beskrivelse
	public void bhintKnapp(View v)
	{
		bhintKnapp.setVisibility(View.INVISIBLE);
		textView2.setVisibility(View.VISIBLE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	//actionBar
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.mk_nyStart:
			startNyttDialog();
			return true;  
	    case R.id.mk_giOpp:
	    	giOppDialog();
	        return true; 
	    }
		return false;
	}
	
	public void startNyttDialog() 
	{
	    popUp nyPopup = new popUp();
	    nyPopup.show(getFragmentManager(), "missiles");
	}
	
	public void giOppDialog() 
	{
	    popUp2 nyPopup2 = new popUp2();
	    nyPopup2.show(getFragmentManager(), "missiles");
	}
	
	//Første popUp dialog for actionbaren, nytt spill? ja nei?
	public class popUp extends DialogFragment {
		
		@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(getResources().getString(R.string.nystart))
	        .setPositiveButton(getResources().getString(R.string.nei), new DialogInterface.OnClickListener() 
	        {
			    public void onClick(DialogInterface dialog, int id) 
			    {				    	
			    }
	        }).setNegativeButton(getResources().getString(R.string.ja), new DialogInterface.OnClickListener() 
	        {
		        public void onClick(DialogInterface dialog, int id) 
		        {
		        	intFil = MainActivity.this.getSharedPreferences("statistikk", MODE_PRIVATE);
		    		antallFeil = intFil.getInt("antallFeil", 0);
		    		SharedPreferences.Editor edit = intFil.edit();
		    		edit.putInt("antallFeil", ++antallFeil);
		    		edit.commit();
		    		
		        	mCountDown.cancel();
		        	
		        	Intent tilEnd = new Intent(MainActivity.this, StartActivity.class);
		        	tilEnd.putExtra("antRiktig", antRiktig);
		    		startActivity(tilEnd);
		    		finish();
		        }
	        });
	        return builder.create();
	    }
	}

	//Andre popUp dialog for actionbaren, gi opp? ja nei?
	public class popUp2 extends DialogFragment {
	
		@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(getResources().getString(R.string.giopp))
	        .setPositiveButton(getResources().getString(R.string.nei), new DialogInterface.OnClickListener() 
	        {
			    public void onClick(DialogInterface dialog, int id) 
			    {				    	
			    }
	        }).setNegativeButton(getResources().getString(R.string.ja), new DialogInterface.OnClickListener() 
	        {
		        public void onClick(DialogInterface dialog, int id) 
		        {
		    		intFil = MainActivity.this.getSharedPreferences("statistikk", MODE_PRIVATE);
		    		antallFeil = intFil.getInt("antallFeil", 0);
		    		SharedPreferences.Editor edit = intFil.edit();
		    		edit.putInt("antallFeil", ++antallFeil);
		    		edit.commit();
		    		
		        	mCountDown.cancel();
		        	
		        	Intent tilEnd = new Intent(MainActivity.this, EndActivity.class);
		        	tilEnd.putExtra("antRiktig", antRiktig);
		    		startActivity(tilEnd);
		    		finish();
		        }
	        });
	        return builder.create();
	    }
	}

}
