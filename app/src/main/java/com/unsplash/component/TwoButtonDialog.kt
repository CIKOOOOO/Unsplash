package com.unsplash.component

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.unsplash.R
import com.unsplash.utils.DialogUtil

class TwoButtonDialog : DialogFragment() {
    private val ALERT_MESSAGE = "ALERT_MESSAGE"

    private val BUTTON_POSITIVE_TITLE = "positive_name"

    private val BUTTON_NEGATIVE_TITLE = "negative_name"

    lateinit var tvDialogContent: TextView

    lateinit var btDialogPositive: Button

    lateinit var btDialogNegative: Button

    private var negativeListener: View.OnClickListener? = null

    private var positiveListener: View.OnClickListener? = null

    private var onDismissListener: DialogInterface.OnDismissListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.two_button_dialog, container, false)
        tvDialogContent = view.findViewById(R.id.tv_message)
        btDialogPositive = view.findViewById(R.id.bt_dialog_positive)
        btDialogNegative = view.findViewById(R.id.bt_dialog_negative)
        initView()
//        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.bg_rounded_dialog);
        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun getInstance(
        message: String?, negativeButton: String?,
        negativeListener: View.OnClickListener?,
        positiveButton: String?,
        positiveListener: View.OnClickListener?
    ): TwoButtonDialog {

        val customAlertDialog = TwoButtonDialog()
        if (negativeListener != null) {
            customAlertDialog.setNegativeListener(negativeListener)
        }
        if (positiveListener != null) {
            customAlertDialog.setPositiveListener(positiveListener)
        }
        val bundle = Bundle()
        bundle.putString(BUTTON_POSITIVE_TITLE, positiveButton)
        bundle.putString(ALERT_MESSAGE, message)
        bundle.putString(BUTTON_NEGATIVE_TITLE, negativeButton)
        customAlertDialog.arguments = bundle
        return customAlertDialog
    }

    fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener) {
        this.onDismissListener = onDismissListener
    }

    fun setNegativeListener(negativeListener: View.OnClickListener) {
        this.negativeListener = negativeListener
    }

    fun setPositiveListener(positiveListener: View.OnClickListener) {
        this.positiveListener = positiveListener
    }


    private fun initView() {
        val content = requireArguments().getString(ALERT_MESSAGE)
        val positiveButtonText = requireArguments().getString(BUTTON_POSITIVE_TITLE)
        val negativeButtonText = requireArguments().getString(BUTTON_NEGATIVE_TITLE)
        btDialogNegative.setText(negativeButtonText)
        btDialogNegative.setOnClickListener {
            this.dismiss()
            negativeListener?.onClick(view)
        }
        btDialogPositive.setText(positiveButtonText)
        btDialogPositive.setOnClickListener {
            this.dismiss()
            positiveListener?.onClick(view)
        }
        tvDialogContent.setText(content)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        DialogUtil().setDialogNoTitleWindow(dialog)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener { dialogInterface: DialogInterface?, i: Int, keyEvent: KeyEvent? ->
            if (i == KeyEvent.KEYCODE_BACK && onDismissListener != null) {
                onDismissListener!!.onDismiss(dialogInterface)
                dismiss()
            }
            false
        }
        return dialog
    }
}