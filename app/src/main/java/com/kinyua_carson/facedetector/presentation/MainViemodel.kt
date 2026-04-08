package com.kinyua_carson.facedetector.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kinyua_carson.facedetector.domain.FaceDetector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewmodel(
    private val faceDetector: FaceDetector
) : ViewModel() {
    private var _state = MutableStateFlow(FaceDetectorState())
    val state = _state.asStateFlow()

    fun onAction(action: FaceDetectorActions) {
        when (action) {
            is FaceDetectorActions.OnImagePick -> _state.update {
                it.copy(
                    uri = action.uri
                )
            }

            FaceDetectorActions.DetectFace -> {
                val uri = _state.value.uri ?: return
                viewModelScope.launch {
                    val faces = faceDetector.detectFace(uri = uri) { size ->
                        _state.update {
                            it.copy(
                                imageSize = size
                            )
                        }
                    }
                    _state.update {
                        it.copy(
                            faces = faces
                        )
                    }
                }
            }
        }
    }
}
