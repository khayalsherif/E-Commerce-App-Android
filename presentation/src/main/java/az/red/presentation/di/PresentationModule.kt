package az.red.presentation.di

import az.red.presentation.MainActivityViewModel
import az.red.presentation.content.home.HomeViewModel
import az.red.presentation.content.cart.CartViewModel
import az.red.presentation.content.login.LoginViewModel
import az.red.presentation.content.productDetail.ProductDetailViewModel
import az.red.presentation.content.orders.OrdersViewModel
import az.red.presentation.content.orders.dialog.LeaveReviewViewModel
import az.red.presentation.content.profile.ProfileViewModel
import az.red.presentation.content.register.RegisterViewModel
import az.red.presentation.content.reviews.ReviewsViewModel
import az.red.presentation.content.wishList.WishListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { MainActivityViewModel() }
    viewModel { LoginViewModel(authUseCase = get(), sessionManagerUseCase = get()) }
    viewModel { RegisterViewModel(authUseCase = get(), sessionManagerUseCase = get()) }
    viewModel { HomeViewModel(getCategoriesUseCase = get(), getProductsFilteredUseCase = get(), getProductsFilteredPaginatedUseCase =  get(), addToWishListUseCase = get()) }
    viewModel { ProfileViewModel(sessionManagerUseCase = get()) }
    viewModel { ProductDetailViewModel(getProductByIdUseCase = get(), getProductsFilteredUseCase = get(), addToCartUseCase = get(), addToWishListUseCase =  get()) }
    viewModel { ReviewsViewModel(useCase = get()) }
    viewModel { CartViewModel(cartUseCase = get(), deleteCartUseCase = get(), createOrderUseCase = get(), sessionManagerUseCase = get()) }
    viewModel { WishListViewModel(getWishListUseCase = get(), removeWishListItemUseCase = get(), addToCartUseCase = get()) }
    viewModel { OrdersViewModel(getCustomerOrdersUseCase = get()) }
    viewModel { LeaveReviewViewModel(addCommentUseCase = get()) }
}