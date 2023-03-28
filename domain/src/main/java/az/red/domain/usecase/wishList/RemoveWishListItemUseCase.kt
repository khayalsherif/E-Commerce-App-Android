package az.red.domain.usecase.wishList

import az.red.domain.common.NetworkResult
import az.red.domain.model.wishlist.WishList
import az.red.domain.repository.WishListRepository
import kotlinx.coroutines.flow.Flow

class RemoveWishListItemUseCase(private val repository: WishListRepository) {
    suspend operator fun invoke(productId: String): Flow<NetworkResult<WishList>> {
        return repository.removeItem(productId)
    }
}