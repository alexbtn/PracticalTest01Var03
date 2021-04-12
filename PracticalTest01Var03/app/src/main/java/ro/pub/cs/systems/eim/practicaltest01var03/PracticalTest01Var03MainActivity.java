package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {
    private Button plusButton, minusButton, goToSecondary;
    private EditText firstNumber, secondNumber;
    private TextView result;

    private int serviceStatus = -1;

    private IntentFilter intentFilter = new IntentFilter();

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(firstNumber.getText().toString()) || TextUtils.isEmpty(secondNumber.getText().toString()) ) {
                Toast.makeText(getApplicationContext(), "One or both of the numbers are invalid !", Toast.LENGTH_LONG).show();
                return;
            }
            Integer firstValue = Integer.valueOf(firstNumber.getText().toString());
            Integer secondValue = Integer.valueOf(secondNumber.getText().toString());
            switch (v.getId()) {
                case R.id.minus_button:
                    if (firstNumber.getText().toString() != "" && secondNumber.getText().toString() != "") {
                        Integer resuult = firstValue - secondValue;
                        result.setText(firstNumber.getText().toString() + " - " + secondNumber.getText().toString() + " = " + resuult.toString());
                        if (serviceStatus == -1) {
                            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                            intent.putExtra("firstNumber", firstValue);
                            intent.putExtra("secondNumber", secondValue);
                            getApplicationContext().startService(intent);
                            serviceStatus = 1;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "One or both of the numbers are invalid !", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.plus_button:
                    if (firstNumber.getText().toString() != null && secondNumber.getText().toString() != null) {
                        Integer resuult = firstValue + secondValue;
                        result.setText(firstNumber.getText().toString() + " + " + secondNumber.getText().toString() + " = " + resuult.toString());
                        if (serviceStatus == -1) {
                            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                            intent.putExtra("firstNumber", firstValue);
                            intent.putExtra("secondNumber", secondValue);
                            getApplicationContext().startService(intent);
                            serviceStatus = 1;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "One or both of the numbers are invalid !", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.go_to_secondary:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
                    intent.putExtra("secondaryResult", result.getText().toString());
                    startActivityForResult(intent, 7007);
                    break;
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("broadCastExtra"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        plusButton = (Button) findViewById(R.id.plus_button);
        minusButton = (Button) findViewById(R.id.minus_button);
        goToSecondary = (Button) findViewById(R.id.go_to_secondary);
        firstNumber = (EditText) findViewById(R.id.first_number);
        secondNumber = (EditText) findViewById(R.id.second_number);
        result = (TextView) findViewById(R.id.result_text);

        ButtonClickListener buttonClickListener = new ButtonClickListener();
        plusButton.setOnClickListener(buttonClickListener);
        minusButton.setOnClickListener(buttonClickListener);
        goToSecondary.setOnClickListener(buttonClickListener);

        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01var03.sum");
        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01var03.diff");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("firstNumber", firstNumber.getText().toString());
        savedInstanceState.putString("secondNumber", secondNumber.getText().toString());
        savedInstanceState.putString("result", result.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("firstNumber") && savedInstanceState.containsKey("secondNumber") && savedInstanceState.containsKey("result")) {
            Toast.makeText(getApplicationContext(), "The saved values are: " + savedInstanceState.getString("firstNumber") + " " + savedInstanceState.getString("secondNumber") + " " + savedInstanceState.getString("result"), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 7007) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
    }
}