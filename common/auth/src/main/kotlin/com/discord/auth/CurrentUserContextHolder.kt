package com.discord.auth

class CurrentUserContextHolder {

    companion object {
        val userHolder: ThreadLocal<CurrentUser> = ThreadLocal<CurrentUser>()


        fun set(user: CurrentUser?) {
            userHolder.set(user)
        }

        fun getOptional(): CurrentUser? {
            return userHolder.get()
        }

        fun getRequired(): CurrentUser {
            val user = userHolder.get()
            if (user == null) {
                throw UnauthorizedException("No user context available")
            }
            return user
        }

        fun clear() {
            userHolder.remove()
        }
    }

}