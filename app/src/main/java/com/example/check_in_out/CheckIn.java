package com.example.check_in_out;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckIn extends AppCompatActivity implements DialogBox.ListenerDialogBox {
    public static final String EXTRA="com.example.check_in_out";
    public static final String EXTRA_ID="com.example.check_in_out.EXTRA_ID";
    EditText nameEdit;
    EditText secondNameEdit;
    EditText idNumberEdit;
    EditText phoneNumberEdit;
    EditText carPlateEdit;
    EditText purposeEdit;
    TextView fReason;
    Button flag;
    Button submit;
    String flaggedReason="";
    boolean flagged=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        nameEdit = findViewById(R.id.name_input);
        secondNameEdit = findViewById(R.id.second_name_input);
        idNumberEdit = findViewById(R.id.id_number_input);
        phoneNumberEdit = findViewById(R.id.phone_number_input);
        purposeEdit = findViewById(R.id.purpose_input);
        carPlateEdit = findViewById(R.id.plate_input);
        fReason = findViewById(R.id.flag_in_reason);
        flag = findViewById(R.id.flag_input);
        submit = findViewById(R.id.submit_input);

        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }


        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput();
            }
        });


    }
    private void openDialog() {
            DialogBox dialogBox = new DialogBox();
            dialogBox.show(getSupportFragmentManager(), "Dialog Box");
    }

    private void getInput(){
        String name = nameEdit.getText().toString();
        String secondName = secondNameEdit.getText().toString();
        String idNumber= idNumberEdit.getText().toString();
        String plate = carPlateEdit.getText().toString();
        String phone = phoneNumberEdit.getText().toString();
        String purposse = purposeEdit.getText().toString();
        if(flagged){
            fReason.setVisibility(View.VISIBLE);
            fReason.setText(flaggedReason);
        }

        if (name.trim().isEmpty() || idNumber.trim().isEmpty()
                || phone.trim().isEmpty() || purposse.trim().isEmpty()) {
            Toast.makeText(this, "fill all the required field", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        Intent data =new Intent();
        data.putExtra(EXTRA+"name", name);
        data.putExtra(EXTRA+"secondname", secondName);
        data.putExtra(EXTRA+"idnumber", idNumber);
        data.putExtra(EXTRA+"plate", plate);
        data.putExtra(EXTRA+"phone", phone);
        data.putExtra(EXTRA+"purpose", purposse);
        data.putExtra(EXTRA+"date", date);
        data.putExtra(EXTRA+"time", currentTime);
        data.putExtra(EXTRA+"flagReason", flaggedReason);
        data.putExtra(EXTRA+"flagged", flagged);

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public void passText(boolean flag, String rFlag) {
        flaggedReason = rFlag;
        flagged = flag;

    }
}
