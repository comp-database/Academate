package com.getfly.technologies
import com.easebuzz.payment.kit.PWECouponsActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    var pweActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //CODE FOR EASE-BUZZ ACTIVITY
        pweActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (data != null) {
                val result = data.getStringExtra("result")
                val payment_response = data.getStringExtra("payment_response")
                try {
                    print(result)
                    // Handle response here
                } catch (e: Exception) {
                    // Handle exception here
                }
            }
        }
        findViewById<Button>(R.id.button).setOnClickListener {
            val intentProceed = Intent(baseContext, PWECouponsActivity::class.java)
            intentProceed.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
            intentProceed.putExtra("access_key", "2PBP7IABZ2")
            intentProceed.putExtra("pay_mode",  "test")
            pweActivityResultLauncher!!.launch(intentProceed)
        }
    }
}