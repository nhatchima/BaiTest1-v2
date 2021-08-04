package com.name.ungdung.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.name.ungdung.Adapter.DanhSachSpAdapter;
import com.name.ungdung.Model.VatPham;
import com.name.ungdung.R;
import com.name.ungdung.Util.CheckConnection;
import com.name.ungdung.ViewModel.LoaiSpViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DanhSachSpActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    DanhSachSpAdapter spAdapter;
    private Context context;
    private LoaiSpViewModel loaiSpViewModel;
    int idkiem = 0;
    private final String videoFileName = "demo.mp4";
    private final int REQUEST_WRITE_PERMISSION = 111;
    private AssetManager assetManager;
    private InputStream inputStream ;
    public static final int MY_PERMISSION_RQUEST_STORAGE = 1;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_sp);

        GetIdLoaiSanPham();
        initView();
        ActionToolBar();
        initInstallTime();
        isPermissionGranted();

        /**
         *  Use ViewModel to call function return data using livedata
         */
        loaiSpViewModel = new ViewModelProvider(this).get(LoaiSpViewModel.class);
        loaiSpViewModel.returnData(idkiem);
        loaiSpViewModel.getListVatPhamLiveData().observe(this, new Observer<List<VatPham>>() {
            @Override
            public void onChanged(List<VatPham> vatPhams) {
                spAdapter = new DanhSachSpAdapter(getApplicationContext(), vatPhams);
                recyclerView.setAdapter(spAdapter);
            }
        });
        /**
         * Check permission
         */
        if(ContextCompat.checkSelfPermission(DanhSachSpActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(DanhSachSpActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(DanhSachSpActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_RQUEST_STORAGE);
            } else{
                ActivityCompat.requestPermissions(DanhSachSpActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_RQUEST_STORAGE);
            }
        }else{
            //do nothing
        }
        /**
         * If permission is grandted. put a param into function to take data from assets
         */

    }
    /**
     * start install-time delivery mode
     */
    private void initInstallTime() {
        try {
            Context context = createPackageContext("com.example.ungdung", 0);
            assetManager = context.getAssets();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
    /**
     * Checks for the Permission is granted or not
     */
    private Boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            } else {
                getInputStreamFromAssetManager(videoFileName);
                return true;
            }
        } else {
            getInputStreamFromAssetManager(videoFileName);
            return true;
        }
        return true;

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getInputStreamFromAssetManager(videoFileName);
        } else if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            CheckConnection.ShowToast(getApplicationContext(), "Please provide the Permission for app to work");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /**
     * This method is used to get Input Stream from the provided filepath
     */
    private void getInputStreamFromAssetManager(String fileName) {
        String[] list = null;
        try {
            inputStream = assetManager.open(fileName);
            list = assetManager.list(""); // returns entire list of assets in directory
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            writeDataToFile(inputStream);
        } else {
            CheckConnection.ShowToast(getApplicationContext(), "InputStream Empty");
        }
    }

    /**
     * This method will create a new hidden temporary file & write datafrom inputstrem to temporary
     * file, so as to play video from file.
     * Note : If you do not want hidden file then just remove ".(dot)" from prefix of fileName
     */
    private void writeDataToFile(InputStream inputStream) {
        String tempFileName = ".tempFile";
        String filePath = getExternalFilesDir("") + File.separator + tempFileName;
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file, false);
            int read;
            byte[] bytes = new byte[8192];
            if (inputStream != null) {
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playVideoInExoplayer(file);
    }
    /**
     * This method will play video from requested file from assets
     *
     * @param file: File
     */
    void playVideoInExoplayer(File file) {
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        Uri uri = Uri.parse(file.getAbsolutePath());
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
    //    private void copyAsset(String fileName, AssetManager assetManager){
//        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Movies";
//        File dir = new File(dirPath);
//        if(!dir.exists()){
//            dir.mkdirs();
//        }
//
//        InputStream in = null;
//        OutputStream out = null;
//        try{
//
//            in = assetManager.open(fileName);
//            File outFile = new File(dirPath,fileName);
//            out = new FileOutputStream(outFile);
//            copyFile(in,out);
//            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
//        } finally {
//            if(in != null){
//                try{
//                    in.close();
//                } catch (IOException e){
//                    e.printStackTrace();
//                }
//            }if(out!= null){
//                try{
//                    out.close();
//                } catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//    private void copyFile(InputStream in, OutputStream out ) throws IOException {
//        byte[]buffer = new byte[1024];
//        int read;
//        while((read =in.read(buffer))!= -1){
//            out.write(buffer,0,read);
//        }
//    }

    /**
     * Get Id to specifed about model data
     */
    private void GetIdLoaiSanPham() {
        idkiem = getIntent().getIntExtra("idloaivatpham", -1);
        Log.d("giatriloaisanpham", idkiem + "");
    }
    /**
     * Toolbar options
     */
    private void ActionToolBar() {
        //truyen vao thanh toolbar
        setSupportActionBar(toolbar);
        // hien thi nut back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_danhsachvatpham);
        recyclerView = (RecyclerView) findViewById(R.id.lvchitietsp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        context = DanhSachSpActivity.this;

    }
}