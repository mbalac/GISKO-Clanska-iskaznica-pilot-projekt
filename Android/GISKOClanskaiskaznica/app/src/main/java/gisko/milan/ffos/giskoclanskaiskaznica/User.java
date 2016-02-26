package gisko.milan.ffos.giskoclanskaiskaznica;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.Map;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class User extends AppCompatActivity {

    ImageView iv;
    TextView tv;
    TextView rok;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-mm");
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_power_settings_new_white_24px);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(User.this);
                a_builder.setMessage("Želite li se odjaviti?")
                        .setCancelable(false)
                        .setNegativeButton("NE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
                                gc.setTime(new Date());
                                gc.add(Calendar.YEAR, 1);
                                SharedPreferences sf = getSharedPreferences(Autorizacija.APLIKACIJA, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sf.edit();
                                editor.putString("datum", df.format(gc.getTime()));
                                editor.commit();
                                finish();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("ODJAVA");
                alert.show();
                alert.getButton(alert.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#d14233"));
                alert.getButton(alert.BUTTON_POSITIVE).setTextColor(Color.parseColor("#d14233"));
            }
        });


        // barcode data
        //  String barcode_data = "6546546546";


        Gson g = new Gson();
        Clan c = g.fromJson(getIntent().getStringExtra("clan"),Clan.class);

        setTitle(c.getKorisnik());

        rok = (TextView) findViewById(R.id.textView_rok);
        rok.setText(c.getValjanost());

        // barcode image
        Bitmap bitmap = null;
        iv = (ImageView) findViewById(R.id.imageView);

        try {

            bitmap = encodeAsBitmap(c.getCl_broj(), BarcodeFormat.CODE_128, 900, 450);
            iv.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


        //barcode text
        tv = (TextView) findViewById(R.id.textView);
        tv.setText(c.getCl_broj());


    }

    /**************************************************************
     * getting from com.google.zxing.client.android.encode.QRCodeEncoder
     * <p/>
     * See the sites below
     * http://code.google.com/p/zxing/
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/EncodeActivity.java
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/QRCodeEncoder.java
     */

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }



    public void onBackPressed() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(User.this);
        a_builder.setMessage("Želite li se odjaviti?")
                .setCancelable(false)
                .setNegativeButton("NE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("ODJAVA");
        alert.show();
        alert.getButton(alert.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#d14233"));
        alert.getButton(alert.BUTTON_POSITIVE).setTextColor(Color.parseColor("#d14233"));
    }


    @Override
    public void onStart() {
        super.onStart();


    }



    @Override
    public void onStop() {
        super.onStop();


    }

}
