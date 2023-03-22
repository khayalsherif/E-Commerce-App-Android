package az.red.domain.di

import az.red.domain.usecase.auth.AuthUseCase
import az.red.domain.usecase.sessionmanager.SessionManagerUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        AuthUseCase(repository = get())
    }
    factory {
        SessionManagerUseCase(sessionManagerRepository = get())
    }
}