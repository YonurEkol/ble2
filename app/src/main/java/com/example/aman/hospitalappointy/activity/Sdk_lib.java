package com.example.aman.hospitalappointy.activity;

import static com.example.aman.hospitalappointy.activity.Oprate.HEART_DETECT_START;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aman.hospitalappointy.MainActivity;
import com.example.aman.hospitalappointy.Setting;
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
import com.veepoo.protocol.listener.data.IHeartDataListener;
import com.veepoo.protocol.listener.data.IPwdDataListener;
import com.veepoo.protocol.listener.data.ISocialMsgDataListener;
import com.veepoo.protocol.model.datas.FunctionDeviceSupportData;
import com.veepoo.protocol.model.datas.FunctionSocailMsgData;
import com.veepoo.protocol.model.datas.HeartData;
import com.veepoo.protocol.model.datas.PwdData;
import com.veepoo.protocol.model.settings.CustomSettingData;

import java.util.concurrent.TimeUnit;

public class Sdk_lib {
    Context mContext;
    public VPOperateManager mVpoperateManager;
    Sdk_lib.WriteResponse writeResponse = new Sdk_lib.WriteResponse();
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
                        Logger.i(message);
                    }
//                    Logger.i(message);
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
                        baslat(oprater);
                        Logger.i("baglandi");
                    } else {
                        Logger.i("baglantı yapilamadi");
                    }
                }
            }, new INotifyResponse() {
                @Override
                public void notifyState(int state) {
                    if (state == Code.REQUEST_SUCCESS) {

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
