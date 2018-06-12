package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.joda.time.Days.daysBetween;

public class Renta extends AppCompatActivity {

    Button buttonSearchDate;
    CalendarView calendarView;
    CalendarView calendarViewFinal;
    String idDepto;
    String idUser;
    String nombreDepto;
    String costoDepto;
    String date;
    String dateFinal;
    String user;
    String dateFinalSend;
    String dateSend;
    int costofinal;
    boolean isRenting = true;
    DateTime start;
    DateTime end;
    DateTime now;
    Date startDate;
    Date endDate;
    Date dateNow = new Date();

    TextView myDate;
    TextView myDateFinal;
    DateTimeZone Mexico = DateTimeZone.forID("America/Mexico_City");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renta);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idDepto = bundle.getString("idDepto");
            idUser = bundle.getString("idUser");
            nombreDepto = bundle.getString("nombreDepto");
            costoDepto = bundle.getString("costoDepto");
            user = bundle.getString("user");
            isRenting = bundle.getBoolean("isRenting");

        }

        buttonSearchDate = (Button) findViewById(R.id.buttonSearchDate);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarViewFinal = (CalendarView) findViewById(R.id.calendarViewFinal);
        myDate = (TextView) findViewById(R.id.myDate);
        myDateFinal = (TextView) findViewById(R.id.myDateFinal);

        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdfSend = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDate = sdf.format(new Date(calendarView.getDate()));
        dateSend = sdfSend.format(new Date(calendarView.getDate()));
        startDate = new Date();
        myDate.setText(selectedDate);
        start = new DateTime(calendarView.getDate());
        now = new DateTime(calendarView.getDate());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = sdf.format(new Date((month + 1) + "/" +  dayOfMonth + "/" + year));
                 dateSend = sdfSend.format(new Date(year + "/" +  (month + 1) + "/" + dayOfMonth));

                 start = new DateTime(year, month +1 , dayOfMonth, 5, 0, 0, Mexico);
                 startDate = new Date(year,month ,dayOfMonth);


                myDate.setText(date);
            }
        });

        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdffSend = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDateF = sdff.format(new Date(calendarViewFinal.getDate()));
        myDateFinal.setText(selectedDateF);
        dateFinalSend = sdffSend.format(new Date(calendarView.getDate()));
        endDate = new Date(calendarView.getDate());
        end = new DateTime(calendarView.getDate());
        calendarViewFinal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = sdff.format(new Date( (month + 1) + "/" + dayOfMonth + "/" + year));
                 dateFinalSend = sdffSend.format(new Date( year + "/" + (month + 1) + "/" + dayOfMonth));
                 end = new DateTime(year, month +1, dayOfMonth, 5, 0, 0, Mexico);
                 endDate = new Date(year,month ,dayOfMonth);

                myDateFinal.setText(date);
            }
        });

        buttonSearchDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


            int multi = compare(startDate,endDate) ;

             if ((compare(startDate, endDate) >= 0) && (daysBetween(start,end).getDays() > 0) && (daysBetween(now,start).getDays()>=0)) {

                     if (compare(startDate, endDate) >= 22800) {
                         multi = multi - 22800;
                     }


                     costofinal = Integer.parseInt(costoDepto) * (multi + 1);

                     Intent intent = new Intent(Renta.this, MetodosDePago.class);
                     Bundle extras = new Bundle();
                     extras.putString("idDepto", idDepto);
                     extras.putString("idUser", idUser);
                     extras.putString("date", dateSend);
                     extras.putString("dateFinal", dateFinalSend);
                     extras.putString("user", user);
                     extras.putString("costoDepto", costoDepto);
                     extras.putString("nombreDepto", nombreDepto);
                     extras.putBoolean("isRenting", isRenting);
                     extras.putInt("costoFinal", costofinal);
                     intent.putExtras(extras);
                     startActivity(intent);
                 } else {
                     Toast.makeText(Renta.this, "Los días son incorrectos " , Toast.LENGTH_LONG).show();
                 }

             }






        });


    }




    public int compare(Date start, Date end)
    {

        Calendar startCalendar = new GregorianCalendar();
        Date startDate = new Date(2013,2,2);
        Date endDate = new Date(2013,3,2);
        startCalendar.setTime(start);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(end);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        return diffMonth;
    }

    public void continuar(View view)
    {
        date = myDate.getText().toString();
        dateFinal = myDateFinal.getText().toString();

    }


}
