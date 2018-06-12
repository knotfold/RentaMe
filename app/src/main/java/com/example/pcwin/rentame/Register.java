package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView mDisplayDate;
    private TextView textViewCompMail;
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
    boolean isAdmin = false;
    String dato;
    String user;
    String idUsuario;
    String hasDepto;
    Date startDate;
    Date endDate = new Date();
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
        textViewCompMail = (TextView) findViewById(R.id.textViewCompMail);
        radioGroup = findViewById(R.id.radioGroup);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            isAdmin = bundle.getBoolean("isAdmin");
        }

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

                 dato = year + "-" + month + "-" + day;
                 startDate =  new Date(year ,month,day);
                 String dateDisplay = day + "-" + month + "-" + year;
                mDisplayDate.setText(dato);
            }
        };


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
        return diffYear;
    }


    public void confirmarCliente(View view)
    {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String sex = radioButton.getText().toString();
        String date = mDisplayDate.getText().toString();
        String nombre = editTextNombre.getText().toString();
        String apellidoP = editTextApellidoP.getText().toString();
        String apellidoM = editTextApellidoM.getText().toString();
        String correo = editTextcorreo.getText().toString();
        String tel = editTextTel.getText().toString();
        String cel = editTextCel.getText().toString();
        String telOf = editTextTelOf.getText().toString();
        String contrasena1 = editTextcontrasena.getText().toString();
        String contrasena2 = editTextcontrasenaComf.getText().toString();


        if(sex.equals("") || date.equals("") || nombre.equals("") || apellidoP.equals("")
                || apellidoM.equals("") || correo.equals("") || tel.equals("") || cel.equals("")
                || contrasena1.equals("") || contrasena2.equals(""))
        {
            Toast.makeText(this, "Por favor llene todos los campos, hay campos incompletos", Toast.LENGTH_LONG).show();
        }

        else
        {
              if(!nombre.matches("[a-zA-Z ]+"))
              {
                  Toast.makeText(this, "El nombre solo puede contener letras", Toast.LENGTH_LONG).show();
              }
              else if (!apellidoP.matches("[a-zA-Z ]+") || !apellidoM.matches("[a-zA-Z ]+"))
              {
                  Toast.makeText(this, "Los apellidos solo pueden contener letras", Toast.LENGTH_LONG).show();
              }
              else
              {
                  if(isValidMobile(tel)) {

                    if (isValidMobile(cel))
                    {
                        if(telOf.equals(""))
                        {
                            if (isValidEmail(correo)) {

                                if (contrasena1.equals(contrasena2)) {
                                    if(compare(endDate,startDate)>=18)
                                    {

                                        addCliente(date, sex);
                                    }
                                    else
                                    {
                                        Toast.makeText(this, "Para completar el registro ustede debe de ser mayor de edad " , Toast.LENGTH_LONG).show();
                                    }






                                } else {
                                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(this, "Dirección de correo no válida", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            if(isValidMobile(telOf))
                            {
                                if (isValidEmail(correo)) {

                                    if (contrasena1.equals(contrasena2)) {
                                        if(compare(endDate,startDate)>=18)
                                        {

                                            addCliente(date, sex);
                                        }
                                        else
                                        {
                                            Toast.makeText(this, "Para completar el registro ustede debe de ser mayor de edad " , Toast.LENGTH_LONG).show();
                                        }






                                    } else {
                                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(this, "Dirección de correo no válida", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                Toast.makeText(this, "El numero de telefono de oficina no es válido", Toast.LENGTH_LONG).show();
                            }


                        }
                    }
                    else
                    {
                        Toast.makeText(this, "El numero de celular no es válido", Toast.LENGTH_LONG).show();
                    }

                  }
                  else
                  {
                      Toast.makeText(this, "El numero de telefono no es válido", Toast.LENGTH_LONG).show();
                  }
              }





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

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String s) {


                super.onPostExecute(s);
                textViewCompMail.setText(s);
                String compare = textViewCompMail.getText().toString().trim();
                if(Objects.equals(compare,"No se pudo agregar Usuario")) {
                    Toast.makeText(Register.this,  s + "El correo ya esta en uso", Toast.LENGTH_LONG).show();

                }
                else
                {

                    GetUserDetails();

                }

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

    public void GetUserDetails() {

        /*URL_DATA = "http://10.0.2.2:81/dbproject/get_platillos.php?IdRestaurante=" + IdRestaurante;*/
        final String URL_DATA = "https://rentame.000webhostapp.com/validarARR.php?email=" + editTextcorreo.getText();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("result");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                idUsuario = o.getString("IDArrendatario");
                                hasDepto = o.getString("IDDepto");
                                user = o.getString("Nombre");

                                if(!isAdmin)
                                {
                                    Toast.makeText(Register.this, "bienvenido", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putBoolean("logedState", logedIn = true);
                                    extras.putString("mail", editTextcorreo.getText().toString());
                                    extras.putString("idUser",idUsuario);
                                    extras.putString("user",user);
                                    extras.putString("idDepto",hasDepto);
                                    intent.putExtras(extras);
                                    Register.this.startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(Register.this, "bienvenido", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Register.this, AdminInterface.class);
                                    Bundle extras = new Bundle();


                                    extras.putString("idUser","admin");
                                    extras.putString("user","admin");
                                    intent.putExtras(extras);
                                    Register.this.startActivity(intent);
                                }




                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() < 6 || phone.length() > 13) {
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
