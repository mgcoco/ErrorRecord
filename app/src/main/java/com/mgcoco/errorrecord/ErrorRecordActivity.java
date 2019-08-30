package com.mgcoco.errorrecord;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ErrorRecordActivity extends AppCompatActivity {

    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_record);
        mList = findViewById(R.id.error_record_list);

        ErrorRecordAdapter adapter = new ErrorRecordAdapter(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(adapter);

        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =  new AlertDialog.Builder(ErrorRecordActivity.this);
                final View disClaimerView = LayoutInflater.from(ErrorRecordActivity.this).inflate(R.layout.dialog_error_detail, null);
                final TextView errorMsg = disClaimerView.findViewById(R.id.error_detail_msg);
                final TextView title = disClaimerView.findViewById(R.id.error_detail_title);
                builder.setView(disClaimerView);
                errorMsg.setText(((ErrorLog)v.getTag()).getMessage());
                title.setText(((ErrorLog)v.getTag()).getTime());
                final AlertDialog dialog = builder.create();

                disClaimerView.findViewById(R.id.error_detail_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    public void onBack(View view){
        finish();
    }

}
