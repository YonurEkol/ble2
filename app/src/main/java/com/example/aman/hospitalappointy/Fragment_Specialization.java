package com.example.aman.hospitalappointy;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;





import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.orhanobut.logger.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * Created by Aman on 14-Feb-18.
 */

public class Fragment_Specialization extends Fragment {

    private TextInputLayout mSearch;
    private EditText searchtext;

    private RecyclerView mRecylerView;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    View rootView;

    public Fragment_Specialization(){
        //Required Empty public constructor otherwise app will crash
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

       rootView = inflater.inflate(R.layout.fragment_specialization,container,false);

        mSearch = (TextInputLayout) rootView.findViewById(R.id.search_by_specialization);
        searchtext = (EditText) rootView.findViewById(R.id.special_searchtxt);

        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onStart();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onStart();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                onStart();
            }
        });

        mRecylerView = (RecyclerView) rootView.findViewById(R.id.specialization_recyclerView);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        if(mAuth.getCurrentUser() != null) {
            getDataSet();
        }
        return rootView;
    }

    private ArrayList getDataSet() {
        final ArrayList[] dataSets = {null};
        ArrayList valueSet1 = new ArrayList();
        BarEntry v1e1 = new BarEntry(0, 0); // Jan
        valueSet1.add(v1e1);
        final int[] num = {0};
        String date;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime ltime = LocalDateTime.now();
             date = ltime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }else {
            date = "2021-12-28";
        }
        Query myTopPostsQuery = mDatabase.child("Patient_Data").child(mAuth.getCurrentUser().getUid().toString()).child("heart_detect").child(date).limitToFirst(10);
        myTopPostsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                float f = Float.parseFloat(dataSnapshot.getValue().toString());
                BarEntry v1e1 = new BarEntry(num[0], f ); // Jan
                num[0] += 1;
                valueSet1.add(v1e1);
                BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
                dataSets[0] = new ArrayList();
                dataSets[0].add(barDataSet1);
                BarChart chart = (BarChart) rootView.findViewById(R.id.chart);
                BarData data = new BarData(dataSets[0]);//, getDataSet()
                chart.setData(data);
                chart.animateXY(2000, 2000);
                chart.invalidate();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Logger.i(s);
                float f = Float.parseFloat(dataSnapshot.getValue().toString());
                BarEntry v1e1 = new BarEntry(1, f ); // Jan
                valueSet1.add(v1e1);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            // TODO: implement the ChildEventListener methods as documented above
            // ...
        });
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        dataSets[0] = new ArrayList();
        dataSets[0].add(barDataSet1);

        return dataSets[0];
    }

    private ArrayList getXAxisValues() {
        ArrayList xAxis = new ArrayList();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }



    @Override
    public void onStart() {
        super.onStart();


        String search = mSearch.getEditText().getText().toString();


        Query query = mDatabase.child("Specialization").orderByKey().startAt(search).endAt(search +"\uf8ff");
        FirebaseRecyclerOptions<BookedAppointmentList> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<BookedAppointmentList>()
                .setQuery(query, BookedAppointmentList.class)
                .build();

        FirebaseRecyclerAdapter<BookedAppointmentList,SpecializationViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<BookedAppointmentList, SpecializationViewHolder>(firebaseRecyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull final SpecializationViewHolder holder, final int position, @NonNull final BookedAppointmentList model) {

//                        final String doctorID = model.getDoctor_ID().toString();
                        final String Special = getRef(position).getKey().toString();
                        holder.setSpecialization(Special);

                        if(position%2 == 0) {
                            holder.setImage(1);

                        } else{
                            holder.setImage(2);

                        }

                        holder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                                Toast.makeText(getContext(), position+" = "+Special+" DoctorID = ", Toast.LENGTH_SHORT).show();

                                alertDialog(Special);

                            }
                        });

                    }

                    @Override
                    public SpecializationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.singel_specialization_list, parent, false);

                        return new SpecializationViewHolder(view);
                    }
                };
        mRecylerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }


    private void alertDialog(String special) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.doctor_list_dialog, null);

        RecyclerView alertRecyclerView = view.findViewById(R.id.doctor_dialog);
        alertRecyclerView.setHasFixedSize(true);
        alertRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        builder.setView(view);

        Query query = mDatabase.child("Specialization").child(special);

        FirebaseRecyclerOptions<BookedAppointmentList> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<BookedAppointmentList>()
                .setQuery(query, BookedAppointmentList.class)
                .build();

        FirebaseRecyclerAdapter<BookedAppointmentList, SpecializationVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<BookedAppointmentList, SpecializationVH>(firebaseRecyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull final SpecializationVH holder, int position, @NonNull final BookedAppointmentList model) {

                        final String doctorID = model.getDoctor_ID().toString();

                        mDatabase.child("Doctor_Details").child(doctorID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final String doctorName = dataSnapshot.child("Name").getValue().toString();
                                final String specialization = dataSnapshot.child("Specialization").getValue().toString();
                                final String contact = dataSnapshot.child("Contact").getValue().toString();
                                final String experience = dataSnapshot.child("Experiance").getValue().toString();
                                final String education = dataSnapshot.child("Education").getValue().toString();
                                final String shift = dataSnapshot.child("Shift").getValue().toString();

                                holder.setDoctorName(doctorName);
                                holder.setSpecialization(specialization);
                                holder.mView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getContext(),Patient_DoctorProfileActivity.class);
                                        intent.putExtra("Name",doctorName);
                                        intent.putExtra("Specialization",specialization);
                                        intent.putExtra("Contact",contact);
                                        intent.putExtra("Experiance",experience);
                                        intent.putExtra("Education",education);
                                        intent.putExtra("Shift",shift);
                                        intent.putExtra("UserId",doctorID);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public SpecializationVH onCreateViewHolder(ViewGroup parent, int viewType) {

                        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_doctor_list, null);
                        return new SpecializationVH(mView);
                    }
                };
        alertRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

//        Window window = dialog.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public class SpecializationVH extends RecyclerView.ViewHolder {

        View mView;

        public SpecializationVH(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDoctorName(String doctorName) {
            TextView userName = (TextView) mView.findViewById(R.id.name_id_single_user);
            userName.setText(doctorName);
        }

        public void setSpecialization(String specialization) {
            TextView userName = (TextView) mView.findViewById(R.id.special_id_single_user);
            userName.setText(specialization);
        }
    }


    public class SpecializationViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public SpecializationViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setSpecialization(String special) {
            TextView userName = (TextView) mView.findViewById(R.id.special_id_single_user);
            userName.setText(special);
        }

        public void setImage(int i) {

            CircleImageView cr1 = (CircleImageView) mView.findViewById(R.id.profile_id_single_user);

            if(i == 1){
                cr1.setImageDrawable(getResources().getDrawable(R.mipmap.stethoscope));

            }else {
                cr1.setImageDrawable(getResources().getDrawable(R.mipmap.injection));
            }

        }
    }
}
