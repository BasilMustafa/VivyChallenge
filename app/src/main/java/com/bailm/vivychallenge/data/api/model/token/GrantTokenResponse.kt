package com.bailm.vivychallenge.data.api.model.token

data class GrantTokenResponse(
    val access_token: String,
    val expires_in: Int,
    val jti: String,
    val phoneVerified: Boolean,
    val refresh_token: String,
    val scope: String,
    val token_type: String
)