package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements TabInfosFragment.OnFragmentInteractionListener, TabFriendFragment.OnFragmentInteractionListener, TabNotificationFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ImageView mIcon;
    private ImageView mAvatar;
    private String mUid;
    private TextView mUsername;
    private TextView mScoreValue;
    private ImageView mMedalBronze;
    private ImageView mMedalSilver;
    private ImageView mMedalGold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(getString(R.string.title_profile));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.name_tab1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.name_tab2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.name_tab3));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);



        mIcon = findViewById(R.id.icon_change_tab);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0) {
                    mIcon.setImageResource(R.drawable.logo_info);

                }
                if (tab.getPosition() == 1) {
                    mIcon.setImageResource(R.drawable.logo_friends);

                }
                if (tab.getPosition() == 2) {
                    mIcon.setImageResource(R.drawable.logo_notif);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mIcon.setImageResource(R.drawable.logo_info);
                }

                if (tab.getPosition() == 1) {
                    mIcon.setImageResource(R.drawable.logo_friends);
                }

                if (tab.getPosition() == 2) {
                    mIcon.setImageResource(R.drawable.logo_notif);
                }
            }
        });

        //Navigation Drawer :
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_profile);
        mToggle = new ActionBarDrawerToggle(ProfileActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Navigation View :
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_profile);
        navigationView.setNavigationItemSelectedListener(this);

        //Affichage du profil dans la nav bar :
        View headerLayout = navigationView.getHeaderView(0);
        mDatabase = FirebaseDatabase.getInstance();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAvatar = headerLayout.findViewById(R.id.image_header);
        mUsername = headerLayout.findViewById(R.id.text_username);
        mScoreValue = headerLayout.findViewById(R.id.text_score_value);
        DatabaseReference pathID = mDatabase.getReference("Users").child(mUid);
        pathID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("avatar").getValue() != null)) {
                    String url = dataSnapshot.child("avatar").getValue(String.class);
                    Glide.with(ProfileActivity.this).load(url).apply(RequestOptions.circleCropTransform()).into(mAvatar);
                }
                if ((dataSnapshot.child("Name").getValue() != null)) {
                    String username = dataSnapshot.child("Name").getValue(String.class);
                    mUsername.setText(username);
                }
                //For Score
                if ((dataSnapshot.child("score").getValue() != null)){
                    String score = String.valueOf(dataSnapshot.child("score").getValue(int.class));
                    mScoreValue.setText(score);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home) {
            Intent goToHome = new Intent(this, MenuActivity.class);
            this.startActivity(goToHome);
        } else if (id == R.id.join) {
            Intent goToJoin = new Intent(this, JoinQuizzActivity.class);
            this.startActivity(goToJoin);
        } else if (id == R.id.create) {
            Intent goToCreate = new Intent(this, CreateQuizzActivity.class);
            this.startActivity(goToCreate);
        } else if (id == R.id.profile) {
            Intent goToProfile = new Intent(this, ProfileActivity.class);
            this.startActivity(goToProfile);
        } else if (id == R.id.displayquizz) {
            Intent goToDisplayQuizz = new Intent(this, DisplayQuizzActivity.class);
            this.startActivity(goToDisplayQuizz);
        }else if (id == R.id.listquizz) {
                Intent goToListQuizz = new Intent(this, ListQuizzActivity.class);
                this.startActivity(goToListQuizz);
        } else if (id == R.id.logout) {
            //Déconnexion
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
