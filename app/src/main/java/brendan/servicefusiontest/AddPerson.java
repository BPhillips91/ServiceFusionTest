package brendan.servicefusiontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddPerson extends AppCompatActivity {
    EditText first,last,zip,dob;
    Button addPerson;
    String firstName,lastName,zipCode,DoB;
    DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference("persons");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        first = (EditText)findViewById(R.id.editText);
        last = (EditText)findViewById(R.id.editText2);
        dob = (EditText)findViewById(R.id.editText3);
        zip =(EditText)findViewById(R.id.editText4);
        addPerson = (Button)findViewById(R.id.addPerson);
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean filledOut = false;
                firstName = first.getText().toString();
                lastName = last.getText().toString();
                zipCode = zip.getText().toString();
                DoB= dob.getText().toString();
                if(firstName.isEmpty()){
                    first.setError("Please enter a First Name");
                    filledOut = true;
                }
                if (lastName.isEmpty()){
                    last.setError("Please enter a Last Name");
                    filledOut = true;
                }
                if (DoB.isEmpty()){
                    dob.setError("Please enter Date of Birth");
                    filledOut = true;
                }
                 if (zipCode.isEmpty()){
                    zip.setError("Please enter Zip Code");
                     filledOut = true;
                 }
                if (!filledOut){
                    String key = personsRef.push().getKey();
                    Map<String,String> newPerson = new HashMap<String, String>();
                    newPerson.put("dob",DoB);
                    newPerson.put("first",firstName);
                    newPerson.put("last",lastName);
                    newPerson.put("zip",zipCode);
                    personsRef.child(key).setValue(newPerson);
                    Toast.makeText(AddPerson.this,firstName+" "+lastName+" was added!",Toast.LENGTH_SHORT).show();
                    first.setText("");
                    last.setText("");
                    dob.setText("");
                    zip.setText("");
                }
            }
        });
    }
}
