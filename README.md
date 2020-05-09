[![Download](https://img.shields.io/github/package-json/v/vlad-markovic/AndroidBriefActions)](https://bintray.com/vlad-markovic/maven/com.vladmarkovic.briefactions/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://source.android.com/setup/start/build-numbers)

```groovy
implementation "com.vladmarkovic.briefactions:briefactions:$briefActionsVersion"
```

## Benefits
**Why use brief-actions library pattern:**
 - Prevent short-term actions reoccurring (i.e. on screen rotation)
 - Easy, readable and intuitive way to call display and navigation actions
 - Code traceability
 - Default `observe` function allows only one observer to subscribe.

On Android, in combination with LiveData, ViewModel is more than what it might be on some other platforms.
It also represents an architectural component for easy state management across view lifecycle events.
When an Activity or Fragment gets recreated, subscribing to observe LiveData gives us the latest value,
thus orientation changes are easy to manage. But sometimes this is not desirable.
Some state delivered by certain action might be only relevant at the time when the action happened.
For instance, navigating to another screen, or displaying a non-persistent message.
In these cases we would not want another screen to open again or another message
to show again when we rotate the device (value is preserved in LiveData).

[BriefAction](https://github.com/vlad-markovic/AndroidBriefActions/blob/master/briefactions/src/main/java/com/vladmarkovic/briefactions/BriefAction.kt)
 type represents these actions and in combination with
[LiveAction](https://github.com/vlad-markovic/AndroidBriefActions/blob/master/briefactions/src/main/java/com/vladmarkovic/briefactions/LiveAction.kt),
helper
[observers](https://github.com/vlad-markovic/AndroidBriefActions/blob/master/briefactions/src/main/java/com/vladmarkovic/briefactions/BriefActionObserver.kt),
[ViewModel](https://github.com/vlad-markovic/AndroidBriefActions/blob/master/briefactions/src/main/java/com/vladmarkovic/briefactions/ViewModel.kt)
 and the rest of the pattern, enables consuming of such actions only once.

The beauty of this pattern is in its easy use and the way it nicely and intuitively reads for initiating display or navigation actions from a ViewModel simply by calling:.
 - `display(ShowMessage(R.string.hello))` or
 - `navigateTo(SettingsScreen)`

or from Activity / Fragment
 - `viewModel.display(ShowMessage(R.string.hello))` or
 - `viewModel.navigateTo(SettingsScreen)`

Another benefit is improving code traceability, which is often a disadvantage of MVVM comparing to MVP.  
Using command+tap on the defined action, we can trace where it is used or handled.

Also, by default `observe` function allows only one observer to subscribe for observing
display or navigation actions. This ensures prevention of opening the same screen or showing of same message
multiple times. If you want you can override this behaviour by passing `true` as the last function param.

## Use

 - Extend the library
[ViewModel](https://github.com/vlad-markovic/AndroidBriefActions/blob/master/briefactions/src/main/java/com/vladmarkovic/briefactions/ViewModel.kt)
when you create your view model.
 - Create your
[BriefAction](https://github.com/vlad-markovic/AndroidBriefActions/blob/master/briefactions/src/main/java/com/vladmarkovic/briefactions/BriefAction.kt)s
(inside VM for scoping or outside).
 - Call them with `display` or `navigateTo` functions defined in the
[ViewModel](https://github.com/vlad-markovic/AndroidBriefActions/blob/master/briefactions/src/main/java/com/vladmarkovic/briefactions/ViewModel.kt).  
   You can call them directly from the view model, or from Activity / Fragment.
 - Observe them inside Activity / Fragment.  
**Important Note:**  
[DisplayAction](https://github.com/vlad-markovic/AndroidBriefActions/blob/master/briefactions/src/main/java/com/vladmarkovic/briefactions/DisplayAction.kt)
 and
[NavigationAction](https://github.com/vlad-markovic/AndroidBriefActions/blob/master/briefactions/src/main/java/com/vladmarkovic/briefactions/NavigationAction.kt)
 are sent using
[LiveAction](https://github.com/vlad-markovic/AndroidBriefActions/blob/master/briefactions/src/main/java/com/vladmarkovic/briefactions/LiveAction.kt)
 which by default **allows for only one observer to subscribe** with the default `observe` function. If you want to enable multiple observers, you will need to explicitly pass `true` for the last `add` param.  
**So:**
    - `viewModel.displayActions.observe(lifecycleOwner: LifecycleOwner, observer: Observer<in A>)` is equivalent to `setObserver`
    - `viewModel.displayActions.observe(lifecycleOwner: LifecycleOwner, observer: Observer<in A>, add = true)` is equivalent to `addObserver`  
      (same for `viewModel.navigationActions`)  
**Just be aware that:**
    - if you `add` several observers and then `set` one after them, it will remove all the previously added ones.
    - if you `set` an observer and then `add` one after, there will be two subscribed.

### Example

```kotlin
class MyViewModel(private val auth: SomeAuth) : ViewModel() {

    //...
    
    fun login(userId: String, password: String) {
        if (userId.isNotBlank() && password.isNotBlank()) {
            signIn(userId, password)
        } else {
            display(ShowMessage("Please fill out all the fields."))
        }
    }
    
    private fun signIn(userId: String, password: String) {
        // helper extension function for launching a coroutine
        launchHandleOnMain {
            suspended = {
                success = auth.signIn(userId, password)
                if (success) navigateTo(DashboardScreen(userId))
                else navigateTo(SignUpScreen)
            },
            onError = { error ->
                display(error.message) 
            }
        }
    }


    sealed class MyNavigationAction : NavigationAction {
        object SignUpScreen : MyNavigationAction()
        data class DashboardScreen(val userId: String) : MyNavigationAction()
    }
}
```

```kotlin
data class ShowMessage(val message: String) : DisplayAction()
```

```kotlin
class MyActivity : AppCompatActivity() {

    private val viewModel by viewModel<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //...
        viewModel.display(ShowMessage("Hello!"))
        
        viewModel.observe(this, DisplayActionObserver { displayAction ->
            when (val data = displayAction) {
                is ShowMessage -> Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
            }
        })
        
        viewModel.observe(this, NavigationActionObserver { navigationAction ->
            when (navigationAction) {
                is MyNavigationAction -> handleMyNavigationAction(navigationAction)
            }
        })
    }
    
    private fun handleMyNavigationAction(navigateTo: MyNavigationAction) =
        when (val data = navigateTo) {
            is SignUpScreen -> startActivity(SignUpActivity.newIntent())
            is DashboardScreen -> startActivity(DashboardActivity.newIntent(data.userId))
        }
}
```
________________________________________________________________________

## License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```