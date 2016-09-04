package brendan.servicefusiontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditPerson extends AppCompatActivity {
    DatabaseReference personRef = FirebaseDatabase.getInstance().getReference("persons");
    String dob,first,last,zip,key;
    EditText firstName,lastName,dateOfBirth,zipCode;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        key = getIntent().getStringExtra("key");

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        zipCode = (EditText) findViewById(R.id.zipCode);
        edit = (Button)findViewById(R.id.edit);

        firstName.setText(getIntent().getStringExtra("first"));
        lastName.setText(getIntent().getStringExtra("last"));
        dateOfBirth.setText(getIntent().getStringExtra("dob"));
        zipCode.setText(getIntent().getStringExtra("zip"));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dob = dateOfBirth.getText().toString();
                first = firstName.getText().toString();
                last = lastName.getText().toString();
                zip = zipCode.getText().toString();
                Map<String,String> editPerson = new HashMap<String, String>();
                editPerson.put("dob",dob);
                editPerson.put("first",first);
                editPerson.put("last",last);
                editPerson.put("zip",zip);
                personRef.child(key).setValue(editPerson);
                finish();
            }
        });
    }
}
