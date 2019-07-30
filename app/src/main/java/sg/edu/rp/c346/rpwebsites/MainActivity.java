package sg.edu.rp.c346.rpwebsites;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView txt1;
    TextView txt2;
    Spinner spn1;
    Spinner spn2;
    Button go;
    WebView wvMyPage;
    ArrayList<String> array1;
    ArrayAdapter<String> array2;
    public String website="";
    public int array =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = findViewById(R.id.textViewCategory);
        txt2 = findViewById(R.id.textViewSub);
        spn1 = findViewById(R.id.spinner1);
        spn2 = findViewById(R.id.spinner2);
        go = findViewById(R.id.buttonGo);
        wvMyPage = findViewById(R.id.WebView);
        wvMyPage.setWebViewClient(new WebViewClient());
        WebSettings settings = wvMyPage.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);

        array1 = new ArrayList<>();



        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        array1.clear();
                        String[] strArray = getResources().getStringArray(R.array.RP);
                        array1.addAll(Arrays.asList(strArray));
                        array=0;
                        array2.notifyDataSetChanged();
                        break;

                    case 1:
                        array1.clear();
                        strArray = getResources().getStringArray(R.array.SOI);
                        array1.addAll(Arrays.asList(strArray));
                        array=1;
                        array2.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        array2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, array1);
        spn2.setAdapter(array2);

        spn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (spn2.getSelectedItem().toString()=="Homepage"){
                            String url="https://www.rp.edu.sg/";
                            website=url;
                        }else{
                            String url="https://www.rp.edu.sg/soi/full-time-diplomas/details/r47";
                            website=url;
                        }

                    case 1:
                        if (spn2.getSelectedItem().toString()=="Student Life"){
                            String url="https://www.rp.edu.sg/student-life";
                            website=url;

                        }else{
                            String url="https://www.rp.edu.sg/soi/full-time-diplomas/details/r12";
                            website=url;
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wvMyPage.loadUrl(website);
                txt1.setVisibility(View.GONE);
                txt2.setVisibility(View.GONE);
                spn1.setVisibility(View.GONE);
                spn2.setVisibility(View.GONE);
                go.setVisibility(View.GONE);
            }
        });


    }

    protected void onPause(){
        super.onPause();
        int spn1position= spn1.getSelectedItemPosition();
        int spn2position= spn2.getSelectedItemPosition();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit=prefs.edit();

        prefEdit.putInt("spinner1",spn1position);
        prefEdit.putInt("spinner2",spn2position);

        prefEdit.commit();
    }

    protected void onResume() {
    super.onResume();
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    int spn1position = prefs.getInt("spinner1",0);
    int spn2position= prefs.getInt("spinner2",0);

    spn1.setSelection(spn1position);
    spn2.setSelection(spn2position);
    }
}