package com.example.android.beeassignment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series,series1,series2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void submit(View view) {

        LineGraphSeries<DataPoint> series,series1,series2;
        double xi, yi=0, xv, yv=0, xp, yp, omega, f, v, i, vo, io, r, l, c, xl, xc, imp, pd;


        r = Double.parseDouble(((EditText) findViewById(R.id.R)).getText().toString());

        c = Double.parseDouble(((EditText) findViewById(R.id.C)).getText().toString());

        l = Double.parseDouble(((EditText) findViewById(R.id.L)).getText().toString());

        v = 220;

        f = 50;

        omega = 2 * Math.PI * f;

        if(c != 0)
            xc = 1 / (omega * c);
        else
            xc = 0;

        xl = omega * l;

        imp = Math.sqrt(Math.pow(r, 2) + Math.pow((xl - xc), 2));

        i = v / imp;

       vo = v * Math.sqrt(2);

        io = i * Math.sqrt(2);

        pd = Math.acos(r / imp);


        xv = 0.0;

        GraphView graph = (GraphView) findViewById(R.id.graph);



        series = new LineGraphSeries<DataPoint>();
        series.setTitle("Voltage");

        series1 = new LineGraphSeries<DataPoint>();
        series1.setColor(Color.GREEN);
        series1.setTitle("Current");

        series2 = new LineGraphSeries<DataPoint>();
        series2.setColor(Color.RED);
        series2.setTitle("Power");

        for (int j = 0; j < 100; j++) {

            if(xc > xl) {
                yv = vo * Math.sin(omega * xv);
                yi = io * Math.sin(omega * xv + pd);
            }
            if(xl > xc) {
                yv = vo * Math.sin(omega * xv);
                yi = io * Math.sin(omega * xv - pd);
            }
            if(xc == xl) {
                yv = vo * Math.sin(omega * xv);
                yi = io * Math.sin(omega * xv );
            }

            yp = yv * yi;
            series.appendData(new DataPoint(xv, yv), true, 100);
            series1.appendData(new DataPoint(xv, yi), true, 100);
            series2.appendData(new DataPoint(xv, yp), true, 100);
            xv += 0.0003;
        }

        graph.addSeries(series);
        graph.addSeries(series1);
        graph.addSeries(series2);
    }
}
