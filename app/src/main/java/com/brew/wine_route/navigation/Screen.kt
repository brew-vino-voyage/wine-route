package com.brew.wine_route.navigation

import androidx.annotation.StringRes
import com.brew.wine_route.R

sealed class Screen(val route: String, @StringRes val title: Int) {
    data object SignIn : Screen("sign_in", R.string.sign_in)
    data object Home : Screen("home", R.string.home)
    data object WineryMap : Screen("winery_map", R.string.winery_map)
    data object Profile : Screen("profile", R.string.profile)
    data object ProductListing : Screen("product_listing", R.string.product_listing)
    data object Community : Screen("community", R.string.community)
    data object ResetPassword : Screen("reset_password", R.string.reset_password)

    companion object {
        fun fromRoute(route: String?): Screen {
            return when (route) {
                SignIn.route -> SignIn
                Home.route -> Home
                WineryMap.route -> WineryMap
                Profile.route -> Profile
                ProductListing.route -> ProductListing
                Community.route -> Community
                ResetPassword.route -> ResetPassword
                else -> Home
            }
        }
    }
}

