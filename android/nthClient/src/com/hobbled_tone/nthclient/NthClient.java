package com.hobbled_tone.nthclient;

import android.os.Bundle;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class NthClient extends ListActivity {

	public static final String TAG = "NthClient";
	
	//private NthAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //String[] values = new String[] { "Item 1", "Item 2" };
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_element_adapter, R.id.listTitle, values);
        setListAdapter(new NthAdapter(getApplicationContext(), new ElementDataSource().getElements()));
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nth_client, menu);
        return true;
    }
    
    @Override
    public void onListItemClick(ListView list, View view, int pos, long id) {
    	Log.d(TAG,"item clicked, running asynctask");
    	
    	DataAccess getData = new DataAccess();
    	getData.execute();
    }
}
