package com.fac.firebasecrud;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //E1
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-crud-44546-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("data");

    //E2
    List<User> userList = new ArrayList();
    boolean isupdated = false;

    //E3
    RecyclerView recyclerView;
    UserAdapter adapter;

    //E4
    FloatingActionButton floatingActionButton;

    /**
     *  E1  - Very Simple CRUD Operation Firebase Realtime Database
     *  E2  - CRUD Operation with Object
     *  E3  - CRUD Operation with List Object RecyclerView and Simple UI
     *  E4  - CRUD Operation with List Object RecyclerView and Custom UI (Button to add/edit/delete) with AlertDialog
     *
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        //createData();
        readData();
        //updateData();
        //deleteData();
    }

    private void initUI()
    {
        //E3
        recyclerView = findViewById(R.id.userRV);
        adapter = new UserAdapter(this, userList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //E4
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAdd();
            }
        });
    }

    //Create
    public void createData()
    {
        //E1
        //myRef.setValue("Hello, World!");

        //E2
        String uid = myRef.push().getKey();
        User user = new User(
                uid,
                "FA",
                27,
                3000000,
                true
        );
        myRef.child(uid).setValue(user);
    }

    //Read
    private void readData()
    {
        /*E1
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("Read", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Read", "Failed to read value.", error.toException());
            }
        });*/

        //E2
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot k : dataSnapshot.getChildren())
                {
                    User value = k.getValue(User.class);
                    Log.d("Read", "Value is: " + value);
                    userList.add(value);

                    //E3
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Read", "Failed to read value.", error.toException());
            }
        });
    }

    //Update
    private void updateData(User user)
    {
        //E1
        //myRef.setValue("Hello, World Updated!");

        //E2
        //user.married = false;
        user.setUsername(user.getUsername());
        user.setAge(user.getAge());
        user.setSaving(user.getSaving());
        user.setMarried(user.isMarried());
        myRef.child(user.getUid()).setValue(user);
    }

    //Delete
    private void deleteData(User user)
    {
        //E1
        //myRef.setValue(null);
        //E2
        myRef.child(user.getUid()).setValue(null);
    }

    //E4

    public void clickAdd()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(this,R.layout.user_list_edit, null);
        EditText usernameET = view.findViewById(R.id.usernameET);
        EditText ageET = view.findViewById(R.id.ageET);
        EditText savingET = view.findViewById(R.id.savingET);
        CheckBox marriedCB = view.findViewById(R.id.marriedCB);

        alertDialog.setView(view);

        alertDialog.setTitle("Add User");
        alertDialog.setPositiveButton("Add", (dialog, which) ->
        {
            String uid = myRef.push().getKey();
            User user = new User(
                    uid,
                    usernameET.getText().toString(),
                    Integer.parseInt(ageET.getText().toString()),
                    Double.parseDouble(savingET.getText().toString()),
                    marriedCB.isChecked()
            );
            myRef.child(uid).setValue(user);
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) ->
        {
            dialog.dismiss();
        });
        alertDialog.show();
    }

    public void clickEdit(User user)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(this,R.layout.user_list_edit, null);
        EditText usernameET = view.findViewById(R.id.usernameET);
        EditText ageET = view.findViewById(R.id.ageET);
        EditText savingET = view.findViewById(R.id.savingET);
        CheckBox marriedCB = view.findViewById(R.id.marriedCB);

        usernameET.setText(user.getUsername());
        ageET.setText(String.valueOf(user.getAge()));
        savingET.setText(String.valueOf(user.getSaving()));
        marriedCB.setChecked(user.isMarried());

        alertDialog.setView(view);

        alertDialog.setTitle("Edit User");
        alertDialog.setPositiveButton("Update", (dialog, which) ->
        {
            user.setUsername(usernameET.getText().toString());
            user.setAge(Integer.parseInt(ageET.getText().toString()));
            user.setSaving(Double.parseDouble(savingET.getText().toString()));
            user.setMarried(marriedCB.isChecked());
            myRef.child(user.getUid()).setValue(user);
            //updateData(user);
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) ->
        {
            dialog.dismiss();
        });
        alertDialog.show();
    }

    public void clickDelete(User user)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Delete User");
        alertDialog.setMessage("Are you sure you want to delete this user?");
        alertDialog.setPositiveButton("Delete", (dialog, which) ->
        {
            deleteData(user);
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) ->
        {
            dialog.dismiss();
        });
        alertDialog.show();
    }
}