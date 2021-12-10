package com.example.expensemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Intialize and make object of Database Helper for Doing all Task
    DatabaseHelper databaseHelper;
    TextView datalist;
    TextView datalist_count;
    FloatingActionButton Click;
    FloatingActionButton delete;
    FloatingActionButton insert;
    FloatingActionButton update;
    FloatingActionButton read;
    boolean isOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intialize all Buttons
        databaseHelper=new DatabaseHelper(MainActivity.this);
        Click=findViewById(R.id.Click);
        delete=findViewById(R.id.delete_data);
        insert=findViewById(R.id.insert_data);
        update=findViewById(R.id.update_data);
        read=findViewById(R.id.refresh_data);
        datalist=findViewById(R.id.alldata);
        datalist_count=findViewById(R.id.data_list_count);
        refreshData();
        Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Click on Button to Perform task",Toast.LENGTH_SHORT).show();

                if(!isOpen){
                    openMenu();


                }else {
                    closeMenu();
                }
            }
        });
        //Click on Listener on Buttons to carry out Insert,Delete,Update and Refresh
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInputDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateIdDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

    }
    //Method which will show Put Values in Input Dialog
    private void ShowInputDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.insert_expanse,null);
        final EditText name=view.findViewById(R.id.name);
        final EditText Amount=view.findViewById(R.id.Amount);

        Button insertBtn=view.findViewById(R.id.insert_btn);
        al.setView(view);
        final AlertDialog alertDialog=al.show();

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetModel budgetModel=new BudgetModel();
                budgetModel.setName(name.getText().toString());
                budgetModel.setAmount(Amount.getText().toString());
                databaseHelper.AddBudget(budgetModel);
                alertDialog.dismiss();
                refreshData();
            }
        });
    }


    //Method for Refreshing data Every time we add Data
    private void refreshData() {
        datalist_count.setText("TOTAL EXPENSE : "+databaseHelper.getTotal()+"      ListCount : "+databaseHelper.getTotalCount());

        List<BudgetModel> budgetModelList=databaseHelper.getAllBudget();
        datalist.setText("");
        for(BudgetModel budgetModel:budgetModelList){
            datalist.append("ID : "+budgetModel.getId()+" | Expense Name : "+budgetModel.getName()+" | Amount : "+budgetModel.getAmount()+"  \n\n");
        }
    }
    //   Method Which will show Delete Dialog and will Delete data with ID of Table
    private void showDeleteDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.delete_expanse,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button delete_btn=view.findViewById(R.id.delete_btn);
        final AlertDialog alertDialog=al.show();

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteBudget(id_input.getText().toString());
                alertDialog.dismiss();
                refreshData();

            }
        });


    }
    // Method which will Show Update ID Dialog and we have to Put ID which we want to update
    private void showUpdateIdDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_id_expanse,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button fetch_btn=view.findViewById(R.id.update_id_btn);
        final AlertDialog alertDialog=al.show();
        fetch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataDialog(id_input.getText().toString());
                alertDialog.dismiss();
                refreshData();
            }
        });

    }
    // Method which will Show Update Dialog box after Putting id in Above method
    private void showDataDialog(final String id) {
        BudgetModel budgetModel=databaseHelper.getBudget(Integer.parseInt(id));
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_expanse,null);
        final EditText name=view.findViewById(R.id.name);
        final EditText Amount=view.findViewById(R.id.Amount);


        Button update_btn=view.findViewById(R.id.update_btn);
        al.setView(view);

        name.setText(budgetModel.getName());
        Amount.setText(budgetModel.getAmount());



        final AlertDialog alertDialog=al.show();
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetModel budgetModel=new BudgetModel();
                budgetModel.setName(name.getText().toString());
                budgetModel.setId(id);
                budgetModel.setAmount(Amount.getText().toString());

                databaseHelper.updateBudget(budgetModel);
                alertDialog.dismiss();
                refreshData();
            }
        });
    }
    private void openMenu() {
        isOpen=true;
        insert.animate().translationY(-getResources().getDimension(R.dimen.f1));
        delete.animate().translationY(-getResources().getDimension(R.dimen.f2));
        update.animate().translationY(-getResources().getDimension(R.dimen.f3));
        read.animate().translationY(-getResources().getDimension(R.dimen.f4));


    }
    private void closeMenu() {
        isOpen=false;
        insert.animate().translationY(0);
        delete.animate().translationY(0);
        update.animate().translationY(0);
        read.animate().translationY(0);

    }

}