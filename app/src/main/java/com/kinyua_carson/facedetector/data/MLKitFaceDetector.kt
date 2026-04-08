package com.kinyua_carson.facedetector.data

import android.content.Context
import android.net.Uri
import androidx.compose.ui.unit.IntSize
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.kinyua_carson.facedetector.domain.FaceDetector
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MLKitFaceDetector(
    private val context: Context
) : FaceDetector {

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .build()

    private val faceDetector = FaceDetection.getClient(options)

    override suspend fun detectFace(uri: Uri, inputImageSize: (IntSize) -> Unit): List<Face> {
        val inputImage = InputImage.fromFilePath(context, uri)

        inputImageSize(IntSize(inputImage.width, inputImage.height))

        return suspendCancellableCoroutine { continuation ->
            faceDetector.process(inputImage)
                .addOnSuccessListener { faces ->
                    continuation.resume(faces)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}
