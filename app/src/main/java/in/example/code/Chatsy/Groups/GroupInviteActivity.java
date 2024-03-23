package in.example.code.Chatsy.Groups;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.example.code.Chatsy.Model;
import in.example.code.Chatsy.R;
import in.example.code.Chatsy.Users;

public class GroupInviteActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;
    private RecyclerView mFriendList;
    private List<Model> mModelList;
    private final List<String> friendlist = new ArrayList<>();
    private final List<Users> userName = new ArrayList<>();
    private GroupInviteAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_invite);
        mAuth = FirebaseAuth.getInstance();
        String gname = getIntent().getStringExtra("gname");
        mCurrent_user_id = mAuth.getCurrentUser().getUid();


        mFriendList=(RecyclerView)findViewById(R.id.grp_friends_list);
        getfriends();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GroupInviteActivity.this);
        mFriendList.setHasFixedSize(true);
        mFriendList.setLayoutManager(linearLayoutManager);
        mAdapter=new GroupInviteAdapter(GroupInviteActivity.this,userName,gname);
        mFriendList.setAdapter(mAdapter);


    }

    private void getfriends(){
        DatabaseReference mFriends = FirebaseDatabase.getInstance().getReference().child("Friends")
                .child(mCurrent_user_id);

        mFriends.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    final DatabaseReference mUsers = FirebaseDatabase.getInstance().getReference().child("Users");
                    mUsers.child(Objects.requireNonNull(snapshot.getKey())).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Users users =dataSnapshot.getValue(Users.class);
                            userName.add(users);
                            mAdapter.notifyDataSetChanged();

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
