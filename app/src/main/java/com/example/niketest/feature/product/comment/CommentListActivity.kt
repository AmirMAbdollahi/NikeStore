package com.example.niketest.feature.product.comment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.niketest.R
import com.example.niketest.common.EXTRA_KEY_ID
import com.example.niketest.common.NikeActivity
import com.example.niketest.data.Comment
import com.example.niketest.data.TokenContainer
import com.example.niketest.feature.auth.AuthActivity
import com.example.niketest.feature.product.CommentAdapter
import com.example.niketest.feature.product.addComment.AddCommentFragment
import kotlinx.android.synthetic.main.activity_comment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity(), AddCommentFragment.NoticeDialogListener {
    val commentListViewModel: CommentListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_ID
            )
        )
    }

    val commentAdapter = CommentAdapter(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        commentListViewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        commentListViewModel.commentsLiveData.observe(this) {
            commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            commentAdapter.comments = it as ArrayList<Comment>
            commentsRv.adapter = commentAdapter
            commentAdapter.submitList(it)
            //commentsRv.setHasFixedSize(true)
        }

        commentListToolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        insertCommentBtn.setOnClickListener {
            if (TokenContainer.token.isNullOrEmpty()) {
                startActivity(Intent(this, AuthActivity::class.java))
            }else{
                val newFragment = AddCommentFragment().apply {
                    arguments = Bundle().apply {
                        putInt(EXTRA_KEY_ID, intent.extras!!.getInt(EXTRA_KEY_ID))
                    }
                }
                newFragment.show(supportFragmentManager, null)
            }
        }

    }

    override fun onDialogPositiveClick(
        dialog: DialogFragment,
        comment: Comment
    ) {
        commentAdapter.addComment(comment)
        commentAdapter.submitList(commentAdapter.comments)
        commentsRv.smoothScrollToPosition(0)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        TODO("Not yet implemented")
    }
}