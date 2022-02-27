package thaingochuubang.com.emojigame;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processResult();
    }

    private void processResult() {
        Intent intent = getIntent();
        boolean isWin = intent.getBooleanExtra("isWin", false);
        if(isWin == false){
            setContentView(R.layout.activity_fail);
        }
        else {
            setContentView(R.layout.activity_success);
        }
    }
}
