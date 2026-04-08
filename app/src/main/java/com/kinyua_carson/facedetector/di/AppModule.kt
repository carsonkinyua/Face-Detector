package com.kinyua_carson.facedetector.di

import com.kinyua_carson.facedetector.data.MLKitFaceDetector
import com.kinyua_carson.facedetector.domain.FaceDetector
import com.kinyua_carson.facedetector.presentation.MainViewmodel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::MLKitFaceDetector).bind<FaceDetector>()
    viewModelOf(::MainViewmodel)
}