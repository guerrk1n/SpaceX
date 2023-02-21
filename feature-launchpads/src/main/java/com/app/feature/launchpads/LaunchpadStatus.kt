package com.app.feature.launchpads

enum class LaunchpadStatus(val value: String){
    ACTIVE("active"),
    INACTIVE("inactive"),
    RETIRED("retired"),
    UNKNOWN("unknown"),
    LOST("lost"),
    UNDER_CONSTRUCTION("under construction");

}