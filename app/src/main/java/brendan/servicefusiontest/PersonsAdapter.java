package brendan.servicefusiontest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brendan on 9/2/16.
 */
public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.personsHolder> {
    DatabaseReference personsReference = FirebaseDatabase.getInstance().getReference("persons");
    LayoutInflater mInflater;
    List<Persons> personsList;
    List<String> mKeysList;
    Context mContext;

    public PersonsAdapter(List<Persons> personsList, List<String> mKeysList, Context mContext) {
        this.personsList = personsList;
        this.mKeysList = mKeysList;
        this.mContext = mContext;
    }


    @Override
    public personsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.from(parent.getContext()).inflate(R.layout.person, parent, false);
        personsHolder holder = new personsHolder(v, mContext, personsList);


        return holder;
    }

    @Override
    public void onBindViewHolder(personsHolder holder, int position) {
        holder.first.setText(personsList.get(position).getfirst());
        holder.last.setText(personsList.get(position).getlast());
        holder.dob.setText("DoB: " +personsList.get(position).getdob());
        holder.zip.setText("Zip Code: " +personsList.get(position).getzip());

    }

    @Override
    public int getItemCount() {
        return personsList.size();
    }

    public class personsHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        List<Persons> mPersons = new ArrayList<>();
        Context context;
        TextView first,last,dob,zip;

        public personsHolder(View itemView, Context context, List<Persons> person) {
            super(itemView);
            this.mPersons = person;
            this.context = context;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            first = (TextView)itemView.findViewById(R.id.firstName);
            last = (TextView)itemView.findViewById(R.id.lastName);
            dob = (TextView)itemView.findViewById(R.id.dob);
            zip = (TextView) itemView.findViewById(R.id.zipCode);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Persons person = mPersons.get(position);
            String key = mKeysList.get(position);
            Intent intent = new Intent(this.context,EditPerson.class);
            intent.putExtra("dob",person.getdob());
            intent.putExtra("first",person.getfirst());
            intent.putExtra("last",person.getlast());
            intent.putExtra("zip",person.getzip());
            intent.putExtra("key",key);
            mContext.startActivity(intent);
            Log.d("Test", "onClick: ");
        }

        @Override
        public boolean onLongClick(View view) {
            Log.d("Test", "onLongClick: ");
            int position = getAdapterPosition();
            createAndShowAlertDialog(position);
            return false;
        }

        private void createAndShowAlertDialog(Integer mPosition) {
            final int position = mPosition;
            AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            builder.setTitle("Remove");
            builder.setMessage("Are you sure you want to remove this person?");
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String getKey = mKeysList.get(position);
                    mPersons.remove(position);
                    mKeysList.remove(position);
                    personsReference.child(getKey).removeValue();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
