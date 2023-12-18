package com.hsyncnblk.workmanager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.hsyncnblk.workmanager2.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonYap.setOnClickListener {

                val calismaKosulu = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED).build()

                val istek = OneTimeWorkRequestBuilder<MyWorker>()
                    .setInitialDelay(10,TimeUnit.SECONDS) // 10 SANİYE GECİKMELİ
                    .setConstraints(calismaKosulu) // internete bağlıysa
                    .build()

                WorkManager.getInstance(this@MainActivity).enqueue(istek)

                WorkManager.getInstance(this@MainActivity).getWorkInfoByIdLiveData(istek.id)
                    .observe(this@MainActivity){

                        val durum= it.state.name
                        Log.e("arkaplan işlem durumu ",durum)

                    }



            }
        }

    }
}