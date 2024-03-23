package in.example.code.Chatsy.Groups;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import in.example.code.Chatsy.R;


public class GroupFragment extends Fragment {
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;
    private RecyclerView mGrouplist,mGrpSuggest;
    private final List<Groups> groupsList = new ArrayList<>();
    private final List<Groups> gList = new ArrayList<>();
    private final List<String> groupsName = new ArrayList<>();
    private GroupsAdapter mAdapter;
    private GroupSuggestAdapter mAdapter2;
    private View mMainView;
    private TextView heading,suggestion_view;
    private List<String> category = new ArrayList<>();
    private Button submit;
    private Button art,entertainment,technology,education;
    private LinearLayout main_layout;
    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_group, container, false);
        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_id = mAuth.getCurrentUser().getUid();


        mGrouplist = mMainView.findViewById(R.id.groups_list2);
        mGrpSuggest = mMainView.findViewById(R.id.suggestion_list);



        heading = (TextView) mMainView.findViewById(R.id.txt7);
        submit = (Button) mMainView.findViewById(R.id.submit);
        main_layout = (LinearLayout) mMainView.findViewById(R.id.cat_main_layout);
        art = (Button) mMainView.findViewById(R.id.art);
        entertainment = (Button) mMainView.findViewById(R.id.entertainment);
        technology = (Button) mMainView.findViewById(R.id.technology);
        education = (Button) mMainView.findViewById(R.id.education);
        suggestion_view = (TextView) mMainView.findViewById(R.id.txt2);


        getGCategory();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mGrouplist.setHasFixedSize(true);
        mGrouplist.setLayoutManager(linearLayoutManager);
        mAdapter=new GroupsAdapter(getContext(),groupsList);
        mGrouplist.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager2= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mGrpSuggest.setHasFixedSize(true);
        mGrpSuggest.setLayoutManager(linearLayoutManager2);
        mAdapter2=new GroupSuggestAdapter(gList,getContext());
        mGrpSuggest.setAdapter(mAdapter2);

        Background task2 = new Background(GroupFragment.this);
        task2.execute("getGroups");


        //adding the context menu
        registerForContextMenu(mGrouplist);

        return mMainView;
    }


    private void getGCategory(){
        final DatabaseReference mUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id);
        mUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("group_cat")){
                    main_layout.setVisibility(View.GONE);
                    heading.setVisibility(View.GONE);
                    submit.setVisibility(View.GONE);
                    Background task = new Background(GroupFragment.this);
                    task.execute("getgrpSuggestions");
                }else{

                    main_layout.setVisibility(View.VISIBLE);
                    heading.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);




                        art.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                    art.setBackgroundResource(R.drawable.selected_bg);
                                    art.setTextColor(getResources().getColor(R.color.colorAccent));
                                    category.add(art.getText().toString());


                            }
                        });



                        entertainment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                entertainment.setBackgroundResource(R.drawable.selected_bg);
                                entertainment.setTextColor(getResources().getColor(R.color.colorAccent));
                                category.add(entertainment.getText().toString());

                            }
                        });
                        technology.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                technology.setBackgroundResource(R.drawable.selected_bg);
                                technology.setTextColor(getResources().getColor(R.color.colorAccent));
                                category.add(technology.getText().toString());

                            }
                        });

                        education.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                education.setBackgroundResource(R.drawable.selected_bg);
                                education.setTextColor(getResources().getColor(R.color.colorAccent));
                                category.add(education.getText().toString());

                            }
                        });



                   submit.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           DatabaseReference mCat = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id).child("group_cat");
                           for (int i=0;i<category.size();i++){
                               final Map<String, Object> messageMap = new HashMap<String, Object>();
                               messageMap.put(category.get(i),category.get(i));
                               mCat.updateChildren(messageMap, new DatabaseReference.CompletionListener() {
                                   @Override
                                   public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                       MDToast mdToast = MDToast.makeText(getContext(), "Submitted",MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                                   }
                               });
                           }
                           main_layout.setVisibility(View.GONE);
                           Animation animation   =    AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
                           animation.setDuration(200);
                           main_layout.setAnimation(animation);
                           main_layout.animate();
                           animation.start();

                           submit.setVisibility(View.GONE);
                           Animation animation2   =    AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
                           animation2.setDuration(200);
                           submit.setAnimation(animation);
                           submit.animate();
                           animation2.start();


                           heading.setVisibility(View.GONE);
                           Animation animation3   =    AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
                           animation3.setDuration(200);
                           heading.setAnimation(animation);
                           heading.animate();
                           animation3.start();

                       }
                   });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public static class Background extends AsyncTask<String,String,String>{
        private WeakReference<GroupFragment>activityWeakReference;
        Background(GroupFragment activity){
            activityWeakReference=new WeakReference<GroupFragment>(activity);
        }
        @Override
        protected String doInBackground(String... strings) {
            final GroupFragment activity=activityWeakReference.get();


            switch (strings[0]) {

                case "getGroups":
                    final DatabaseReference mGroups = FirebaseDatabase.getInstance().getReference().child("Groups");
                    mGroups.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            activity.groupsList.clear();
                            activity.groupsName.clear();
                            for (final DataSnapshot snapshot:dataSnapshot.getChildren()){
                                if (dataSnapshot.child(Objects.requireNonNull(snapshot.getKey())).child("Users").hasChild(activity.mCurrent_user_id)){
                                    Groups groups=snapshot.getValue(Groups.class);
                                    activity.groupsList.add(groups);
                                    activity.mAdapter.notifyDataSetChanged();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;

                case "getgrpSuggestions" :
                    activity.gList.clear();
                    final DatabaseReference mGroups2 = FirebaseDatabase.getInstance().getReference().child("Groups");
                    mGroups2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (final DataSnapshot snapshot:dataSnapshot.getChildren()) {
                                Groups groups = snapshot.getValue(Groups.class);
                                assert groups != null;

                                mGroups2.child(groups.getGname()).child("Users").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (!dataSnapshot.hasChild( activity.mCurrent_user_id)){
                                            DatabaseReference muser = FirebaseDatabase.getInstance().getReference().child("Users").child(activity.mCurrent_user_id).child("group_cat");
                                            muser.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.hasChild(groups.getCategory())) {
                                                        activity. gList.add(groups);
                                                        activity.mAdapter2.notifyDataSetChanged();
                                                    }
                                                    if ( activity.gList.size()== 0){
                                                        activity.suggestion_view.setVisibility(View.GONE);

                                                    }else {
                                                        activity.suggestion_view.setVisibility(View.VISIBLE);

                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;

            }

            return null;
        }
    }

    //added
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = Objects.requireNonNull(getActivity()).getMenuInflater();
        menu.setHeaderIcon(R.drawable.icon);
        inflater.inflate(R.menu.override_group_menu, menu);
    }


    //added for group chat deletion
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear_group_chat:
                mAdapter.clearChats(mGrouplist);
                return true;
        }
        return super.onContextItemSelected(item);

    }

}
