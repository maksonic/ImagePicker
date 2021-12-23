package com.maksonic.imagepicker.di

import com.maksonic.imagepicker.feature_picker.navigation.Communication
import com.maksonic.imagepicker.navigation.CommunicationImpl
import org.koin.dsl.module

/**
 * @Author: maksonic on 23.12.2021
 */
val navigationModule=  module {
    single <Communication> { CommunicationImpl() }
}