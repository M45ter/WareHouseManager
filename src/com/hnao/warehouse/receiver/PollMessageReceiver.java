package com.hnao.warehouse.receiver;

import com.hnao.warehouse.R;
import com.hnao.warehouse.activity.MainActivity;
import com.hnao.warehouse.domain.BizResult;
import com.hnao.warehouse.domain.User;
import com.hnao.warehouse.task.GetNotReadTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PollMessageReceiver extends BroadcastReceiver {

	public final static String TAG = "PollMessageReceiver";

	private Notification mNotification;

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceive User = " + User.getInstance(context).UserName);
		GetNotReadTask task = new GetNotReadTask(context, false);
		task.setCompletedListener(new GetNotReadTask.TaskCompletedListener() {

			@Override
			public void resultSucceed(BizResult<Integer> result) {
				// TODO Auto-generated method stub
				int unread = result.Data.intValue();
				Log.d(TAG, "unread = " + unread);
				if (unread > 0) {
					mNotification = new Notification();
					mNotification.icon = R.drawable.ic_launcher;
					mNotification.tickerText = "未读消息提醒";
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					mNotification.defaults |= Notification.DEFAULT_SOUND;
					mNotification.when = System.currentTimeMillis();

					Intent intent = new Intent(context, MainActivity.class);

					intent.putExtra("tab_index", 2);

					PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

					// mNotification.contentIntent = pi;

					mNotification.setLatestEventInfo(context, "提醒", "您有" + unread + "条未读消息，点击查看详情！", pi);

					NotificationManager notificationManager = (NotificationManager) context
							.getSystemService(Context.NOTIFICATION_SERVICE);

					notificationManager.notify(123, mNotification);
				}
			}

			@Override
			public void resultFaild(BizResult<Integer> result) {
				// TODO Auto-generated method stub

			}
		});
		task.execute(User.getInstance(context).UserName);
	}

}
