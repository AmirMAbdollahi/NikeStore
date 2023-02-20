package com.example.niketest.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.niketest.R
import com.example.niketest.common.NikeFragment
import com.example.niketest.data.TokenContainer
import com.example.niketest.feature.auth.AuthActivity
import com.example.niketest.feature.favorites.FavoritesProductActivity
import com.example.niketest.feature.order.OrderHistoryActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : NikeFragment() {
    private val viewModel: ProfileViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteBtn.setOnClickListener {
            if(!TokenContainer.token.isNullOrEmpty()){
                startActivity(Intent(requireContext(),FavoritesProductActivity::class.java))
            }else showSnackBar(getString(R.string.cartEmptyStateLoginRequired))
        }
        orderHistoryBtn.setOnClickListener {
            if(!TokenContainer.token.isNullOrEmpty()){
                startActivity(Intent(requireContext(),OrderHistoryActivity::class.java))
            }else showSnackBar(getString(R.string.cartEmptyStateLoginRequired))
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState() {
        if (viewModel.isSignedIn) {
            authBtn.text = getString(R.string.signOut)
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_out, 0)
            userNameTv.text = viewModel.userName
            authBtn.setOnClickListener {
                viewModel.signOut()
                checkAuthState()
            }
        } else {
            authBtn.text = getString(R.string.signIn)
            authBtn.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        AuthActivity::class.java
                    )
                )
            }
            userNameTv.text = getString(R.string.guest_user)
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_in, 0)
        }
    }

}