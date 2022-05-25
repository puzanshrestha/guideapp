package com.project.guideapp.ui.scanqr.qrcode

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.GlobalHistogramBinarizer
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader

class QRCodeAnalyzer(private val qrCodeDetectorInterface: QRCodeDetector) : ImageAnalysis.Analyzer {
    private val qrCodeReader = QRCodeReader()

    interface QRCodeDetector {
        fun onQrCodeDetected(result: Result)
    }

    override fun analyze(image: ImageProxy) {
        if ((image.format == ImageFormat.YUV_420_888 || image.format == ImageFormat.YUV_422_888 || image.format == ImageFormat.YUV_444_888)
            && image.planes.size == 3
        ) {
            val buffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.capacity())
            buffer[bytes]
            val luminanceSource: LuminanceSource = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
            )
            val binarizer: GlobalHistogramBinarizer = HybridBinarizer(luminanceSource)
            val binaryBitmap = BinaryBitmap(binarizer)
            try {
                qrCodeDetectorInterface.onQrCodeDetected(qrCodeReader.decode(binaryBitmap))
            } catch (e: NotFoundException) {
                e.printStackTrace()
            } catch (e: ChecksumException) {
                e.printStackTrace()
            } catch (e: FormatException) {
                e.printStackTrace()
            }
            image.close()
        }
    }
}