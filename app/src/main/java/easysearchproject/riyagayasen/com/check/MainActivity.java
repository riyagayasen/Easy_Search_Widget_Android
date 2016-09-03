package easysearchproject.riyagayasen.com.check;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.riyagayasen.easysearchwidget.activity.SearchActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView testTextView;

    ArrayList<String> stringList = new ArrayList<String>();

    final int searchRequestCode = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testTextView = (TextView) findViewById(R.id.test_text_view);
        prepareStringList();
        final Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
        searchIntent.putStringArrayListExtra(SearchActivity.ITEM_LIST_KEY, stringList);

        testTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(searchIntent, searchRequestCode);
            }
        });

    }

    private void prepareStringList() {
        stringList.add("India");
        stringList.add("USA");
        stringList.add("Sri Lanka");
        stringList.add("Sweden");
        stringList.add("France");
        stringList.add("Denmark");
        stringList.add("England");
        stringList.add("Scotland");
        stringList.add("Wales");
        stringList.add("Spain");
        stringList.add("Germany");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == searchRequestCode) {
            switch (resultCode) {
                case RESULT_OK:
                    String chosenString = data.getStringExtra(SearchActivity.CHOSEN_VALUE_KEY);
                    testTextView.setText(chosenString);
                    break;
                default:
                    Toast.makeText(this, "No string chosen", Toast.LENGTH_SHORT).show();
            }

        }
    }
}

