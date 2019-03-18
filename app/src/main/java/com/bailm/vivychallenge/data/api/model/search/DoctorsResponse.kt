package com.bailm.vivychallenge.data.api.model.search

data class DoctorsResponse(
        var doctors: List<Doctor>,
        var lastKey: String
)