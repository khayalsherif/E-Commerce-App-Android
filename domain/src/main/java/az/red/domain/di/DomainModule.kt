package az.red.domain.di

import az.red.domain.usecase.cart.AddToCartUseCase
import az.red.domain.usecase.cart.DecreaseCartProductUseCase
import az.red.domain.usecase.cart.DeleteCartUseCase
import az.red.domain.usecase.cart.GetCartUseCase
import az.red.domain.usecase.home.GetCategoriesUseCase
import az.red.domain.usecase.home.GetProductByIdUseCase
import az.red.domain.usecase.home.GetProductsFilteredPaginatedUseCase
import az.red.domain.usecase.home.GetProductsFilteredUseCase
import az.red.domain.usecase.review.ReviewUseCase
import az.red.domain.usecase.order.CreateOrderUseCase
import az.red.domain.usecase.order.GetCustomerOrdersUseCase
import az.red.domain.usecase.add_review.AddCommentUseCase
import az.red.domain.usecase.auth.LoginUseCase
import az.red.domain.usecase.auth.RegisterUseCase
import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import az.red.domain.usecase.wishList.AddToWishListUseCase
import az.red.domain.usecase.wishList.GetWishListUseCase
import az.red.domain.usecase.wishList.RemoveWishListItemUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        LoginUseCase(repository = get())
    }

    factory {
        RegisterUseCase(repository = get())
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
    
    factory { GetProductByIdUseCase(repository = get()) }

    factory { ReviewUseCase(repository = get()) }

    factory {
        AddCommentUseCase(reviewRepository = get())
    }

    factory {
        GetWishListUseCase(repository = get())
    }
    factory {
        RemoveWishListItemUseCase(repository = get())
    }
    factory {
        AddToWishListUseCase(repository = get())
    }
    factory {
        AddToCartUseCase(repository = get())
    }
    factory {
        SessionManagerUseCase(sessionManagerRepository = get())
    }
}