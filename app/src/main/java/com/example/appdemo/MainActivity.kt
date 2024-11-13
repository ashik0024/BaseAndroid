package com.example.appdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.appdemo.databinding.ActivityMainBinding
import com.example.appdemo.ui.room.ContactAdapter
import com.example.appdemo.roomDb.UserInfoDao
import com.example.appdemo.roomDb.UserInfoViewModel
import com.microsoft.clarity.Clarity
import com.microsoft.clarity.ClarityConfig

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

//        userInfoDao = LocalDb.getDatabase(applicationContext).dao
//
//        val viewModelFactory = UserInfoViewModelFactory(userInfoDao)
//        userInfoViewModel = ViewModelProvider(this, viewModelFactory)[UserInfoViewModel::class.java]
//
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        contactAdapter = ContactAdapter(this)
//        binding.recyclerView.adapter = contactAdapter
//
//        userInfoViewModel.usersList.observe(this, Observer { users ->
//            Log.d("userInfoViewModel", ": $users")
//            if (users != null) {
//                contactAdapter.removeAll()
//                contactAdapter.addAll(users)
//            }
//        })
//
//        userInfoViewModel.playerList.observe(this, Observer { players ->
//            Log.d("userInfoViewModel2", ": $players")
//
//        })
//
//        binding.buttonAction.setOnClickListener {
//           if (!binding.editTextName.text.isNullOrEmpty() &&
//               !binding.editTextPhoneNumber.text.isNullOrEmpty() &&
//               !binding.editTextCountry.text.isNullOrEmpty()){
//
//               val name= binding.editTextName.text.toString()
//               val msisdn= binding.editTextPhoneNumber.text.toString().toInt()
//               val country= binding.editTextCountry.text.toString()
//
//               val userData=UserInfo(name=name, phoneNum = msisdn,country=country)
//               userInfoViewModel.addOrUpdateUser(userData)
//
//               val playerInfo=PlayerInfo(playerName = generateRandomString())
//               userInfoViewModel.addOrUpdatePlayer(playerInfo)
//
//               binding.editTextName.text.clear()
//               binding.editTextPhoneNumber.text.clear()
//               binding.editTextCountry.text.clear()
//
//           }
//        }
    }
//    override fun onItemClicked(item: UserInfo) {
//
//       userInfoViewModel.removeUserInfo(item)
//
//    }

//    fun generateRandomString(): String {
//        // Define a list of football player names to choose from
//        val footballPlayers = listOf(
//            "Messi", "Henry", "Neymar", "Mbappe", "Lewandowski", "Salah", "Kante", "Kane", "Sterling", "Benzema",
//            "De Bruyne", "Haaland", "Modric", "Kimmich", "Pogba", "Verratti", "Zidane", "Iniesta", "Xavi", "Pirlo",
//            "Rashford", "Foden", "Son", "Aguero", "Griezmann", "Dybala", "Ronaldo", "Lukaku", "Van Dijk"
//        )
//
//
//        return footballPlayers.random()
//    }
}