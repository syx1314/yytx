package com.bdlm.yytx.module.business;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseLoginActivity;
import com.bdlm.yytx.entity.BusinessBean;
import com.bdlm.yytx.entity.ManagerTypeBean;
import com.bdlm.yytx.entity.UploadPicRespon;
import com.bdlm.yytx.module.map.PositionEntity;
import com.bdlm.yytx.module.map.SearchPoiActivity;
import com.trsoft.app.lib.BaseApplication;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.Validator;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;


/**
 * 商家加盟
 */
public class BusinessJoinActivity extends BaseLoginActivity implements BusinessContact.IBusinessView {

    @BindView(R.id.et_business_name)
    EditText etBusinessName;
    @BindView(R.id.sp_join_type)
    Spinner spJoinType;//加盟类型
    @BindView(R.id.et_business_discount)
    EditText etBusinessDiscount;
    @BindView(R.id.et_business_phone)
    EditText etBusinessPhone;
    @BindView(R.id.tv_business_address)
    TextView tvBusinessAddress;
    @BindView(R.id.et_feature)
    EditText etFeature;
    @BindView(R.id.iv_business_banner)
    ImageView ivBusinessBanner;
    @BindView(R.id.btn_approve)
    Button btnApprove;
    BusinessPersenter persenter;
    private List<ManagerTypeBean> managerTypeBeanList;//经营类型
    private String selManagerTypeId;//选中的经营类型id
    private BusinessBean businessBean = new BusinessBean();
    ;//商家加盟 要上传到服务器的实体类
    private File mUploadPicFile;//要上传到服务器的图片地址

    public int ALBUM_REQUEST_CODE = 10;
    public int CAMERA_REQUEST_CODE = 11;
    public int PICTURE_CUT = 12;
    private Uri outputUri;//裁剪之后返回的图片Uri
    private Uri imageCameraUri;//相机拍摄完照片的Uri

    @Override
    protected int getLayout() {
        return R.layout.activity_business_join;
    }

    @Override
    protected void createPersenter() {
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_status_bar).init();
        persenter = new BusinessPersenter(this);
        persenter.requestManagerType();//请求经营类型

    }

    //选择店铺招牌
    @OnClick(R.id.iv_business_banner)
    public void selImg(View view) {
        String[] itemList = {"拍照", "相册"};
        DialogUtil.showAlertMenuCust(activity, " ", itemList, 0, new CommonCallback<Integer>() {
            @Override
            public void onCallBack(Integer data) {
                if (data == 0) {
                    openCamera(activity);
                } else if (data == 1) {
                    openAlbum(activity);
                }
            }
        });
    }


    @OnClick(R.id.tv_business_address)
    public void selAddress(View view) {
        toActicityResultNoClear(1000, SearchPoiActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            //相机回来的
            if (resultCode == RESULT_OK) {

                cropPhoto(imageCameraUri);//裁剪图片
            }
        } else if (requestCode == ALBUM_REQUEST_CODE && data != null) {
//            //相册回来的
            // 判断手机系统版本号
            if (Build.VERSION.SDK_INT >= 19) {
                // 4.4及以上系统使用这个方法处理图片
                handleImageOnKitKat(data);
            } else {
                // 4.4以下系统使用这个方法处理图片
                handleImageBeforeKitKat(data);
            }
        } else if (requestCode == PICTURE_CUT) {

            persenter.uploadFile("shoplicense", mUploadPicFile);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(outputUri));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (bitmap != null)
                ivBusinessBanner.setImageBitmap(bitmap);
        }

        if (resultCode == 1000) {
            PositionEntity entity = (PositionEntity) data.getSerializableExtra("address");
            if (entity != null) {
                ValidatorUtil.setTextVal(tvBusinessAddress, entity.city + entity.address);
                businessBean.setLatitude(entity.getLatitue() + "");
                businessBean.setLongitude(entity.getLongitude() + "");
            }
        }
    }

    //提交审核
    @OnClick(R.id.btn_approve)
    public void setBtnApprove(View view) {
        String name = etBusinessName.getText().toString();
        String discount = etBusinessDiscount.getText().toString();
        String phone = etBusinessPhone.getText().toString();
        String address = tvBusinessAddress.getText().toString();
        String feature = etFeature.getText().toString();
        if (TextUtils.isEmpty(name)) {
            DialogUtil.showAlert(activity, getString(R.string.business_address_hint), null);
            return;
        }
        if (TextUtils.isEmpty(discount)) {
            DialogUtil.showAlert(activity, getString(R.string.business_discount_hint), null);
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            DialogUtil.showAlert(activity, getString(R.string.business_phone_hint), null);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            DialogUtil.showAlert(activity, getString(R.string.business_address_hint), null);
            return;
        }
        if (TextUtils.isEmpty(feature)) {
            DialogUtil.showAlert(activity, getString(R.string.business_feature_hint), null);
            return;
        }
        businessBean.setAddress(address);
        businessBean.setDescribe(feature);
        businessBean.setDiscount_info(discount);
        businessBean.setMobile(phone);
        businessBean.setShop_name(name);
        businessBean.setManage_type(selManagerTypeId);
        persenter.submit(businessBean);

    }

    @OnItemSelected(R.id.sp_join_type)
    public void onItemSelect(int position) {
        if (managerTypeBeanList != null)
            selManagerTypeId = managerTypeBeanList.get(position).getId();
    }

    @Override
    public void error(String msg) {
    }

    @Override
    public void resultApprove(String msg) {
    }

    @Override
    public void resultManagerType(List<ManagerTypeBean> managerTypeBeanList) {

        if (managerTypeBeanList != null && managerTypeBeanList.size() > 0) {
            this.managerTypeBeanList = managerTypeBeanList;
            ArrayAdapter<ManagerTypeBean> adapter = new ArrayAdapter<ManagerTypeBean>(activity, android.R.layout.simple_list_item_1, managerTypeBeanList);
            spJoinType.setAdapter(adapter);
            spJoinType.setSelection(0);
        }
    }

    @Override
    public void reultUploadFile(UploadPicRespon respon) {
        //上传照片的结果
        MyLog.e("上传照片的结果"+respon.getPath());
    }

    public void openAlbum(Activity context) {
        //在这里跳转到手机系统相册里面
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }

    public void openCamera(Activity context) {
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT < 24) {
            imageCameraUri = Uri.fromFile(outputImage);
        } else {
            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
            //参数二:fileprovider绝对路径 com.dyb.testcamerademo：项目包名
            imageCameraUri = FileProvider.getUriForFile(activity, "com.bdlm.yytx.fileprovider", outputImage);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCameraUri);// 更改系统默认存储路径
        context.startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }


    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
//        mTempImgPath = getImagePath(uri, null);
        cropPhoto(uri);
    }

    // 4.4及以上系统使用这个方法处理图片 相册图片返回的不再是真实的Uri,而是分装过的Uri
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(BaseApplication.mContext, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        cropPhoto(uri);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        File file = new File(getExternalCacheDir(), "crop_image.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mUploadPicFile = new File(getImagePath(uri, null));
        outputUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        //裁剪图片的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("crop", "true");//可裁剪
        // 裁剪后输出图片的尺寸大小
        //intent.putExtra("outputX", 400);
        //intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);//支持缩放
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出图片格式
        intent.putExtra("noFaceDetection", true);//取消人脸识别
        startActivityForResult(intent, PICTURE_CUT);
    }
}
