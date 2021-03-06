package hfad.com.majorsystemdrill;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.io.File;

public class ChooseDictionary extends AppCompatActivity {

    RadioGroup radioGroup;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        radioGroup = new RadioGroup(this);
        ll.addView(radioGroup);

        sharedPreferences = getSharedPreferences("activeDictionary", MODE_PRIVATE);
        String activeFile = sharedPreferences.getString("active", "majorsystem_EN.txt");

        // get assets dictionaries
        RadioButton r1 = new RadioButton(this);
        r1.setText("majorsystem_EN.txt");
        if (activeFile.contentEquals("majorsystem_EN.txt")) r1.setChecked(true);
        RadioButton r2 = new RadioButton(this);
        r2.setText("majorsystem_HR.txt");
        if (activeFile.contentEquals("majorsystem_HR.txt")) r2.setChecked(true);

        radioGroup.addView(r1);
        radioGroup.addView(r2);



        File file = new File(getBaseContext().getFilesDir().toString());
        File[] list = file.listFiles();

        for (File f: list) {
            final String name = f.getName();
            RadioButton radio = new RadioButton(this);
            radio.setText(name);
            if (activeFile.contentEquals(name)) radio.setChecked(true);
            radioGroup.addView(radio);


        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                sharedPreferences = getSharedPreferences("activeDictionary", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                RadioButton rb = group.findViewById(checkedId);
                editor.putString("active", rb.getText().toString());
                editor.commit();
                finish();
            }
        });
        setContentView(sv);
    }
}
