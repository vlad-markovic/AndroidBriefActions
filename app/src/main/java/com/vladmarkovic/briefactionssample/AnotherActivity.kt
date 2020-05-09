package com.vladmarkovic.briefactionssample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.vladmarkovic.briefactions.NavigationAction
import com.vladmarkovic.briefactions.ViewModel
import com.vladmarkovic.briefactionssample.MainViewModel.AnotherScreen
import com.vladmarkovic.briefactionssample.MessageAction.ShowMessage
import com.vladmarkovic.briefactionssample.MessageAction.ShowToast
import kotlinx.android.synthetic.main.activity_another.*

/**
 * Created by Vlad Markovic on 09 May 2020
 */
class AnotherActivity : BaseActivity<ViewModel>() {

    companion object {
        private const val SCREEN_TITLE_EXTRA = "SCREEN_TITLE"

        fun newIntent(context: Context, screenTitle: String): Intent =
            Intent(context, AnotherActivity::class.java).apply {
                putExtra(SCREEN_TITLE_EXTRA, screenTitle)
            }
    }

    override val layoutRes: Int = R.layout.activity_another

    // In real application view models would be injected
    override val viewModel = ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.getStringExtra(SCREEN_TITLE_EXTRA)?.let { title = it }

        setOnClickListener(openAnotherScreenButton, screenTitleInput) {
            navigateTo(AnotherScreen(screenTitleInput.text.toString()))
        }

        openAnotherMainScreenButton.setOnClickListener {
            navigateTo(MainScreen)
        }

        setOnClickListener(showMessageButton, messageInput) {
            display(ShowMessage(messageInput.text.toString()))
        }

        showToastButton.setOnClickListener {
            display(ShowToast(messageInput.text.toString()))
        }
    }

    private fun display(messageAction: MessageAction) {
        viewModel.display(messageAction)
        viewModel.display(HideKeyboard)
    }

    private fun navigateTo(navigationAction: NavigationAction) {
        viewModel.navigateTo(navigationAction)
        viewModel.display(HideKeyboard)
    }

    override fun handleMessageAction(action: MessageAction) =
        when (action) {
            is ShowToast -> Toast.makeText(this, action.message, Toast.LENGTH_SHORT).show()
            else -> super.handleMessageAction(action)
        }

    /**
     * Using = instead of {} helps in ensuring all are handled.
     */
    override fun handleNavigationAction(action: NavigationAction) =
        when (action) {
            is AnotherScreen -> startActivity(newIntent(this, action.screenTitle))
            is MainScreen -> startActivity(MainActivity.newIntent(this))
            else -> super.handleNavigationAction(action)
        }


    object MainScreen : NavigationAction
}
