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

import static com.example.check_in_out.CheckIn.EXTRA;
import static com.example.check_in_out.CheckIn.EXTRA_ID;

public class CheckOut extends AppCompatActivity implements DialogBox.ListenerDialogBox {

    TextView nameTv;
    TextView secondNameTv;
    TextView idTv;
    TextView plateTv;
    TextView purposeTv;
    TextView dateInTv;
    TextView timeInTv;
    TextView phoneTv;
    TextView timeOutTv;
    TextView flagReason;
    EditText commentOut;
    Button flagOut;
    Button submitOut;
    String flaggedReason;
    boolean flagged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        nameTv = findViewById(R.id.name_out);
        secondNameTv = findViewById(R.id.second_name_out);
        idTv = findViewById(R.id.id_number_out);
        plateTv = findViewById(R.id.plate_out);
        purposeTv = findViewById(R.id.purpose_out);
        dateInTv = findViewById(R.id.date_in);
        timeInTv = findViewById(R.id.time_in);
        phoneTv = findViewById(R.id.phone_out);
        timeOutTv = findViewById(R.id.time_out);
        commentOut = findViewById(R.id.comment_outgoing);
        flagOut = findViewById(R.id.flag_out);
        submitOut = findViewById(R.id.submit_out);
        flagReason = findViewById(R.id.flag_out_reason);


        setTexts();
        flagOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        submitOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });


    }
    private void openDialog() {
        DialogBox dialogBox = new DialogBox();
        dialogBox.show(getSupportFragmentManager(), "Dialog Box");
    }

    private void sendData() {
        String name = nameTv.getText().toString();
        String secondName = secondNameTv.getText().toString();
        String idNumber= idTv.getText().toString();
        String plate = plateTv.getText().toString();
        String phone = phoneTv.getText().toString();
        String purposse = purposeTv.getText().toString();
        String date = dateInTv.getText().toString();
        String time = timeInTv.getText().toString();
        String comment = commentOut.getText().toString();
        boolean flagIn = getIntent().getBooleanExtra(EXTRA + "flag", false);


        String currentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        Intent data =new Intent();
        data.putExtra(EXTRA+"name", name);
        data.putExtra(EXTRA+"secondname", secondName);
        data.putExtra(EXTRA+"idnumber", idNumber);
        data.putExtra(EXTRA+"plate", plate);
        data.putExtra(EXTRA+"phone", phone);
        data.putExtra(EXTRA+"purpose", purposse);
        data.putExtra(EXTRA+"date", date);
        data.putExtra(EXTRA+"time", time);
        data.putExtra(EXTRA+"comment", comment);
        data.putExtra(EXTRA+"timeOut", currentTime);
        data.putExtra(EXTRA+"flagReason", flaggedReason);
        data.putExtra(EXTRA+"dateOut", currentdate);
        data.putExtra(EXTRA + "flag", flagIn);
        data.putExtra(EXTRA + "flagOut", flagged);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1)
            data.putExtra(EXTRA_ID, id);

        setResult(RESULT_OK, data);
        finish();
    }

    private void setTexts() {
        Intent intent = getIntent();

        if(!intent.getBooleanExtra(EXTRA+"status", true)){
            submitOut.setEnabled(false);
            flagOut.setEnabled(false);
            timeOutTv.setVisibility(View.VISIBLE);
        }
        if(intent.getBooleanExtra(EXTRA+"flagged", false) || intent.getBooleanExtra(EXTRA + "flag", false)){
            flagReason.setVisibility(View.VISIBLE);
            flagReason.setText(intent.getStringExtra(EXTRA+"flagReason"));
        }

        String timeOut = ""+intent.getStringExtra(EXTRA+"timeOut")+"--"+intent.getStringExtra(EXTRA+"dateOut");

        nameTv.setText(intent.getStringExtra(EXTRA+"name"));
        secondNameTv.setText(intent.getStringExtra(EXTRA+"secondname"));
        idTv.setText(intent.getStringExtra(EXTRA+"idnumber"));
        plateTv.setText(intent.getStringExtra(EXTRA+"plate"));
        phoneTv.setText(intent.getStringExtra(EXTRA+"phone"));
        purposeTv.setText(intent.getStringExtra(EXTRA+"purpose"));
        dateInTv.setText(intent.getStringExtra(EXTRA+"date"));
        timeInTv.setText(intent.getStringExtra(EXTRA+"time"));
        timeOutTv.setText(timeOut);




    }

    @Override
    public void passText(boolean flag, String rFlag) {

        flagged = flag;
        if(flagged && flagReason.getText().toString().trim().isEmpty())
            flaggedReason = ""+rFlag;
        else  if(flagged && !flagReason.getText().toString().trim().isEmpty()) {
            flaggedReason = flagReason.getText().toString() + " and flagged out reason is "
                    + rFlag;
        }
        else
            flaggedReason = "";


    }
}
