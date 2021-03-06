package com.graduation.jinoh.autofishing.fragment;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.graduation.jinoh.autofishing.R;
import com.graduation.jinoh.autofishing.activity.FragmentContainer;
import com.graduation.jinoh.autofishing.activity.MainActivity;

/**
 * Created by jinoh on 2016-04-25.
 */
public class OptionFragment extends Fragment {

    private Button senserbutton;
    private Button startbutton;
    private Button stopbutton;
    private Button startcolor;
    private Button stopcolor;
    private Button resetcolor;
    private CheckBox checkBoxred;
    private CheckBox checkBoxblue;
    private CheckBox checkBoxgreen;
    private CheckBox checkBoxlime;
    private CheckBox checkBoxorange;
    private CheckBox checkBoxpink;
    private CheckBox checkBoxpurple;
    private CheckBox checkBoxteal;
    private CheckBox checkBoxyellow;
    private RadioGroup radioGroup;
    private String sens;
    private String run;
    public String recivearduino;
    private SoundPool soundpool;
    private int soundbeep;

    private String colorcheck;
    private String colorcheck2;


    // 사용자 정의 함수로 블루투스 활성 상태의 변경 결과를 App으로 알려줄때 식별자로 사용됨 (0보다 커야함)
    static final int REQUEST_ENABLE_BT = 10;
    int mPariedDeviceCount = 0;
    Set<BluetoothDevice> mDevices;
    // 폰의 블루투스 모듈을 사용하기 위한 오브젝트.
    BluetoothAdapter mBluetoothAdapter;
    /**
     BluetoothDevice 로 기기의 장치정보를 알아낼 수 있는 자세한 메소드 및 상태값을 알아낼 수 있다.
     연결하고자 하는 다른 블루투스 기기의 이름, 주소, 연결 상태 등의 정보를 조회할 수 있는 클래스.
     현재 기기가 아닌 다른 블루투스 기기와의 연결 및 정보를 알아낼 때 사용.
     */
    BluetoothDevice mRemoteDevie;
    // 스마트폰과 페어링 된 디바이스간 통신 채널에 대응 하는 BluetoothSocket
    BluetoothSocket mSocket = null;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    String mStrDelimiter = "\n";
    char mCharDelimiter =  '\n';



    Thread mWorkerThread = null;
    byte[] readBuffer;
    int readBufferPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        final View root = inflater.inflate(R.layout.fragment_option, container, false);
        initViewSetting(root);

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run = "2";
                sendData(run);
            }
        });

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run = "4";
                sendData(run);
            }
        });

        senserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) root.findViewById(id);
                if(rb.getText().toString().equals("낮음")){
                    sens = "7";
                }
                else if(rb.getText().toString().equals("보통")){
                    sens = "5";
                }
                else
                {
                    sens = "3";
                }
                sendData(sens);
            }
        });

        checkBoxred.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.checkBoxRed){
                    if(isChecked){
                        colorcheck = "r";
                        colorcheck2 = "R";
                    }
                }
            }
        });
        checkBoxblue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.checkBoxBlue){
                    if(isChecked){
                        colorcheck = "b";
                        colorcheck2 = "B";
                    }
                }
            }
        });
        checkBoxgreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.checkBoxGreen){
                    if(isChecked){
                        colorcheck = "g";
                        colorcheck2 = "G";
                    }
                }
            }
        });
        checkBoxlime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.checkBoxLime){
                    if(isChecked){
                        colorcheck = "l";
                        colorcheck2 = "L";
                    }
                }
            }
        });
        checkBoxorange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.checkBoxOrange){
                    if(isChecked){
                        colorcheck = "o";
                        colorcheck2 = "O";
                    }
                }
            }
        });
        checkBoxpink.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.checkBoxPink){
                    if(isChecked){
                        colorcheck = "p";
                        colorcheck2 = "P";
                    }
                }
            }
        });
        checkBoxpurple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.checkBoxPurple){
                    if(isChecked){
                        colorcheck = "u";
                        colorcheck2 = "U";
                    }
                }
            }
        });
        checkBoxteal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.checkBoxTeal){
                    if(isChecked){
                        colorcheck = "t";
                        colorcheck2 = "T";
                    }
                }
            }
        });
        checkBoxyellow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.checkBoxYellow){
                    if(isChecked){
                        colorcheck = "y";
                        colorcheck2 = "Y";
                    }
                }
            }
        });

        startcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(colorcheck);
            }
        });
        stopcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(colorcheck2);
            }
        });
        resetcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData("k");
            }
        });

        soundpool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        soundbeep = soundpool.load(getActivity(), R.raw.catch3, 1);


        checkBluetooth();
        return root;


    }

    private void initViewSetting(View root){
        senserbutton = (Button) root.findViewById(R.id.sensSetButton);
        startbutton = (Button) root.findViewById(R.id.startButton);
        stopbutton = (Button) root.findViewById(R.id.stopButton);
        startcolor = (Button) root.findViewById(R.id.startcolor);
        stopcolor = (Button) root.findViewById(R.id.stopcolor);
        resetcolor = (Button) root.findViewById(R.id.resetcolor);
        radioGroup = (RadioGroup) root.findViewById(R.id.radioGroup);
        checkBoxred = (CheckBox) root.findViewById(R.id.checkBoxRed);
        checkBoxblue = (CheckBox) root.findViewById(R.id.checkBoxBlue);
        checkBoxgreen = (CheckBox) root.findViewById(R.id.checkBoxGreen);
        checkBoxlime = (CheckBox) root.findViewById(R.id.checkBoxLime);
        checkBoxorange = (CheckBox) root.findViewById(R.id.checkBoxOrange);
        checkBoxpink = (CheckBox) root.findViewById(R.id.checkBoxPink);
        checkBoxpurple = (CheckBox) root.findViewById(R.id.checkBoxPurple);
        checkBoxteal = (CheckBox) root.findViewById(R.id.checkBoxTeal);
        checkBoxyellow = (CheckBox) root.findViewById(R.id.checkBoxYellow);
    }
    // 문자열 전송하는 함수(쓰레드 사용 x)
    void sendData(String msg) {
        msg += mStrDelimiter;  // 문자열 종료표시 (\n)
        try{
            // getBytes() : String을 byte로 변환
            // OutputStream.write : 데이터를 쓸때는 write(byte[]) 메소드를 사용함. byte[] 안에 있는 데이터를 한번에 기록해 준다.
            mOutputStream.write(msg.getBytes());  // 문자열 전송.
        }catch(Exception e) {  // 문자열 전송 도중 오류가 발생한 경우
            Toast.makeText(getActivity(), "데이터 전송중 오류가 발생", Toast.LENGTH_LONG).show();
            //finish();  // App 종료
        }
    }
    //  connectToSelectedDevice() : 원격 장치와 연결하는 과정을 나타냄.
    //        실제 데이터 송수신을 위해서는 소켓으로부터 입출력 스트림을 얻고 입출력 스트림을 이용하여 이루어 진다.
    void connectToSelectedDevice(String selectedDeviceName) {
        // BluetoothDevice 원격 블루투스 기기를 나타냄.
        mRemoteDevie = getDeviceFromBondedList(selectedDeviceName);
        // java.util.UUID.fromString : 자바에서 중복되지 않는 Unique 키 생성.
        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            // 소켓 생성, RFCOMM 채널을 통한 연결.
            // createRfcommSocketToServiceRecord(uuid) : 이 함수를 사용하여 원격 블루투스 장치와 통신할 수 있는 소켓을 생성함.
            // 이 메소드가 성공하면 스마트폰과 페어링 된 디바이스간 통신 채널에 대응하는 BluetoothSocket 오브젝트를 리턴함.
            mSocket = mRemoteDevie.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect(); // 소켓이 생성 되면 connect() 함수를 호출함으로써 두기기의 연결은 완료된다.

            // 데이터 송수신을 위한 스트림 얻기.
            // BluetoothSocket 오브젝트는 두개의 Stream을 제공한다.
            // 1. 데이터를 보내기 위한 OutputStrem
            // 2. 데이터를 받기 위한 InputStream
            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();

            // 데이터 수신 준비.
            beginListenForData();

        }catch(Exception e) { // 블루투스 연결 중 오류 발생
            Toast.makeText(getActivity(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            //finish();  // App 종료
        }
    }

    // 데이터 수신(쓰레드 사용 수신된 메시지를 계속 검사함)
    void beginListenForData() {
        final Handler handler = new Handler();

        readBufferPosition = 0;                 // 버퍼 내 수신 문자 저장 위치.
        readBuffer = new byte[1024];            // 수신 버퍼.

        // 문자열 수신 쓰레드.
        mWorkerThread = new Thread(new Runnable()
        {
            @Override
            public void run() {
                // interrupt() 메소드를 이용 스레드를 종료시키는 예제이다.
                // interrupt() 메소드는 하던 일을 멈추는 메소드이다.
                // isInterrupted() 메소드를 사용하여 멈추었을 경우 반복문을 나가서 스레드가 종료하게 된다.
                while(!Thread.currentThread().isInterrupted()) {
                    try {
                        // InputStream.available() : 다른 스레드에서 blocking 하기 전까지 읽은 수 있는 문자열 개수를 반환함.
                        int byteAvailable = mInputStream.available();   // 수신 데이터 확인
                        if(byteAvailable > 0) {                        // 데이터가 수신된 경우.

                            byte[] packetBytes = new byte[byteAvailable];
                            // read(buf[]) : 입력스트림에서 buf[] 크기만큼 읽어서 저장 없을 경우에 -1 리턴.
                            mInputStream.read(packetBytes);
                            for(int i=0; i<byteAvailable; i++) {
                                Log.i("메시지","eeee");
                                //byte b = packetBytes[i];
                                soundpool.play(soundbeep, 1f,1f,0,3,1f);
                                byte b = '\n';
                                if(b == mCharDelimiter) {
                                    Log.i("메시지","ffff");
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    //  System.arraycopy(복사할 배열, 복사시작점, 복사된 배열, 붙이기 시작점, 복사할 개수)
                                    //  readBuffer 배열을 처음 부터 끝까지 encodedBytes 배열로 복사.
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);

                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    Log.i("메시지",data);
                                    handler.post(new Runnable(){
                                        // 수신된 문자열 데이터에 대한 처리.
                                        @Override
                                        public void run() {
                                            // mStrDelimiter = '\n';
                                            recivearduino =  data;
                                            //Toast.makeText(getActivity(), recivearduino, Toast.LENGTH_SHORT).show();
                                            Log.i("메시지",recivearduino);
                                            Log.i("메시지","gggg");
                                        }
                                    });
                                }
                                else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }

                    } catch (Exception e) {    // 데이터 수신 중 오류 발생.
                        Toast.makeText(getActivity(), "데이터 수신에 실패했습니다.", Toast.LENGTH_LONG).show();
                        //finish();            // App 종료.
                    }
                }
            }

        });
        mWorkerThread.start();
    }

    // 블루투스 지원하며 활성 상태인 경우.
    void selectDevice() {
        // 블루투스 디바이스는 연결해서 사용하기 전에 먼저 페어링 되어야만 한다
        // getBondedDevices() : 페어링된 장치 목록 얻어오는 함수.
        mDevices = mBluetoothAdapter.getBondedDevices();
        mPariedDeviceCount = mDevices.size();

        if(mPariedDeviceCount == 0 ) { // 페어링된 장치가 없는 경우.
            Toast.makeText(getActivity(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            //finish(); // App 종료.
        }
        // 페어링된 장치가 있는 경우.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("블루투스 장치 선택");

        // 각 디바이스는 이름과(서로 다른) 주소를 가진다. 페어링 된 디바이스들을 표시한다.
        List<String> listItems = new ArrayList<String>();
        for(BluetoothDevice device : mDevices) {
            // device.getName() : 단말기의 Bluetooth Adapter 이름을 반환.
            listItems.add(device.getName());
        }
        listItems.add("취소");  // 취소 항목 추가.


        // CharSequence : 변경 가능한 문자열.
        // toArray : List형태로 넘어온것 배열로 바꿔서 처리하기 위한 toArray() 함수.
        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        // toArray 함수를 이용해서 size만큼 배열이 생성 되었다.
        listItems.toArray(new CharSequence[listItems.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                if(item == mPariedDeviceCount) { // 연결할 장치를 선택하지 않고 '취소' 를 누른 경우.
                    Toast.makeText(getActivity(), "연결을 취소했습니다..", Toast.LENGTH_LONG).show();
                    //finish();
                }
                else { // 연결할 장치를 선택한 경우, 선택한 장치와 연결을 시도함.
                    connectToSelectedDevice(items[item].toString());
                }
            }

        });

        builder.setCancelable(false);  // 뒤로 가기 버튼 사용 금지.
        AlertDialog alert = builder.create();
        alert.show();
    }

    // 블루투스 장치의 이름이 주어졌을때 해당 블루투스 장치 객체를 페어링 된 장치 목록에서 찾아내는 코드.
    BluetoothDevice getDeviceFromBondedList(String name) {
        // BluetoothDevice : 페어링 된 기기 목록을 얻어옴.
        BluetoothDevice selectedDevice = null;
        // getBondedDevices 함수가 반환하는 페어링 된 기기 목록은 Set 형식이며,
        // Set 형식에서는 n 번째 원소를 얻어오는 방법이 없으므로 주어진 이름과 비교해서 찾는다.
        for(BluetoothDevice deivce : mDevices) {
            // getName() : 단말기의 Bluetooth Adapter 이름을 반환
            if(name.equals(deivce.getName())) {
                selectedDevice = deivce;
                break;
            }
        }
        return selectedDevice;
    }

    void checkBluetooth() {
        /**
         * getDefaultAdapter() : 만일 폰에 블루투스 모듈이 없으면 null 을 리턴한다.
         이경우 Toast를 사용해 에러메시지를 표시하고 앱을 종료한다.
         */
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null ) {  // 블루투스 미지원
            Toast.makeText(getActivity(), "기기가 블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show();
            //finish();  // 앱종료
        }
        else { // 블루투스 지원
            /** isEnable() : 블루투스 모듈이 활성화 되었는지 확인.
             *               true : 지원 ,  false : 미지원
             */
            if(!mBluetoothAdapter.isEnabled()) { // 블루투스 지원하며 비활성 상태인 경우.
                Toast.makeText(getActivity(), "현재 블루투스가 비활성 상태입니다.", Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // REQUEST_ENABLE_BT : 블루투스 활성 상태의 변경 결과를 App 으로 알려줄 때 식별자로 사용(0이상)
                /**
                 startActivityForResult 함수 호출후 다이얼로그가 나타남
                 "예" 를 선택하면 시스템의 블루투스 장치를 활성화 시키고
                 "아니오" 를 선택하면 비활성화 상태를 유지 한다.
                 선택 결과는 onActivityResult 콜백 함수에서 확인할 수 있다.
                 */
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            else // 블루투스 지원하며 활성 상태인 경우.
                selectDevice();
        }
    }

    // onDestroy() : 어플이 종료될때 호출 되는 함수.
    //               블루투스 연결이 필요하지 않는 경우 입출력 스트림 소켓을 닫아줌.
    @Override
    public void onDestroy() {
        try{
            mWorkerThread.interrupt(); // 데이터 수신 쓰레드 종료
            mInputStream.close();
            mSocket.close();
        }catch(Exception e){}
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // startActivityForResult 를 여러번 사용할 땐 이런 식으로 switch 문을 사용하여 어떤 요청인지 구분하여 사용함.
        switch(requestCode) {
            case REQUEST_ENABLE_BT:
                if(resultCode == -1) { // 블루투스 활성화 상태
                    selectDevice();
                }
                else if(resultCode == 0) { // 블루투스 비활성화 상태 (종료)
                    Toast.makeText(getActivity(), "블루투스를 사용할 수 없습니다.", Toast.LENGTH_LONG).show();
                    //finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
