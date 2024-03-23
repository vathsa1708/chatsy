package in.example.code.Chatsy.Groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import in.example.code.Chatsy.R;

public class GroupInfoActivity extends AppCompatActivity {

    private RecyclerView mGroupUsersList;
    private DatabaseReference mUserList,mUsers;
    private String group_name;
    private List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        mGroupUsersList=(RecyclerView)findViewById(R.id.gusers_list);
        group_name=getIntent().getStringExtra("group_name");

        mUserList= FirebaseDatabase.getInstance().getReference().child("Users").child("Groups");
        mUsers= FirebaseDatabase.getInstance().getReference().child("Users");


    }

}
