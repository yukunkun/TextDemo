package com.maneater.picscreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maneater.picscreator.view.ImageObject;
import com.maneater.picscreator.view.ImageWrapper;
import com.maneater.picscreator.view.PicsGestureLayout;
import com.maneater.util.BitmapUtil;
import com.maneater.util.L;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;



/**
 * demo界面
 */
public class DemoActivity extends Activity implements OnClickListener {

	private PicsGestureLayout picsGestureLayout = null;

	public static final int ACTION_DRAWLINE = 1;
	public static final int ACTION_NORMAL = 2;
	
	private View btnAdd;
	private View btnDelete;
	private View btnClearSelected;
	private View btnCopy;
	private View btnReset;
	private View btnUplayout;
	private View btnDownLayout;
	private View btnModify;
	private View btnPaint;
	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_demo);
		mContext = this;
		picsGestureLayout = (PicsGestureLayout) findViewById(R.id.picsGestureLayout);
		btnAdd = findViewById(R.id.btnAdd);
		btnClearSelected = findViewById(R.id.btnClearSelected);
		btnCopy = findViewById(R.id.btnCopy);
		btnReset = findViewById(R.id.btnReset);
		btnUplayout = findViewById(R.id.btnUplayout);
		btnDelete = findViewById(R.id.btnDelete);
		btnDownLayout = findViewById(R.id.btnDownlayout);
		btnModify = findViewById(R.id.btnModify);
		
		btnPaint =findViewById(R.id.btnPaint);
		
		btnPaint.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
		btnClearSelected.setOnClickListener(this);
		btnCopy.setOnClickListener(this);
		btnReset.setOnClickListener(this);
		btnUplayout.setOnClickListener(this);
		btnDownLayout.setOnClickListener(this);
		btnModify.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDelete:
			deleteImage(picsGestureLayout.getSelectedChildImage());
			break;
		case R.id.btnAdd:
			showAddDialog();
			break;
		case R.id.btnModify:
			showModifyDialog(picsGestureLayout.getSelectedChildImage());
			break;
		case R.id.btnClearSelected:
			picsGestureLayout.clearSelectedImage();
			break;
		case R.id.btnCopy:
			ImageWrapper imWrapper = picsGestureLayout.getSelectedChildImage();
			if (imWrapper == null) {
				L.i("所选子对象为空,无法复制.");
			} else {
				imWrapper = imWrapper.copyToNew();
				imWrapper.onDrag(20, 20);
				imWrapper.setChecked(true);
				picsGestureLayout.clearSelectedImage();
				picsGestureLayout.addChildImage(imWrapper);
				 
			}
			break;
		case R.id.btnReset:
			
			picsGestureLayout.clearChildImage();
			
			break;
		case R.id.btnUplayout:
			if (picsGestureLayout.getSelectedChildImage() == null) {
				L.i("所选子对象为空,无法上移图层.");
			} else {
				picsGestureLayout.upSelectedImageLayout();
			}
			break;
		case R.id.btnDownlayout:
			if (picsGestureLayout.getSelectedChildImage() == null) {
				L.i("所选子对象为空,无法下移图层.");
			} else {
				picsGestureLayout.downSelectedImageLayout();
			}
			break;
			
			
		case R.id.btnPaint:
			L.i("画笔功能.");
//			if (picsGestureLayout.getSelectedChildImage() != null) {
//				picsGestureLayout.clearChildImage();
//			}
			//picsGestureLayout.setActionDrawline(true,0,false);
			//picsGestureLayout.action=ACTION_DRAWLINE;
			
			break;
			
		default:
			break;
		}
	}

	private void deleteImage(ImageWrapper target) {
		if (target == null) {
			return;
		} else {
			picsGestureLayout.deleteChildImage(target);
		}
	}

	private Dialog addDialog;
	private Dialog modifyDialog;
	private List<ImageObject> imageObjectList = new ArrayList<ImageObject>();
	{
		imageObjectList.add(new ImageObject(R.drawable.color_1,
				"color_1_pic.png"));
		imageObjectList.add(new ImageObject(R.drawable.color_2,
				"color_2_pic.png"));
		imageObjectList.add(new ImageObject(R.drawable.color_3,
				"color_3_pic.png"));
		imageObjectList.add(new ImageObject(R.drawable.color_4,
				"color_4_pic.png"));
		imageObjectList.add(new ImageObject(R.drawable.color_5,
				"color_5_pic.png"));
		imageObjectList.add(new ImageObject(R.drawable.color_6,
				"color_6_pic.png"));
	}

	private void showModifyDialog(ImageWrapper imageWrapper) {
		if (imageWrapper == null) {
			
			return;
		}

		if (modifyDialog == null) {
			modifyDialog = new Dialog(this, R.style.dialog_list);
			LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(
					getApplicationContext()).inflate(
					R.layout.view_item_image_layout, null);
			for (final ImageObject imageObj : imageObjectList) {
				ImageView imageView = (ImageView) LayoutInflater.from(
						getApplicationContext()).inflate(
						R.layout.view_item_image, null);
				imageView.setImageResource(imageObj.getShotRes());
				imageView.setTag(imageObj);
				linearLayout.addView(imageView);
				imageView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						modifyDialog.dismiss();
						picsGestureLayout.getSelectedChildImage().setBitmap(
								load(imageObj.getImageName()));
						picsGestureLayout.invalidate();
					}
				});
			}
			modifyDialog.setContentView(linearLayout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
		modifyDialog.show();
	}

	private void showAddDialog() {
		if (addDialog == null) {
			addDialog = new Dialog(this, R.style.dialog_list);
			LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(
					getApplicationContext()).inflate(
					R.layout.view_item_image_layout, null);
			for (final ImageObject imageObj : imageObjectList) {
			ImageView imageView = (ImageView) LayoutInflater.from(
			getApplicationContext()).inflate(R.layout.view_item_image, null);
			
				imageView.setImageResource(imageObj.getShotRes());
				imageView.setTag(imageObj);
				linearLayout.addView(imageView);
				imageView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						addDialog.dismiss();
						L.i("imageObj对象name:"+imageObj.getImageName());
						Bitmap bitmap = load(imageObj.getImageName());//从集合里取出
						if (bitmap == null)
							return;
						//初始化时,图片坐标(布局正中间)
					    ImageWrapper imageWrapper = new ImageWrapper(
								picsGestureLayout.getWidth() / 2,
								picsGestureLayout.getHeight() / 2, bitmap);
						picsGestureLayout.clearSelectedImage();
						imageWrapper.setChecked(true);//是否选中
						picsGestureLayout.addChildImage(imageWrapper);
					}
				});
			}
			addDialog.setContentView(linearLayout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
		addDialog.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		for (String key : cachedBitmaps.keySet()) {
			cachedBitmaps.get(key).recycle();
		}
	}
	private Map<String, Bitmap> cachedBitmaps = new HashMap<String, Bitmap>();
	
	private Bitmap load(String fileName) {
		Bitmap bitmap = cachedBitmaps.get(fileName);
		if (bitmap == null) {
			bitmap = BitmapUtil.loadFromAsset(getApplicationContext(), fileName);
			cachedBitmaps.put(fileName, bitmap);
		}
		return bitmap;
	}

}
