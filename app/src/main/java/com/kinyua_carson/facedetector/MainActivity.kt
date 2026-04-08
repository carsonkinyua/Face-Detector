package com.kinyua_carson.facedetector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kinyua_carson.facedetector.presentation.MainScreen
import com.kinyua_carson.facedetector.presentation.MainViewmodel
import com.kinyua_carson.facedetector.ui.theme.FaceDetectorTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FaceDetectorTheme {
                val viewmodel = koinInject<MainViewmodel>()
                val state by viewmodel.state.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    MainScreen(
                        modifier = Modifier
                            .padding(innerPadding),
                        state = state,
                        onAction = viewmodel::onAction
                    )
                }
            }
        }
    }
}
