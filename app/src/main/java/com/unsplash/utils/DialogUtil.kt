package com.unsplash.utils

import androidx.fragment.app.FragmentActivity

import android.app.Activity

import android.R
import android.app.Dialog
import android.view.View
import com.unsplash.component.OneButtonDialog
import java.lang.Exception
import android.graphics.drawable.ColorDrawable

import android.view.Gravity

import android.view.ViewGroup
import android.view.Window
import com.unsplash.component.TwoButtonDialog

import android.content.DialogInterface





class DialogUtil {
    fun showOneButtonDialog(context: Activity, errorMessage: String?) {
        showOneButtonDialog(context, errorMessage, { v -> })
    }

    fun showOneButtonDialog(
        context: Activity, errorMesssage: String?,
        clickListener: View.OnClickListener?
    ) {
        showOneButtonDialog(
            context, errorMesssage, "Ok",
            clickListener
        )
    }

    fun showOneButtonDialog(
        context: Activity, errorMesssage: String?, buttonMessage: String?,
        clickListener: View.OnClickListener?
    ) {
        showOneButtonDialog(
            context, errorMesssage, buttonMessage,
            clickListener, false
        )
    }

    fun showOneButtonDialog(
        context: Activity, errorMesssage: String?, buttonMessage: String?,
        clickListener: View.OnClickListener?, isBackCancel: Boolean
    ) {
        try {
            val dialog: OneButtonDialog =
                OneButtonDialog().getInstance(errorMesssage, buttonMessage)
            dialog.setOnClickListener(clickListener)
            dialog.setBackCancel(isBackCancel)
            (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .add(dialog, "TAG")
                .commitAllowingStateLoss()
        } catch (ignored: Exception) {
        }
    }

    fun setDialogNoTitleWindow(dialog: Dialog): Window? {
        return setDialogNoTitleWindow(
            dialog, ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun setDialogNoTitleWindow(dialog: Dialog, width: Int, height: Int): Window? {
        val window: Window = dialog.getWindow()!!
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setGravity(Gravity.CENTER)
        window.getDecorView().setPadding(0, 0, 0, 0)
        window.setLayout(width, height)
        window.setBackgroundDrawable(ColorDrawable(0))
        return window
    }

    fun showTwoButtonDialog(
        context: Activity,
        message: String?,
        negativeButton: String?,
        negativeListener: View.OnClickListener?,
        positiveButton: String?,
        positiveListener: View.OnClickListener?
    ) {
        showTwoButtonDialog(context, message, negativeButton, negativeListener,
            positiveButton, positiveListener, { dialog: DialogInterface? -> })
    }

    fun showTwoButtonDialog(
        context: Activity,
        message: String?,
        negativeButton: String?,
        negativeListener: View.OnClickListener?,
        positiveButton: String?,
        positiveListener: View.OnClickListener?,
        dismissListener: DialogInterface.OnDismissListener?
    ) {
        try {
            val dialog: TwoButtonDialog = TwoButtonDialog().getInstance(
                message,
                negativeButton, negativeListener, positiveButton, positiveListener
            )
            if (dismissListener != null) {
                dialog.setOnDismissListener(dismissListener)
            }
            (context as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .add(dialog, "TAG")
                .commitAllowingStateLoss()
        } catch (ignored: Exception) {
        }
    }
}