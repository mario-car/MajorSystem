package hfad.com.majorsystemdrill;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class NewDictionary extends AppCompatActivity {

    int no;
    ArrayList<String> list;
    TextView number;
    EditText image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dictionary);

        number = findViewById(R.id.number);
        image = findViewById(R.id.imagineNumber);
        no = 0;
        list = new ArrayList<>();
        number.setText(Integer.toString(no));
    }

    public void actionOnButtonClicked(View view) {
        Button btn = findViewById(view.getId());
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

            EditText fileEditText = findViewById(R.id.nameOfFile);
            String filename = fileEditText.getText().toString();

            if (filename.isEmpty()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Warning");
                alert.setMessage("Please provide name of the dictionary");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeOptionsMenu();
                    }
                });
                alert.show();
            } else {
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
            }
        return super.onOptionsItemSelected(item);
    }
}
