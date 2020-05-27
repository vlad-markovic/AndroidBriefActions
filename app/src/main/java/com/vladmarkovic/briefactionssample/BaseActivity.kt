package com.vladmarkovic.briefactionssample

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.vladmarkovic.briefactions.DisplayAction
import com.vladmarkovic.briefactions.DisplayActionObserver
import com.vladmarkovic.briefactions.NavigationActionObserver
import com.vladmarkovic.briefactions.ViewModel
import com.vladmarkovic.briefactions.message.MessageDisplay


/**
 * Created by Vlad Markovic on 09 May 2020
 */
abstract class BaseActivity<VM: ViewModel> : AppCompatActivity(), MessageDisplay {

    abstract val layoutRes: Int

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        viewModel.displayActions.observe(this, DisplayActionObserver { action ->
            handleDisplayAction(action)
        })

        viewModel.navigationActions.observe(this, NavigationActionObserver { action ->
            handleNavigationAction(action)
        })
    }

    override fun handleDisplayAction(action: DisplayAction) =
        when (action) {
            is HideKeyboard -> hideKeyboard()
            else -> super.handleDisplayAction(action)
        }

    private fun hideKeyboard() {
        currentFocus?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(it.windowToken, 0)
        }
    }


    object HideKeyboard : DisplayAction
}
