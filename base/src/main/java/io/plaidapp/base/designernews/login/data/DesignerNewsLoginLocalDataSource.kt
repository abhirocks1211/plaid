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

package io.plaidapp.base.designernews.login.data

import android.content.SharedPreferences
import androidx.core.content.edit
import io.plaidapp.base.designernews.data.api.model.User

/**
 * Local storage for Designer News login related data, implemented using SharedPreferences
 */
class DesignerNewsLoginLocalDataSource(private val prefs: SharedPreferences) {

    /**
     * Instance of the logged in user. If missing, null is returned
     */
    var user: User?
        get() {
            val userId = prefs.getLong(KEY_USER_ID, 0L)
            val username = prefs.getString(KEY_USER_NAME, null)
            val userAvatar = prefs.getString(KEY_USER_AVATAR, null)
            if (userId == 0L && username == null && userAvatar == null) {
                return null
            }
            return User.Builder()
                    .setId(userId)
                    .setDisplayName(username)
                    .setPortraitUrl(userAvatar)
                    .build()
        }
        set(value) {
            if (value != null) {
                prefs.edit {
                    KEY_USER_ID to value.id
                    KEY_USER_NAME to value.display_name
                    KEY_USER_AVATAR to value.portrait_url
                }
            }
        }

    /**
     * Clear all data related to this Designer News instance: user data and access token
     */
    fun logout() {
        prefs.edit {
            KEY_USER_ID to 0L
            KEY_USER_NAME to null
            KEY_USER_AVATAR to null
        }
    }

    companion object {
        const val DESIGNER_NEWS_PREF = "DESIGNER_NEWS_PREF"
        private const val KEY_USER_ID = "KEY_USER_ID"
        private const val KEY_USER_NAME = "KEY_USER_NAME"
        private const val KEY_USER_AVATAR = "KEY_USER_AVATAR"
    }
}