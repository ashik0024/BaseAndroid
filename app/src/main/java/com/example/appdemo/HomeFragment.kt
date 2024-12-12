package com.example.appdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appdemo.databinding.FragmentHomeBinding
import com.example.appdemo.databinding.FragmentRoomBinding

class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.roomTv?.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_Room)
        }
//        binding?.roomTv?.setOnClickListener {
//            findNavController().navigate(R.id.action_home_to_PokemonDetails)
//        }
        binding?.apiTv?.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_nonPaging)
        }
        binding?.pagingApiTv?.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_paging)
        }
        binding?.player?.setOnClickListener {
            findNavController().navigate(R.id.action_to_player)
        }
    }
}