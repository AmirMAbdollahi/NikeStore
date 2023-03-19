package com.example.niketest.feature.product.addComment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.niketest.R
import com.example.niketest.common.EXTRA_KEY_ID
import com.example.niketest.common.NikeCompletableObserver
import com.example.niketest.data.Comment
import com.example.niketest.feature.product.ProductDetailActivity
import com.example.niketest.feature.product.comment.CommentListActivity
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCommentFragment : DialogFragment() {

    val viewModel: AddCommentViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()
    var parentActivity: FragmentActivity? = null
    internal lateinit var listener: NoticeDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        parentActivity = activity
        if (parentActivity is CommentListActivity) {
            try {
                // Instantiate the NoticeDialogListener so we can send events to the host
                listener = context as NoticeDialogListener
            } catch (e: ClassCastException) {
                // The activity doesn't implement the interface, throw exception
                throw ClassCastException(
                    (context.toString() +
                            " must implement NoticeDialogListener")
                )
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val productId: Int? = arguments?.getInt(EXTRA_KEY_ID)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val v: View = inflater.inflate(R.layout.item_add_comment, null)
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.addComment,
                    DialogInterface.OnClickListener { dialog, id ->
                        val titleEt = v.findViewById<TextInputEditText>(R.id.titleEt)
                        val contentEt = v.findViewById<TextInputEditText>(R.id.contentEt)
                        if (parentActivity is CommentListActivity) {
                            viewModel.addCommentSingle(
                                titleEt!!.text.toString(),
                                contentEt!!.text.toString(),
                                productId!!
                            )
                            Thread.sleep(500)
                            viewModel.commentLiveData.observe(this) { it ->
                                listener.onDialogPositiveClick(this, it)
                            }
                        } else if (parentActivity is ProductDetailActivity) {
                            viewModel.addCommentCompletable(
                                titleEt!!.text.toString(),
                                contentEt!!.text.toString(),
                                productId!!
                            ).subscribeOn(Schedulers.io()).observeOn(
                                AndroidSchedulers.mainThread()
                            )
                                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                                    override fun onComplete() {

                                    }

                                })
                        }

                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()!!.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }


    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, comment: Comment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

}