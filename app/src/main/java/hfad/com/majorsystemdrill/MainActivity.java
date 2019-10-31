 package hfad.com.majorsystemdrill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> dict;
    int displayedNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dict = getArrayList();
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
            Intent intent = new Intent(this, SettingsCategoryActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClicked(View view) {
        Button btn = findViewById(view.getId());
        String btnText = btn.getText().toString();
        TextView display = findViewById(R.id.display);

        switch (btnText) {
            case "clear":
                displayedNumber = 0;
                display.setText("");
                break;
            case "back":
                displayedNumber /= 10;
                display.setText(numberToImage(displayedNumber));
                break;
            default :
                int numberButton;
                try {
                    numberButton = Integer.parseInt(btnText);
                } catch (Exception e) {
                    numberButton = 0;
                }
                displayedNumber = displayedNumber * 10 + numberButton;
                if (displayedNumber >= dict.size()) {
                    displayedNumber = numberButton;
                }
                display.setText(numberToImage(displayedNumber));
        }
    }

    private String numberToImage(int number) {
        String image;
        try {
            image = dict.get(number);
        } catch (IndexOutOfBoundsException e) {
            image = "?";
        }
        return (number + ": " + image);
    }

    private ArrayList<String> getArrayList() {
        ArrayList<String> dictionary = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("activeDictionary", MODE_PRIVATE);
        String file = sharedPreferences.getString("active", "majorsystem_HR.txt");
        try {
            InputStream is;
            if (file.contentEquals("majorsystem_HR.txt")) {
                is = getAssets().open(file);
            } else {
                is = openFileInput(file);
            }
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                dictionary.add(line);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }
}
