package com.kinyua_carson.facedetector.presentation

import android.net.Uri
import androidx.compose.ui.unit.IntSize
import com.google.mlkit.vision.face.Face

data class FaceDetectorState(
    val uri: Uri? = null,
    val faces: List<Face> = emptyList(),
    val imageSize: IntSize? = null
)