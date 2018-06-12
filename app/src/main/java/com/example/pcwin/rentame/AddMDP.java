package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.lang.Boolean.FALSE;


public class AddMDP extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    EditText editTextNoTarjeta;
    EditText editTextNombreP;
    EditText editTextSC;
    Boolean isRenting;
    String dato;
    String idUser;
    String user;
    String idDepto;
    String date;
    String nombreDepto;
    String costoDepto;
    String dateFinal;
    String fechaMDP;
    int costoFinal;
    Button buttonAdd;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {

            idUser = bundle.getString("idUser");
            user = bundle.getString("user");
            idDepto = bundle.getString("idDepto");
            isRenting = bundle.getBoolean("isRenting");
            date = bundle.getString("date");
            dateFinal = bundle.getString("dateFinal");
            nombreDepto = bundle.getString("nombreDepto");
            costoDepto = bundle.getString("costoDepto");
            costoFinal = bundle.getInt("costoFinal");




        }
        setContentView(R.layout.activity_add_mdp);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        editTextNoTarjeta = (EditText) findViewById(R.id.editTextNoCuenta);
        editTextNombreP = (EditText) findViewById(R.id.editTextNombreP);
        editTextSC = (EditText) findViewById(R.id.editTextsecurityCode);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddMDP.this,
                        android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/yyy: " + month +  "/" + year);

                dato = year + "-" + month ;
                fechaMDP = year + "-" + month + "-" + day;


                mDisplayDate.setText(dato);
            }
        };
    }

    public void AddMeDP(View view) {
        final String noTarjeta = editTextNoTarjeta.getText().toString();
        String nombreP = editTextNombreP.getText().toString();
        String codigoS = editTextSC.getText().toString();
        String dateTarjeta = mDisplayDate.getText().toString();
        if (noTarjeta.equals("") || dateTarjeta.equals("") || nombreP.equals("") || codigoS.equals(""))
        {
            Toast.makeText(this, "Por favor llene todos los campos, hay campos incompletos", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(!isValidMobile(noTarjeta))
            {
                Toast.makeText(this, "El numero de tarjeta no es válido" + noTarjeta, Toast.LENGTH_LONG).show();
            }
            else
            {
                if (!isValidCS(codigoS))
                {
                    Toast.makeText(this, "El numero de seguridad no es válido", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(!nombreP.matches("[a-zA-Z ]+"))
                    {
                        Toast.makeText(this, "El nombre ingresado no es válido", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(isRenting)
                        {
                            buttonAdd.setEnabled(false);

                            addMDP(idUser,noTarjeta);
                            new CountDownTimer(3000, 1000) {
                                public void onFinish() {
                                    // When timer is finished
                                    // Execute your code here
                                    Intent intent = new Intent(AddMDP.this, MetodosDePago.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("idUser", idUser);
                                    extras.putString("user", user);
                                    extras.putString("idDepto",idDepto);
                                    extras.putBoolean("isRenting",isRenting);
                                    extras.putString("date",date);
                                    extras.putString("dateFinal" ,dateFinal);
                                    extras.putString("nombreDepto",nombreDepto);
                                    extras.putString("costoDepto",costoDepto);
                                    extras.putInt("costoFinal",costoFinal);
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                    AddMDP.this.finish();

                                }

                                public void onTick(long millisUntilFinished) {
                                    // millisUntilFinished    The amount of time until finished.
                                }
                            }.start();


                        }
                        else {

                            buttonAdd.setEnabled(false);


                            new CountDownTimer(3000, 1000) {
                                public void onFinish() {
                                    // When timer is finished
                                    // Execute your code here
                                    @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdffSend = new SimpleDateFormat("yyyy-MM-dd");
                                    fechaMDP = sdffSend.format(new Date());
                            addMDP(idUser, noTarjeta);
                            Intent intent = new Intent(AddMDP.this, MainActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("idDepto", idDepto);
                            extras.putString("user", user);
                            extras.putString("idUser", idUser);
                            boolean logedin;
                            extras.putBoolean("logedState", logedin = true);
                            intent.putExtras(extras);
                            startActivity(intent);
                                AddMDP.this.finish();
                                }

                                public void onTick(long millisUntilFinished) {
                                    // millisUntilFinished    The amount of time until finished.
                                }
                            }.start();

                        }
                    }
                }
            }
        }


    }


    public void addMDP(String getIdUser, String getNoTarjeta){

        final String noTarjeta = getNoTarjeta.trim();
        final String nombreP = editTextNombreP.getText().toString().trim();
        final String codigoS = editTextSC.getText().toString().trim();
        final String idUser = getIdUser.trim();
        final String fecha = mDisplayDate.getText().toString().trim();




        class addMeDP extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String s) {


                super.onPostExecute(s);
                Toast.makeText(AddMDP.this, s, Toast.LENGTH_LONG).show();



            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMP_Nocuenta, noTarjeta);
                params.put(Config.KEY_EMP_NombreP, nombreP);
                params.put(Config.KEY_EMP_Codseg,codigoS);
                params.put(Config.KEY_EMP_Fecha_ArrenMpago,fecha);
                params.put(Config.KEY_EMP_IDArrendatario, idUser);





                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_NoCuenta, params);
                return res;
            }
        }

        addMeDP ae = new addMeDP();
        ae.execute();




    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }



    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() == 16) {
                // if(phone.length() != 10) {
                check = true;

            } else {
                check = false;
            }
        }
        else {
            check=false;
        }
        return check;
    }

    private boolean isValidCS(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() != 3) {
                // if(phone.length() != 10) {
                check = false;

            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }
}
