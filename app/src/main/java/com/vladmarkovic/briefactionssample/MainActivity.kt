package com.vladmarkovic.briefactionssample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.vladmarkovic.briefactions.NavigationAction
import com.vladmarkovic.briefactionssample.MainViewModel.AnotherScreen
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Vlad Markovic on 09 May 2020
 */
class MainActivity : BaseActivity<MainViewModel>() {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    override val layoutRes: Int = R.layout.activity_main

    // In real application view model would be injected
    override val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOnClickListener(openAnotherScreenButton, screenTitleInput) {
            viewModel.openAnotherScreen(screenTitleInput.text.toString())
        }

        setOnClickListener(showMessageButton, messageInput) {
            viewModel.showMessage(messageInput.text.toString())
        }
    }

    override fun handleNavigationAction(action: NavigationAction) {
        when (action) {
            is AnotherScreen -> startActivity(AnotherActivity.newIntent(this, action.screenTitle))
        }
    }
}
