package com.xunam.passwordgenerator;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private CheckBox use_letters, use_number, use_symbols;
    private TextView length_size;
    private EditText password_result;
    private SeekBar password_length;
    Integer password_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        setup_seekbar();
    }

    private void findViews() {
        use_letters = findViewById(R.id.use_letters_upper);
        use_number = findViewById(R.id.use_numbers);
        use_symbols = findViewById(R.id.use_symbols);
        password_length = findViewById(R.id.password_length);
        length_size = findViewById(R.id.length_size);
        password_result = findViewById(R.id.password_result);
    }

    private void setup_seekbar() {
        password_length.setMax(512);

        password_length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                length_size.setText(String.valueOf(progress));
                password_size = progress;
                password_result.setText(generatePassword());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String generatePassword() {
        String buffer = "abcdefghijklmnopqrstuvwxyz";
        if(use_symbols.isChecked()) {
            buffer += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if(use_number.isChecked()) {
            buffer += "0123456789";
        }
        if(use_symbols.isChecked()) {
            buffer += "'!@#$%&*()-~^><?/:;}]{[";
        }

        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);

        char[] result = new char[password_size + 1];
        for(int i = 0; i < password_size; i++) {
            int index = rand.nextInt(buffer.length());
            result[i] = buffer.charAt(index);
        }

        return new String(result);
    }
}