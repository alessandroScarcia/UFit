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
    private String userLinkId = null;

    public AdviceList(iAdvice.View view) {
        this.view = view;
        adviceList= new ArrayList<>();
        loadAdviceList();
    }


    /**
     * Function used to generate a random id. This function is useful for the new advice added in the
     * list showed in the view
     * @param len lenght of the id
     * @return sb.toString()
     */
    public String randomId(int len) {
        StringBuilder sb = new StringBuilder(len);
        String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }


    /**
     * This function provide to find the userLinkId and call the function to get a single advice
     */
    public void getRandomAdvice(){
        database = FirebaseDbSingleton.getInstance().getReference(User.CHILD_NAME);
        firebaseUser = FirebaseAuthSingleton.getFirebaseAuth().getCurrentUser();

        Log.d(TAG, "loggato" + firebaseUser.getUid());

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
                            }
                        }
                        getSingleAdvice();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    /**
     * Function used to get a random advice from the database
     */
    public void getSingleAdvice(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Advice");
        final String language = Locale.getDefault().getISO3Language();
        Query mRandomAdviceQuery;

        Log.d(TAG, "Avvio di getSingleAdvice "+ userLinkId );

        if(userLinkId != null){
            //query if the athlete is connected to a trainer
            mRandomAdviceQuery = mDatabase.orderByChild("author").equalTo(userLinkId);
        }else{
            //query used to retrive the advice of the system
            mRandomAdviceQuery = mDatabase.orderByChild("author").equalTo("-1");
        }

        mRandomAdviceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adviceList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d(TAG, "loadPersonalTrainerAdvice->onDataChange:" + child.getKey());

                    Advice temp = child.getValue(Advice.class);

                    //we check the advice taked from the database and check the language
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

    /**
     * Function that communicate with the view
     * @param advice advice that we want to show
     */
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

    /**
     * Function used to add a new advice into the databse with the information passed and the language
     * of the system
     * @param title  of the advice
     * @param description of the advice
     * @param author of the advice
     */
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


    /**
     * Function used in the section of trainer advice that show all the advice created from a specific
     * trainer
     */
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
