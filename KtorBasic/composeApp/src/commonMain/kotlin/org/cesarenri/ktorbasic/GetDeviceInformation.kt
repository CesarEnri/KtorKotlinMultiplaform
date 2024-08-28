package org.cesarenri.ktorbasic

import ktorbasic.composeapp.generated.resources.Res

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class GetDeviceInformation() {
    fun getDeviceInfo():String
}