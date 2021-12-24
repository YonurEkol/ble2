package com.example.aman.hospitalappointy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.hospitalappointy.activity.Sdk_lib;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.orhanobut.logger.Logger;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context mContext = MainActivity.this;
    private Toolbar mToolbar;
    TextView heartStatus;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    private String Type = "", status = "";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Fragment_SectionPagerAdapter mFragment_SectionPagerAdapter;

    //Firebase Auth
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
            //.getReferenceFromUrl("https://pure-coda-174710.firebaseio.com/users");

    LocationManager lm;
    Intent intent1;

    public String sw_name;
    public String mac_address;
    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notifyThis("asd","asdcas");

        Intent intent = new Intent(getApplicationContext(),service.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }

        // Open bluetooth
        if (!BluetoothUtils.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
        // Control gps and if not open go to setting panel
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(!provider.contains("gps")){ //if gps is disabled
            intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);
        }
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", getPackageName(), null);
//        intent.setData(uri);
//        startActivity(intent);



        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Hospital Appointy");

        //DrawerLayout and ToggleButton
        mDrawerLayout = findViewById(R.id.main_drawerLayout);
        mToggle = new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //NavigationView
        mNavigationView = (NavigationView) findViewById(R.id.main_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        //TabLayout , SectionPagerAdapter & ViewPager
        mViewPager = (ViewPager) findViewById(R.id.main_ViewPager);
        mFragment_SectionPagerAdapter = new Fragment_SectionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragment_SectionPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.main_tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        View mView = mNavigationView.getHeaderView(0);
        heartStatus = (TextView) mView.findViewById(R.id.heart_status);
        heartStatus.setText("0");

    }



    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(this, currentUser.getUid().toString(), Toast.LENGTH_SHORT).show();

        Menu menuNav = mNavigationView.getMenu();
        final MenuItem nav_profile = menuNav.findItem(R.id.nav_profile);
        final MenuItem nav_ShowAppointment = menuNav.findItem(R.id.nav_showAppointment);
        final MenuItem nav_BookedAppointment = menuNav.findItem(R.id.nav_bookedAppointment);
        final MenuItem nav_feedback = menuNav.findItem(R.id.nav_feedback);
        MenuItem nav_logOut = menuNav.findItem(R.id.nav_logout);
        MenuItem nav_logIn = menuNav.findItem(R.id.nav_login);

        nav_profile.setVisible(false);
        nav_ShowAppointment.setVisible(false);
        nav_BookedAppointment.setVisible(false);
        nav_logIn.setVisible(false);
        nav_logOut.setVisible(false);
        nav_feedback.setVisible(false);


        View mView = mNavigationView.getHeaderView(0);

        // Check if user is signed in  or not
        if(currentUser == null){
            nav_logIn.setVisible(true);

            TextView userName = (TextView) mView.findViewById(R.id.header_userName);
            TextView userEmail = (TextView) mView.findViewById(R.id.header_userEmail);


            userName.setText("User Name");
            userEmail.setText("User Email");

            Toast.makeText(getBaseContext(),"Your Account is not Logged In ",Toast.LENGTH_LONG).show();
        }else {
            String currnetUID = mAuth.getCurrentUser().getUid().toString();
            //sw_name =  mUserDatabase.child("Patient_Details").child(currnetUID).child("sw_name").getValue().toString();
            mUserDatabase.child("User_Type").child(currnetUID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String type = dataSnapshot.child("Type").getValue().toString();

                    final String[] name = {""};

                    if(type.equals("Patient")){

                        mUserDatabase.child("Patient_Details").child(currnetUID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child("sw_name").getValue() != null && dataSnapshot.child("mac_address").getValue() != null){
                                    sw_name =  dataSnapshot.child("sw_name").getValue().toString();
                                    mac_address =  dataSnapshot.child("mac_address").getValue().toString();
                                    RunOnBack rr = new RunOnBack();
                                    new Thread(rr).start();
//                                    Sdk_lib sl = new Sdk_lib(mContext);
//                                    sl.heartStatus = heartStatus;
//                                    sl.connectDevice(dataSnapshot.child("mac_address").getValue().toString(), dataSnapshot.child("sw_name").getValue().toString(),"HEART_DETECT_START");
//                                    sl.baslat("FATIGUE_OPEN");
                                    //sl.baslat("HEART_DETECT_START");
                                    //sl.baslat("LIANSUO_SOS");
                                }
                                //email[0] = dataSnapshot.child("Email").getValue().toString();

                                //mEmail.setText(email[0]);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }else if(type.equals("Doctor")){

                        mUserDatabase.child("Doctor_Details").child(currnetUID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            nav_logOut.setVisible(true);
            chechType();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Logger.i("Starting the service in >=26 Mode");
            startForegroundService(new Intent(MainActivity.this,Sdk_lib.class));
//            return;
        }

    }

    private void chechType() {

        Menu menuNav = mNavigationView.getMenu();
        final MenuItem nav_profile = menuNav.findItem(R.id.nav_profile);
        final MenuItem nav_ShowAppointment = menuNav.findItem(R.id.nav_showAppointment);
        final MenuItem nav_BookedAppointment = menuNav.findItem(R.id.nav_bookedAppointment);
        final MenuItem nav_feedback = menuNav.findItem(R.id.nav_feedback);

        nav_profile.setVisible(false);
        nav_ShowAppointment.setVisible(false);
        nav_BookedAppointment.setVisible(false);
        nav_feedback.setVisible(false);

        final String uid = mAuth.getUid().toString();

        mUserDatabase.child("User_Type").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Type = (String) dataSnapshot.child("Type").getValue();
                status = (String) dataSnapshot.child("Status").getValue();
//                Toast.makeText(MainActivity.this, status+" -"+Type, Toast.LENGTH_SHORT).show();

                if(Type.equals("Patient")){
                    nav_BookedAppointment.setVisible(true);
                    nav_feedback.setVisible(true);


                    mUserDatabase.child("Patient_Details").child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("Name").getValue().toString();
                            String email = dataSnapshot.child("Email").getValue().toString();

                            View mView = mNavigationView.getHeaderView(0);
                            TextView userName = (TextView) mView.findViewById(R.id.header_userName);
                            TextView userEmail = (TextView) mView.findViewById(R.id.header_userEmail);

                            userName.setText(name);
                            userEmail.setText(email);

                            Toast.makeText(MainActivity.this, "Your Are Logged In", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if(Type.equals("Doctor") && status.equals("Approved")){
                    nav_profile.setVisible(true);
                    nav_ShowAppointment.setVisible(true);
                    nav_feedback.setVisible(true);
                    nav_BookedAppointment.setVisible(true);

//                    Toast.makeText(MainActivity.this, status+" -"+Type, Toast.LENGTH_SHORT).show();

                    mUserDatabase.child("Doctor_Details").child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String name = dataSnapshot.child("Name").getValue().toString();
                            String email = dataSnapshot.child("Email").getValue().toString();

                            View mView = mNavigationView.getHeaderView(0);
                            TextView userName = (TextView) mView.findViewById(R.id.header_userName);
                            TextView userEmail = (TextView) mView.findViewById(R.id.header_userEmail);

                            userName.setText(name);
                            userEmail.setText(email);

                            Toast.makeText(MainActivity.this, "Your Are Logged In", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "You are not authorized for this facility or Account Under Pending", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    onStart();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_profile:
                //Toast.makeText(getBaseContext(),"Profile Clicked",Toast.LENGTH_LONG).show();
                Intent profile_Intent = new Intent(MainActivity.this,Doctor_ProfileActivity.class);
                startActivity(profile_Intent);

                break;

            case R.id.nav_showAppointment:
//                Toast.makeText(getBaseContext(),"Show Appointment Clicked",Toast.LENGTH_LONG).show();
                Intent showAppointment_Intent = new Intent(MainActivity.this,Doctor_ShowAppointmentActivity.class);
                startActivity(showAppointment_Intent);
                break;

            case R.id.nav_bookedAppointment:
//                Toast.makeText(getBaseContext(),"Booked Appointment Clicked",Toast.LENGTH_LONG).show();
                Intent bookedAppointment_Intent = new Intent(MainActivity.this,Patient_ShowBookedAppointmentActivity.class);
                startActivity(bookedAppointment_Intent);
                break;

            case R.id.nav_login:
                Intent login_Intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login_Intent);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                onStart();

                Toast.makeText(getBaseContext(),"Successfully Logged Out",Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_feedback:
//                Toast.makeText(getBaseContext(),"Feedback Clicked",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,FeedbackActivity.class));
                break;

            case R.id.nav_aboutapp:
//                Toast.makeText(getBaseContext(),"About Us Clicked",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,About_App.class));
                break;

            case R.id.nav_setting:
//                Toast.makeText(getBaseContext(),"About Us Clicked",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,Setting.class));
                break;

//            case R.id.nav_aman:
//                Toast.makeText(this, "Aman Clicked", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.nav_tejas:
//                Toast.makeText(this, "Tejas Clicked", Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.nav_narendra:
//                Toast.makeText(this, "Narendra Clicked", Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.nav_divya:
//                Toast.makeText(this, "Divya Clicked", Toast.LENGTH_SHORT).show();

//                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        RunOnBack rr = new RunOnBack();
//        new Thread(rr).start();
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

//        rr.run();
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Sdk_lib sl = new Sdk_lib(mContext);
//                sl.connectDevice(mac_address, sw_name,"HEART_DETECT_START");
//
////                sl.baslat("HEART_DETECT_START");
//            }
//        });
    }

//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        super.onTaskRemoved();
//    }

    class RunOnBack implements Runnable{

        @Override
        public void run(){
            Handler threadHandler = new Handler(Looper.getMainLooper());
            threadHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, "Toast calisiyor", Toast.LENGTH_LONG).show();
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
//                    builder
//                            .setContentTitle("Title")
//                            .setContentText("content")
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

//                    Sdk_lib sl = new Sdk_lib(mContext);
//                    sl.connectDevice(mac_address, sw_name,"HEART_DETECT_START");


                }
            });

//            Sdk_lib sl = new Sdk_lib(mContext);
//            sl.connectDevice(mac_address, sw_name,"HEART_DETECT_START");
//            for ( int i = 0; i < 100; i++) {
//                Logger.i( "startThread: " + i);
//
//                mainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        heartStatus.setText(" bir seyler oldu" );
//                    }
//                });
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }
    public void notifyThis(String title, String message) {

        NotificationCompat.Builder b = new NotificationCompat.Builder(this,"M_CH_ID");
        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.about_icon)
                .setTicker("{your tiny message}")
                .setContentTitle(title)
                .setContentText(message)
                .setContentInfo("INFO");

        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, b.build());
    }


}
