package com.teamdev.demngayyeu2020.dialog.wave

data class SVGModel(val id: Int, val resId: Int, val rawSvg: Int) {
    override fun equals(other: Any?): Boolean {
        return (other is SVGModel) && other.id == id
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + resId
        result = 31 * result + rawSvg
        return result
    }
}
