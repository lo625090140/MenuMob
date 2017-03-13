package com.demo.mob.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

public class BitmapUtil {
	public static Bitmap getBitmapFormUri(String url,Boolean flag)
			throws FileNotFoundException, IOException {
		InputStream input = new URL(url).openStream();
		// 比例压缩
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = computeSampleSize(bitmapOptions, -1,
				1920 * 1440);// 设置缩放比例
		bitmapOptions.inDither = true;// optional
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);

		bitmapOptions.inJustDecodeBounds = true;
		float originalWidth = bitmapOptions.outWidth;
		float originalHeight = bitmapOptions.outHeight;
		float bi = originalHeight / originalWidth;
		int i;
		if (originalWidth < originalHeight) {
			i = 1;
		} else if (originalWidth > originalHeight) {
			i = 2;
		} else if (originalHeight == originalWidth) {
			bi = 10;
			i = 3;
		} else {
			bi = 0.75f;
			i = 0;
		}
		bitmapOptions.inJustDecodeBounds = false;

		input.close();

		return compressImageUrl(bitmap, bi, i,flag);// 再进行质量压缩
	}

	private static Bitmap compressImageUrl(Bitmap bitmap, float bi, int i,Boolean flag) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 32) {
			baos.reset();// 重置baos即清空baos
			// 第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差 ，第三个参数：保存压缩后的数据的流
			bitmap.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中

		Bitmap bitmap1 = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		if (flag){
			if (i == 1) {
				return Bitmap.createScaledBitmap(bitmap1, (int) (150 * bi), 150,
						true);
			} else if (i == 2) {
				return Bitmap.createScaledBitmap(bitmap1, 150, (int) (150 * bi),
						true);
			} else if (i == 3) {
				return Bitmap.createScaledBitmap(bitmap1, (int) (150 - bi),
						(int) (150 - bi), true);
			} else {
				return Bitmap.createScaledBitmap(bitmap1, (int) (150 * bi), 150,
						true);
			}
		}else{
			return bitmap1;
		}
	}

	public static Bitmap getBitmapFormPath(String path)
			throws FileNotFoundException, IOException {

		FileInputStream input = new FileInputStream(path);
		// 比例压缩
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = computeSampleSize(bitmapOptions, -1,
				1920 * 1440);// 设置缩放比例
		bitmapOptions.inDither = true;// optional
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional

		Bitmap bitmap = BitmapFactory.decodeFileDescriptor(input.getFD(), null,
				bitmapOptions);

		bitmapOptions.inJustDecodeBounds = true;
		float originalWidth = bitmapOptions.outWidth;
		float originalHeight = bitmapOptions.outHeight;
		float bi = originalHeight / originalWidth;
		int i;
		if (originalWidth < originalHeight) {
			i = 1;
		} else if (originalWidth > originalHeight) {
			i = 2;
		} else if (originalHeight == originalWidth) {
			bi = 10;
			i = 3;
		} else {
			bi = 0.75f;
			i = 0;
		}
		bitmapOptions.inJustDecodeBounds = false;
		Log.e("dsadad", bi + "||" + originalHeight + "||" + originalWidth);
		input.close();

		return compressImagePath(bitmap, bi, i);// 再进行质量压缩
	}

	private static Bitmap compressImagePath(Bitmap bitmap, float bi, int i) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 85, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中

		Bitmap bitmap1 = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		if (i == 1) {
			return Bitmap.createScaledBitmap(bitmap1, (int) (150 * bi), 150,
					true);
		} else if (i == 2) {
			return Bitmap.createScaledBitmap(bitmap1, 150, (int) (150 * bi),
					true);
		} else if (i == 3) {
			return Bitmap.createScaledBitmap(bitmap1, (int) (150 - bi),
					(int) (150 - bi), true);
		} else {
			return Bitmap.createScaledBitmap(bitmap1, (int) (150 * bi), 150,
					true);
		}
	}

	/**
	 * 为了得到恰当的inSampleSize，Android提供了一种动态计算的方法
	 *
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private static int computeSampleSize(BitmapFactory.Options options,
										 int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
												int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	// 转换方法
	private static byte[] data = null;

	public static byte[] bmpToByteArray(final Bitmap bmp,
										final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static byte[] getHtmlByteArray(final String url) {
		Vector<Thread> threads = new Vector<Thread>();
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				URL htmlUrl = null;
				InputStream inStream = null;
				try {
					htmlUrl = new URL(url);
					URLConnection connection = htmlUrl.openConnection();
					HttpURLConnection httpConnection = (HttpURLConnection) connection;
					int responseCode = httpConnection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						inStream = httpConnection.getInputStream();
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				data = inputStreamToByte(inStream);
			}
		});
		threads.add(thread);
		thread.start();
		for (Thread t : threads) {
			try {
				thread.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	// 从资源文件保存到SDcard下面
	public static boolean BitmapformRes(String path, Resources res, int img) {
		File file = new File(path);
		if (!file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeResource(res, img);
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	public static boolean BitmapSaveSD(String path,Bitmap bitmap) {
		File file = new File(path);
		if (!file.exists()) {
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	public static byte[] inputStreamToByte(InputStream is) {
		try {
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			return imgdata;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	//截屏
	public static Bitmap cutActivity(Activity activity) {
		// 获取windows中最顶层的view
		View view = activity.getWindow().getDecorView();
		view.buildDrawingCache();

		// 获取状态栏高度
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeights = rect.top;
		Display display = activity.getWindowManager().getDefaultDisplay();

		// 获取屏幕宽和高
		int widths = display.getWidth();
		int heights = display.getHeight();

		// 允许当前窗口保存缓存信息
		view.setDrawingCacheEnabled(true);

		// 去掉状态栏
		Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
				statusBarHeights, widths, heights - statusBarHeights);

		// 销毁缓存信息
		view.destroyDrawingCache();

		return bmp;
	}


	//删除文件
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {//判断是不是目录____是就递归删除，不是则直接删除;
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

}
