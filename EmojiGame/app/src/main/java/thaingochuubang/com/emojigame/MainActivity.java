package thaingochuubang.com.emojigame;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> randomEmojis;
    String emojiTarget;
    int wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        initGame();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initGame();
    }

    private void initGame()
    {

        String[] emojis = getRandomEmojis();
        randomEmojis = new ArrayList<>(Arrays.asList(emojis));

        setEmojiTarget();

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item, emojis);

        GridView mainGridView = findViewById(R.id.mainGridView);
        mainGridView.setAdapter(myAdapter);
        mainGridView.setOnItemClickListener(onSelectedEmoji());
    }

    private String[] getRandomEmojis()
    {
        String[] emojis = getResources().getStringArray(R.array.list_emojis);
        Collections.shuffle(Arrays.asList(emojis));
        return emojis;
    }

    private void setEmojiTarget()
    {
        TextView mainItem = findViewById(R.id.mainItem);

        Random rand = new Random();
        int pos = rand.nextInt(randomEmojis.size());
        emojiTarget= randomEmojis.get(pos);
        mainItem.setText(emojiTarget);
    }

    private AdapterView.OnItemClickListener onSelectedEmoji()
    {
        return (adapterView, view, i, l) -> {
            TextView textView = (TextView) view;
            String pickedEmoji = textView.getText().toString();
            if (pickedEmoji.equals(emojiTarget)) {
                textView.setText("");
                randomEmojis.remove(pickedEmoji);

                if (randomEmojis.size() == 0) {
                    runActivityResult(true);
                    return;
                }

                setEmojiTarget();
            }
            else {
                wrong++;
                if(wrong >=5){
                    runActivityResult(false);
                    wrong = 0;
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Failed! Remaining: "+(5-wrong)+" lives",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void runActivityResult(boolean isWin)
    {
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putExtra("isWin", isWin);
        startActivity(intent);
    }
}
