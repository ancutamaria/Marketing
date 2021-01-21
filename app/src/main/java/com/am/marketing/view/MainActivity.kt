package com.am.marketing.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.am.marketing.R
import com.am.marketing.model.MarketingAPIClient
import com.am.marketing.model.MarketingAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: fix this
        //for REST call test purposes
        GlobalScope.launch(Dispatchers.Main) {
            val marketing = MarketingAPIClient.apiClient().
                                create(MarketingAPIService::class.java)
                                .getMarketingResponse()
            Toast.makeText(applicationContext, marketing.toString(), Toast.LENGTH_LONG).show()
        }

    }
}