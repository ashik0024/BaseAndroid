package com.example.appdemo.Services

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.appdemo.R
import com.example.appdemo.databinding.FragmentServiceBinding
import com.google.android.gms.cast.CastRemoteDisplayLocalService.startService
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import android.provider.Settings

class ServiceFragment : Fragment() {

    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                checkAndRequestPermissions() // Check again after granting permission
            } else {
                handlePermissionDenied()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.foreground?.setOnClickListener {
            checkAndRequestPermissions()
        }

        binding?.background?.setOnClickListener {
            // Implement background service logic here
        }

        binding?.bound?.setOnClickListener {
            // Implement bound service logic here
        }
    }

    override fun onStart() {
        super.onStart()
//        checkAndRequestPermissions()
    }

    /**
     * Check and request both FOREGROUND_SERVICE_MEDIA_PLAYBACK (Android 14+)
     * and POST_NOTIFICATIONS (Android 13+)
     */
    private fun checkAndRequestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // Android 14+
            if (checkSelfPermission(requireContext(), Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.first()) // Request one at a time
        } else {
            startMediaService()
        }
    }

    private fun startMediaService() {
        val serviceIntent = Intent(requireContext(), MediaPlayerForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireContext().startForegroundService(serviceIntent)
        } else {
            requireContext().startService(serviceIntent)
        }
    }

    private fun handlePermissionDenied() {
        val shouldShowRationale = Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
                shouldShowRequestPermissionRationale(Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK) ||
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)

        if (shouldShowRationale) {
            Toast.makeText(requireContext(), "Permissions are needed to continue", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Enable permissions from settings", Toast.LENGTH_LONG).show()
            openAppSettings()
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = android.net.Uri.fromParts("package", requireContext().packageName, null)
        startActivity(intent)
    }
}