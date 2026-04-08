package com.kinyua_carson.facedetector.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlin.math.min

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: FaceDetectorState,
    onAction: (FaceDetectorActions) -> Unit
) {
    val pickSingleImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            onAction(FaceDetectorActions.OnImagePick(it))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement
            .spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (state.uri != null) {
            Box(
                modifier = Modifier
                    .heightIn(
                        250.dp,
                        400.dp
                    )
            ) {
                AsyncImage(
                    model = state.uri,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    val imageSize = state.imageSize ?: return@Canvas

                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val imageWidth = imageSize.width
                    val imageHeight = imageSize.height

                    val scale =
                        min(canvasWidth / imageWidth, canvasHeight / imageHeight)

                    val offsetX = (canvasWidth - imageWidth * scale) / 2f
                    val offsetY = (canvasHeight - imageHeight * scale) / 2f

                    val faces = state.faces
                    if (faces.isNotEmpty()) {

                        faces.forEach { face ->
                            val bounds = face.boundingBox
                            drawRect(
                                color = Color.Red,
                                topLeft = Offset(
                                    bounds.left * scale + offsetX,
                                    bounds.top * scale + offsetY
                                ),
                                size = Size(
                                    bounds.width() * scale,
                                    bounds.height() * scale
                                ),
                                style = Stroke(width = 2.dp.toPx())
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .border(
                        2.dp,
                        Color.Gray,
                        RoundedCornerShape(26.dp)
                    )
                    .clickable {
                        pickSingleImageLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Click to Select an Image!"
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(FaceDetectorActions.DetectFace) }
        ) {
            Text(
                text = "Detect Face"
            )
        }
    }
}