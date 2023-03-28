package az.red.domain.di

import az.red.domain.usecase.auth.AuthUseCase
import az.red.domain.usecase.cart.DecreaseCartProductUseCase
import az.red.domain.usecase.cart.DeleteCartUseCase
import az.red.domain.usecase.cart.GetCartUseCase
import az.red.domain.usecase.home.GetCategoriesUseCase
import az.red.domain.usecase.home.GetProductsFilteredPaginatedUseCase
import az.red.domain.usecase.home.GetProductsFilteredUseCase
import az.red.domain.usecase.order.CreateOrderUseCase
import az.red.domain.usecase.order.GetCustomerOrdersUseCase
import az.red.domain.usecase.review.AddCommentUseCase
import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        AuthUseCase(repository = get())
    }

    factory {
        GetCartUseCase(cartRepository = get())
    }

    factory {
        DeleteCartUseCase(cartRepository = get())
    }

    factory {
        DecreaseCartProductUseCase(cartRepository = get())
    }

    factory {
        GetCategoriesUseCase(repository = get())
    }
    factory {
        GetProductsFilteredUseCase(repository = get())
    }

    factory {
        CreateOrderUseCase(orderRepository = get())
    }

    factory {
        GetCustomerOrdersUseCase(orderRepository = get())
    }

    factory {
        GetProductsFilteredPaginatedUseCase(repository = get())
    }

    factory {
        AddCommentUseCase(reviewRepository = get())
    }

    factory {
        SessionManagerUseCase(sessionManagerRepository = get())
    }
}