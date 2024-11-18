package com.example.appdemo

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

import com.example.appdemo.databinding.ActivityMainBinding
import com.example.appdemo.ui.room.ContactAdapter
import com.example.appdemo.roomDb.UserInfoDao
import com.example.appdemo.roomDb.UserInfoViewModel
import com.microsoft.clarity.Clarity
import com.microsoft.clarity.ClarityConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var userInfoViewModel: UserInfoViewModel
    private lateinit var userInfoDao: UserInfoDao
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val config = ClarityConfig("ow8fm7j9qk")
        Clarity.initialize(applicationContext, config)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE
        )
    }

}