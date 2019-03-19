package jm.org.androidshell;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvShell;
    private Button btnRoot,btnWifi,btnSend;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        tvShell=findViewById(R.id.tv_shell_result);
        btnRoot=findViewById(R.id.btn_root_status);
        btnWifi=findViewById(R.id.btn_root_wifi);
        btnSend=findViewById(R.id.btn_root_send);
        editText=findViewById(R.id.et_cmd);
        btnWifi.setEnabled(false);
        btnSend.setEnabled(false);
        btnRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean a=RootCmd.haveRoot();
                if (a){
                    btnWifi.setEnabled(true);
                    btnSend.setEnabled(true);
                    setText(a?"有权限":"无权限");
                }
            }
        });
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWifiAdb();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd=editText.getText().toString().trim();
                if (cmd.length() == 0){
                    return;
                }
                editText.setText(null);
                String result=RootCmd.execRootCmd(cmd);
                setText(result);

            }
        });
    }

    private void openWifiAdb(){
        RootCmd.execRootCmd("setprop service.adb.tcp.port 5555");
        RootCmd.execRootCmd("stop adbd");
        RootCmd.execRootCmd("start adbd");
    }

    private void setText(String text){
        tvShell.setText(tvShell.getText().toString()+"\n"+text);
    }
}
