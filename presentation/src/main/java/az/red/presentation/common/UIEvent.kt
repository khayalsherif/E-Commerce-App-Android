package az.red.presentation.common


sealed class UIEvent(val message: String? = null, val navAction: Int? = null) {
    class Error(message: String) : UIEvent(message = message)
    class Message(message: String) : UIEvent(message = message)
    class Navigate(navAction: Int) : UIEvent(navAction = navAction)
    class Loading() : UIEvent()
}