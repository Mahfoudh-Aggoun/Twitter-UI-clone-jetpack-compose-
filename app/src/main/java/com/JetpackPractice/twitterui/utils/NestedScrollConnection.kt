package com.JetpackPractice.twitterui.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

class NestedScrollConnectionEnterAlways (var collapsingTopHeight : Float, var offset: Float){


    fun calculateOffset(delta: Float): Offset {
        val oldOffset = offset
        val newOffset = (oldOffset + delta).coerceIn(-collapsingTopHeight, 0f)
        offset = newOffset
        return Offset(0f, newOffset - oldOffset)
    }

    fun onPreScroll(available: Offset, source: NestedScrollSource): Offset =
        when {
            available.y >= 0 -> calculateOffset(available.y)
            offset == -collapsingTopHeight -> Offset.Zero
            else -> calculateOffset(available.y)
        }
    fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource,
    ): Offset =
        when {
            available.y <= 0 -> calculateOffset(available.y)
            offset == 0f -> Offset.Zero
            else -> calculateOffset(available.y)
        }
}