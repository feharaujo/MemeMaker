package com.fearaujo.data

import com.fearaujo.model.ResultModel

fun getErrorModel(): ResultModel {
    return ResultModel(null, null, null, null, true, false)
}

fun getLoadingModel(): ResultModel {
    return ResultModel(null, null, null, null, false, true)
}