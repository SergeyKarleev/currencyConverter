package ru.sergeykarleev.useuconverter;

import java.util.ArrayList;

import android.content.Context;

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;


/**����� �������� ��� ����������� ������� �� ������ ����������� ������� �������� (���������)
 * @author SergeyKarleev
 *
 */
public class MyGraphClass extends GraphView{

	GraphView gView;	
	
	public MyGraphClass(Context context) {		
		super(context);		
		gView = new GraphView(context);
	}
	
	/**����� ���������� ������� ������ ��� ����������� ��� � ����������
	 * @param prices ������ �������� ���� �������� ��� ������� ��� ���������� ������
	 * @return ������ GraphView
	 */
	
	public GraphView createGraph(ArrayList<Float> prices){		
		LineGraphSeries<DataPoint> gSeries = new LineGraphSeries<DataPoint>(generateData(prices));
		gView.addSeries(gSeries);		
		return gView;		
	}
	
	 private DataPoint[] generateData(ArrayList<Float> prices) {
	        int count = prices.size();
	        DataPoint[] values = new DataPoint[count];
	        for (int x=0; x<count; x++) {	                 
	            double y = prices.get(x);
	            DataPoint v = new DataPoint(x+1, y);
	            values[x] = v;
	        }
	        return values;
	    }
}
