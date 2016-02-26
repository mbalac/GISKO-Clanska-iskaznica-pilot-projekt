package gisko.milan.ffos.giskoclanskaiskaznica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Autorizacija extends AppCompatActivity {

    EditText usernamet, passwordt;
    SharedPreferences sf;
    public static final String APLIKACIJA = "GSKOS";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-mm");

    //private static CheckBox show;
    private static ImageView info;
    private static ImageView help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorizacija);

        usernamet = (EditText)findViewById(R.id.username);
        usernamet.requestFocus();
        sf = getSharedPreferences(Autorizacija.APLIKACIJA, Context.MODE_PRIVATE);
        usernamet.setText(sf.getString("clanskiBroj", ""));
        passwordt = (EditText)findViewById(R.id.password);

        //addOnCheckedChangeListener();
        InfoButton();
        HelpButton();

        String td = sf.getString("datum", "prazno");
        if(td.equals("prazno")) {
            return;

        }
        try {
            Date datum = df.parse(td);
            GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
            gc.setTime(datum);
            gc.add(Calendar.DAY_OF_YEAR, -7);
            if (gc.getTime().compareTo(new Date()) < 0) {
                //odmah prikaži autorizirani screen
                Autoriziraj back = new Autoriziraj(this);
                Gson g = new Gson();
                Clan c = g.fromJson(sf.getString("clan", ""),Clan.class);
                Log.d("korisnik",c.getCl_broj());
                Log.d("lozinka",c.getLozinka());
                back.execute("login", c.getCl_broj(), c.getLozinka());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void autoriziran(String c){
        SharedPreferences.Editor editor = sf.edit();
        editor.putString("datum", df.format(new Date()));
        editor.putString("clan", c);
        editor.commit();

        Toast.makeText(Autorizacija.this, "Uspješna prijava" , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,User.class);
        intent.putExtra("clan", c);
        startActivity(intent);
    }



    public void pogreskaLogin(){
        Toast.makeText(Autorizacija.this, "Nepostojeći korisnik", Toast.LENGTH_SHORT).show();
    }

    public void nemaNeta(){
        Toast.makeText(Autorizacija.this, "Nema internet konekcije", Toast.LENGTH_SHORT).show();
    }

    public void Login(View View){

        SharedPreferences.Editor editor = sf.edit();
        editor.putString("clanskiBroj", usernamet.getText().toString());
        editor.commit();
        String type = "login";
        Autoriziraj back = new Autoriziraj(this);
        String username = usernamet.getText().toString();
        String password = passwordt.getText().toString();
        back.execute(type, username, password);
    }

//    KOD ZA CHECKBOX
//    public void addOnCheckedChangeListener(){
//        passwordt = (EditText)findViewById(R.id.password);
//        show = (CheckBox)findViewById(R.id.checkBox);
//        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (!isChecked) {
//                    passwordt.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                } else {
//                    passwordt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                }
//            }
//        });
//    }

    public void InfoButton(){
        info = (ImageView)findViewById(R.id.imageView_info);

        info.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        otvoriInfo();
                    }
                });
    }

    private void otvoriInfo(){
        Intent intent2 = new Intent(this,Info.class);
        startActivity(intent2);
    }

    public void HelpButton(){
        help = (ImageView)findViewById(R.id.imageView_help);

        help.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        otvoriPomoc();
                    }
                });
    }

    private void otvoriPomoc(){
        Intent intent2 = new Intent(this,Pomoc.class);
        startActivity(intent2);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}