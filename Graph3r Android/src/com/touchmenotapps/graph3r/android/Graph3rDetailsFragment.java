package com.touchmenotapps.graph3r.android;

import java.util.ArrayList;
import java.util.Random;

import com.touchmenotapps.Graph3r.Graph;
import com.touchmenotapps.Graph3r.GraphColors;
import com.touchmenotapps.Graph3r.bar.BarGraphRenderer;
import com.touchmenotapps.Graph3r.bar.BarObject;
import com.touchmenotapps.Graph3r.bar.BarGraphView.BarGraphInterface;
import com.touchmenotapps.Graph3r.line.LineGraphObject;
import com.touchmenotapps.Graph3r.line.LineGraphRenderer;
import com.touchmenotapps.Graph3r.meter.MeterGraphRenderer;
import com.touchmenotapps.Graph3r.pie.PieGraphObject;
import com.touchmenotapps.Graph3r.pie.PieGraphRenderer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * @author Arindam Nath
 *
 */

public class Graph3rDetailsFragment extends Fragment{
	public final static String ARG_POSITION = "position";
    private int mCurrentPosition = -1;
    private ArrayList<PieGraphObject> mPieGraphObject = new ArrayList<PieGraphObject>();
	private ArrayList<LineGraphObject> mLineChartObject = new ArrayList<LineGraphObject>(0);
	
	private LineGraphRenderer mLineRenderer;
	private PieGraphRenderer mPieRenderer;
	private BarGraphRenderer mBarRenderer;
	private MeterGraphRenderer mMeterRenderer;
	
	private LinearLayout mLayout;
	private Random mNumGen  = new Random();
	private int mSize = 240;
	private int mWidth = 0 , mHeight = 0;
	private double meterData[] = { 5, 12, 25, 30 }; 
	private double meterReading = 11; 

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        if (savedInstanceState != null) 
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        View view = inflater.inflate(R.layout.layout_graph_details, container, false);
        mLayout = (LinearLayout) view.findViewById(R.id.graph_holder);
        //Get the graph layout width and height
       if (getFragmentManager().findFragmentById(R.id.detail_fragment) == null) {
	        mWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
	        mHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        } else {
        	mWidth = mLayout.getWidth();
        	mHeight = mLayout.getHeight();
        }
        //Check the max value
        if(mWidth < mHeight)
        	mSize = mWidth;
        else if(mHeight < mWidth) 
        	mSize = mHeight;
        return view;
    }	
    
    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
        switch(position) {
        case 0:
        	/*************************************************************
             * LINE GRAPH IMPLEMENTATION
             *************************************************************/
            for(int j = 0; j < 2; j++) {
            	int[] values = new int[12];
    			for (int i = 0; i < 12; i++) {
    				int val = mNumGen.nextInt(256);
    				values[i] = val;
    			}
    			if(j== 0)
    				mLineChartObject.add(new LineGraphObject(values, "Plot group " + String.valueOf(j), GraphColors.HOLO_DARK_BLUE));
    			else 
    				mLineChartObject.add(new LineGraphObject(values, "Plot group " + String.valueOf(j), GraphColors.HOLO_DARK_GREEN));
            }
    		mLineRenderer = new LineGraphRenderer(mLayout, mLineChartObject);
    		mLineRenderer.setStyle(Graph.STYLE_LINE_GRAPH_FILL);
    		mLineRenderer.setGraphWidthAndHeight(mSize, mSize);
    		mLineRenderer.setGraphIsZoomable(true);
    		mLineRenderer.setGraphIsPannable(true);
    		mLineRenderer.setShowAverageLine(true);
    		mLineRenderer.setGraphHasPlotHighlighter(true);
    		mLineRenderer.setGraphPadding(30);
    		mLineRenderer.setShowLegends(true);
    		try {
    			mLayout.addView(mLineRenderer.renderGraph());
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        	break;
        case 1:
        	/*************************************************************
             * BAR GRAPH IMPLEMENTATION
             *************************************************************/
    		ArrayList <String> barLabels = new ArrayList<String>();
    		ArrayList<int[]> barColors = new ArrayList<int[]>();
    		barColors.add(new int[] {0xFF13C9F5 , 0xFF0464BB});
    		ArrayList<Double> data = new ArrayList<Double>();
    		for (int i = 0; i < 12; i++) {
    			data.add((double) mNumGen.nextInt(256));
    			barLabels.add(String.valueOf(i));
    		}
    		mBarRenderer = new BarGraphRenderer(mLayout, data, barColors);
    		mBarRenderer.setGraphWidthAndHeight(mSize, mSize);
    		mBarRenderer.setBarLabels(barLabels);
    		mBarRenderer.setStyle(Graph.STYLE_BAR_NORMAL);
    		mBarRenderer.setYAxisLabel("Y_LABEL");
    		mBarRenderer.setXAxisLabelSize(15);
    		mBarRenderer.setGraphIsZoomable(true);
    		mBarRenderer.setGraphIsPannable(true);
    		mBarRenderer.setGraphIsClickable(true, new  BarGraphInterface() {
    			@Override
    			public void onBarClickedListener(BarObject data) {
    				Toast.makeText(getActivity(), "Value : " + data.getValue(), Toast.LENGTH_LONG).show();
    			}
    		});
    		
    		try {
    			mLayout.addView(mBarRenderer.renderGraph());
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		mLayout.setBackgroundColor(Color.WHITE);
        	break;
        case 2:
        	/*************************************************************
             * PIE GRAPH IMPLEMENTATION
             *************************************************************/
            for (int i = 0; i < 5 ; i++) {
            	mPieGraphObject.add(new PieGraphObject(mNumGen.nextInt(256), 
            			"Value " + mNumGen.nextInt(1000),
            			0xff000000 + 256*256*mNumGen.nextInt(256) + 256*mNumGen.nextInt(256) + mNumGen.nextInt(256)));
            }
    	    mPieRenderer = new PieGraphRenderer(mLayout, mPieGraphObject);
    	    mPieRenderer.setGraphWidthAndHeight(mSize, mSize);
    	    mPieRenderer.setGraphIsPannable(true);
    	    mPieRenderer.setGraphIsZoomable(true);
    	    mPieRenderer.setGraphIsClickable(true);
    	   try {
    			mLayout.addView(mPieRenderer.renderGraph());
    		} catch (Exception e1) {
    			e1.printStackTrace();
    		}
        	break;
        case 3:
        	/*************************************************************
             * METER GRAPH IMPLEMENTATION
             *************************************************************/
    		mMeterRenderer = new MeterGraphRenderer(mLayout, meterData, meterReading, 60);
    		mMeterRenderer.setGraphSize(mSize);
    		mMeterRenderer.setGraphDialTextSize(20);
    		mMeterRenderer.setGraphDialBorderGradientThickness(30);
    		try {
    			mLayout.addView(mMeterRenderer.renderGraph());
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        	break;
        }
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
