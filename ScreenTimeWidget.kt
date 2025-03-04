package com.example.lab5

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.RemoteViews
import java.util.concurrent.TimeUnit

class ScreenTimeWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }

        startAutoUpdate(context)
    }

    override fun onEnabled(context: Context) {
        startAutoUpdate(context)
    }

    override fun onDisabled(context: Context) {
        stopAutoUpdate(context)
    }

    private var handler: Handler? = null
    private var runnable: Runnable? = null

    private fun startAutoUpdate(context: Context) {
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }

        if (runnable == null) {
            runnable = object : Runnable {
                override fun run() {
                    updateScreenTime(context, 60000)

                    val appWidgetManager = AppWidgetManager.getInstance(context)
                    val appWidgetIds = appWidgetManager.getAppWidgetIds(
                        ComponentName(context, ScreenTimeWidget::class.java)
                    )
                    onUpdate(context, appWidgetManager, appWidgetIds)

                    handler?.postDelayed(this, 60000)
                }
            }

            handler?.postDelayed(runnable!!, 60000)
        }
    }

    private fun stopAutoUpdate(context: Context) {
        handler?.removeCallbacks(runnable!!)
        handler = null
        runnable = null
    }

    private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val screenTime = getScreenTime(context)

        val views = RemoteViews(context.packageName, R.layout.screen_time_widget)
        views.setTextViewText(R.id.tvScreenTime, "Час: ${formatTime(screenTime)}")

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.btnOpenApp, pendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun getScreenTime(context: Context): Long {
        val prefs = context.getSharedPreferences("ScreenTimePrefs", Context.MODE_PRIVATE)
        return prefs.getLong("todayScreenTime", 0)
    }

    private fun updateScreenTime(context: Context, increment: Long) {
        val prefs = context.getSharedPreferences("ScreenTimePrefs", Context.MODE_PRIVATE)
        val newTime = prefs.getLong("todayScreenTime", 0) + increment
        prefs.edit().putLong("todayScreenTime", newTime).apply()
    }

    private fun formatTime(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
        return "$hours год $minutes хв"
    }
}