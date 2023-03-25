package az.red.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.red.domain.common.EventBus
import az.red.domain.common.enum.AuthenticationStatus
import az.red.presentation.R
import az.red.presentation.common.UIEvent
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    init {
        viewModelScope.launch {
            EventBus.subscribe<AuthenticationStatus> { authed ->
                if (authed == AuthenticationStatus.UNAUTHORIZED) {
                    triggerUIEvent(
                        UIEvent.Message(
                            "Unauthorized"
                        )
                    )
                    triggerUIEvent(
                        UIEvent.Navigate(
                            R.id.loginFragment
                        )
                    )
                }
            }
        }
    }

    fun triggerUIEvent(event: UIEvent) {
        viewModelScope.launch {
            EventBus.publish(event)
        }
    }

    fun consumeUIEvent(action: (UIEvent) -> Unit) {
        viewModelScope.launch {
            EventBus.subscribe<UIEvent>(action)
        }
    }
}

