package com.kinyua_carson.facedetector.presentation

import android.net.Uri

sealed interface FaceDetectorActions {
    data class OnImagePick(val uri: Uri) : FaceDetectorActions
    data object DetectFace : FaceDetectorActions

    data object OnClearImage : FaceDetectorActions
}