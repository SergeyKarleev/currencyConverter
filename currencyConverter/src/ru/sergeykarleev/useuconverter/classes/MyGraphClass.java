package ru.sergeykarleev.useuconverter.classes;

import java.util.ArrayList;

import android.content.Context;

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

/**
 *  ласс работает над построением графика на основе полученного массива значений
 * (котировок)
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
	 * ћетод возвращает готовый график дл€ отображени€ его в контейнере
	 * 
	 * @param prices
	 *            массив значений цены закрыти€ дл€ каждого дн€ выбранного
	 *            мес€ца
	 * @return объект GraphView
	 */

	public GraphView createGraph(Double[] doubles) {
		LineGraphSeries<DataPoint> gSeries = new LineGraphSeries<DataPoint>(
				generateData(doubles));
		gView.addSeries(gSeries);
		return gView;
	}

	private DataPoint[] generateData(Double[] doubles) {
		int count = doubles.length;
		DataPoint[] values = new DataPoint[count];
		for (int x = 0; x < count; x++) {
			Double y = doubles[x];
			DataPoint v = new DataPoint(x + 1, y);
			values[x] = v;
		}
		return values;
	}
}
