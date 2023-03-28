package az.red.domain.model.order.response

import az.red.domain.model.product.Product

data class ProductAvailabilityInfo (
    val productsAvailibilityStatus : Boolean,
    val productsAvailibilityDetails : List<ProductAvailabilityDetails>,
    val unavailableProducts : List<Product>
)