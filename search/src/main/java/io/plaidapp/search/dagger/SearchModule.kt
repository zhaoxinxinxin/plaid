/*
 * Copyright 2018 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.plaidapp.search.dagger

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.util.ViewPreloadSizeProvider
import dagger.Module
import dagger.Provides
import io.plaidapp.R
import io.plaidapp.core.dagger.DataManagerModule
import io.plaidapp.core.dagger.FilterAdapterModule
import io.plaidapp.core.dagger.OnDataLoadedModule
import io.plaidapp.core.dagger.SharedPreferencesModule
import io.plaidapp.core.dagger.dribbble.DribbbleDataModule
import io.plaidapp.core.data.pocket.PocketUtils
import io.plaidapp.core.dribbble.data.api.model.Shot
import io.plaidapp.search.domain.SearchDataManager
import io.plaidapp.search.ui.SearchActivity
import io.plaidapp.search.ui.SearchViewModel
import io.plaidapp.search.ui.SearchViewModelFactory

@Module(
    includes = [
        DataManagerModule::class,
        DribbbleDataModule::class,
        FilterAdapterModule::class,
        OnDataLoadedModule::class,
        SharedPreferencesModule::class
    ]
)
class SearchModule(private val activity: SearchActivity) {

    @Provides
    fun context(activity: Activity): Context = activity

    @Provides
    fun columns(activity: Activity): Int = activity.resources.getInteger(R.integer.num_columns)

    @Provides
    fun viewPreloadSizeProvider() = ViewPreloadSizeProvider<Shot>()

    @Provides
    fun isPocketInstalled(activity: Activity) = PocketUtils.isPocketInstalled(activity)

    @Provides
    fun searchViewModel(factory: SearchViewModelFactory): SearchViewModel {
        return ViewModelProviders.of(activity, factory).get(SearchViewModel::class.java)
    }

    @Provides
    fun searchViewModelFactory(dataManager: SearchDataManager): SearchViewModelFactory {
        return SearchViewModelFactory(dataManager)
    }
}
