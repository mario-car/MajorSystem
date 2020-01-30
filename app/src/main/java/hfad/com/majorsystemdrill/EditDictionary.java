package hfad.com.majorsystemdrill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.io.File;

public class EditDictionary extends AppCompatActivity {

    RadioGroup radioGroup;
    Button btnEdit;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ScrollView sv = new ScrollView(this);
        ll.addView(sv);
        LinearLayout ll1 = new LinearLayout(this);
        ll1.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll1);
        radioGroup = new RadioGroup(this);
        ll1.addView(radioGroup);

        // get assets dictionaries
        RadioButton r1 = new RadioButton(this);
        r1.setText("majorsystem_EN.txt");
        RadioButton r2 = new RadioButton(this);
        r2.setText("majorsystem_HR.txt");
        radioGroup.addView(r1);
        radioGroup.addView(r2);


        File file = new File(getBaseContext().getFilesDir().toString());
        File[] list = file.listFiles();

        for (File f: list) {
            final String name = f.getName();
            RadioButton radio = new RadioButton(this);
            radio.setText(name);
            radioGroup.addView(radio);


        }

        LinearLayout ll2 = new LinearLayout(this);
        ll2.setOrientation(LinearLayout.HORIZONTAL);
        btnEdit = new Button(this);
        btnEdit.setText("Edit");
        btnDelete = new Button(this);
        btnDelete.setText("Delete");

        ll2.addView(btnEdit);
        ll2.addView(btnDelete);
        ll.addView(ll2);

        setContentView(ll);

        btnEdit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
                    RadioButton rb = radioGroup.findViewById(checkedRadioButton);
                    String fileName = rb.getText().toString();
                    Intent intent = new Intent(v.getContext(), ModifyDictionary.class);
                    intent.putExtra("fileName", fileName);
                    startActivity(intent);
                }
            });

        btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
                    RadioButton rb = radioGroup.findViewById(checkedRadioButton);
                    String fileName = rb.getText().toString();
                    File file = new File(getBaseContext().getFilesDir().toString() + "/" + fileName);
                    file.delete();
                    recreate();
                }
            });

    }
}
