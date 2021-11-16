package com.unsplash.component

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import android.view.KeyEvent
import com.unsplash.R
import com.unsplash.utils.DialogUtil





class OneButtonDialog : DialogFragment() {

    private val DIALOG_MESSAGE = "DIALOG_MESSAGE"

    private val POSITIVE_BUTTON_TITLE = "POSITIVE_BUTTON_TITLE"

    lateinit var tvDialogContent: TextView

    lateinit var btDialog: Button

    private var onClickListener: View.OnClickListener? = null

    private var dialogShowListener: OnShowListener? = null

    private var isBackCancel = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.one_button_dialog, container, false)
        tvDialogContent = view.findViewById(R.id.tv_message)
        btDialog = view.findViewById(R.id.bt_dialog)
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
        alertContent: String?,
        alertButtonName: String?
    ): OneButtonDialog {
        val oneButtonDialog = OneButtonDialog()
        val bundle = Bundle()
        bundle.putString(DIALOG_MESSAGE, alertContent)
        bundle.putString(POSITIVE_BUTTON_TITLE, alertButtonName)
        oneButtonDialog.arguments = bundle
        return oneButtonDialog
    }

    fun setOnClickListener(onClickListener: View.OnClickListener?) {
        this.onClickListener = onClickListener
    }


    private fun initView() {
        if (arguments == null) return
        val dialogMessage = requireArguments().getString(DIALOG_MESSAGE)

        tvDialogContent.setText(dialogMessage)
        //        tvDialogContent.setText(Html.fromHtml(dialogMessage)); <- bila ingin baca html tag seperti <b>
        val buttonName = requireArguments().getString(POSITIVE_BUTTON_TITLE)
        btDialog.setText(buttonName)

        btDialog.setOnClickListener {
            this.dismiss()
            onClickListener?.onClick(view)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        DialogUtil().setDialogNoTitleWindow(dialog)
        dialog.setCanceledOnTouchOutside(false)

        dialog.setOnKeyListener { dialogInterface: DialogInterface?, i: Int, keyEvent: KeyEvent? -> i == KeyEvent.KEYCODE_BACK && isBackCancel }
        if (dialogShowListener != null) {
            dialog.setOnShowListener(dialogShowListener)
        }
        return dialog
    }

    fun setOnShowListener(listener: OnShowListener) {
        dialogShowListener = listener
    }

    fun setBackCancel(isBackCancel: Boolean) {
        this.isBackCancel = isBackCancel
    }
}