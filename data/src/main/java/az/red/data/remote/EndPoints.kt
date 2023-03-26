package az.red.data.remote

object EndPoints {
    private const val USER_BASE = "customers"

    // Auth
    const val LOGIN = "$USER_BASE/login"
    const val REGISTER = USER_BASE
    const val CURRENT_USER = "$USER_BASE/customer"
    const val UPDATE_USER = USER_BASE
    const val UPDATE_USER_PASSWORD = "$USER_BASE/password"

    //Cart
    private const val CART_BASE = "cart"
    const val GET_CART = CART_BASE
    const val DECREASE_CART_PRODUCT = "$CART_BASE/product/{productId}"
    const val DELETE_CART = CART_BASE

    //Product
    private const val PRODUCT_BASE = "products"
    const val PRODUCTS_FILTERED = "$PRODUCT_BASE/filter"
    const val PRODUCT = PRODUCT_BASE
    const val PRODUCT_SEARCH = "$PRODUCT_BASE/search"

    //Product Filters
    const val CATEGORY = "catalog"
    const val BRAND = "filters/brand"
    const val SIZE = "sizes"
    const val COLOR = "colors"

    //Orders
    private const val ORDER_BASE = "order"
    const val CREATE_ORDER = ORDER_BASE


}