package az.red.presentation.common


sealed class UIEvent(val message: String? = null, val route: Int? = null) {
    class Error(message: String) : UIEvent(message = message)
    class Message(message: String) : UIEvent(message = message)
    class Navigate(route: Int) : UIEvent(route = route)
    class Loading() : UIEvent()
}