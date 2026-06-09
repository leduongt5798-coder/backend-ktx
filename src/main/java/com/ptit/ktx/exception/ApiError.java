package com.ptit.ktx.exception;

import java.util.Map;

public record ApiError(
    String message,
    Map<String, String> fieldErrors
) {}
