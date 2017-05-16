package com.mao.guli.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mao.guli.Bean.Teacher;
import com.mao.guli.Bean.TeacherInfo;
import com.mao.guli.R;
import com.mao.guli.util.GlobalValue;
import com.mao.guli.util.PhotoUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient okHttpClient=new OkHttpClient();
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;

    private Button selectImage;
    private Button detectButton;
    private ImageView image;
    Bitmap photo;//保存照片

    private ScrollView scrollView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView=(ScrollView)findViewById(R.id.recognized_faces);
        textView=(TextView)findViewById(R.id.information);
        selectImage=(Button)findViewById(R.id.select_image);
        image=(ImageView)findViewById(R.id.image);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoosePicDialog();
            }
        });
        detectButton=(Button)findViewById(R.id.detect);
        //照片上传识别，button在图片点击前不可以点击，图片出来后可以点击
        detectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(MainActivity.this,"启动上传...",Toast.LENGTH_LONG).show();
                setTeacherInfo(photo);
            }
        });
    }
    private void setTeacherInfo(Bitmap bitmap){
        String imagePath = PhotoUtil.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        //通过路径来截取图片的文件名
        String imageName=imagePath.trim();
        imageName=imageName.substring(imageName.lastIndexOf("/")+1);

        File file=new File(imagePath);
        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("imageName",imageName);
        builder.addFormDataPart("mPhoto","a.jpg", RequestBody.create(MediaType.parse("application/octet-stream"),file));

        RequestBody requestBody=builder.build();
        Request.Builder builder1=new Request.Builder();
        Request request=builder1.url(GlobalValue.BASE_URL+"GetTeacherInfo").post(requestBody).build();
        executeRequest(request);
    }
    private void executeRequest(final Request request) {
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //拿到从数据库中请求的数据，在UI线程中更新界面
               final String  result=response.body().string();
                GlobalValue.RESULT=result;
               Log.i("json:",result.substring(1,result.length()-1));
                TeacherInfo teacherInfo=JSON.parseObject(result.substring(1,result.length()-1), TeacherInfo.class);
                final String information="姓名:"+teacherInfo.getTeacherName()+"\n"
                        +"性别:"+teacherInfo.getTeacherGender()+"\n"
                        +"年龄:"+teacherInfo.getTeacherAge()+"\n"
                        +"老师信息:"+teacherInfo.getTeacherInfo();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(information);
                    }
                });

            }
        });
    }

    public ArrayList<Teacher> getTeacherArrayList(){
        ArrayList<Teacher> teachers=new ArrayList<>();
        for(int i=0;i<10;i++){
            teachers.add(new Teacher("http://img5.imgtn.bdimg.com/it/u=1262818165,1999931026&fm=23&gp=0.jpg","田茂","计算机科学与工程"));
        }
        return teachers;
    }
    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 92);
        intent.putExtra("aspectY", 112);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 92);
        intent.putExtra("outputY", 112);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");//Bitmap
            image.setImageBitmap(photo);
        }
    }


}
