package pl.mworld.schooltimer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hiosdra
 * TODO Validation of ringtimes(next ring time must be greater than previous)
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private long actualRingChanged = 1;
    private String actualRingChangedString;
    private Long actualRingChangedValue;
    private EditText editText;
    private SharedPreferences.Editor mSharedEditor;
    private SharedPreferences mShared;
    private Spinner spinner;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Getting SharedPreferences instance & editor
        mShared = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedEditor = mShared.edit();

        // Getting Spinner instance of ring number spinner and setting listener
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        // Getting EditText instance of ring time editor
        editText = (EditText) findViewById(R.id.editText);

        // Getting Button instance of acceptation editing ringtime and setting listener
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        // Getting Spinner Drop down elements
        List<String> ringList = new ArrayList<String>();
        for(int i = 1; i < 31; i++) {
            ringList.add(new Integer(i).toString());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ringList);

        // Setting drop down layout style -> list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Seting actual edited ring
        actualRingChanged = l + 1;

        setEdittextText();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        // Put time edited into actual ring
        mSharedEditor.putLong(Long.valueOf(actualRingChanged).toString(), Long.valueOf(editText.getText().toString())).commit();

        setEdittextText();

    }

    /**
     * Sets Edittext(editing ringtime) into actually edited ringtime
     */
    private void setEdittextText() {
        // Get string of actual changed ring -> set text of edittext
        actualRingChangedString = Long.valueOf(mShared.getLong(Long.valueOf(actualRingChanged).toString(), 0)).toString();

        // Set edittext text of actual ring
        editText.setText(actualRingChangedString);
    }
}