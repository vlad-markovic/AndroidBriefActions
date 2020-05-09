package com.vladmarkovic.briefactionssample

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.vladmarkovic.briefactions.DisplayAction
import com.vladmarkovic.briefactions.DisplayActionObserver
import com.vladmarkovic.briefactions.NavigationAction
import com.vladmarkovic.briefactions.NavigationActionObserver
import com.vladmarkovic.briefactions.ViewModel


/**
 * Created by Vlad Markovic on 09 May 2020
 */
abstract class BaseActivity<VM: ViewModel> : AppCompatActivity() {

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

    protected open fun handleDisplayAction(action: DisplayAction) =
        when (action) {
            is MessageAction -> handleMessageAction(action)
            is HideKeyboard -> hideKeyboard()
            else -> throw IllegalArgumentException("Unhandled display action: $action")
        }

    // Depending how many action categories you have, you might want to split handling into separate functions like this,
    // though in this example it would also make sense to handle these in the previous function and not have this extra function.
    protected open fun handleMessageAction(action: MessageAction) =
        when (action) {
            is MessageAction.ShowMessage -> {
                Snackbar.make(findViewById(android.R.id.content), action.message, Snackbar.LENGTH_SHORT).show()
            }
            else -> {
                throw IllegalArgumentException("Unhandled message display action: $action")
            }
        }

    protected open fun handleNavigationAction(action: NavigationAction) {
        throw IllegalArgumentException("Unhandled navigation action: $action")
    }

    private fun hideKeyboard() {
        currentFocus?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(it.windowToken, 0)
        }
    }



    object HideKeyboard : DisplayAction
}
