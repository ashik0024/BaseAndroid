package com.example.appdemo.ui.room

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemo.common.BaseListItemCallback
import com.example.appdemo.databinding.FragmentRoomBinding
import com.example.appdemo.roomDb.LocalDb
import com.example.appdemo.roomDb.PlayerInfo
import com.example.appdemo.roomDb.UserInfo
import com.example.appdemo.roomDb.UserInfoDao
import com.example.appdemo.roomDb.UserInfoViewModel
import com.example.appdemo.roomDb.UserInfoViewModelFactory


class RoomFragment : Fragment(), BaseListItemCallback<UserInfo> {

    private var _binding: FragmentRoomBinding?=null
    private val binding get() =_binding!!

    private lateinit var userInfoViewModel: UserInfoViewModel
    private lateinit var userInfoDao: UserInfoDao
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userInfoDao = LocalDb.getDatabase(requireContext()).dao

        val viewModelFactory = UserInfoViewModelFactory(userInfoDao)
        userInfoViewModel = ViewModelProvider(this, viewModelFactory)[UserInfoViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        contactAdapter = ContactAdapter(this)
        binding.recyclerView.adapter = contactAdapter

        userInfoViewModel.usersList.observe(viewLifecycleOwner, Observer { users ->
            Log.d("userInfoViewModel", ": $users")
            if (users != null) {
                contactAdapter.removeAll()
                contactAdapter.addAll(users)
            }
        })

        userInfoViewModel.playerList.observe(viewLifecycleOwner, Observer { players ->
            Log.d("userInfoViewModel2", ": $players")

        })

        binding.buttonAction.setOnClickListener {
            if (!binding.editTextName.text.isNullOrEmpty() &&
                !binding.editTextPhoneNumber.text.isNullOrEmpty() &&
                !binding.editTextCountry.text.isNullOrEmpty()){

                val name= binding.editTextName.text.toString()
                val msisdn= binding.editTextPhoneNumber.text.toString().toInt()
                val country= binding.editTextCountry.text.toString()

                val userData=UserInfo(name=name, phoneNum = msisdn,country=country)
                userInfoViewModel.addOrUpdateUser(userData)

                val playerInfo= PlayerInfo(playerName = generateRandomString())
                userInfoViewModel.addOrUpdatePlayer(playerInfo)

                binding.editTextName.text.clear()
                binding.editTextPhoneNumber.text.clear()
                binding.editTextCountry.text.clear()

            }
        }
    }
    override fun onItemClicked(item: UserInfo) {
        userInfoViewModel.removeUserInfo(item)

    }

    fun generateRandomString(): String {
        // Define a list of football player names to choose from
        val footballPlayers = listOf(
            "Messi", "Henry", "Neymar", "Mbappe", "Lewandowski", "Salah", "Kante", "Kane", "Sterling", "Benzema",
            "De Bruyne", "Haaland", "Modric", "Kimmich", "Pogba", "Verratti", "Zidane", "Iniesta", "Xavi", "Pirlo",
            "Rashford", "Foden", "Son", "Aguero", "Griezmann", "Dybala", "Ronaldo", "Lukaku", "Van Dijk"
        )


        return footballPlayers.random()
    }
}