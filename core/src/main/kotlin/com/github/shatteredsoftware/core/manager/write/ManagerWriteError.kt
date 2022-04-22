package com.github.shatteredsoftware.core.manager.write

class ManagerWriteError(val message: String, val type: ReadErrorType) {
        enum class ReadErrorType {
            InvalidError,
            /** When writing to a local source fails due to an IO Error */
            IOError,
            /** When writing to a remote source that fails to connect */
            ConnectionError,
            /** When trying to access a file fails due to permissions */
            PermissionError
        }
}