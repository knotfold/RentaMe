package com.example.pcwin.rentame;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.Editable;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

public class Busqueda extends AppCompatActivity {

    String idUser;
    String user;
    String idDepto;
    boolean logedIn;
    private Button buttonSearch;
    String noHabitaciones;
    String noBanos;
    String capcidadDepto;
    private EditText textHabitaciones;
    private EditText textBanos;
    private EditText textCapacidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idDepto = bundle.getString("idDepto");
            idUser = bundle.getString("idUser");
            logedIn = bundle.getBoolean("logedState");
            user = bundle.getString("user");


        }

        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        textBanos = (EditText) findViewById(R.id.textNoBanos);
        textCapacidad = (EditText) findViewById(R.id.textNoPersonas);
        textHabitaciones = (EditText) findViewById(R.id.textNoHabitaciones);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textBanos.getText().toString().equals(""))
                {
                    noBanos= textBanos.getText().toString();
                }
                if(!textCapacidad.getText().toString().equals(""))
                {
                    capcidadDepto = textCapacidad.getText().toString();
                }
                if(!textHabitaciones.getText().toString().equals(""))
                {
                    noHabitaciones=textHabitaciones.getText().toString();
                }

                Intent intent = new Intent(Busqueda.this, MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("idDepto", idDepto);
                extras.putString("idUser", idUser);
                extras.putBoolean("logedState",logedIn);
                extras.putString("noBanos", noBanos);
                extras.putString("user", user);
                extras.putString("capacidadDepto", capcidadDepto);
                extras.putString("noHabitaciones" , noHabitaciones);



                intent.putExtras(extras);

                startActivity(intent);
                Busqueda.this.finish();


            }
        });
    }
}
