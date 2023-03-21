package az.red.presentation.di

import az.red.presentation.content.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { LoginViewModel(authUseCase = get()) }
}