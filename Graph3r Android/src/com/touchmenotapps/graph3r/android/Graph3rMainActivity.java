package com.touchmenotapps.graph3r.android;

import com.touchmenotapps.graph3r.android.Graph3rListFragment.OnGraphTypeSelectedListener;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

/**
 * 
 * @author Arindam Nath
 *
 */
public class Graph3rMainActivity extends FragmentActivity implements OnGraphTypeSelectedListener{	
   
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTitleBar);
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.layout_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_appbar);
        
        if (findViewById(R.id.fragment_holder) != null) {
            if (savedInstanceState != null) 
                return;
            Graph3rListFragment listFragment = new Graph3rListFragment();
            listFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_holder, listFragment).commit();
        }
    }

	@Override
	public void onGraphTypeSelected(int position) {
		Graph3rDetailsFragment detailFrag = (Graph3rDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        if (detailFrag != null) {
            detailFrag.updateArticleView(position);

        } else {
            Graph3rDetailsFragment newDetailFragment = new Graph3rDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(Graph3rDetailsFragment.ARG_POSITION, position);
            newDetailFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_holder, newDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
	}
}