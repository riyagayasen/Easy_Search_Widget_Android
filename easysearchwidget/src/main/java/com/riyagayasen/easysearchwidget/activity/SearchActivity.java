package com.riyagayasen.easysearchwidget.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.riyagayasen.easysearchwidget.R;
import com.riyagayasen.easysearchwidget.utilities.AppHelper;
import com.riyagayasen.easysearchwidget.utilities.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchActivity extends AppCompatActivity {

    ArrayList<Item> itemList = new ArrayList<Item>();

    public static String ITEM_LIST_KEY = "item_list";

    public static String CHOSEN_VALUE_KEY = "chosen_value";
    EditText search;

    View thisView;
    LinearLayout list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ArrayList<String> stringList = (ArrayList<String>) getIntent().getSerializableExtra(ITEM_LIST_KEY);
        if(AppHelper.isNullOrBlank(stringList)){
            Toast.makeText(this,"No proper set of Strings passed",Toast.LENGTH_LONG).show();
            this.finish();
        }
        getItemList(stringList);
        thisView = getWindow().getDecorView().getRootView();
        list = (LinearLayout)findViewById(R.id.list);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // add the custom view to the action bar
        actionBar.setCustomView(R.layout.custom_action_bar);
        search = (EditText) actionBar.getCustomView().findViewById(
                R.id.edit_query);





        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchTimer.start();



            }
        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);



    }





    void getItemList(ArrayList<String> stringList) {
        for (String itemString : stringList) {
            Item item = new Item(itemString, AppHelper.distanceBetween(itemString, "", itemString.length(), "".length()));
            itemList.add(item);

        }
        //Sort them in ascending order
        sortItemList(true);


    }

    void updateDistances(String query) {
        for(Item item:itemList){
            item.distance = AppHelper.distanceBetween(query,item.string, query.length(),item.string.length());
        }
    }

    void sortItemList(final Boolean isAscending) {
        Collections.sort(itemList, new Comparator<Item>() {
            /**
             * Compares the two specified objects to determine their relative ordering. The ordering
             * implied by the return value of this method for all possible pairs of
             * {@code (lhs, rhs)} should form an <i>equivalence relation</i>.
             * This means that
             * <ul>
             * <li>{@code compare(a,a)} returns zero for all {@code a}</li>
             * <li>the sign of {@code compare(a,b)} must be the opposite of the sign of {@code
             * compare(b,a)} for all pairs of (a,b)</li>
             * <li>From {@code compare(a,b) > 0} and {@code compare(b,c) > 0} it must
             * follow {@code compare(a,c) > 0} for all possible combinations of {@code
             * (a,b,c)}</li>
             * </ul>
             *
             * @param lhs an {@code Object}.
             * @param rhs a second {@code Object} to compare with {@code lhs}.
             * @return an integer < 0 if {@code lhs} is less than {@code rhs}, 0 if they are
             * equal, and > 0 if {@code lhs} is greater than {@code rhs}.
             * @throws ClassCastException if objects are not of the correct type.
             */
            @Override
            public int compare(Item lhs, Item rhs) {
                if (lhs.distance == rhs.distance)
                    return 0;
                else if (lhs.distance < rhs.distance) {
                    if (isAscending)
                        return -1;
                    else
                        return 1;
                }
                else {
                    if (isAscending)
                        return 1;
                    else
                        return -1;
                }

            }

            /**
             * Compares this {@code Comparator} with the specified {@code Object} and indicates whether they
             * are equal. In order to be equal, {@code object} must represent the same object
             * as this instance using a class-specific comparison.
             * <p/>
             * A {@code Comparator} never needs to override this method, but may choose so for
             * performance reasons.
             *
             * @param object the {@code Object} to compare with this comparator.
             * @return boolean {@code true} if specified {@code Object} is the same as this
             * {@code Object}, and {@code false} otherwise.
             * @see Object#hashCode
             * @see Object#equals
             */
            @Override
            public boolean equals(Object object) {
                return false;
            }
        });
    }

    final CountDownTimer searchTimer = new CountDownTimer(500,250) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

            String query = search.getText().toString();
            list.removeAllViews();
            if(!AppHelper.isNullOrBlank(query)) {

                updateDistances(query);
                sortItemList(true);
                for (final Item item : itemList) {
                    TextView result = new TextView(SearchActivity.this);
                    result.setText(item.string);
                    result.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent finishIntent = new Intent();
                            finishIntent.putExtra(CHOSEN_VALUE_KEY, item.string);
                            SearchActivity.this.setResult(RESULT_OK, finishIntent);
                            SearchActivity.this.finish();
                        }
                    });
                    result.setPadding(20, 20, 20, 20);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 0, 10);
                    result.setLayoutParams(layoutParams);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        result.setBackground(AppHelper.getDrawableFromRes(SearchActivity.this, R.drawable.shadow_background));
                    }
                    list.addView(result);
                }
            }
        }
    };


    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        SearchActivity.this.setResult(RESULT_CANCELED, i);
        SearchActivity.this.finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
