package az.red.domain.di

import az.red.domain.usecase.auth.AuthUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        AuthUseCase(repository = get())
    }
}