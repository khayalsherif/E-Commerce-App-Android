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
    const val ADD_CART_PRODUCT = "$CART_BASE/{productId}"
    const val DELETE_CART = CART_BASE

    //Product
    private const val PRODUCT_BASE = "products"
    const val PRODUCTS_FILTERED = "$PRODUCT_BASE/filter"
    const val PRODUCT = PRODUCT_BASE
    const val PRODUCT_SEARCH = "$PRODUCT_BASE/search"

    // Product Filters
    const val CATEGORY = "catalog"
    const val BRAND = "filters/brand"
    const val SIZE = "sizes"
    const val COLOR = "colors"

    // Reviews
    private const val REVIEW = "comments"
    const val GET_COMMENTS_OF_CUSTOMER = "$REVIEW/product/{productId}"
    
    //Wishlist
    private const val WISHLIST_BASE = "wishlist"
    const val WISHLIST = WISHLIST_BASE
    const val WISHLIST_REMOVE_ITEM = "$WISHLIST_BASE/{productId}"
    const val WISHLIST_ADD = "$WISHLIST_BASE/{productId}"

    //Orders
    private const val ORDER_BASE = "orders"
    const val CREATE_ORDER = ORDER_BASE
    const val GET_CUSTOMER_ORDERS = ORDER_BASE

    //Review
    private const val REVIEW_BASE = "comments"
    const val ADD_COMMENT = REVIEW_BASE

}