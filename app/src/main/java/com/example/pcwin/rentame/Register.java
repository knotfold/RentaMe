package com.example.pcwin.rentame;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

public class Register extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView mDisplayDate;
    private EditText editTextNombre;
    private EditText editTextApellidoP;
    private EditText editTextApellidoM;
    private EditText editTextcorreo;
    private EditText editTextcontrasena;
    private EditText editTextcontrasenaComf;
    private EditText editTextTel;
    private EditText editTextCel;
    private EditText editTextTelOf;
    RadioGroup radioGroup;
    RadioButton radioButton;
    boolean logedIn;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextApellidoP = (EditText) findViewById(R.id.editTextApP);
        editTextApellidoM = (EditText) findViewById(R.id.editTextApM);
        editTextcorreo = (EditText) findViewById(R.id.editTextMail);
        editTextcontrasena = (EditText) findViewById(R.id.editTextPass);
        editTextcontrasenaComf = (EditText) findViewById(R.id.editTextPassComf);
        editTextTel = (EditText) findViewById(R.id.editTextTel);
        editTextCel = (EditText) findViewById(R.id.editTextCel);
        editTextTelOf = (EditText) findViewById(R.id.editTextTelOf);
        radioGroup = findViewById(R.id.radioGroup);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Register.this,
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
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = year + "-" + month + "-" + day;
                mDisplayDate.setText(date);
            }
        };


    }



    public void confirmarCliente(View view)
    {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String sex = radioButton.getText().toString();
        String date = mDisplayDate.getText().toString();

        String contrasena1 = editTextcontrasena.getText().toString();
        String contrasena2 = editTextcontrasenaComf.getText().toString();

        if(contrasena1.equals(contrasena2))
        {


                Toast.makeText(this, "Las contraseñas coinciden " + sex, Toast.LENGTH_LONG).show();
                addCliente(date,sex);
            Intent intent = new Intent(this, MainActivity.class);
            Bundle extras = new Bundle();
            extras.putBoolean("logedState",  logedIn = true);
            extras.putString("mail", editTextcorreo.getText().toString());
            intent.putExtras(extras);
            this.startActivity(intent);



        }
        else
        {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }
    }

    public void tyc (View view)
    {
        startActivity(new Intent(this,DetailsPoliticasDeUso.class));
    }
    //Adding an user
    public void addCliente(String getDate, String getSex){

        final String nombre = editTextNombre.getText().toString().trim();
        final String apellidoP = editTextApellidoP.getText().toString().trim();
        final String apellidoM = editTextApellidoM.getText().toString().trim();
        final String correo = editTextcorreo.getText().toString().trim();
        final String contrasena = editTextcontrasena.getText().toString().trim();
        final String tel = editTextTel.getText().toString().trim();
        final String cel = editTextCel.getText().toString().trim();
        final String telOf = editTextTelOf.getText().toString().trim();
        final String date = getDate.trim();
        final String sex = getSex.trim();


        class AddCliente extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(Register.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NombreUsuario, nombre);
                params.put(Config.KEY_EMP_ApellidoP, apellidoP);
                params.put(Config.KEY_EMP_ApellidoM, apellidoM);
                params.put(Config.KEY_EMP_TCasa, tel);
                params.put(Config.KEY_EMP_Tcel,cel);
                params.put(Config.KEY_EMP_TOficina,telOf);
                params.put(Config.KEY_EMP_FechNac,date);
                params.put(Config.KEY_EMP_Email,correo);
                params.put(Config.KEY_EMP_ContrasenaUsuario,contrasena);
                params.put(Config.KEY_EMP_Sexo,sex);




                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_USER_WEB, params);
                return res;
            }
        }

        AddCliente ae = new AddCliente();
        ae.execute();

    }


}
