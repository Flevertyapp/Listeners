package ru.example.androidwithjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "[LifecycleActivity]";
    private final static String keyCounters = "Counters"; //для сериализации

    private Counters counters;

    private TextView textCounter1;
    private TextView textCounter2;
    private TextView textCounter3;
    private TextView textCounter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String instanceState;
        if (savedInstanceState == null) {
            instanceState = "Первый запуск";
        } else {
            instanceState = "Повторный запуск";
        }
        //вывод тостов переехал в отдельный метод, избавляемся от дублирования в методах
        //Toast.makeText(getApplicationContext(), instanceState + " - onCreate() Тост", Toast.LENGTH_SHORT).show();
        makeToast(instanceState + "- onCreate() Метод");
        counters = new Counters();
        initView();
    }

    private void initView() {
        textCounter1 = findViewById(R.id.textView1);
        textCounter2 = findViewById(R.id.textView2);
        textCounter3 = findViewById(R.id.textView3);
        textCounter4 = findViewById(R.id.textView4);
        initButton2ClickListener();
        initButton3ClickListener();
        initButton4ClickListener();
    }

    //обработка нажатия через атрибут в макете- фу так делать
    public void button1_onClick(View view) {
        counters.incrementCounter1();
        setTextCounter(textCounter1, counters.getCounter1());
    }

    //установка слушателя на кнопку
    private void initButton2ClickListener() {
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //counter2++;
                counters.incrementCounter2();
                setTextCounter(textCounter2, counters.getCounter2());
            }
        });
    }

    //использование активити как слушателя канает для одной кнопки, для каждой следующей надо прописывать поведение
    private void initButton3ClickListener() {
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        counters.incrementCounter3();
        setTextCounter(textCounter3, counters.getCounter3());
    }

    //переменная анонимного класса реализует интерфейс  View.OnClickListener()
    private void initButton4ClickListener() {
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(button4ClickListener);
    }

    public View.OnClickListener button4ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            counters.incrementCounter4();
            setTextCounter(textCounter4, counters.getCounter4());
        }
    };

    private void setTextCounter(TextView textCounter, int counter) {
        textCounter.setText(String.format(Locale.getDefault(), "%d", counter));
    }

    private void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        Log.d(TAG, message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        makeToast("onStart()");
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putParcelable(keyCounters, counters);
        makeToast("onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        makeToast("Повторный запуск- onRestoreInstanceState()");
        counters = (Counters) savedInstanceState.getParcelable(keyCounters);
        restoreTextCounter();
    }

    private void restoreTextCounter() { //восстановление счетчиков
        setTextCounter(textCounter1, counters.getCounter1());
        setTextCounter(textCounter2, counters.getCounter2());
        setTextCounter(textCounter3, counters.getCounter3());
        setTextCounter(textCounter4, counters.getCounter4());
    }

    @Override
    protected void onStop() {
        super.onStop();
        makeToast("onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        makeToast("onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        makeToast("onDestroy()");
    }
}