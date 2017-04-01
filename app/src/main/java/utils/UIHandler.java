package utils;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

public class UIHandler {
	private Handler handler;

	private synchronized void prepare() {
		if (handler == null) {
			reset();
		}
	}

	private void reset() {
		handler = new Handler(Looper.getMainLooper(), new Callback() {
			public boolean handleMessage(Message msg) {
				UIHandler.handleMessage(msg);
				return false;
			}
		});
	}


	private static void handleMessage(Message msg) {
		InnerObj io = (InnerObj) msg.obj;
		Message inner = io.msg;
		Callback callback = io.callback;
		if (callback != null) {
			callback.handleMessage(inner);
		}
	}

	private Message getShellMessage(Message msg, Callback callback) {
		Message shell = new Message();
		shell.obj = new InnerObj(msg, callback);
		return shell;
	}

	private Message getShellMessage(int what, Callback callback) {
		Message msg = new Message();
		msg.what = what;
		return getShellMessage(msg, callback);
	}

	public boolean sendMessage(Message msg, Callback callback) {
		prepare();
		return handler.sendMessage(getShellMessage(msg, callback));
	}

	public boolean sendMessageDelayed(Message msg, long delayMillis,
			Callback callback) {
		prepare();
		return handler.sendMessageDelayed(getShellMessage(msg, callback),
				delayMillis);
	}

	public boolean sendMessageAtTime(Message msg, long uptimeMillis,
			Callback callback) {
		prepare();
		return handler.sendMessageAtTime(getShellMessage(msg, callback),
				uptimeMillis);
	}

	public boolean sendMessageAtFrontOfQueue(Message msg,
			Callback callback) {
		prepare();
		return handler
				.sendMessageAtFrontOfQueue(getShellMessage(msg, callback));
	}

	public boolean sendEmptyMessage(int what, Callback callback) {
		prepare();
		return handler.sendMessage(getShellMessage(what, callback));
	}

	public boolean sendEmptyMessageAtTime(int what, long uptimeMillis,
			Callback callback) {
		prepare();
		return handler.sendMessageAtTime(getShellMessage(what, callback),
				uptimeMillis);
	}

	public boolean sendEmptyMessageDelayed(int what, long delayMillis,
			Callback callback) {
		prepare();
		return handler.sendMessageDelayed(getShellMessage(what, callback),
				delayMillis);
	}

	public boolean post(Runnable run) {
		prepare();
		return handler.post(run);
	}

	public boolean postDelayed(Runnable run, long delayMillis) {
		prepare();
		return handler.postDelayed(run, delayMillis);
	}

	private final class InnerObj {
		public final Message msg;
		public final Callback callback;

		public InnerObj(Message msg, Callback callback) {
			this.msg = msg;
			this.callback = callback;
		}
	}
}
