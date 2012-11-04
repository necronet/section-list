package com.android.sectionlist.sample;

import android.app.Activity;
import android.os.Bundle;

/**
 * Example activity.
 */
public class SectionListActivity extends Activity {


    SectionListItem[] exampleArray = { // Comment to prevent re-format
    new SectionListItem("Test 1 - A", "A"), //
            new SectionListItem("Test 2 - A", "A"), //
            new SectionListItem("Test 3 - A", "A"), //
            new SectionListItem("Test 4 - A", "A"), //
            new SectionListItem("Test 5 - A", "A"), //
            new SectionListItem("Test 6 - B", "B"), //
            new SectionListItem("Test 7 - B", "B"), //
            new SectionListItem("Test 8 - B", "B"), //
            new SectionListItem("Test 9 - Long", "Long section"), //
            new SectionListItem("Test 10 - Long", "Long section"), //
            new SectionListItem("Test 11 - Long", "Long section"), //
            new SectionListItem("Test 12 - Long", "Long section"), //
            new SectionListItem("Test 13 - Long", "Long section"), //
            new SectionListItem("Test 14 - A again", "A"), //
            new SectionListItem("Test 15 - A again", "A"), //
            new SectionListItem("Test 16 - A again", "A"), //
            new SectionListItem("Test 17 - B again", "B"), //
            new SectionListItem("Test 18 - B again", "B"), //
            new SectionListItem("Test 19 - B again", "B"), //
            new SectionListItem("Test 20 - B again", "B"), //
            new SectionListItem("Test 21 - B again", "B"), //
            new SectionListItem("Test 22 - B again", "B"), //
            new SectionListItem("Test 23 - C", "C"), //
            new SectionListItem("Test 24 - C", "C"), //
            new SectionListItem("Test 25 - C", "C"), //
            new SectionListItem("Test 26 - C", "C"), //
    };

    

    private SectionListAdapter sectionAdapter;

    private SectionListView listView;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        sectionAdapter = new SectionListAdapter(getLayoutInflater(),exampleArray);
        listView = (SectionListView) findViewById(getResources().getIdentifier(
                "section_list_view", "id",
                this.getClass().getPackage().getName()));
        listView.setAdapter(sectionAdapter);
    }

}