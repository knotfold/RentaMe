package com.example.pcwin.rentame;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
                    edit = false;
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
                    editTextNombre.setFocusable(true);
                    editTextApellidoP.setFocusable(true);
                    editTextApellidoM.setFocusable(true);
                    editTextcorreo.setFocusable(true);
                    editTextcontrasena.setFocusable(true);
                    editTextTel.setFocusable(true);
                    editTextCel.setFocusable(true);
                    editTextTelOf.setFocusable(true);
                    editTextSex.setVisibility(View.GONE);
                    editTextFechNac.setVisibility(View.GONE);
                    mDisplayDate.setVisibility(View.VISIBLE);
                }
                else
                {
                    edit = false;

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
        editTextcontrasenaComf.setText(editTextcontrasena.getText().toString());

    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
