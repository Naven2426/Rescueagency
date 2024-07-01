package com.example.rescueagency;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import androidx.camera.core.Preview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TakePhotoActivity extends AppCompatActivity {

    private ImageCapture imageCapture = null;
    private File outputDirectory;
    private ExecutorService cameraExecutor;
    private static final String TAG = "CameraXGFG";
    private static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final int REQUEST_CODE_PERMISSIONS = 20;
    private final List<Uri> images=new ArrayList<>();
    private static final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_take_photo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (allPermissionsGranted()) {
            try {
                startCamera();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
        // Set onClickListener for the capture button
        findViewById(R.id.camera_capture_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        outputDirectory = getOutputDirectory();
        cameraExecutor = Executors.newSingleThreadExecutor();
    }
    private void takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        ImageCapture imageCapture = this.imageCapture;
        if (imageCapture == null) return;

        // Create time-stamped output file to hold the image
        File photoFile = new File(
                outputDirectory,
                new SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        );

        // Create output options object which contains file + metadata
        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        // Set up image capture listener, which is triggered after photo has been taken
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onError(@NonNull ImageCaptureException exc) {
                        Log.e(TAG, "Photo capture failed: " + exc.getMessage(), exc);
                    }

                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults output) {
                        Uri savedUri = Uri.fromFile(photoFile);

                        // Set the saved URI to the ImageView
                        ImageView imageView = findViewById(R.id.iv_capture);
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageURI(savedUri);
                        images.add(savedUri);
                        imageView.setOnClickListener(v->{
                            ImagePreviewActivity.images=images;
                            startActivity(new Intent(TakePhotoActivity.this,ImagePreviewActivity.class));
                        });

                        String msg = "Photo capture succeeded: " + savedUri;
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                        Log.d(TAG, msg);
                    }
                }
        );
    }

    private void startCamera() throws ExecutionException, InterruptedException {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    // Used to bind the lifecycle of cameras to the lifecycle owner
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                    // Preview
                    Preview preview = new Preview.Builder().build();
                    PreviewView previewView=findViewById(R.id.viewFinder);
                    preview.setSurfaceProvider(previewView.createSurfaceProvider());

                    imageCapture = new ImageCapture.Builder().build();

                    // Select back camera as a default
                    CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll();

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(TakePhotoActivity.this, cameraSelector, preview, imageCapture);
                } catch (Exception exc) {
                    Log.e(TAG, "Use case binding failed", exc);
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getBaseContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                try {
                    startCamera();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    // Create a folder inside internal storage
    private File getOutputDirectory() {
        File mediaDir = getExternalMediaDirs().length > 0 ? getExternalMediaDirs()[0] : null;
        if (mediaDir != null) {
            File appDir = new File(mediaDir, getResources().getString(R.string.app_name));
            appDir.mkdirs();
            if (appDir.exists()) {
                return appDir;
            }
        }
        return getFilesDir();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}