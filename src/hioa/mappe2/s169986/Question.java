package hioa.mappe2.s169986;

import android.app.Activity;

//Denne klassen er for å gjøre det enklere å kombinere 
//riktig svar, alernativ 1, 2, 3 og hintet til riktig spørsmål
public class Question extends Activity{
	String questionText;
    String correctAnswerText;       
    String wrongAnswer1;
    String wrongAnswer2;
    String wrongAnswer3;
    String hint;
    
    Question (String qst, String cAns, String wAns1, String wAns2, String wAns3, String h)
    {
        questionText = qst;
        correctAnswerText = cAns;
        wrongAnswer1 = wAns1;
        wrongAnswer2 = wAns2;
        wrongAnswer3 = wAns3;
        hint = h;
    }
}