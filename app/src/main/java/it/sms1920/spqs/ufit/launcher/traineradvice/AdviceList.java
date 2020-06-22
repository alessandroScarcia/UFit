package it.sms1920.spqs.ufit.launcher.traineradvice;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.sms1920.spqs.ufit.model.firebase.database.Advice;


public class AdviceList implements iAdvice.Presenter {

    private static final String TAG = AdviceList.class.getCanonicalName();
    private static final int ID_LENGHT = 8;
    private final iAdvice.View view;

    private List<Advice> adviceList;
    private Random RANDOM = new Random();



    public String randomId(int len) {
        StringBuilder sb = new StringBuilder(len);
        String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }


    public AdviceList(iAdvice.View view) {
        this.view = view;
        adviceList= new ArrayList<>();
        loadAdviceList();
    }

    @Override
    public void getRandomAdvice(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Advice");

//        Query mRandomAdviceQuery = mDatabase.child("Advice").orderByChild("author").equalTo("-1").startAt(1).endAt(4);
        Query zone1Ref = mDatabase.orderByChild("author").equalTo("-1");
        Log.d(TAG, String.valueOf(adviceList.size()));
        zone1Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Advice adviceR = dataSnapshot.getValue(Advice.class);
//                Log.d(TAG, adviceR.getTitle());
//                saveRandomAdvice(adviceR);
                adviceList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d(TAG, "loadPersonalTrainerAdvice->onDataChange:" + child.getKey());

                    Advice temp = child.getValue(Advice.class);
                    if (temp != null) {
                        Log.d(TAG, temp.getTitle());
                        adviceList.add(temp);
                    }
                }
                Random random =  new Random();
                saveRandomAdvice(adviceList.get(random.nextInt(adviceList.size())));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public void saveRandomAdvice(Advice advice){
        view.setRandomAdvice(advice.getTitle(),advice.getDescription());
    }


    @Override
    public void onDeleteClicked(int position, String adviceId) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Advice").child(adviceId).removeValue();
        adviceList.remove(adviceList.remove(position));
        view.callNotifyDataSetChanged();
    }

    @Override
    public void onBindAdviceItemListViewAtPosition(iAdvice.View.Item holder, int position) {
        Advice itemData = adviceList.get(position);
        Log.d(TAG, itemData.getTitle());
        holder.setPosition(position);
        holder.setTitle(itemData.getTitle());
        holder.setDescription(itemData.getDescription());
        holder.setAuthor(itemData.getAuthor());
        holder.setAdviceId(itemData.getAdviceId());

    }

    @Override
    public int getAdviceCount() {
        return adviceList.size();
    }

    @Override
    public void onPersonalAdviceRequired() {
    }

    @Override
    public void addNewAdviceItem(String title, String description, String author){
        Advice advice = new Advice();
        advice.setTitle(title);
        advice.setDescription(description);
        advice.setAuthor(author);
        advice.setAdviceId(randomId(ID_LENGHT));


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Advice").child(advice.getAdviceId()).setValue(advice);

        adviceList.add(advice);
        view.callNotifyDataSetChanged();
    }


    private void loadAdviceList() {
        Log.d(TAG, "loadAdvice");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        // TODO add reference to local user
        Query mPersonalTrainerAdviceQuery = mDatabase.child("Advice").orderByChild("author").equalTo("-1");

        mPersonalTrainerAdviceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d(TAG, "loadPersonalTrainerAdvice->onDataChange:" + child.getKey());

                    Advice temp = child.getValue(Advice.class);
                    if (temp != null) {
                        Log.d(TAG, temp.getTitle());
                        adviceList.add(temp);
                    }
                }

                view.callNotifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // TODO add action when listener is cancelled
            }
        });
    }

}
