/*
 *  Copyright (c)  2018 by Sina Dalvand
 *  All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  All Classes & Files Created for CoolPlayer project
 *  This file created by LOGAN Username
 */

package ir.sinadalvand.player.nora.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.sinadalvand.player.nora.view.Fragments.*

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun bindAlbumsFragment(): AlbumsFragment

    @ContributesAndroidInjector
    abstract fun bindSongsFragment(): SongsFragment

    @ContributesAndroidInjector
    abstract fun bindArtistFragment(): ArtistFragment

    @ContributesAndroidInjector
    abstract fun bindSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun bindcontrolFragment(): QuickControlFragment
}