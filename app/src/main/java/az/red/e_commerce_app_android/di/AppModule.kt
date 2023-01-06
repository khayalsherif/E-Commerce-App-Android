package az.red.e_commerce_app_android.di

import az.red.data.di.dataModule
import az.red.domain.di.domainModule
import az.red.presentation.di.presentationModule

val appModule = listOf(
    dataModule,
    domainModule,
    presentationModule
)