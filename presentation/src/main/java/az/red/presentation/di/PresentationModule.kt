package az.red.presentation.di

import az.red.presentation.content.cart.CartViewModel
import az.red.presentation.content.home.HomeViewModel
import az.red.presentation.content.login.LoginViewModel
import az.red.presentation.content.orders.OrdersViewModel
import az.red.presentation.content.profile.ProfileViewModel
import az.red.presentation.content.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { LoginViewModel(authUseCase = get(), sessionManagerUseCase = get()) }
    viewModel { RegisterViewModel(authUseCase = get(), sessionManagerUseCase = get()) }
    viewModel { HomeViewModel(getCategoriesUseCase = get(), getProductsFilteredUseCase = get(), getProductsFilteredPaginatedUseCase =  get()) }
    viewModel { ProfileViewModel(sessionManagerUseCase = get()) }
    viewModel { CartViewModel(cartUseCase = get(), deleteCartUseCase = get(), createOrderUseCase = get(), sessionManagerUseCase = get()) }
    viewModel { OrdersViewModel(getCustomerOrdersUseCase = get()) }
}