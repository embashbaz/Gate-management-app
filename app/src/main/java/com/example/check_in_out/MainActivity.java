package com.example.check_in_out;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import static com.example.check_in_out.CheckIn.EXTRA;
import static com.example.check_in_out.CheckIn.EXTRA_ID;

public class MainActivity extends AppCompatActivity {

    private VisitorsViewModel viewModel;
    private int requestCodes=1;
    private int requestCodesUpdate=2;
    final VisitorsAdapter adapter = new VisitorsAdapter();
    UtilClass utilClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(adapter);


        viewModel = ViewModelProviders.of(this).get(VisitorsViewModel.class);
        viewModel.getAllVisitors().observe(this, new Observer<List<Visitors>>() {
            @Override
            public void onChanged(List<Visitors> visitors) {
                adapter.setVisitors(visitors);

            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CheckIn.class);

                startActivityForResult(intent, requestCodes);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.botton_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);

        adapter.setOnItemCLickListener(new VisitorsAdapter.OnItemCLickListerner() {
            @Override
            public void onItemCLick(Visitors visitor) {
                Intent data = new Intent(MainActivity.this, CheckOut.class);
                data.putExtra(EXTRA_ID,visitor.getId());
                data.putExtra(EXTRA + "name", visitor.getName());
                data.putExtra(EXTRA + "secondname", visitor.getSecondName());
                data.putExtra(EXTRA + "idnumber",visitor.getIdNumber());
                data.putExtra(EXTRA + "plate", visitor.getPlate());
                data.putExtra(EXTRA + "phone",visitor.getPhone());
                data.putExtra(EXTRA + "purpose", visitor.getPurpose());
                data.putExtra(EXTRA + "date",visitor.getDateIn());
                data.putExtra(EXTRA + "time", visitor.getTimeIn());
                data.putExtra(EXTRA + "flag", visitor.isFlagIn());
                data.putExtra(EXTRA+"flagReason", visitor.getFlagReason());
                data.putExtra(EXTRA + "status", visitor.isStatus());
                data.putExtra(EXTRA + "timeOut", visitor.getTimeOut());
                data.putExtra(EXTRA + "dateOut", visitor.getDateOut());

                startActivityForResult(data, requestCodesUpdate);

            }
        });
        utilClass = new UtilClass();
        utilClass.scheduleJob(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.menu_in:
                    viewModel.getInVisitors().observe(MainActivity.this, new Observer<List<Visitors>>() {
                        @Override
                        public void onChanged(List<Visitors> visitors) {
                            adapter.setVisitors(visitors);


                        }
                    });
                    break;
                case R.id.menu_out:
                    viewModel.getOutVisitors().observe(MainActivity.this, new Observer<List<Visitors>>() {
                        @Override
                        public void onChanged(List<Visitors> visitors) {
                            adapter.setVisitors(visitors);
                                       }
                    });
                    break;
                case R.id.flaged:
                    viewModel.getFlagVisitors().observe(MainActivity.this, new Observer<List<Visitors>>() {
                        @Override
                        public void onChanged(List<Visitors> visitors) {
                            adapter.setVisitors(visitors);

                        }
                    });
                    break;
                case R.id.all:
                    viewModel.getAllVisitors().observe(MainActivity.this, new Observer<List<Visitors>>() {
                        @Override
                        public void onChanged(List<Visitors> visitors) {
                            adapter.setVisitors(visitors);

                        }
                    });
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == requestCodes && resultCode == RESULT_OK ){
            String name = data.getStringExtra(EXTRA+"name");
            String secondName = data.getStringExtra(EXTRA+"secondname");
            String idNumber=data.getStringExtra(EXTRA+"idnumber") ;
            String plate = data.getStringExtra(EXTRA+"plate");
            String phone = data.getStringExtra(EXTRA+"phone");
            String purposse = data.getStringExtra(EXTRA+"purpose");
            String date = data.getStringExtra(EXTRA+"date");
            String currentTime =data.getStringExtra(EXTRA+"time");
            String flagReason = data.getStringExtra(EXTRA+"flagReason");
            boolean flag = data.getBooleanExtra(EXTRA+"flagged", false);


            long phoneLong = Long.parseLong(phone);

          Visitors visitors = new Visitors(name,secondName,true,idNumber,date,
                  "","",currentTime,"",flag,false,
                  flagReason,purposse,phoneLong,plate);
                viewModel.insert(visitors);
            Toast.makeText(this, "submited", Toast.LENGTH_SHORT).show();

        }else  if(requestCode == requestCodesUpdate && resultCode == RESULT_OK ){
            String name = data.getStringExtra(EXTRA+"name");
            String secondName = data.getStringExtra(EXTRA+"secondname");
            String idNumber=data.getStringExtra(EXTRA+"idnumber") ;
            String plate = data.getStringExtra(EXTRA+"plate");
            String phone = data.getStringExtra(EXTRA+"phone");
            String purposse = data.getStringExtra(EXTRA+"purpose");
            String date = data.getStringExtra(EXTRA+"date");
            String currentTime =data.getStringExtra(EXTRA+"time");
            String comment = data.getStringExtra(EXTRA+"comment");
            String timeOut = data.getStringExtra(EXTRA+"timeOut");
            String flagReason = data.getStringExtra(EXTRA+"flagReason");
            String dateOut = data.getStringExtra(EXTRA+"dateOut");
            boolean flagIn = data.getBooleanExtra(EXTRA+"flag", false);
            boolean flagOut = data.getBooleanExtra(EXTRA+"flagOut", false);
            int id = data.getIntExtra(EXTRA_ID, -1);
            if(id==-1){
                Toast.makeText(this, "can not check out the guest", Toast.LENGTH_SHORT).show();
                return;
            }

            //long phoneLong = Long.parseLong(phone);

            Visitors visitors = new Visitors(name,secondName,false,idNumber,date,
                    dateOut,timeOut,currentTime,comment,flagIn,flagOut,
                    flagReason,purposse,0,plate);
            visitors.setId(id);
            viewModel.update(visitors);
            Toast.makeText(this, "submited", Toast.LENGTH_SHORT).show();

        }
    }
}
