package school.alt.teacher.main.ui.screen.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.foundation.IcDelete
import school.alt.teacher.main.ui.foundation.IcGallery
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.scan.ScanViewModel
import school.alt.teacher.main.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

var imageCapture: ImageCapture? = ImageCapture.Builder()
    .setTargetRotation(Surface.ROTATION_90)  // 항상 가로 모드로 설정
    .build()

@Composable
fun MealScan(scanViewModel: ScanViewModel, onMoveScreen: (String) -> Unit) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val activity = context as Activity
    if (configuration.orientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }


    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    val hasCameraPermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission.value = granted
        }
    )

    val getImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                scanViewModel.setUri(uri)
                onMoveScreen(ScreenNavigate.MENU_RESULT.name)
            }
        }
    )

    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            factory = {
                val rootView = LayoutInflater.from(context)
                    .inflate(R.layout.fragment_camera, null, false) as ConstraintLayout
                val previewView = rootView.findViewById<PreviewView>(R.id.viewFinder)

                startCamera(previewView, context, lifecycleOwner, cameraExecutor)

                rootView
            },
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .background(color = Color(0xFF202020))
                .fillMaxHeight()
                .width(120.dp)
                .align(Alignment.BottomEnd),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.clickable {
                    onMoveScreen(ScreenNavigate.MENU_ADD_TITLE.name)
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IcDelete(contentDescription = "", tint = white, size = 48.dp)
                Spacer(modifier = Modifier.padding(top = 3.dp))
                Text(
                    text = "돌아가기",
                    fontSize = 11.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    color = white
                )
            }

            Box(modifier = Modifier
                .clickable {
                    val imageCapture = imageCapture

                    val photoFile = getOutputMediaFile()

                    val outputOptions = ImageCapture.OutputFileOptions
                        .Builder(photoFile!!)
                        .build()

                    imageCapture!!.takePicture(
                        outputOptions,
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onError(exc: ImageCaptureException) {
                                Log.e("CameraFragment", "사진 저장 실패: ${exc.message}", exc)
                            }

                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                val savedUri = Uri.fromFile(photoFile)
                                onMoveScreen(ScreenNavigate.MENU_RESULT.name)
                                scanViewModel.setUri(savedUri)
                            }
                        }
                    )
                }
                .wrapContentSize()) {
                Column(
                    modifier = Modifier
                        .background(color = Color(0xFFD9D9D9), shape = CircleShape)
                        .size(70.dp)
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.Center
                ) {

                }
                Column(
                    modifier = Modifier
                        .background(color = white, shape = CircleShape)
                        .size(57.dp)
                        .align(Alignment.Center)
                ) {

                }
            }

            Column(
                modifier = Modifier.clickable {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                    getImage.launch("image/*")
                },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IcGallery(contentDescription = "", tint = white, size = 48.dp)
                Spacer(modifier = Modifier.padding(top = 3.dp))
                Text(
                    text = "불러오기",
                    fontSize = 11.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    color = white
                )
            }
        }
    }
}

private fun getOutputMediaFile(): File? {
    val mediaStorageDir = File(
        Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        ), "LetTeacher"
    )

    if (!mediaStorageDir.exists()) {
        if (!mediaStorageDir.mkdirs()) {
            return null
        }
    }

    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val mediaFile = File(mediaStorageDir.path + File.separator + "IMG_${timeStamp}.jpg")
    return mediaFile
}


private fun startCamera(
    previewView: PreviewView,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    cameraExecutor: ExecutorService
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (e: Exception) {
            Log.e("CameraPreview", "카메라 초기화 중 에러 발생", e)
        }

    }, ContextCompat.getMainExecutor(context))
}


