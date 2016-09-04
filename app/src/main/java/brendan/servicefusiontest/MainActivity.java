package brendan.servicefusiontest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public String TAG = "TESTING";

    PersonsAdapter mAdapter;
    RecyclerView mRecycler;
    RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton fab;
    List<Persons> mPersonsList;
    List<String> mPersonsKeys;
    FirebaseDatabase DB = FirebaseDatabase.getInstance();
    DatabaseReference personsRef = DB.getReference("persons");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);

        updateAdapter();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPerson.class);
                startActivity(intent);
            }
        });

        personsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                updateAdapter();
                Log.d(TAG, "onDataChange: "+dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Animation to hide the add Persons button while scrolling
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


    }



    public void updateAdapter(){
        mPersonsList = new ArrayList<>();
        mPersonsKeys = new ArrayList<>();

     ChildEventListener mChildListener = new ChildEventListener() {
         @Override
         public void onChildAdded(DataSnapshot dataSnapshot, String s) {
             String keys = dataSnapshot.getKey();
             mPersonsKeys.add(keys);
             String zip = dataSnapshot.child("zip").getValue().toString();
             String first = dataSnapshot.child("first").getValue().toString();
             String last = dataSnapshot.child("last").getValue().toString();
             String dob = dataSnapshot.child("dob").getValue().toString();
             Persons person = new Persons(first, last, dob, zip);
             mPersonsList.add(person);
             mAdapter = new PersonsAdapter(mPersonsList, mPersonsKeys, MainActivity.this);
             mRecycler.setAdapter(mAdapter);
         }

         @Override
         public void onChildChanged(DataSnapshot dataSnapshot, String s) {
             Log.d(TAG, "onChildChanged: ");
             mAdapter.notifyDataSetChanged();
         }
         @Override
         public void onChildRemoved(DataSnapshot dataSnapshot) {
             mAdapter.notifyDataSetChanged();
         }

         @Override
         public void onChildMoved(DataSnapshot dataSnapshot, String s) {

         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     };
     personsRef.addChildEventListener(mChildListener);

 }
}
