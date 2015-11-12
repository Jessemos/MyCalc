package com.start.jmp.mycalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String CurrentFormulaText = ""; // No values
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String DIVIDE = "/";
    public static final String MULTIPLY = "*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateAllButtonsListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void activateAllButtonsListener() {
        Button buttonNum1 = (Button) findViewById(R.id.ButtonNum1);
        buttonNum1.setOnClickListener(this); // calling onClick() method
        Button buttonNum2 = (Button) findViewById(R.id.ButtonNum2);
        buttonNum2.setOnClickListener(this); // calling onClick() method
        Button buttonNum3 = (Button) findViewById(R.id.ButtonNum3);
        buttonNum3.setOnClickListener(this); // calling onClick() method
        Button buttonNum4 = (Button) findViewById(R.id.ButtonNum4);
        buttonNum4.setOnClickListener(this); // calling onClick() method
        Button buttonNum5 = (Button) findViewById(R.id.ButtonNum5);
        buttonNum5.setOnClickListener(this); // calling onClick() method
        Button buttonNum6 = (Button) findViewById(R.id.ButtonNum6);
        buttonNum6.setOnClickListener(this); // calling onClick()6 method
        Button buttonNum7 = (Button) findViewById(R.id.ButtonNum7);
        buttonNum7.setOnClickListener(this); // calling onClick() method
        Button buttonNum8 = (Button) findViewById(R.id.ButtonNum8);
        buttonNum8.setOnClickListener(this); // calling onClick() method
        Button buttonNum9 = (Button) findViewById(R.id.ButtonNum9);
        buttonNum9.setOnClickListener(this); // calling onClick() method
        Button buttonNum0 = (Button) findViewById(R.id.ButtonNum0);
        buttonNum0.setOnClickListener(this); // calling onClick() method
        Button buttonFuncMinus = (Button) findViewById(R.id.ButtonFuncMinus);
        buttonFuncMinus.setOnClickListener(this); // calling onClick() method
        Button buttonFuncPlus = (Button) findViewById(R.id.ButtonFuncPlus);
        buttonFuncPlus.setOnClickListener(this); // calling onClick() method
        Button buttonFuncDivide = (Button) findViewById(R.id.ButtonFuncDivide);
        buttonFuncDivide.setOnClickListener(this); // calling onClick() method
        Button buttonFuncMultiply = (Button) findViewById(R.id.ButtonFuncMultiply);
        buttonFuncMultiply.setOnClickListener(this); // calling onClick() method
        Button ClearButton = (Button) findViewById(R.id.ClearButton);
        ClearButton.setOnClickListener(this); // calling onClick() method
        Button CalculateButton = (Button) findViewById(R.id.ButtonCalc);
        CalculateButton.setOnClickListener(this); // calling onClick() method
        Button Quit = (Button) findViewById(R.id.QuitButton);
        Quit.setOnClickListener(this); // calling onClick() method
        Button BackSpace = (Button) findViewById(R.id.ButtonFuncBackspace);
        BackSpace.setOnClickListener(this); // calling onClick() method
        Button ClearLastNum = (Button) findViewById(R.id.ClearButton);
        ClearLastNum.setOnClickListener(this); // calling onClick() method
    }

    @Override
    public void onClick(View v) {

        Button buttons = (Button) v;
        TextView CurrentFormulaTextView = (TextView) findViewById(R.id.CurrentFormulaView);
        String buttonsString = buttons.getText().toString();
        if (tryParseInt(buttonsString)>0) CurrentFormulaText=CurrentFormulaText.concat(buttonsString);
        else
        if (CurrentFormulaText.length() == 0)
            if (buttonsString.equals(MINUS)) CurrentFormulaText=MINUS;
                    else
                        if (tryParseInt(buttonsString) > 0)
                            CurrentFormulaText=buttonsString;
                            else
                                  CurrentFormulaText="";
            else
            switch (buttonsString.toUpperCase())
            {
                /* case "+":
                case "-":
                case "X":
                case "/":
                    CurrentFormulaText = CurrentFormulaText.concat(buttonsString);
                    break; */
                case "<":
                case ">":
                    if (CurrentFormulaText.length() > 0)
                        CurrentFormulaText = CurrentFormulaText.substring(0, CurrentFormulaText.length() - 1);
                    break;
                case "<<":
                case ">>":
                    // eraseLastNumber(CurrentFormulaText);
                    break;
                case "CLEAR":
                    CurrentFormulaText = "";
                    break;
                case "CALCULATE":
                    CurrentFormulaText.trim();
                    if(tryParseInt(CurrentFormulaText.charAt(CurrentFormulaText.length()-1)+"")<0)
                        CurrentFormulaText=CurrentFormulaText.substring(0,CurrentFormulaText.length()-1);
                    CurrentFormulaText = calcByOrderOfOperation(CurrentFormulaText);

                    break;
                case "CLOSE CALCULATOR":
                    finish();
                    System.exit(0);
                    break;
                default:
                    if (tryParseInt(CurrentFormulaText.charAt(CurrentFormulaText.length() - 1) + "") > -1)
                        CurrentFormulaText = CurrentFormulaText.concat(buttonsString);

                    break;
            }
        CurrentFormulaTextView.setText(CurrentFormulaText);

    }

    private int extractRightNumber(String formulaResult, int index) {
        int i = index+1;
        while ((i < formulaResult.length()-1) && tryParseInt("" + formulaResult.charAt(i+1)) > -1)
            i++;
        return tryParseInt(formulaResult.substring(index+1,i+1));
    }

    private int extractLeftNumber(String formulaResult, int index) {
        int i = index-1;
        while ((i>0) && tryParseInt("" + formulaResult.charAt(i-1)) > -1)
            i--;
        return tryParseInt(formulaResult.substring(i,index));
    }

    private int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);

        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String calculateTwoNumbers(int num1, int num2, String Func)
    {
        int result=0;
        switch (Func)
        {
            case PLUS:
                result = num1+num2;
                break;
            case MINUS:
                result = num1-num2;
                break;
            case MULTIPLY:
                result = num1*num2;
                break;
            case DIVIDE:
                result = num1/num2;
                break;
        }
        return ""+result;
    }

    private String calcByOrderOfOperation(String formula){
        String currentFormula=formula,tempCalc="",fs="",newform="";
        int index=0,leftN=0,rightN=0;
        String currentFunc="";
        boolean end=true;
        while (end) {

            if ((index=currentFormula.indexOf(MULTIPLY)) > -1)currentFunc=MULTIPLY;
            else if ((index=currentFormula.indexOf(DIVIDE)) > -1) currentFunc=DIVIDE;
                else if ((index=currentFormula.indexOf(MINUS)) > -1)currentFunc=MINUS;
                     else if ((index=currentFormula.indexOf(PLUS)) > -1)currentFunc=PLUS;
                        else end = false;
             //replace an expression with new result
            if (index!= -1)
            {
                leftN=extractLeftNumber(currentFormula, index);
                rightN=extractRightNumber(currentFormula, index);
                tempCalc=calculateTwoNumbers(leftN, rightN,currentFunc);
                fs = leftN+"\\"+currentFunc+rightN;
                currentFormula=currentFormula.replaceAll(fs,tempCalc);
            }
        }
        return currentFormula;
    }


}
