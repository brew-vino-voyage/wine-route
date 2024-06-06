package com.brew.wine_route.navigation

import androidx.annotation.StringRes
import com.brew.wine_route.R

enum class WineRouteScreen(@StringRes val title: Int) {
    SIGN_IN(title = R.string.sign_in),
    SIGN_UP(title = R.string.sign_up),
    HOME(title = R.string.home),
    WINERY_MAP(title = R.string.winery_map),
    PROFILE(title = R.string.profile),
    PRODUCT_LISTING(title = R.string.product_listing),
    COMMUNITY(title = R.string.community),
}

