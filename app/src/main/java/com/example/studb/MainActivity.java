package com.example.studb;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private EditText editName, editRollNo, editMarks, editAddress;
    private Button btnAddData, btnViewAll, btnUpdate, btnDelete, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();

        myDb = new DatabaseHelper(this);

        setupListeners();
    }

    private void initializeUI() {
        editName = findViewById(R.id.editText_name);
        editRollNo = findViewById(R.id.editText_rollno);
        editMarks = findViewById(R.id.editText_marks);
        editAddress = findViewById(R.id.editText_address);

        btnAddData = findViewById(R.id.button_add);
        btnViewAll = findViewById(R.id.button_viewAll);
        btnUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);
        btnView = findViewById(R.id.button_view);
    }

    private void setupListeners() {
        btnAddData.setOnClickListener(v -> insertData());
        btnViewAll.setOnClickListener(v -> displayAllData());
        btnUpdate.setOnClickListener(v -> updateData());
        btnDelete.setOnClickListener(v -> deleteData());
        btnView.setOnClickListener(v -> displaySingleData());
    }

    private void insertData() {
        boolean isInserted = myDb.insertStudent(
                Integer.parseInt(editRollNo.getText().toString()),
                editName.getText().toString(),
                Integer.parseInt(editMarks.getText().toString()),
                editAddress.getText().toString()
        );

        showToast(isInserted ? "Data Inserted" : "Data not Inserted");
    }

    private void displayAllData() {
        Cursor res = myDb.getAllStudents();
        if (res.getCount() == 0) {
            showMessage("Error", "Nothing found");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append("Roll No :").append(res.getString(0)).append("\n")
                    .append("Name :").append(res.getString(1)).append("\n")
                    .append("Marks :").append(res.getString(2)).append("\n")
                    .append("Address :").append(res.getString(3)).append("\n\n");
        }

        showMessage("Data", buffer.toString());
    }

    private void updateData() {
        boolean isUpdate = myDb.updateStudent(
                Integer.parseInt(editRollNo.getText().toString()),
                editName.getText().toString(),
                Integer.parseInt(editMarks.getText().toString()),
                editAddress.getText().toString()
        );

        showToast(isUpdate ? "Data Updated" : "Data not Updated");
    }

    private void deleteData() {
        int deletedRows = myDb.deleteStudent(Integer.parseInt(editRollNo.getText().toString()));
        showToast(deletedRows > 0 ? "Data Deleted" : "Data not Deleted");
    }

    private void displaySingleData() {
        Cursor res = myDb.getStudent(Integer.parseInt(editRollNo.getText().toString()));
        if (res.getCount() == 0) {
            showMessage("Error", "Nothing found");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append("Roll No :").append(res.getString(0)).append("\n")
                    .append("Name :").append(res.getString(1)).append("\n")
                    .append("Marks :").append(res.getString(2)).append("\n")
                    .append("Address :").append(res.getString(3)).append("\n\n");
        }

        showMessage("Data", buffer.toString());
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void showMessage(String title, String message) {
        new android.app.AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}
