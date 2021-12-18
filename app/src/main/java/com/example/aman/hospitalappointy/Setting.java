package com.example.aman.hospitalappointy;

import static com.veepoo.protocol.model.enums.EFunctionStatus.SUPPORT;

import com.example.aman.hospitalappointy.activity.OperaterActivity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.hospitalappointy.activity.Sdk_lib;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.orhanobut.logger.Logger;
import com.veepoo.protocol.VPOperateManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import com.example.aman.hospitalappointy.adapter.BleScanViewAdapter;
import com.example.aman.hospitalappointy.adapter.DividerItemDecoration;
import com.veepoo.protocol.listener.base.IABleConnectStatusListener;
import com.veepoo.protocol.listener.base.IConnectResponse;
import com.veepoo.protocol.listener.base.INotifyResponse;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.DefaultItemAnimator;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;

import com.veepoo.protocol.listener.base.IBleWriteResponse;
import com.veepoo.protocol.listener.data.ICustomSettingDataListener;
import com.veepoo.protocol.listener.data.IDeviceFuctionDataListener;
import com.veepoo.protocol.listener.data.IHeartDataListener;
import com.veepoo.protocol.listener.data.IBreathDataListener;
import com.veepoo.protocol.listener.data.ILanguageDataListener;
import com.veepoo.protocol.listener.data.IPwdDataListener;
import com.veepoo.protocol.listener.data.ISocialMsgDataListener;
import com.veepoo.protocol.listener.data.ITemptureDetectDataListener;
import com.veepoo.protocol.listener.data.IAllHealthDataListener;
import com.veepoo.protocol.model.datas.BreathData;
import com.veepoo.protocol.model.datas.FunctionDeviceSupportData;
import com.veepoo.protocol.model.datas.FunctionSocailMsgData;
import com.veepoo.protocol.model.datas.HeartData;
import com.veepoo.protocol.model.datas.LanguageData;
import com.veepoo.protocol.model.datas.OriginData;
import com.veepoo.protocol.model.datas.OriginHalfHourData;
import com.veepoo.protocol.model.datas.PwdData;
import com.veepoo.protocol.model.datas.SleepData;
import com.veepoo.protocol.model.datas.TemptureDetectData;
import com.veepoo.protocol.model.enums.EFunctionStatus;
import com.veepoo.protocol.model.enums.ELanguage;
import com.veepoo.protocol.model.settings.CustomSettingData;

public class Setting extends AppCompatActivity {
    Context mContext = Setting.this;
    private final static String TAG = MainActivity.class.getSimpleName();

    private TextView mName, mEmail;
    private EditText mFeedbackText;
    private Button mSubmitFeedback, stopConnection, startDetection;
    public VPOperateManager mVpoperateManager;
    private String currnetUID;

    List<SearchResult> mListData = new ArrayList<>();
    List<String> mListAddress = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    BleScanViewAdapter bleConnectAdatpter;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public String mac_address = "";

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    WriteResponse writeResponse = new WriteResponse();

    ISocialMsgDataListener socialMsgDataListener = new ISocialMsgDataListener() {
        @Override
        public void onSocialMsgSupportDataChange(FunctionSocailMsgData socailMsgData) {
            String message = "FunctionSocailMsgData:\n" + socailMsgData.toString();
            Logger.t(TAG).i(message);
//            sendMsg(message, 3);
        }

        @Override
        public void onSocialMsgSupportDataChange2(FunctionSocailMsgData socailMsgData) {
            String message = "FunctionSocailMsgData2:\n" + socailMsgData.toString();
            Logger.t(TAG).i(message);
//            sendMsg(message, 3);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        if (!BluetoothUtils.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
        currnetUID = mAuth.getCurrentUser().getUid().toString();
        mVpoperateManager = mVpoperateManager.getMangerInstance(mContext.getApplicationContext());
        mName = (TextView) findViewById(R.id.feedback_name);
        //mEmail = (TextView) findViewById(R.id.feedback_email);
        mFeedbackText = (EditText) findViewById(R.id.feedback_text);
        mSubmitFeedback = (Button) findViewById(R.id.bt_search_button);
        stopConnection = (Button) findViewById(R.id.bt_stop_connection);
        startDetection = (Button) findViewById(R.id.bt_start_detection);

        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<String>(Setting.this, android.R.layout.simple_list_item_1, mListAddress);

        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        connectDevice(mListData.get(pos).getAddress(),mListData.get(pos).getName());
//                    confirmDevice();
//                    mListData.get(pos).getAddress();
                        //                        items.remove(pos);
                        // Refresh the adapter
                        //                        itemsAdapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }
        });



        items = new ArrayList<String>();
        items.add("First Item");
        items.add("Second Item");

        mSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVpoperateManager.getMangerInstance(mContext.getApplicationContext()).startScanDevice(mSearchResponse);

//                mVpoperateManager.startScanDevice(mSearchResponse);

                //String feedback = mFeedbackText.getText().toString();
                //mDatabase.child("Feedback").child(mAuth.getCurrentUser().getUid()).push().child("Feedback").setValue(feedback);

                //startActivity(new Intent(Setting.this,MainActivity.class));
            }
        });
        stopConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVpoperateManager.disconnectWatch(writeResponse);
                changeDeviceOnDb(null, null);
            }
        });

        startDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Sdk_lib sl = new Sdk_lib(mContext);
                        sl.baslat("HEART_DETECT_START");
                    }
                });
//                mVpoperateManager.startDetectBreath(writeResponse, new IBreathDataListener() {
//                    @Override
//                    public void onDataChange(BreathData var1) {
//                        String message = "kalp durumu" + var1.toString();
//                        Logger.t(TAG).i(message);
//                    }
//                });

//                mVpoperateManager.startDetectTempture(writeResponse, new ITemptureDetectDataListener() {
//                    @Override
//                    public void onDataChange(TemptureDetectData var1) {
//                        String message = "kalp durumu" + var1.toString();
//                        Logger.t(TAG).i(message);
//                    }
//                });
//                mVpoperateManager.startDetectHeart(writeResponse, new IHeartDataListener() {
//                    @Override
//                    public void onDataChange(HeartData var1) {
//                        String message = "kalp durumu" + var1.toString();
//                        Logger.t(TAG).i(message);
//                    }
//                });

//                mVpoperateManager.readAllHealthData(new IAllHealthDataListener() {
//                    @Override
//                    public void onProgress(float var1) {
//                        String message = "kalp durumu" + var1;
//                        Logger.t(TAG).i(message);
//                    }
//
//                    @Override
//                    public void onSleepDataChange(String s, SleepData sleepData) {
//                        Logger.t(TAG).i("onSleepDataChange");
//                    }
//
//                    @Override
//                    public void onReadSleepComplete() {
//                        Logger.t(TAG).i("onReadSleepComplete");
//                    }
//
//                    @Override
//                    public void onOringinFiveMinuteDataChange(OriginData originData) {
//                        Logger.t(TAG).i("onOringinFiveMinuteDataChange");
//                    }
//
//                    @Override
//                    public void onOringinHalfHourDataChange(OriginHalfHourData originHalfHourData) {
//                        Logger.t(TAG).i("onOringinHalfHourDataChange");
//                    }
//
//                    @Override
//                    public void onReadOriginComplete() {
//                        Logger.t(TAG).i("onReadOriginComplete");
//                    }
//                },3);
//

//                sl.startFunction("HEART_DETECT_START");

//                mVpoperateManager.settingDeviceLanguage(writeResponse, new ILanguageDataListener() {
//                    @Override
//                    public void onLanguageDataChange(LanguageData languageData) {
//                        String message = "dil degistir" + languageData.toString();
//                        Logger.t(TAG).i(message);
//                    }
//                }, ELanguage.ENGLISH); //CHINA ENGLISH
//
//                Intent intent = new Intent(mContext, OperaterActivity.class);
//                intent.putExtra("isoadmodel", false);
//                intent.putExtra("deviceaddress",  mac_address);
                //startActivity(intent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.child("User_Type").child(currnetUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String type = dataSnapshot.child("Type").getValue().toString();

                final String[] name = {""};

                if(type.equals("Patient")){

                    mDatabase.child("Patient_Details").child(currnetUID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("sw_name").getValue() != null && dataSnapshot.child("mac_address").getValue() != null){
                                connectDevice(dataSnapshot.child("mac_address").getValue().toString(), dataSnapshot.child("sw_name").getValue().toString());
                                name[0] = dataSnapshot.child("sw_name").getValue().toString();
                                mac_address = dataSnapshot.child("mac_address").getValue().toString();

                                mName.setText(name[0]);
                            } else {
                                mName.setText("Bağlantı yok!");
                            }
                            //email[0] = dataSnapshot.child("Email").getValue().toString();

                            //mEmail.setText(email[0]);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else if(type.equals("Doctor")){

                    mDatabase.child("Doctor_Details").child(currnetUID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            name[0] = dataSnapshot.child("Name").getValue().toString();
                            //email[0] = dataSnapshot.child("Email").getValue().toString();

                            mName.setText(name[0]);
                           // mEmail.setText(email[0]);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
//        confirmDevice();
    }

    private final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            //Logger.t(TAG).i("onSearchStarted");
        }

        @Override
        public void onDeviceFounded(final SearchResult device) {
            //Logger.t(TAG).i(String.format("device for %s-%s-%d", device.getName(), device.getAddress(), device.rssi));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!mListAddress.contains(device.getName())) {
                        Toast.makeText(mContext, "calistiiii....", Toast.LENGTH_SHORT).show();
                        mListData.add(device);
                        mListAddress.add(device.getName());
                        lvItems.setAdapter(itemsAdapter);
                    }
//                    Collections.sort(mListData, new DeviceCompare());
//                    bleConnectAdatpter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onSearchStopped() {
//            refreshStop();
//            Logger.t(TAG).i("onSearchStopped");
        }

        @Override
        public void onSearchCanceled() {
//            refreshStop();
//            Logger.t(TAG).i("onSearchCanceled");
        }
    };

    private void initRecyleView() {

        mSwipeRefreshLayout = (SwipeRefreshLayout) super.findViewById(R.id.mian_swipeRefreshLayout);
        mRecyclerView = (RecyclerView) super.findViewById(R.id.main_recylerlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bleConnectAdatpter = new BleScanViewAdapter(this, mListData);
        mRecyclerView.setAdapter(bleConnectAdatpter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
//        bleConnectAdatpter.setBleItemOnclick(this);
//        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    private void connectDevice(final String mac, final String deviceName) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mVpoperateManager.registerConnectStatusListener(mac, mBleConnectStatusListener);

                mVpoperateManager.connectDevice(mac, deviceName, new IConnectResponse() {

                    @Override
                    public void connectState(int code, BleGattProfile profile, boolean isoadModel) {
                        if (code == Code.REQUEST_SUCCESS) {
                            changeDeviceOnDb(mac, deviceName);

                            //蓝牙与设备的连接状态
                            Logger.t(TAG).i("baglandi");
//                    Logger.t(TAG).i("是否是固件升级模式=" + isoadModel);
//                    mIsOadModel = isoadModel;
//                    isStartConnecting = true;
                        } else {
                            changeDeviceOnDb(null, null);
                            Logger.t(TAG).i("baglantı yapilamadi");
//                    isStartConnecting = false;
                        }
                    }
                }, new INotifyResponse() {
                    @Override
                    public void notifyState(int state) {
                        if (state == Code.REQUEST_SUCCESS) {
                            //蓝牙与设备的连接状态
                            Logger.t(TAG).i("uyari");
//                    isStartConnecting = true;
//                    Intent intent = new Intent(mContext, OperaterActivity.class);
//                    intent.putExtra("isoadmodel", mIsOadModel);
//                    intent.putExtra("deviceaddress", mac);
//                    startActivity(intent);
                        } else {
                            Logger.t(TAG).i("uyari yok");
//                    isStartConnecting = false;
                        }

                    }
                });

            }
        });




    }

    private final IABleConnectStatusListener mBleConnectStatusListener = new IABleConnectStatusListener() {

        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (status == Constants.STATUS_CONNECTED) {
                Logger.t(TAG).i("STATUS_CONNECTED");
            } else if (status == Constants.STATUS_DISCONNECTED) {
                Logger.t(TAG).i("STATUS_DISCONNECTED");
            }
        }
    };
    private void changeDeviceOnDb (String mac, String sw_name) {
        mDatabase.child("Patient_Details").child(currnetUID).child("mac_address").setValue(mac);
        mDatabase.child("Patient_Details").child(currnetUID).child("sw_name").setValue(sw_name);
    }
    static class WriteResponse implements IBleWriteResponse {

        @Override
        public void onResponse(int code) {
            Logger.t(TAG).i("write response calisti..........:" + code);

        }
    }

    private void confirmDevice(){
        mVpoperateManager.confirmDevicePwd(writeResponse, new IPwdDataListener() {
            @Override
            public void onPwdDataChange(PwdData pwdData) {
                String message = "PwdData:\n" + pwdData.toString();
                Logger.t(TAG).i(message);
//                    sendMsg(message, 1);
//
//                deviceNumber = pwdData.getDeviceNumber();
//                deviceVersion = pwdData.getDeviceVersion();
//                deviceTestVersion = pwdData.getDeviceTestVersion();
//                titleBleInfo.setText("设备号：" + deviceNumber + ",版本号：" + deviceVersion + ",\n测试版本号：" + deviceTestVersion);
            }
        }, new IDeviceFuctionDataListener() {
            @Override
            public void onFunctionSupportDataChange(FunctionDeviceSupportData functionSupport) {
                String message = "FunctionDeviceSupportData:\n" + functionSupport.toString();
                Logger.t(TAG).i(message);
//                    sendMsg(message, 2);
//                EFunctionStatus newCalcSport = functionSupport.getNewCalcSport();
//                if (newCalcSport != null && newCalcSport.equals(SUPPORT)) {
//                    isNewSportCalc = true;
//                } else {
//                    isNewSportCalc = false;
//                }
//                watchDataDay = functionSupport.getWathcDay();
//                weatherStyle = functionSupport.getWeatherStyle();
//                contactMsgLength = functionSupport.getContactMsgLength();
//                allMsgLenght = functionSupport.getAllMsgLength();
//                isSleepPrecision = functionSupport.getPrecisionSleep() == SUPPORT;
            }
        }, socialMsgDataListener, new ICustomSettingDataListener() {
            @Override
            public void OnSettingDataChange(CustomSettingData customSettingData) {
                String message = "CustomSettingData:\n" + customSettingData.toString();
                Logger.t(TAG).i(message);
//                    sendMsg(message, 4);
            }
        }, "0000", false);
    }

}
