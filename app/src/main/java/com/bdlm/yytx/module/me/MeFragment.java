package com.bdlm.yytx.module.me;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseFragment;
import com.bdlm.yytx.constant.Constant;
import com.bdlm.yytx.entity.UploadPicRespon;
import com.bdlm.yytx.entity.UserInfoBean;
import com.bdlm.yytx.module.login.LoginActivity;
import com.bdlm.yytx.module.map.PositionEntity;
import com.bdlm.yytx.module.webview.LoadHtmlActivity;
import com.orhanobut.logger.Logger;
import com.trsoft.app.lib.BaseApplication;
import com.trsoft.app.lib.inter.CommonCallback;
import com.trsoft.app.lib.utils.DialogUtil;
import com.trsoft.app.lib.utils.ImageLoader;
import com.trsoft.app.lib.utils.MyLog;
import com.trsoft.app.lib.utils.PreferenceUtils;
import com.trsoft.app.lib.utils.validator.ValidatorUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static android.app.Activity.RESULT_OK;


public class MeFragment extends BaseFragment<MePresenter> implements MeContact.IMeView {
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_passport_num)
    TextView tvPassportNum;
    @BindView(R.id.tv_passport_type)
    TextView tvPassportType;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.tv_advance_record)
    TextView tvAdvanceRecord;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_qrcode)
    TextView tvQrcode;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_opinion)
    TextView tvOpinion;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    private MePresenter presenter;
    private File mUploadPicFile;//要上传到服务器的图片地址

    public int ALBUM_REQUEST_CODE = 10;
    public int CAMERA_REQUEST_CODE = 11;
    public int PICTURE_CUT = 12;
    private Uri outputUri;//裁剪之后返回的图片Uri
    private Uri imageCameraUri;//相机拍摄完照片的Uri

    @Override
    protected void createPresenter() {


        sv.smoothScrollTo(0, 20);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean login = isLogin();
        if (!login) {
            return;
        }
        presenter = new MePresenter(this);
        presenter.userInfo();
    }

    @Override
    public void error(String msg) {
        Logger.e(msg);
        DialogUtil.showAlert(mContext, msg, new CommonCallback<Boolean>() {
            @Override
            public void onCallBack(Boolean data) {
                toActivity(LoginActivity.class);
            }
        });

    }

    @Override
    public void getUserInfo(UserInfoBean userInfoBean) {


        if (userInfoBean != null) {
            ValidatorUtil.setTextVal(tvName, userInfoBean.getNick_name());
            ValidatorUtil.setTextVal(tvPassportNum, mContext.getString(R.string.me_passport_num) + userInfoBean.getPassport_num());
            ValidatorUtil.setTextVal(tvPassportType, mContext.getString(R.string.me_passport_type) + userInfoBean.getType_name());
            tvBalance.setText("¥" + userInfoBean.getBalance());
            ImageLoader.displayCircleImage(mContext, userInfoBean.getAvatar(), ivHead);
        }
    }

    @Override
    public void uploadHeadImg(UploadPicRespon respon) {
        if (respon != null && respon.getPath() != null) {
            MyLog.e(respon.getPath() + "要加载的图片");
            ImageLoader.displayCircleImage(mContext, respon.getPath(), ivHead);
        }
    }

    @Override
    public void responseLogout(String msg) {
        DialogUtil.showAlert(mContext, msg, new CommonCallback<Boolean>() {
            @Override
            public void onCallBack(Boolean data) {
                PreferenceUtils.getInstance().clear();
                toActivityNoClear(LoginActivity.class);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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
            Bitmap bitmap = null;
            try {
                InputStream inputStream = mContext.getContentResolver().openInputStream(outputUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();

//                mUploadPicFile=new File(getExternalCacheDir(),"uploadImg.jpg");
//                compressImageToFile(bitmap, mUploadPicFile);
////
////                mUploadPicFile = new File(getImagePath(outputUri, null));
                presenter.uploadHeadImg(mUploadPicFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                MyLog.e("头像文件有异常");
            }
            if (bitmap != null)
                ivHead.setImageBitmap(bitmap);

        }

    }

    @OnClick({R.id.iv_head, R.id.lin_exchange, R.id.tv_bind,R.id.tv_xufei,R.id.tv_buy,R.id.tv_advance_record, R.id.tv_recommend, R.id.tv_qrcode, R.id.tv_order, R.id.tv_about, R.id.tv_opinion, R.id.tv_exit})
    public void onViewClicked(View view) {
        Intent intent = new Intent(mContext, LoadHtmlActivity.class);
        String token = PreferenceUtils.getInstance().getString(Constant.TOKEN);
        switch (view.getId()) {
            case R.id.iv_head:
                String[] itemList = {"拍照", "相册"};
                DialogUtil.showAlertMenuCust(mContext, " ", itemList, 0, new CommonCallback<Integer>() {
                    @Override
                    public void onCallBack(Integer data) {
                        if (data == 0) {
                            openCamera(mContext);
                        } else if (data == 1) {
                            openAlbum(mContext);
                        }
                    }
                });
                break;
            case R.id.lin_exchange:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_exchange));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Index/rechargeBalance?&token=" + token);
                startActivity(intent);
                break;
            case R.id.tv_bind:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_bind_card));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Index/bindPassport?token=" + token);
                startActivity(intent);
                break;
            case R.id.tv_xufei:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_xufei));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Passport/renewUpgrade/?token=" + token);

                startActivity(intent);
                break;
            case R.id.tv_buy:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_buy));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Passport/uplevel/?token=" + token);

                startActivity(intent);
                break;
            case R.id.tv_advance_record:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_addvance_recored));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Advance/showlist?token=" + token);
                startActivity(intent);
                break;
            case R.id.tv_recommend:


//                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_recommend_friends));
//                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Distribution/recommend?token=" + token);
//                startActivity(intent);
//                String[] aa={"aaaa","bbbb"};
//                DialogUtil.showAlertMenuCust(mContext,"aa",aa,0,null);
                showShare(Constant.BASEURL2 + "/Distribution/recommend?token=" + token);
                break;
            case R.id.tv_qrcode:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_qrcode));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Distribution/qrcode?token=" + token);
                startActivity(intent);
                break;
            case R.id.tv_order:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_order));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL2 + "/Ticket/orderList?token=" + token);
                startActivity(intent);
                break;
            case R.id.tv_about:
                intent.putExtra(Constant.BUNDLE_STRING, getString(R.string.me_about_me));
                intent.putExtra(Constant.BUNDLE_URL, Constant.BASEURL + "/User/get_system_config/type/ABOUTUS");
                startActivity(intent);
                break;
            case R.id.tv_opinion:
                startActivity(new Intent(mContext, FeedBackActivity.class));
                break;
            case R.id.tv_exit:
                presenter.requestLogout();
                break;
        }
    }

    private void showShare(String str) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("亿游天下");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(str);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("欢迎您加入亿游天下旅游网");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(str);
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("欢迎您加入亿游天下旅游网");
        // 启动分享GUI
        oks.show(getActivity());
    }

    public void openCamera(Activity context) {
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(mContext.getExternalCacheDir(), "output_image.jpg");
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
            imageCameraUri = FileProvider.getUriForFile(mContext, "com.bdlm.yytx.fileprovider", outputImage);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCameraUri);// 更改系统默认存储路径
        this.startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    public void openAlbum(Activity context) {
        //在这里跳转到手机系统相册里面
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        mUploadPicFile = new File(mContext.getExternalCacheDir(), "crop_image.jpg");
        try {
            if (mUploadPicFile.exists()) {
                mUploadPicFile.delete();
            }
            mUploadPicFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputUri = Uri.fromFile(mUploadPicFile);
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

    public static void compressImageToFile(Bitmap bmp, File file) {
        // 0-100 100为不压缩
        int options = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Cursor cursor = mContext.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
