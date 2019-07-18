package com.thunderlabs.callcenter;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //objetos
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7,
            btn8, btn9, btn0, btnAsterisco, btnNumeral, btnDel, btnCall;
    TextView textView;
    StringBuilder fono = new StringBuilder();

    private final int PERMISSION_REQUEST_CONTACT = 1;
    private static final int PERMISSIONS_REQUEST_SEND_SMS = 123;
    private static final int PERMISSIONS_REQUEST_CALL = 112;
    private Context mContext = MainActivity.this;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_call:
                    if (Build.VERSION.SDK_INT >= 23) {
                        String[] PERMISSIONS = {android.Manifest.permission.CALL_PHONE};
                        if (!hasPermissions(mContext, PERMISSIONS)) {
                            ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, PERMISSIONS_REQUEST_CALL);
                        } else {
                            makeCall();
                        }
                    } else {
                        makeCall();
                    }
                    return true;

                case R.id.navigation_sms:
                    if (Build.VERSION.SDK_INT >= 23) {
                        String[] PERMISSIONS = {Manifest.permission.SEND_SMS};
                        if (!hasPermissions(mContext, PERMISSIONS)) {
                            ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, PERMISSIONS_REQUEST_SEND_SMS);
                        } else {
                            sendSMS();
                        }
                    } else {
                        sendSMS();
                    }
                    return true;
                case R.id.navigation_contact:

                    if (Build.VERSION.SDK_INT >= 23) {
                        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
                        if (!hasPermissions(mContext, PERMISSIONS)) {
                            ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, PERMISSION_REQUEST_CONTACT);
                        } else {
                            addContacts();
                        }
                    } else {
                        addContacts();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //se definen los objetos y se asigna OnClickListener
        textView = (TextView) findViewById(R.id.txtTelefono);
        btnDel = (Button) findViewById(R.id.btnDelete);
        btn1 = (Button) findViewById(R.id.btnNum1);
        btn2 = (Button) findViewById(R.id.btnNum2);
        btn3 = (Button) findViewById(R.id.btnNum3);
        btn4 = (Button) findViewById(R.id.btnNum4);
        btn5 = (Button) findViewById(R.id.btnNum5);
        btn6 = (Button) findViewById(R.id.btnNum6);
        btn7 = (Button) findViewById(R.id.btnNum7);
        btn8 = (Button) findViewById(R.id.btnNum8);
        btn9 = (Button) findViewById(R.id.btnNum9);
        btn0 = (Button) findViewById(R.id.btnNum0);
        btnAsterisco = (Button) findViewById(R.id.btnAsterisco);
        btnNumeral = (Button) findViewById(R.id.btnNumeral);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnDel.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btnAsterisco.setOnClickListener(this);
        btnNumeral.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        textView.setText("");
        fono.setLength(0);
        //fin
    }

    public void makeCall() {
        String number = " ";
        Intent call = new Intent(Intent.ACTION_CALL);
        call.setData(Uri.parse("tel:" + fono.toString()));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(call);
    }


    private void addContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        //Por si quieres ingresar el nombre
        contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, " ");

        //Add number
        ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
        ContentValues phoneRow = new ContentValues();
        phoneRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        phoneRow.put(ContactsContract.CommonDataKinds.Phone.NUMBER, fono.toString());
        contactData.add(phoneRow);
        contactIntent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
        startActivity(contactIntent);
    }



    private void sendSMS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //String smsNumber = "83345234";
        String sms = " ";

        //Uri uri = Uri.parse("smsto:" + smsNumber);
        //Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
       // smsIntent.putExtra("sms_body", sms);
        //startActivity(smsIntent);


            //envia msj
            Uri uri= Uri.parse("smsto:" + fono.toString());
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
            smsIntent.putExtra("sms_body", sms);
            startActivity(smsIntent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CONTACT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addContacts();
            } else {
                Toast.makeText(mContext, "La aplicación no pudo guardar el contacto.", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
            } else {
                Toast.makeText(mContext, "La aplicación no pudo guardar el contacto.", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == PERMISSIONS_REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                Toast.makeText(mContext, "La aplicación no pudo llamar.", Toast.LENGTH_LONG).show();
            }
        }

    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnNum1:
                fono.append("1");
                break;
            case R.id.btnNum2:
                fono.append("2");
                break;
            case R.id.btnNum3:
                fono.append("3");
                break;
            case R.id.btnNum4:
                fono.append("4");
                break;
            case R.id.btnNum5:
                fono.append("5");
                break;
            case R.id.btnNum6:
                fono.append("6");
                break;
            case R.id.btnNum7:
                fono.append("7");
                break;
            case R.id.btnNum8:
                fono.append("8");
                break;
            case R.id.btnNum9:
                fono.append("9");
                break;
            case R.id.btnNum0:
                fono.append("0");
                break;
            case R.id.btnNumeral:
                fono.append("#");
                break;
            case R.id.btnAsterisco:
                fono.append("*");
                break;
            case R.id.btnDelete:
                delete();
                break;
            case R.id.btnCall:
                call();
                break;
        }

        textView.setText(fono);

        }
    /**
     * Metodo para eliminar el ultimo caracter del numero de telefono
     * que se escribe en el StringBuilder
     * */
    private void delete() {
        if (fono.length() > 0) {
            fono.deleteCharAt(fono.length() - 1);
        }
    }

    /**
     * Metodo para realizar la llamada telefonica
     * */
    private void call() {
        try {
            if (fono.length() > 0) {
                //realiza la llamada
                Uri numero = Uri.parse("tel:" + fono.toString());
                Intent intent = new Intent(Intent.ACTION_CALL, numero);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
            else
            {
                //si no se escribio numero telefonico, avisa
                Toast.makeText(getBaseContext(),"Debe escribir un numero de telefono",Toast.LENGTH_SHORT).show();
            }

        }
        catch (ActivityNotFoundException activityException) {
            //si se produce un error, se muestra en el LOGCAT
            Log.e("ET", "No se pudo realizar la llamada.", activityException);
        }

    }


    }

