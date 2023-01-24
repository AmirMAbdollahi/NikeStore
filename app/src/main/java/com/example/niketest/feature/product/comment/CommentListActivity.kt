package com.example.niketest.feature.product.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.niketest.R
import com.example.niketest.common.EXTRA_KEY_ID
import com.example.niketest.common.NikeActivity
import com.example.niketest.data.Comment
import com.example.niketest.feature.product.CommentAdapter
import kotlinx.android.synthetic.main.activity_comment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {
    val commentListViewModel: CommentListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_ID
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        commentListViewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }

        commentListViewModel.commentsLiveData.observe(this) {
            val commentAdapter = CommentAdapter(true)
            commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            commentAdapter.comments=it as ArrayList<Comment>
            commentsRv.adapter=commentAdapter
        }

        commentListToolbar.onBackButtonClickListener= View.OnClickListener {
            finish()
        }


    }
}