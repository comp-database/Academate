package com.getfly.technologies
import com.easebuzz.payment.kit.PWECouponsActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.api.AcademateWebService
import com.getfly.technologies.model.response.PersonalDetailsResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainScreenViewModel
    var pweActivityResultLauncher: ActivityResultLauncher<Intent>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = AcademateRepository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainScreenViewModel::class.java]
        viewModel.postLogin( "vu1f2122054@pvppcoe.ac.in", "Vishal@7963")
//        val post = LoginInput("vu1f2122054@pvppcoe.ac.in", "Vishal@7963")
//        viewModel.postLogin2(post)
        viewModel.LoginResponse.observe(this) { LoginResponseStatus ->
            if (LoginResponseStatus.isSuccessful) {
                findViewById<TextView>(R.id.textView).text = LoginResponseStatus.body()?.uid.toString()
                Log.d("Success",LoginResponseStatus.code().toString())
            } else {
                //Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
                Log.d("Error",LoginResponseStatus.code().toString())
            }
        }

        viewModel.getPersonalDetails("774")

        viewModel.PersonalDeatailResponse.observe(this){ PersonalDetailsResponseStatus ->
            if (PersonalDetailsResponseStatus.isSuccessful){
                Log.d("DetailsSuccessBody", PersonalDetailsResponseStatus.body()?.radd!![0].name.toString())
                Log.d("DetailsSuccessCode",PersonalDetailsResponseStatus.code().toString())
            } else{
                Log.d("Error",PersonalDetailsResponseStatus.code().toString())
            }
        }

        val imageView = findViewById<ImageView>(R.id.imageView)
        viewModel.getDocDetails("773")
        viewModel.DocDeatailResponse.observe(this){DocDetailsResponseStatus->
            if(DocDetailsResponseStatus.isSuccessful){
                Glide.with(this)
                    .load(DocDetailsResponseStatus.body()!!.docs.photo)
                    .centerCrop()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
            }
        }
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
//    fun urlEncoded() {
//
//        // Create Retrofit
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://postman-echo.com")
//            .build()
//
//        // Create Service
//        val service = retrofit.create(AcademateWebService.AcademateApi::class.java)
//
//        // Create HashMap with fields
//        val params = HashMap<String?, String?>()
//        params["name"] = "Jack"
//        params["salary"] = "8054"
//        params["age"] = "45"
//
//        CoroutineScope(Dispatchers.IO).launch {
//
//            // Do the POST request and get response
//            val response = service.postLogin(params)
//
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//
//                    // Convert raw JSON to pretty JSON using GSON library
//                    val gson = GsonBuilder().setPrettyPrinting().create()
//                    val prettyJson = gson.toJson(
//                        JsonParser.parseString(
//                            response.body()
//                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
//                        )
//                    )
//
//                    Log.d("Pretty Printed JSON :", prettyJson)
//
//                } else {
//
//                    Log.e("RETROFIT_ERROR", response.code().toString())
//
//                }
//            }
//        }
//    }
}