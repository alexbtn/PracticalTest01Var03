package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {
    private Button correct, incorrect;
    private TextView secondaryResult;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.correct_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.incorrect_button:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        correct = (Button)findViewById(R.id.correct_button);
        incorrect = (Button)findViewById(R.id.incorrect_button);
        secondaryResult = (TextView)findViewById(R.id.secondary_text);

        correct.setOnClickListener(buttonClickListener);
        incorrect.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("secondaryResult")) {
            String result = intent.getStringExtra("secondaryResult");
            secondaryResult.setText(result);
        }
    }
}