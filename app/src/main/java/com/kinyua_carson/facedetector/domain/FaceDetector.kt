package com.kinyua_carson.facedetector.domain

import android.net.Uri
import androidx.compose.ui.unit.IntSize
import com.google.mlkit.vision.face.Face

interface FaceDetector {
    suspend fun detectFace(uri: Uri, inputImageSize: (IntSize) -> Unit): List<Face>
}