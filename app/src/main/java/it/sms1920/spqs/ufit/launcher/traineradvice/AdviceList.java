package it.sms1920.spqs.ufit.launcher.traineradvice;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import it.sms1920.spqs.ufit.model.firebase.auth.FirebaseAuthSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.Advice;
import it.sms1920.spqs.ufit.model.firebase.database.FirebaseDbSingleton;
import it.sms1920.spqs.ufit.model.firebase.database.User;


public class AdviceList implements iAdvice.Presenter {

    private static final String TAG = AdviceList.class.getCanonicalName();
    private static final int ID_LENGHT = 8;
    private final iAdvice.View view;

    private List<Advice> adviceList;
    private Random RANDOM = new Random();


    private FirebaseUser firebaseUser;
    private DatabaseReference database;
    private String userLinkId;


    public String randomId(int len) {
        StringBuilder sb = new StringBuilder(len);
        String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }


    public void getUserLinkTrainer(){
        database = FirebaseDbSingleton.getInstance().getReference(User.CHILD_NAME);
        firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();

        database.keepSynced(true);

        database.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            if (user.getLinkedUserId() != null) {
                                userLinkId = user.getLinkedUserId()+"";
                                Log.d(TAG, "Id trainer" + user.getLinkedUserId());
                                getSingleAdvice();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    public AdviceList(iAdvice.View view) {
        this.view = view;
        adviceList= new ArrayList<>();
        loadAdviceList();
    }

    @Override
    public void getRandomAdvice(){
        getUserLinkTrainer();
    }


    public void getSingleAdvice(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Advice");
        final String language = Locale.getDefault().getISO3Language();
        Query mRandomAdviceQuery;

        Log.d(TAG, "Avvio di getSingleAdvice "+ userLinkId );

        if(userLinkId != null){
            mRandomAdviceQuery = mDatabase.orderByChild("author").equalTo(userLinkId);
        }else{
            mRandomAdviceQuery = mDatabase.orderByChild("author").equalTo("-1");
        }

        mRandomAdviceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adviceList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d(TAG, "loadPersonalTrainerAdvice->onDataChange:" + child.getKey());

                    Advice temp = child.getValue(Advice.class);
                    if (temp != null && temp.getCodLanguage().equals(language)) {
                        Log.d(TAG, temp.getTitle());
                        adviceList.add(temp);
                    }
                }
                Log.d(TAG, String.valueOf(adviceList.size()));
                Random random =  new Random();
                if(adviceList.size() != 0) {
                    saveRandomAdvice(adviceList.get(random.nextInt(adviceList.size())));
                    Log.d(TAG, "Consigli del sistema");
                }else{
                    Log.d(TAG, "il trainer non ha pubblicato nessun consiglio");
                    userLinkId = null;
                    getSingleAdvice();
                }
                if(adviceList.size() != 0 && userLinkId != null) {
                    Log.d(TAG, "il Trainer ha pubblicato consigli");
                }
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
        database = FirebaseDbSingleton.getInstance().getReference(User.CHILD_NAME);
        firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();

        assert firebaseUser != null;
        advice.setAuthor(firebaseUser.getUid());
        advice.setAdviceId(randomId(ID_LENGHT));

        String language = Locale.getDefault().getISO3Language();
        advice.setCodLanguage(language);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Advice").child(advice.getAdviceId()).setValue(advice);

        adviceList.add(advice);
        view.callNotifyDataSetChanged();
    }


    private void loadAdviceList() {
        Log.d(TAG, "loadAdvice");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDbSingleton.getInstance().getReference(User.CHILD_NAME);
        firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();

        assert firebaseUser != null;

        // TODO add reference to local user
        Query mPersonalTrainerAdviceQuery = mDatabase.child("Advice").orderByChild("author").equalTo(firebaseUser.getUid());

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
