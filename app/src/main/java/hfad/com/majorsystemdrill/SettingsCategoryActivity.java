package hfad.com.majorsystemdrill;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsCategoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] entities = getResources().getStringArray(R.array.settingsCategoryList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entities);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, ChooseDictionary.class));
                break;
            case 1:
                startActivity(new Intent(this, NewDictionary.class));
                break;
            case 2:
                startActivity(new Intent(this, EditDictionary.class));
                break;
        }
        finish();
    }
}
