package com.example.bingo2;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private static final int MY_DATA_CHECK_CODE=1;
    Button btnnuevo;
    Button btnnumero;
    TextView txtnumero;
    TextView txtnumerosalido;
    TextToSpeech voz;

    public int x;
    public int y;

     List<Integer> listnumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent chatIntent = new Intent();
        chatIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(chatIntent, MY_DATA_CHECK_CODE);*/
        voz = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    voz.setLanguage(Locale.getDefault());
                }
            }
        });

        btnnuevo = findViewById(R.id.btnnuevo);
        btnnumero = findViewById(R.id.btnnumero);
        txtnumero = findViewById(R.id.txtnumero);
        txtnumerosalido = findViewById(R.id.txtnsalido);

        listnumeros = new ArrayList<>();

        x = 0; y = 0;
        fill();
    }

    public void fill(){

        Random rn = new Random();
        for(int i =1; i < 76; i++){
            int num = rn.nextInt(75) + 1;
            //Log.d("numero: ",""+answer);
            if (!existe(listnumeros, num)){
                listnumeros.add(num);
            }else
                i = i-1;
        }
    }

    public boolean existe(List<Integer> list, int val){
        boolean ret = false;
        for(int i=0;i<list.size();i++) {
            if(list.get(i) == val){
                ret = true;
                break;
            }
        }
        return ret;
    }

    public void reset(View view){
        listnumeros.clear();
        txtnumero.setText("");
        txtnumerosalido.setText("00");
        fill();
        
    }

    public void newnumero(View view) {
        if(listnumeros.size() != 0 && !txtnumero.getText().equals("!!!!!!")){
            txtnumero.setText(String.valueOf(listnumeros.get(0)));
            voz.speak(""+String.valueOf(listnumeros.get(0)), TextToSpeech.QUEUE_FLUSH,null);
            txtnumerosalido.append(" , "+String.valueOf(listnumeros.get(0)));
            listnumeros.remove(0);
        }else if (!txtnumero.getText().equals("!!!!!!")){
            txtnumero.setText("!!!!!!");
            voz.speak("Se han acabado las bolitas debes de reiniciar el bingo", TextToSpeech.QUEUE_FLUSH,null);
        }
    }
}
