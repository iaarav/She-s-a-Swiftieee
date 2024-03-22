package com.aarav.shesaswiftieee.di

import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule,
        viewModelModule,
        domainModule
    )
}