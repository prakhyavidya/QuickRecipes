package com.example.project;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class add extends AppCompatActivity {


    int from_Where_I_Am_Coming = 0;
    private MySqlLiteHelper mydb;

    TextView name;
    TextView materials;
    TextView instructions;
    int id_To_Update = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec_add);


        name = (TextView) findViewById(R.id.editRecipeName);
        materials = (TextView) findViewById(R.id.editMaterials);
        instructions = (TextView) findViewById(R.id.editInstructions);
        mydb = new MySqlLiteHelper(this);
        Bundle extras = getIntent().getExtras();

        Button edi = (Button)findViewById(R.id.button_edit);
        edi.setVisibility(View.INVISIBLE);
        Button del= (Button) findViewById(R.id.button_delete);
        del.setVisibility(View.INVISIBLE);
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                //means this is the view part not the add recipe part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String nam = rs.getString(rs.getColumnIndex(MySqlLiteHelper.RECIPE_NAME));
                String material = rs.getString(rs.getColumnIndex(MySqlLiteHelper.RECIPE_MATERIALS));
                String instruction = rs.getString(rs.getColumnIndex(MySqlLiteHelper.RECIPE_INSTRUCTIONS));
                if (!rs.isClosed()) {
                    rs.close();
                }
                Button con = (Button) findViewById(R.id.confirm);
                con.setVisibility(View.INVISIBLE);
                Button edit= (Button) findViewById(R.id.button_edit);
                edit.setVisibility(View.VISIBLE);
                Button dele= (Button) findViewById(R.id.button_delete);
                dele.setVisibility(View.VISIBLE);


                name.setText((CharSequence) nam);
                name.setFocusable(false);
                name.setClickable(false);

                materials.setText((CharSequence) material);
                materials.setFocusable(false);
                materials.setClickable(false);

                instructions.setText((CharSequence) instruction);
                instructions.setFocusable(false);
                instructions.setClickable(false);

            }
        }

    }
    //Add && Update Recipe
    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                if (mydb.updateRecipe(id_To_Update, name.getText().toString(), materials.getText().toString(),
                        instructions.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else {

                if (mydb.insertRecipe(name.getText().toString(), materials.getText().toString(),
                        instructions.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                Intent addIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(addIntent);
            }
        }
    }




    //Edit Recipe
    public void editrun(View view) {
        Bundle extras = getIntent().getExtras();
        int Value = extras.getInt("id");
        if (Value > 0) {
            //means this is the view part not the add contact part.
            Cursor rs = mydb.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();
            String nam = rs.getString(rs.getColumnIndex(MySqlLiteHelper.RECIPE_NAME));
            String material = rs.getString(rs.getColumnIndex(MySqlLiteHelper.RECIPE_MATERIALS));
            String instruction = rs.getString(rs.getColumnIndex(MySqlLiteHelper.RECIPE_INSTRUCTIONS));
            if (!rs.isClosed()) {
                rs.close();
            }
            Button conf = (Button) findViewById(R.id.confirm);
            conf.setVisibility(View.VISIBLE);
            Button ed= (Button) findViewById(R.id.button_edit);
            ed.setVisibility(View.INVISIBLE);
            Button de = (Button) findViewById(R.id.button_delete);
            de.setVisibility(View.INVISIBLE);


            name.setText((CharSequence) nam);
            name.setFocusable(true);
            name.setFocusableInTouchMode(true);
            name.setClickable(true);

            materials.setText((CharSequence) material);
            materials.setFocusable(true);
            materials.setFocusableInTouchMode(true);
            materials.setClickable(true);

            instructions.setText((CharSequence) instruction);
            instructions.setFocusable(true);
            instructions.setFocusableInTouchMode(true);
            instructions.setClickable(true);

        }


    }
    //Delete Recipe
    public void deleterun(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deleteRecipe)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mydb.deleteRecipe(id_To_Update);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        AlertDialog d = builder.create();
        d.setTitle("Are you sure");
        d.show();

    }
}




