package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.regex.Pattern;

public class DetailsUsers extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView mDisplayDate;
    private TextView textViewAviso1;
    private TextView textContraComf;
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
    private EditText editTextFechNac;
    private EditText editTextSex;
    private boolean isAdmin = false;
    private boolean edit= false;
    private Button buttonChat;
    private Button buttonEdit;
    private Button buttonCancelar;
    String dato;
    Date startDate;
    String idUser;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    Date endDate = new Date();


    String URL_DATA= "https://rentame.000webhostapp.com/get_one_user.php?IDArrendatario=";

    public void tyc (View view)
    {
        startActivity(new Intent(this,DetailsPoliticasDeUso.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_users);
        textViewAviso1 = (TextView) findViewById(R.id.textAviso1);
        textContraComf = (TextView) findViewById(R.id.textViewComfContra);
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
        editTextFechNac = (EditText) findViewById(R.id.editFechNac);
        editTextSex = (EditText) findViewById(R.id.editTextSex);
        textViewCompMail = (TextView) findViewById(R.id.textViewCompMail);
        radioGroup = findViewById(R.id.radioGroup);
        buttonChat = (Button) findViewById(R.id.buttonChat);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonCancelar = (Button) findViewById(R.id.buttonCancel);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            idUser = bundle.getString("idUser");
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
                        DetailsUsers.this,
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
        if(!isAdmin)
        {
            buttonChat.setVisibility(View.GONE);
        }

        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Chat.class);
                Bundle extras = new Bundle();
                extras.putString("idUser", idUser);

                intent.putExtras(extras);
                v.getContext().startActivity(intent);
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit)
                {
                    if (isAdmin)
                    {
                        buttonChat.setVisibility(View.VISIBLE);
                    }
                    edit = false;
                    buttonEdit.setText("MODIFICAR DATOS");
                    buttonCancelar.setVisibility(View.GONE);
                    textContraComf.setVisibility(View.GONE);
                    editTextcontrasenaComf.setVisibility(View.GONE);
                    textViewAviso1.setVisibility(View.GONE);
                    radioGroup.setVisibility(View.GONE);
                    editTextSex.setVisibility(View.VISIBLE);
                    editTextFechNac.setVisibility(View.VISIBLE);
                    editTextNombre.setEnabled(false);
                    editTextApellidoP.setEnabled(false);
                    editTextApellidoM.setEnabled(false);
                    editTextcorreo.setEnabled(false);
                    editTextcontrasena.setEnabled(false);
                    editTextTel.setEnabled(false);
                    editTextCel.setEnabled(false);
                    editTextTelOf.setEnabled(false);
                    editTextSex.setEnabled(false);
                    editTextFechNac.setEnabled(false);
                    editTextcontrasenaComf.setEnabled(false);
                    mDisplayDate.setVisibility(View.GONE);
                }
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit)
                {
                    edit=true;
                    buttonEdit.setText("ACEPTAR");
                    buttonCancelar.setVisibility(View.VISIBLE);
                    textContraComf.setVisibility(View.VISIBLE);
                    buttonChat.setVisibility(View.GONE);
                    editTextcontrasenaComf.setVisibility(View.VISIBLE);
                    textViewAviso1.setVisibility(View.VISIBLE);
                    radioGroup.setVisibility(View.VISIBLE);
                    editTextNombre.setEnabled(true);
                    editTextApellidoP.setEnabled(true);
                    editTextApellidoM.setEnabled(true);
                    editTextcorreo.setEnabled(true);
                    editTextcontrasena.setEnabled(true);
                    editTextTel.setEnabled(true);
                    editTextCel.setEnabled(true);
                    editTextTelOf.setEnabled(true);
                    editTextcontrasenaComf.setEnabled(true);
                    editTextNombre.setFocusableInTouchMode(true);
                    editTextApellidoP.setFocusableInTouchMode(true);
                    editTextApellidoM.setFocusableInTouchMode(true);
                    editTextcorreo.setFocusableInTouchMode(true);
                    editTextcontrasena.setFocusableInTouchMode(true);
                    editTextTel.setFocusableInTouchMode(true);
                    editTextCel.setFocusableInTouchMode(true);
                    editTextTelOf.setFocusableInTouchMode(true);
                    editTextcontrasenaComf.setFocusableInTouchMode(true);
                    editTextSex.setVisibility(View.GONE);
                    editTextFechNac.setVisibility(View.GONE);
                    mDisplayDate.setVisibility(View.VISIBLE);
                }
                else
                {


                    verificarDatos();

                }
            }
        });
        if (!edit)
        {
            buttonCancelar.setVisibility(View.GONE);
            textContraComf.setVisibility(View.GONE);
            editTextcontrasenaComf.setVisibility(View.GONE);
            textViewAviso1.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
            editTextNombre.setEnabled(false);
            editTextApellidoP.setEnabled(false);
            editTextApellidoM.setEnabled(false);
            editTextcorreo.setEnabled(false);
            editTextcontrasena.setEnabled(false);
            editTextTel.setEnabled(false);
            editTextCel.setEnabled(false);
            editTextTelOf.setEnabled(false);
            editTextSex.setEnabled(false);
            editTextFechNac.setEnabled(false);
            editTextNombre.setFocusable(false);
            editTextApellidoP.setFocusable(false);
            editTextApellidoM.setFocusable(false);
            editTextcorreo.setFocusable(false);
            editTextcontrasena.setFocusable(false);
            editTextTel.setFocusable(false);
            editTextCel.setFocusable(false);
            editTextTelOf.setFocusable(false);
            mDisplayDate.setVisibility(View.GONE);

        }
        loadRecyclerViewDataDepto();
        mDisplayDate.setText(editTextFechNac.getText().toString());

        hideSoftKeyboard();
    }

    public void loadRecyclerViewDataDepto() {

        /*URL_DATA = "http://10.0.2.2:81/dbproject/get_platillos.php?IdRestaurante=" + IdRestaurante;*/
        final String URL_DATA = "https://rentame.000webhostapp.com/get_one_user.php?IDArrendatario=" + idUser;

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

                                editTextNombre.setText(o.getString("Nombre"));
                                editTextApellidoP.setText(o.getString("ApellidoP"));
                                editTextApellidoM.setText(o.getString("ApellidoM"));
                                editTextCel.setText(o.getString("Tcelular"));
                                editTextTel.setText( o.getString("TelCasa"));
                                editTextTelOf.setText(o.getString("TelOficina"));
                                editTextFechNac.setText(o.getString("FechNac"));
                                editTextcorreo.setText(o.getString("Email"));
                                editTextcontrasena.setText(o.getString("contrasena"));
                                editTextSex.setText(o.getString("Sexo"));




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

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void verificarDatos()


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
                                    if(compare(endDate,startDate)<=1882)
                                    {

                                        updateUser(idUser,date,sex);
                                        edit = false;
                                        if(isAdmin)
                                        {
                                            buttonChat.setVisibility(View.VISIBLE);
                                        }
                                        buttonEdit.setText("MODIFICAR DATOS");
                                        buttonCancelar.setVisibility(View.GONE);
                                        textContraComf.setVisibility(View.GONE);
                                        editTextcontrasenaComf.setVisibility(View.GONE);
                                        textViewAviso1.setVisibility(View.GONE);
                                        radioGroup.setVisibility(View.GONE);
                                        editTextNombre.setEnabled(false);
                                        editTextApellidoP.setEnabled(false);
                                        editTextApellidoM.setEnabled(false);
                                        editTextcorreo.setEnabled(false);
                                        editTextcontrasena.setEnabled(false);
                                        editTextTel.setEnabled(false);
                                        editTextCel.setEnabled(false);
                                        editTextTelOf.setEnabled(false);
                                        editTextSex.setEnabled(false);
                                        editTextFechNac.setEnabled(false);
                                        editTextcontrasenaComf.setEnabled(false);
                                        editTextNombre.setFocusable(false);
                                        editTextApellidoP.setFocusable(false);
                                        editTextApellidoM.setFocusable(false);
                                        editTextcorreo.setFocusable(false);
                                        editTextcontrasena.setFocusable(false);
                                        editTextcontrasenaComf.setFocusable(false);
                                        editTextTel.setFocusable(false);
                                        editTextCel.setFocusable(false);
                                        editTextTelOf.setFocusable(false);
                                        mDisplayDate.setVisibility(View.GONE);
                                        editTextSex.setVisibility(View.VISIBLE);
                                        editTextFechNac.setVisibility(View.VISIBLE);
                                        editTextNombre.setText(nombre);
                                        editTextApellidoP.setText(apellidoP);
                                        editTextApellidoM.setText(apellidoM);
                                        editTextcorreo.setText(correo);
                                        editTextTel.setText(tel);
                                        editTextCel.setText(cel);
                                        editTextTelOf.setText(telOf);
                                        editTextcontrasena.setText(contrasena1);
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
                                        if(compare(endDate,startDate)<=1882)
                                        {

                                            updateUser(idUser,date,sex);
                                            edit = false;
                                            if(isAdmin)
                                            {
                                                buttonChat.setVisibility(View.VISIBLE);
                                            }
                                            buttonEdit.setText("MODIFICAR DATOS");
                                            buttonCancelar.setVisibility(View.GONE);
                                            textContraComf.setVisibility(View.GONE);
                                            editTextcontrasenaComf.setVisibility(View.GONE);
                                            textViewAviso1.setVisibility(View.GONE);
                                            radioGroup.setVisibility(View.GONE);
                                            editTextNombre.setEnabled(false);
                                            editTextApellidoP.setEnabled(false);
                                            editTextApellidoM.setEnabled(false);
                                            editTextcorreo.setEnabled(false);
                                            editTextcontrasena.setEnabled(false);
                                            editTextTel.setEnabled(false);
                                            editTextCel.setEnabled(false);
                                            editTextTelOf.setEnabled(false);
                                            editTextSex.setEnabled(false);
                                            editTextFechNac.setEnabled(false);
                                            editTextcontrasenaComf.setEnabled(false);
                                            editTextNombre.setFocusable(false);
                                            editTextApellidoP.setFocusable(false);
                                            editTextApellidoM.setFocusable(false);
                                            editTextcorreo.setFocusable(false);
                                            editTextcontrasena.setFocusable(false);
                                            editTextcontrasenaComf.setFocusable(false);
                                            editTextTel.setFocusable(false);
                                            editTextCel.setFocusable(false);
                                            editTextTelOf.setFocusable(false);
                                            mDisplayDate.setVisibility(View.GONE);
                                            editTextSex.setVisibility(View.VISIBLE);
                                            editTextFechNac.setVisibility(View.VISIBLE);
                                            editTextcontrasenaComf.setText("");
                                            editTextSex.setText(sex);
                                            editTextFechNac.setText(date);
                                            editTextSex.setText(sex);
                                            editTextFechNac.setText(date);
                                            editTextNombre.setText(nombre);
                                            editTextApellidoP.setText(apellidoP);
                                            editTextApellidoM.setText(apellidoM);
                                            editTextcorreo.setText(correo);
                                            editTextTel.setText(tel);
                                            editTextCel.setText(cel);
                                            editTextTelOf.setText(telOf);
                                            editTextcontrasena.setText(contrasena1);

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

    private void updateUser(final String idUser,final String getDate, final String getSex){

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



        class UpdateUser extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(DetailsUsers.this, s, Toast.LENGTH_LONG).show();


            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_IDArrendatario,idUser);
                hashMap.put(Config.KEY_EMP_NombreUsuario, nombre);
                hashMap.put(Config.KEY_EMP_ApellidoP, apellidoP);
                hashMap.put(Config.KEY_EMP_ApellidoM, apellidoM);
                hashMap.put(Config.KEY_EMP_TCasa, tel);
                hashMap.put(Config.KEY_EMP_Tcel,cel);
                hashMap.put(Config.KEY_EMP_TOficina,telOf);
                hashMap.put(Config.KEY_EMP_FechNac,date);
                hashMap.put(Config.KEY_EMP_Email,correo);
                hashMap.put(Config.KEY_EMP_ContrasenaUsuario,contrasena);
                hashMap.put(Config.KEY_EMP_Sexo,sex);
                hashMap.put(Config.KEY_EMP_IDENTICIACION,"no");


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDate_User,hashMap);

                return s;
            }
        }

        UpdateUser ue = new UpdateUser();
        ue.execute();
    }

}
