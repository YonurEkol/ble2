package com.example.aman.hospitalappointy.activity;

import static com.example.aman.hospitalappointy.activity.Oprate.FATIGUE_OPEN;
import static com.example.aman.hospitalappointy.activity.Oprate.HEART_DETECT_START;
import static com.example.aman.hospitalappointy.activity.Oprate.LIANSUO_SOS;

import android.content.Context;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aman.hospitalappointy.MainActivity;
import com.example.aman.hospitalappointy.R;
import com.example.aman.hospitalappointy.Setting;
//import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.orhanobut.logger.Logger;
import com.veepoo.protocol.VPOperateManager;
import com.veepoo.protocol.listener.base.IABleConnectStatusListener;
import com.veepoo.protocol.listener.base.IBleWriteResponse;
import com.veepoo.protocol.listener.base.IConnectResponse;
import com.veepoo.protocol.listener.base.INotifyResponse;
import com.veepoo.protocol.listener.data.ICustomSettingDataListener;
import com.veepoo.protocol.listener.data.IDeviceFuctionDataListener;
import com.veepoo.protocol.listener.data.IFatigueDataListener;
import com.veepoo.protocol.listener.data.IHeartDataListener;
import com.veepoo.protocol.listener.data.IPwdDataListener;
import com.veepoo.protocol.listener.data.ISOSListener;
import com.veepoo.protocol.listener.data.ISocialMsgDataListener;
import com.veepoo.protocol.model.datas.FatigueData;
import com.veepoo.protocol.model.datas.FunctionDeviceSupportData;
import com.veepoo.protocol.model.datas.FunctionSocailMsgData;
import com.veepoo.protocol.model.datas.HeartData;
import com.veepoo.protocol.model.datas.PwdData;
import com.veepoo.protocol.model.settings.CustomSettingData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;





public class Sdk_lib {
    Context mContext;
    public TextView heartStatus;
    public VPOperateManager mVpoperateManager;
    Sdk_lib.WriteResponse writeResponse = new Sdk_lib.WriteResponse();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String currnetUID = mAuth.getCurrentUser().getUid().toString();

//silll    FileInputStream serviceAccount;
//
//    {
//        try {
//            serviceAccount = new FileInputStream("C:\\Users\\yakup.onur\\OneDrive\\Code\\BLE2New\\app\\firebase_admin_service.json");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }


//    FirebaseOptions options = FirebaseOptions.builder()
//            .setCredentials(GoogleCredentials.getApplicationDefault())
//            .setDatabaseUrl("https://ekolswapp-default-rtdb.europe-west1.firebasedatabase.app/")
//            .build();
//
//    FirebaseApp.initializeApp();

    ISocialMsgDataListener socialMsgDataListener = new ISocialMsgDataListener() {
        @Override
        public void onSocialMsgSupportDataChange(FunctionSocailMsgData socailMsgData) {
            String message = "FunctionSocailMsgData:\n" + socailMsgData.toString();
            Logger.i(message);
        }

        @Override
        public void onSocialMsgSupportDataChange2(FunctionSocailMsgData socailMsgData) {
            String message = "FunctionSocailMsgData2:\n" + socailMsgData.toString();
            Logger.i(message);
        }
    };

    public Sdk_lib(Context mContext) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
//
        mDatabase.child("Patient_Details").child(currnetUID).child("status").setValue("Sdkda Basladi");
//        FirebaseDatabase.getInstance().getReference().child("Patient_Details").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("status").setValue("Sdkda Basladi");

        mVpoperateManager = mVpoperateManager.getMangerInstance(mContext.getApplicationContext());
    }

    public void startFunction(String oprater) {
        if (oprater.equals(HEART_DETECT_START)) {
//            startListenADC();
            mVpoperateManager.startDetectHeart(writeResponse, new IHeartDataListener() {

                @Override
                public void onDataChange(HeartData heart) {
                    String message = "heart:\n" + heart.toString();
                    if(heart.getHeartStatus().toString() == "STATE_HEART_WEAR_ERROR"){

                        try {
                            TimeUnit.SECONDS.sleep(10);
                            startFunction(oprater);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Logger.i(message);
                    } else if (heart.getHeartStatus().toString() == "STATE_HEART_NORMAL"){
                        try{
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                LocalDateTime ltime = LocalDateTime.now();
                                String date = ltime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                String time = ltime.format(DateTimeFormatter.ofPattern("H:m:s"));
                                mDatabase.child("Patient_Data").child(currnetUID).child("heart_detect").child(date).child(time).setValue(heart.getData());
                            }
                            heartStatus.setText(String.valueOf(heart.getData()));
                        }catch(Exception e){
                        }
//                        heart_status
                        Logger.i(message);
                    }
//                    Logger.i(message);
                }
            });
        }else if (oprater.equals(FATIGUE_OPEN)) {
            mVpoperateManager.startDetectFatigue(writeResponse, new IFatigueDataListener() {
                @Override
                public void onFatigueDataListener(FatigueData fatigueData) {
                    String message = "疲劳度-开始:\n" + fatigueData.toString();
                    Logger.i(message);
                }
            });
        } else if (oprater.equals(LIANSUO_SOS)) {
            String message = "LIANSUO_SOS";
            mVpoperateManager.settingSOSListener(new ISOSListener() {
                @Override
                public void sos() {
                    String liansuo_sos_call_back = "liansuo_sos call back";
                    Logger.i(liansuo_sos_call_back);
                }
            });
        }
    }
    public boolean baslat(String oprater) {
        final Boolean[] result = {false};
        mVpoperateManager.confirmDevicePwd(writeResponse, new IPwdDataListener() {
            @Override
            public void onPwdDataChange(PwdData pwdData) {
                result[0] = true;
                String message = "PwdData:\n" + pwdData.toString();
                startFunction(oprater);

            }
        }, new IDeviceFuctionDataListener() {
            @Override
            public void onFunctionSupportDataChange(FunctionDeviceSupportData functionSupport) {
                String message = "FunctionDeviceSupportData:\n" + functionSupport.toString();

            }
        }, socialMsgDataListener, new ICustomSettingDataListener() {
            @Override
            public void OnSettingDataChange(CustomSettingData customSettingData) {
                String message = "CustomSettingData:\n" + customSettingData.toString();
            }
        }, "0000", false);
        return result[0];
    }
    static class WriteResponse implements IBleWriteResponse {

        @Override
        public void onResponse(int code) {
            Logger.i("write response calisti..........:" + code);

        }
    }

    public void connectDevice(final String mac, final String deviceName, String oprater) {

            mVpoperateManager.registerConnectStatusListener(mac, mBleConnectStatusListener);

            mVpoperateManager.connectDevice(mac, deviceName, new IConnectResponse() {

                @Override
                public void connectState(int code, BleGattProfile profile, boolean isoadModel) {
                    if (code == Code.REQUEST_SUCCESS) {

                        Logger.i("baglandi");
                    } else {
                        Logger.i("baglantı yapilamadi");
                    }
                }
            }, new INotifyResponse() {
                @Override
                public void notifyState(int state) {
                    if (state == Code.REQUEST_SUCCESS) {
                        baslat(oprater);
                        Logger.i("uyari");

                    } else {
                        Logger.i("uyari yok");
                    }

                }
            });


    }

    private final IABleConnectStatusListener mBleConnectStatusListener = new IABleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (status == Constants.STATUS_CONNECTED) {
                Logger.i("STATUS_CONNECTED");
            } else if (status == Constants.STATUS_DISCONNECTED) {
                Logger.i("STATUS_DISCONNECTED");
            }
        }
    };


}
