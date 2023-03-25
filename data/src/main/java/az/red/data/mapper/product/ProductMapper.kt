package az.red.data.mapper.product

import az.red.data.model.product.ProductListRemoteRequest
import az.red.data.model.product.ProductResponse
import az.red.domain.model.product.Product
import az.red.domain.model.product.ProductListRequest

fun ProductResponse.productResponseToProduct(): Product {
    return Product(
        _id,
        categories,
        color,
        currentPrice,
        date,
        description,
        enabled,
        fabric,
        imageUrls,
        itemNo,
        name,
        previousPrice,
        quantity,
        size
    )
}

fun ProductListRequest.toRemoteRequest():ProductListRemoteRequest{
    return ProductListRemoteRequest(categories = categories)
}