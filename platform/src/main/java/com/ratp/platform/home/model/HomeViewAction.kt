package com.ratp.platform.home.model

sealed class HomeViewAction
object OnStart : HomeViewAction()
object OnLoadMore : HomeViewAction()