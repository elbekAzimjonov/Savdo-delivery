package com.imsoft.savdodelivery.di.components

import com.imsoft.savdodelivery.di.module.NetworkModule
import com.imsoft.savdodelivery.presentation.fragments.*
import dagger.Component

import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
fun inject(homeFragment: HomeFragment)
fun injectMap(mapFragment: MapsFragment)
fun injectMenu(menuFragment: MenuFragment)
fun injectLogin(loginFragment: LoginFragment)
fun injectHistory(historyFragment: HistoryFragment)
fun injectMyOrders(myOrdersFragment: MyOrdersFragment)
fun injectNewOrders(newOrdersFragment: NewOrdersFragment)
}