package mobi.generalmobi.com.wap_sdk_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.generalmobi.vas.wap.sdk.PaymentFacade;
import com.generalmobi.vas.wap.sdk.WapPayment;
import com.generalmobi.vas.wap.sdk.listener.ChargeResponseListener;
import com.generalmobi.vas.wap.sdk.listener.SupportCallback;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ChargeResponseListener, View.OnClickListener {

    private TextView result_text=null;
    private Button pay_button=null;
    private PaymentFacade paymentFacade = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result_text = (TextView) findViewById(R.id.result_text);
        pay_button =  (Button) findViewById(R.id.pay_button);
        pay_button.setOnClickListener(this);

        Map<String, String> map=new HashMap<String, String>(0);

        paymentFacade=new WapPayment(7332,"vault",this,map);
    }

    @Override
    public void OnResponse(final boolean b)
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (b)
                {
                    result_text.setText("Payment completed");
                }
                else
                {
                    result_text.setText("Payment failed");
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {                            result_text.setText("Please wait...");

        paymentFacade.support(new SupportCallback() {
            @Override
            public void onResponse(boolean b) {
                if (b)
                {
                    paymentFacade.charge(1,"44654646",MainActivity.this);
                }
                else
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            result_text.setText("Payment not available, Please go through SHORTCODE Billing 53010");
                        }
                    });
                }
            }
        });
    }
}
