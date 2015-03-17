package ru.sergeykarleev.useuconverter.classes;

import java.util.ArrayList;

import android.content.Context;

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

/**
 * ����� �������� ��� ����������� ������� �� ������ ����������� ������� ��������
 * (���������)
 * 
 * @author SergeyKarleev
 * 
 */
public class MyGraphClass extends GraphView {

	GraphView gView;

	public MyGraphClass(Context context) {
		super(context);
		gView = new GraphView(context);
	}

	/**
	 * ����� ���������� ������� ������ ��� ����������� ��� � ����������
	 * 
	 * @param prices
	 *            ������ �������� ���� �������� ��� ������� ��� ����������
	 *            ������
	 * @return ������ GraphView
	 */

	public GraphView createGraph(ArrayList<Double> quotes) {
		LineGraphSeries<DataPoint> gSeries = new LineGraphSeries<DataPoint>(
				generateData(quotes));
		gView.addSeries(gSeries);
		return gView;
	}

	private DataPoint[] generateData(ArrayList<Double> quotes) {
		int count = quotes.size();
		DataPoint[] values = new DataPoint[count];
		for (int x = 0; x < count; x++) {
			Double y = quotes.get(x);
			DataPoint v = new DataPoint(x + 1, y);
			values[x] = v;
		}
		return values;
	}
}
