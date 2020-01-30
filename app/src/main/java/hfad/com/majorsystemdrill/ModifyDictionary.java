package hfad.com.majorsystemdrill;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ModifyDictionary extends AppCompatActivity {

    int no;
    ArrayList<String> list;
    EditText number;
    EditText image;
    boolean invalidImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_dictionary);

        invalidImage = false;
        number = findViewById(R.id.numberKey);
        image = findViewById(R.id.imagineNumber);
        TextView dictName = findViewById(R.id.nameOfDict);
        dictName.setText(getIntent().getStringExtra("fileName"));

        no = 0;
        list = getArrayList();
        number.setText(Integer.toString(no));
        image.setText(list.get(no));
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String in = number.getText().toString();
                try {
                    int n = Integer.valueOf(in);
                    if (n >= 0 && n < list.size()) {
                        invalidImage = false;
                        no = n;
                        image.setText(list.get(no));
                    } else {
                        invalidImage = true;
                        image.setText("");
                    }
                } catch (NumberFormatException e){
                    invalidImage = true;
                    image.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void actionOnButtonClicked(View view) {
        Button btn = findViewById(view.getId());
        String in = number.getText().toString();

        if (btn.getText().toString().contentEquals("back")) {
            updateList();
            no--;
            if (no < 0) no = 0;
            number.setText(Integer.toString(no));
            image.setText(list.get(no));
        } else if (btn.getText().toString().contentEquals("next")) {
            updateList();
            no++;
            number.setText(Integer.toString(no));
            try {
                image.setText(list.get(no));
            }catch (IndexOutOfBoundsException e) {
                image.setText("");
            }
        }

    }

    private void updateList() {
        if (invalidImage)
            return;

        try {
            list.set(no, image.getText().toString());
        }catch (IndexOutOfBoundsException e) {
            list.add(no, image.getText().toString());
        }
    }


//    SharedPreferences sharedPreferences = getSharedPreferences("activeDict", MODE_PRIVATE);
//    SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("default", "cowboy_girl.txt");
//        editor.commit();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_dict, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_dict) {
            updateList();

            TextView fileEditText = findViewById(R.id.nameOfDict);
            String filename = fileEditText.getText().toString();

            try {
                FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                for (String s : list) {
                    outputStream.write(s.getBytes());
                    outputStream.write("\n".getBytes());
                }
                outputStream.close();
                finish();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> getArrayList() {
        ArrayList<String> dictionary = new ArrayList<>();
       String fileName = getIntent().getStringExtra("fileName");
        try {
            InputStream is;
            if (fileName.contentEquals("majorsystem_HR.txt")) {
                is = getAssets().open(fileName);
            } else {
                is = openFileInput(fileName);
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
