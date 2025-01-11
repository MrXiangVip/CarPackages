package com.tianmai.systemlibrary.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemUpdateManager;
import android.os.UpdateEngine;
import android.os.UpdateEngineCallback;
import android.os.UpdateParser;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianmai.systemlibrary.R;
import com.tianmai.systemlibrary.widget.FolderItem;
import com.tianmai.systemlibrary.widget.FolderListAdapter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class SystemUpdateFragment extends Fragment implements  View.OnClickListener {
    RecyclerView mRecyclerView;
    FolderListAdapter folderAdapter ;
    List<FolderItem> mFolderList = new ArrayList<>();
    private String TAG ="Main";
    private TextView progress, currentPath;
    private Button startUpdate;
    private File file;
    private static final String UPDATE_FILE_SUFFIX = ".zip";
    private StorageManager mStorageManager;

    private SystemUpdateManager mUpdateManager;
    private UpdateEngineCallback    callback;
    private PowerManager mPowerManager;
    private static final String REBOOT_REASON = "reboot-ab-update";

    public static final String SYSTEM_UPDATE_SERVICE = "system_update";
    private static final FileFilter UPDATE_FILE_FILTER =
            file -> !file.isHidden() && (file.isDirectory()
                    || file.getName().toLowerCase().endsWith(UPDATE_FILE_SUFFIX));

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.startUpdate){
            try{
                String path = "/sdcard/x9hp_ms-ota-androiduserdebug.linux.customer_c.zip";
                file = new File(path);
                boolean result = mUpdateManager.installUpdate( getContext(), file, callback);
                if( !result ){
                    Log.d(TAG, "文件校验失败");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class TmUpdateEngineCallback extends UpdateEngineCallback {

        @Override
        public void onStatusUpdate(int status, float percent) {// status  当前状态  ,percent 升级的百分比进度
            switch (status) {
                case UpdateEngine.UpdateStatusConstants.UPDATED_NEED_REBOOT:
                    rebootNow();
                    break;
                case UpdateEngine.UpdateStatusConstants.DOWNLOADING:
//                    mProgressBar.setProgress((int) (percent * 100));
                    progress.setText("status "+status +" percent "+percent);
                    break;
                default:
                    // noop
            }
        }

        @Override
        public void onPayloadApplicationComplete(int errcode) {// 错误码 除了 success 都是失败
            progress.setText( "errcode"+errcode);
            switch ( errcode){
                case  UpdateEngine.ErrorCodeConstants.SUCCESS:
                    Log.d(TAG, "升级成功");
                    break;
                default:
                    Log.d(TAG, "升级失败");
                    break;
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate( R.layout.fragment_system_update, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStorageManager =(StorageManager) getContext().getSystemService(Context.STORAGE_SERVICE );
        mUpdateManager = (SystemUpdateManager)getContext().getSystemService(SYSTEM_UPDATE_SERVICE);
        mPowerManager = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);

        mRecyclerView = view.findViewById(R.id.recyclerview);
        progress = view.findViewById(R.id.progress);
        currentPath = view.findViewById(R.id.currentPath);
        startUpdate = view.findViewById( R.id.startUpdate);
        startUpdate.setOnClickListener( this );
        // 构造一些数据
        mFolderList = getMountedVolumes();
        folderAdapter = new FolderListAdapter( mFolderList);
        mRecyclerView.setAdapter(folderAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        callback = new TmUpdateEngineCallback();

    }

    public  List  getMountedVolumes(){
        if( mStorageManager == null){
            return null;
        }
        List<StorageVolume> vols =mStorageManager.getStorageVolumes();
        ArrayList<File>  volums =new ArrayList<>(vols.size());
        for( StorageVolume vol: vols){
            File  path =  vol.getDirectory();
            volums.add( path);
            Log.d(TAG, "path "+path.toString());
        }
        List<FolderItem>  fileList =new ArrayList<>();
        for( File file :volums){
            FolderItem item =new FolderItem( );
            item.setTitle( file.getAbsolutePath().toString() );
            item.setOnItemClickedListener( i->onFileSelected(file));
            fileList.add( item);

        }
        return  fileList;
    }
    private void onFileSelected(File file) {
        Log.d(TAG, "onFileSelected" );
        if( isUpdateFile( file )){
            try{
                   boolean result = mUpdateManager.installUpdate( getContext(), file, callback);
                   if( !result ){
                       Log.d(TAG, "文件校验失败");
                   }
            }catch (Exception e){
                e.printStackTrace();
            }

        }else if( file.isDirectory()){
            getFolderContent( file);
        }else{
            Toast.makeText(getContext(), R.string.invalid_file_type, Toast.LENGTH_LONG).show();

        }
    }
    private void getFolderContent(File folder) {
        currentPath.setText( folder.getAbsolutePath() );

        File[] files = folder.listFiles();//UPDATE_FILE_FILTER
        List<FolderItem>  fileList =new ArrayList<>();
        for( File file :files){
            FolderItem item =new FolderItem( );
            item.setTitle( file.getAbsolutePath().toString() );
            item.setOnItemClickedListener( i->onFileSelected(file));
            fileList.add( item);

        }
        folderAdapter = new FolderListAdapter( fileList);
        mRecyclerView.setAdapter(folderAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }
    private static boolean isUpdateFile(File file) {
        return file.getName().endsWith(UPDATE_FILE_SUFFIX);
    }

    private void rebootNow() {
        Log.i(TAG, "Rebooting Now.");
        mPowerManager.reboot(REBOOT_REASON);
    }
}
