package com.project.guideapp.ui.scanqr

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.zxing.Result
import com.project.guideapp.R
import com.project.guideapp.ui.scanqr.qrcode.QRCodeAnalyzer
import com.project.guideapp.ui.scanqr.qrcode.QRCodeAnalyzer.QRCodeDetector
import com.project.guideapp.ui.scanqr.qrcode.QRScanningView
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

class ScanQRFragment : Fragment() {
    private var previewView: PreviewView? = null
    private val REQUEST_CODE_PERMISSIONS = 1001
    private val REQUIRED_PERMISSIONS =
        arrayOf("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE")
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var ivBack: ImageView? = null
    private var qrScanningView: QRScanningView? = null

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Override
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qr_scan, container, false)
        previewView = view.findViewById(R.id.camera_preview_view)
        ivBack = view.findViewById(R.id.iv_back)
        qrScanningView = view.findViewById(R.id.qr_scanning_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivBack!!.setOnClickListener { activity?.onBackPressed() }
        qrScanningView!!.startAnimation()
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        if (allPermissionsGranted()) startCamera() else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )

            }
        }
    }

    override fun onPause() {
        if (cameraProvider != null) cameraProvider!!.unbindAll()
        super.onPause()
    }

    override fun onDestroy() {
        if (cameraProvider != null) cameraProvider!!.unbindAll()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(activity, "Permissions not granted by the user.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun startCamera() {
        if (cameraProvider != null) cameraProvider!!.unbindAll()
        val cameraProviderFuture = context?.let {
            ProcessCameraProvider.getInstance(
                it
            )
        }
        cameraProviderFuture?.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                bindPreview(cameraProvider!!)
            } catch (e: ExecutionException) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            } catch (e: InterruptedException) {
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setImageQueueDepth(1)
            .build()
        imageAnalysis.setAnalyzer(cameraExecutor, QRCodeAnalyzer(object : QRCodeDetector {
            override fun onQrCodeDetected(result: Result) {
                activity!!.runOnUiThread {
                    cameraProvider.unbindAll()
                    handleQRCodeScanResult(result!!.text)
                }
            }
        }))
        val builder = ImageCapture.Builder()
        val imageCapture = activity?.windowManager?.defaultDisplay?.rotation?.let {
            builder
                .setTargetRotation(it)
                .build()
        }
        preview.setSurfaceProvider(previewView!!.surfaceProvider)
        camera = cameraProvider.bindToLifecycle(
            (this as LifecycleOwner),
            cameraSelector,
            preview,
            imageAnalysis
        )
    }

    private fun handleQRCodeScanResult(text: String) {
        //TODO Handle QR Code
    }

    fun toggleFlashLight() {
        if (camera != null) {
            if (camera!!.cameraInfo.hasFlashUnit()) camera!!.cameraControl.enableTorch(if (camera!!.cameraInfo.torchState.value == TorchState.ON) false else true)
        }
    }

    fun setFlashLight(torchOn: Boolean) {
        if (camera != null) {
            if (camera!!.cameraInfo.hasFlashUnit()) camera!!.cameraControl.enableTorch(torchOn)
        }
    }

    val isTorchOn: Boolean
        get() = if (camera != null) camera!!.cameraInfo.torchState.value == TorchState.ON else false
}